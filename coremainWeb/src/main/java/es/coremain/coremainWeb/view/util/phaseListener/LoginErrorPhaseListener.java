package es.coremain.coremainWeb.view.util.phaseListener;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
 

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

import org.springframework.security.web.WebAttributes;

import es.coremain.coremainWeb.view.util.FacesUtils;

/**
 * Este PhaseListener se necesita para que los mensajes que devuelve Spring-Security puedan mostrarse en una pagina de facelets.
 * 
 * Ademas es aprovechado para mostrar los mensajes en el idioma definido en la aplicacion.
 * 
 * @author mcs
 *
 */
public class LoginErrorPhaseListener implements PhaseListener
{
    private static final long serialVersionUID = -1216620620302322995L;
 
    @Override
    public void beforePhase(final PhaseEvent arg0)
    {     	
    	Exception e = (Exception) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(WebAttributes.AUTHENTICATION_EXCEPTION);
    	
    	if (e != null) {
    	
	        if (e instanceof AccountExpiredException) {
	        	
	            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(WebAttributes.AUTHENTICATION_EXCEPTION, null);                        
	            FacesUtils.setErrorMessage(null, "login_errorCuentaCaducada", null);
	        }
	        else if (e instanceof BadCredentialsException) {
	        	
	        	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(WebAttributes.AUTHENTICATION_EXCEPTION, null);                        
	        	FacesUtils.setErrorMessage(null, "login_errorUsuario", null);
	        }
	        else if (e instanceof CredentialsExpiredException) {
	        	
	        	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(WebAttributes.AUTHENTICATION_EXCEPTION, null);                        
	        	FacesUtils.setErrorMessage(null, "login_errorCredencialesCaducadas", null);
	        }
	        else if (e instanceof DisabledException) {
	        	
	        	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(WebAttributes.AUTHENTICATION_EXCEPTION, null);                        
	        	FacesUtils.setErrorMessage(null, "login_errorUsuarioBaja", null);
	        }
	        else if (e instanceof LockedException) {
	        	
	        	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(WebAttributes.AUTHENTICATION_EXCEPTION, null);                        
	        	FacesUtils.setErrorMessage(null, "login_errorUsuarioBloqueado", null);
	        }
        
    	}        
        
    }
 
    @Override
    public void afterPhase(final PhaseEvent arg0)
    {}
 
    @Override
    public PhaseId getPhaseId()
    {
        return PhaseId.RENDER_RESPONSE;
    }
 
}