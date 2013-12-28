package es.coremain.coremainWeb.view.util.exceptionHandler;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;


/**
 * 
 * @author mcs
 * @see http://jugojava.blogspot.com/2010/09/jsf-2-exception-handling.html
 *
 */
public class CoremainWebExceptionHandlerFactory extends ExceptionHandlerFactory {
 
private ExceptionHandlerFactory parent;

	//this injection handles jsf 
	public CoremainWebExceptionHandlerFactory(ExceptionHandlerFactory parent) {
		this.parent = parent;
	}

	//create CoremainWeb own ExceptionHandler 
	@Override
	public ExceptionHandler getExceptionHandler() {
		ExceptionHandler result = parent.getExceptionHandler();
		result = new CoremainWebExceptionHandler(result);
	 
		return result;
	}
}
