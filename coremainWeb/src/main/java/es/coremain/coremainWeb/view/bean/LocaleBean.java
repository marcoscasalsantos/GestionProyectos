package es.coremain.coremainWeb.view.bean;

import java.io.Serializable;
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;



import es.coremain.coremainWeb.view.util.FacesUtils;

@ManagedBean(name = "localeBean")
@SessionScoped
public class LocaleBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Devuelve el timeZone por defecto del sistema, (necesario para convertir
	 * correctamente la fecha en los componentes JSF)
	 */

	public TimeZone getTimeZone() {
		return TimeZone.getDefault();
	}	
	
	public Locale getLocale() {
		return FacesUtils.getLocale();
	}

	public String doSetLocaleLanguageCastellanoAction() {
		
		Locale locale = new Locale("es", "");
		
		FacesUtils.setLocale(locale);
		

		return null;
	}
	
	public String doSetLocaleLanguageGallegoAction() {
		
		Locale locale = new Locale("gl", "");
		
		FacesUtils.setLocale(locale);
		

		return null;
	}	
	
}
