package es.coremain.coremainWeb.view.util.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.model.DualListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.coremain.coremainUserService.dto.UserDto;
import es.coremain.coremainUserService.service.UserService;
import es.coremain.coremainWeb.view.util.FacesUtils;
import es.uvigo.esei.pfc.eseiPfcProjectService.service.ProjectService;

@FacesConverter("userSelectOneMenuConverter") 
public class UserSelectOneMenuConverter implements Converter{
	
	private static Logger log = LoggerFactory.getLogger(UserPickListConverter.class);

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		log.trace("String value: {}", value);
		
		return getObjectFromSelectOneMenuComponent(component,value);
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
	
	
	private UserDto getObjectFromSelectOneMenuComponent(UIComponent component,String value){
		
		UserDto user = null;
		
		try{
			
			if (!value.trim().equals("") && !(value == null)){
				
				UserService userService = FacesUtils.getService("userService", UserService.class);	
				//user = userService.getUser(Long.valueOf(value));
				
				//user = (UserDto)((SelectOneMenu)component).getValue();
				
				
				//return user;
				
				user = userService.getUser(Long.parseLong(value));
				
			}
				
			return user;
		
		}catch(ClassCastException cce){
			throw new ConverterException();
		}catch(NumberFormatException nfe){
			throw new ConverterException();
		}catch(NullPointerException ex){
			return user;
		}
		
	}
	
}