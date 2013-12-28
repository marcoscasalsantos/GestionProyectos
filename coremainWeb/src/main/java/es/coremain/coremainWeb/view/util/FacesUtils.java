package es.coremain.coremainWeb.view.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.validator.ValidatorException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import es.coremain.coremainWeb.view.util.ConstantesCoremainWeb;

public class FacesUtils {
	
	private static String resourceBundleName = ConstantesCoremainWeb.RESOURCE_BUNDLE_NAME;

	/**
	 * Obtiene una referencia de un resource bundle a partir del nombre del
	 * mismo del contexto de faces en funcion del locale que tenga configurado
	 * en ese momento el contexto de faces.
	 * 
	 * @param bundleName
	 * @return
	 */
	public static ResourceBundle getResourceBundle(String bundleName) {

		ResourceBundle bundle = ResourceBundle.getBundle(bundleName, getLocale());

		return bundle;
	}	
	

	public static void setMessage(UIComponent component, Severity severity, String summaryMessageKey, String detailMessageKey, 
			Object... summaryMessageParameters) {
		
		FacesContext facesContext = getFacesContext();
		
		String clientId = null;
		if (component != null)
			clientId = component.getClientId(facesContext);
				
		ResourceBundle resourceBundle = getResourceBundle(resourceBundleName);

		String summaryMessageText = null;
		if (summaryMessageKey != null) {
			
			summaryMessageText = resourceBundle.getString(summaryMessageKey);
			
			// Sustituye cada parameters en el pattern del mensaje, los parameters pueden ser 0, 1 o mas y con tipos de distintos. 
			// En el pattern se indicara la posiscion de cada parameter y el tipo ej.
			// String result = MessageFormat.format("At {1,time} on {1,date}, there was {2} on planet " + "{0,number,integer}.", 7, new Date(), "a disturbance in the Force");
			// result sera igual a "At 12:30 PM on Jul 3, 2053, there was a disturbance in the Force on planet 7."	 	    		    			
			
			if (summaryMessageParameters != null)
				summaryMessageText = MessageFormat.format(summaryMessageText, summaryMessageParameters);			
		}
		
		String detailMessageText = null;
		if (detailMessageKey != null)
			detailMessageText = resourceBundle.getString(detailMessageKey);				
		
		facesContext.addMessage(clientId, new FacesMessage(severity, summaryMessageText, detailMessageText));
	}	
	
	
	public static void setInfoMessage(UIComponent component, String summaryMessageKey, String detailMessageKey, Object... summaryMessageParameters){
		setMessage(component, FacesMessage.SEVERITY_INFO, summaryMessageKey, detailMessageKey, summaryMessageParameters);
	}
	
	

	public static void setWarnMessage(UIComponent component, String summaryMessageKey, String detailMessageKey, Object... summaryMessageParameters){
		setMessage(component, FacesMessage.SEVERITY_WARN, summaryMessageKey, detailMessageKey, summaryMessageParameters);
	}

	public static void setErrorMessage(UIComponent component, String summaryMessageKey, String detailMessageKey, Object... summaryMessageParameters){
		setMessage(component, FacesMessage.SEVERITY_ERROR, summaryMessageKey, detailMessageKey, summaryMessageParameters);
	}
	

	public static void setSummaryMessages(Collection<String> summaryMessageKeys, Severity severity) {
		
		if (summaryMessageKeys != null && !summaryMessageKeys.isEmpty()) {
			for (String summaryMessageKey : summaryMessageKeys) {
				setMessage(null, severity, summaryMessageKey, null);
			}
		}		
	}
	
	public static void setSummaryErrorMessages(Collection<String> summaryMessageKeys) {
		setSummaryMessages(summaryMessageKeys, FacesMessage.SEVERITY_ERROR);
	}

		
	/**
	 * 
	 * @param uii User Interface input faces component
	 * @param severity Severity of the message
	 * @param summaryMessageKey 
	 * @param detailMessageKey
	 * @param summaryMessageParameters
	 */
	public static void invalidateInput(UIInput uii, Severity severity, String summaryMessageKey, String detailMessageKey, 
			Object... summaryMessageParameters) {

		uii.setValid(false);

		ResourceBundle resourceBundle = getResourceBundle(resourceBundleName);
		
		String summaryMessageText = null;
		if (summaryMessageKey != null) {
			
			summaryMessageText = resourceBundle.getString(summaryMessageKey);
			
			// Sustituye cada parameters en el pattern del mensaje, los parameters pueden ser 0, 1 o mas y con tipos de distintos. 
			// En el pattern se indicara la posiscion de cada parameter y el tipo ej.
			// String result = MessageFormat.format("At {1,time} on {1,date}, there was {2} on planet " + "{0,number,integer}.", 7, new Date(), "a disturbance in the Force");
			// result sera igual a "At 12:30 PM on Jul 3, 2053, there was a disturbance in the Force on planet 7."	 	    		    			
			
			if (summaryMessageParameters != null)
				summaryMessageText = MessageFormat.format(summaryMessageText, summaryMessageParameters);			
		}
		
		String detailMessageText = null;
		if (detailMessageKey != null)
			detailMessageText = resourceBundle.getString(detailMessageKey);	
		
				
		FacesMessage facesMessage = new FacesMessage(severity, summaryMessageText, detailMessageText);
				
		throw new ValidatorException(facesMessage);
	}
    
	public static String getResource(String messageKey) {
		
		ResourceBundle resourceBundle = getResourceBundle(resourceBundleName);
		
		return resourceBundle.getString(messageKey);		
	}
	
	
	/**
	 * 
	 * @return El contexto de faces
	 */
	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
	/**
	 * 
	 * @return La request
	 */
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = null;

		try {
			FacesContext facesContext = getFacesContext();
			if (facesContext.getExternalContext().getRequest() != null) {
				request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
			}
		} catch (Exception e) {

		}
		return request;
	}	
	
	/**
	 * 
	 * @return La response
	 */
	private static HttpServletResponse getResponse() {
		HttpServletResponse response = null;

		FacesContext facesContext = getFacesContext();
		if (facesContext.getExternalContext().getResponse() != null) {
			response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		}		

		return response;
	}	
	
	/**
	 * 
	 * @return La session 
	 */
	public static HttpSession getSession() {
		HttpSession session = null;

		if (getRequest() != null) {
			session = getRequest().getSession();
		}

		return session;
	}	
	
	public static void invalidateSession() {
		try {
			HttpSession session = getSession();
			session.invalidate();
			
			SecurityContextHolder.getContext().setAuthentication(null);

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}	
	
	



	/**
	 * 
	 * @param cookieName
	 * @return Obtiene la cookie con el nombre indicado
	 */
	public static Cookie getCookie(String cookieName) {
		Cookie[] cookieArray = null;
		try {
			cookieArray = getRequest().getCookies();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			System.out.println("cookie?");
		}

		Cookie cookie = null;
		if (cookieArray != null) {

			boolean seguir = true;
			int i = 0;
			while ((i < cookieArray.length) && seguir) {
				if (cookieArray[i].getName().equals(cookieName)) {
					seguir = false;
					cookie = cookieArray[i];
				}
				i = i + 1;
			}
		}

		return cookie;
	}

	/**
	 * Establece la cookie pasada
	 * @param cookie
	 */
	public static void setCookie(Cookie cookie) {
		getResponse().addCookie(cookie);
	}

	public static String getRequestParameter(String name) {
		HttpServletRequest request = getRequest();
		if (request != null) {
			return request.getParameter(name);
		}
		return null;
	}

	public static void setRequestAttribute(String name, Object valor) {
		HttpServletRequest request = getRequest();
		if (request != null) {
			request.setAttribute(name, valor);
		}
	}

	public static Object getRequestAttribute(String name) {
		HttpServletRequest request = getRequest();
		if (request != null) {
			return request.getAttribute(name);
		}
		return null;
	}

	
	public static String getRealPath(String webContextPath) {
		return getSession().getServletContext().getRealPath(webContextPath);
	}	
	
	public static void setResponseFile(String nombreFichero, byte[] contenido) throws Exception {

		HttpServletResponse response = null;

		try {
			response = getResponse();

			response.setContentType("APPLICATION/OCTET-STREAM");

			response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreFichero + "\"");

			InputStream inputStream = new ByteArrayInputStream(contenido);

			int i;

			ServletOutputStream out = response.getOutputStream();
			while ((i = inputStream.read()) != -1) {
				out.write(i);
			}
			inputStream.close();
			out.close();

			FacesContext.getCurrentInstance().responseComplete();

		} catch (Exception e) {
			if (response != null) {
				response.resetBuffer();
				response.reset();
			}
			throw new Exception(e);
		}

	}



	/**
	 * Obtiene la instancia de un Managed Bean 
	 * 
	 * @param instanceName
	 * @return la instancia del Managed Bean
	 */
	public static <T> T getManagedBean(String beanName, Class<T> beanClass) {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		Application application = facesContext.getApplication();

		return beanClass.cast(application.evaluateExpressionGet(facesContext, "#{" + beanName + "}", beanClass));
	}

	/**
	 * 
	 * @param idComponentString
	 *            Cadena de idsComponentes separadas por caracter ":"
	 * @return
	 */
	public static UIComponent getComponent(String idComponentString) {

		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		String[] idsComponents = idComponentString.split(":");

		UIComponent componente = null;
		if (idsComponents.length != 0) {
			componente = viewRoot.findComponent(idsComponents[0]);

			// recorre el resto de componentes quedandose con el ultimo
			// componente de la cadena idComponentString
			for (int i = 1; i < idsComponents.length; i++) {
				componente = componente.findComponent(idsComponents[i]);
			}
		}

		return componente;
	}	


	public static String getFromProperties(InputStream archivoProperties, String key) {
		// Carga las propiedades del archivo properties
		Properties properties = new Properties();

		String value = null;

		try {
			properties.load(archivoProperties);
			value = properties.getProperty(key);
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return value;

	}

	public static TimeZone getTimeZone() {
		
		//TODO: probar si return  TimeZone.getDefault() devuelve lo mismo		
		return TimeZone.getTimeZone("Europe/Madrid");
	}

	public static Locale getLocale() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return facesContext.getViewRoot().getLocale();
	}

	public static void setLocale(Locale locale) {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		facesContext.getViewRoot().setLocale(locale);
	}

	
	/*
	 * Si se usan las anotaciones de JSF @ManagedBean frente a las de Spring, se gana facilidad en la configuracion
	 * de JSF, pero se pierde el autowire de Spring para cablear los servicios utilizados en los managedBeans.
	 * 
	 * Para cablear los servicios de la capa de negocio en los managedBeans se usa esta clase que obtiene el 
	 * webApplicationContext con el cual se puede obtener cualquier bean de spring, por ejemplo los servicios 
	 * requeridos en el managedBean.
	 * 
	 */	
	public static <T> T getService(String serviceName, Class<T> beanClass) {
		
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getSession().getServletContext());
		
		return webApplicationContext.getBean(serviceName, beanClass);
	}	

	
	public static MethodExpression createMethodExpression(String expression, Class<?> expectedReturnType, Class<?>[] expectedParamTypes) throws javax.el.ELException, java.lang.NullPointerException {
	
		ELContext elContext = getFacesContext().getELContext();
	
		MethodExpression methodExpression = FacesUtils.getFacesContext().getApplication().getExpressionFactory().createMethodExpression(
				elContext, expression, expectedReturnType, expectedParamTypes);
		
		return methodExpression;

	}
	
	/**
	 * Obtiene el flashScope para trabajar con un bean con ambito viewScope entre dos vistas
	 * 
	 * @param 
	 * @return el flashScope
	 */
	
    public static Flash flashScope (){
    	
	   return (FacesContext.getCurrentInstance().getExternalContext().getFlash());
    
    }
    
}
