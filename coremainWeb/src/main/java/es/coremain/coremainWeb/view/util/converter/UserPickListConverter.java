package es.coremain.coremainWeb.view.util.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.coremain.coremainUserService.dto.UserDto;

@FacesConverter("userPickListConverter") 
public class UserPickListConverter implements Converter{
	
	private static Logger log = LoggerFactory.getLogger(UserPickListConverter.class);

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		log.trace("String value: {}", value);
		
		return getObjectFromUIPickListComponent(component,value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) throws ConverterException {
	    String string;
		
		log.trace("Object value: {}", object);
		
		if(object == null){
			string="";
		}else{
			try{
				string = String.valueOf(((UserDto) object).getUserId());
			}catch(ClassCastException cce){
				throw new ConverterException();
			}
		}
		return string;
	}
	
	
	@SuppressWarnings("unchecked")
	private UserDto getObjectFromUIPickListComponent(UIComponent component, String value){
		
		final DualListModel<UserDto> dualList;
		
		try{		
		      dualList = (DualListModel<UserDto>) ((PickList)component).getValue();
		      UserDto user = getObjectFromList(dualList.getSource(),Long.valueOf(value));
		      if (user == null){
		    	  user = getObjectFromList(dualList.getTarget(),Long.valueOf(value));
		      }
		      return user;
		      
		}catch(ClassCastException cce){
			throw new ConverterException();
		}catch(NumberFormatException nfe){
			throw new ConverterException();
		}
		
	}
	
	
	private UserDto getObjectFromList(final List<?> list, final Long id) {
		for(final Object object:list){
			final UserDto user = (UserDto) object;
			if(user.getUserId().equals(id)){
				return user;
			}
		}
		return null;
	}
	
	
}