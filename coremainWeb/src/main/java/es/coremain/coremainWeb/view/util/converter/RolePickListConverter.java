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

import es.coremain.coremainUserService.dto.RoleDto;

@FacesConverter("rolePickListConverter")
public class RolePickListConverter implements Converter {

	private static Logger log = LoggerFactory.getLogger(RolePickListConverter.class);

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		log.trace("String value: {}", value);

		return getObjectFromUIPickListComponent(component,value);
	}

	public String getAsString(FacesContext context, UIComponent component, Object object) {
		String string;
		
		log.trace("Object value: {}", object);
		
		if(object == null){
			string="";
		}else{
			try{
				string = String.valueOf(((RoleDto) object).getId());
			}catch(ClassCastException cce){
				throw new ConverterException();
			}
		}
		return string;
	}

	@SuppressWarnings("unchecked")
	private RoleDto getObjectFromUIPickListComponent(UIComponent component, String value) {
		final DualListModel<RoleDto> dualList;
		try{
			dualList = (DualListModel<RoleDto>) ((PickList)component).getValue();
			RoleDto role = getObjectFromList(dualList.getSource(), Long.valueOf(value));
			if(role == null){
				role = getObjectFromList(dualList.getTarget(), Long.valueOf(value));
			}			
			return role;
			
		}catch(ClassCastException cce){
			throw new ConverterException();
		}catch(NumberFormatException nfe){
			throw new ConverterException();
		}
	}

	private RoleDto getObjectFromList(final List<?> list, final Long id) {
		for(final Object object:list){
			final RoleDto role = (RoleDto) object;
			if(role.getId().equals(id)){
				return role;
			}
		}
		return null;
	}
}
