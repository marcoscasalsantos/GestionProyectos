package es.coremain.coremainWeb.view.util.converter;

import java.math.BigDecimal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("eurosConverter")
public class EurosConverter implements Converter {

	
	public Object getAsObject(FacesContext arg0, UIComponent component, String value) throws ConverterException {

		BigDecimal euros = null;
		BigDecimal cantidad = null;
		try {

			if (value != null && !value.equals("")) {

				euros = new BigDecimal(value);

				// Se toma el criterio de redondear a la baja
				// Es necesario usar un nuevo Objeto BigDecimal ya que es un objeto inmutable
				cantidad = euros.setScale(2, BigDecimal.ROUND_DOWN);
			}

			return cantidad;
		} catch (Exception exception) {
			throw new ConverterException(exception);
		}
	}

	public String getAsString(FacesContext arg0, UIComponent component, Object value) throws ConverterException {

		String cadenaEuros = "";

		try {
			if (value instanceof BigDecimal) {

				// Se toma el criterio de redondear a la baja
				// Es necesario usar un nuevo Objeto BigDecimal ya que es un objeto inmutable				
				BigDecimal cantidad = ((BigDecimal) value).setScale(2, BigDecimal.ROUND_DOWN); 
																								
				cadenaEuros = cantidad.toString();
			}

			return cadenaEuros;
		} catch (Exception exception) {
			throw new ConverterException(exception);
		}
	}

}
