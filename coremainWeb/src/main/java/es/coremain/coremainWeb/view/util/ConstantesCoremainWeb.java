package es.coremain.coremainWeb.view.util;

public class ConstantesCoremainWeb{

	/**
	 * Constantes propias de la aplicacion coremainWeb, ej. RESOURCE_BUNDLE_NAME,
	 * las constantes susceptibles de ser utilizadas por la capa servicios estaran
	 * en la clase constantesBaseService.java 
	 * 
	 * Nota: Usar los mismos tipos de datos en las constantes que la variable
	 * correspondiente para facilitar la comparacion sobre todo al usarse en
	 * JSPs, En caso de que la variable sea un objeto de un tipo primitivo hacer
	 * la constante del tipo primitivo para evitar errores comparando los datos
	 * en las JSPs.
	 * 
	 * Ojo: Antes de anhadir nuevas constantes comprobar que no existe ya en las constantes de
	 * NoitebusCore
	 */

	public static final String RESOURCE_BUNDLE_NAME = "MessagesResources";
	
	public static final String INFORME_INCIDENCIAS_FILE_NAME = "informe_incidencias.pdf";
	public static final String CERTIFICADO_FACTURACION_SERVICIOS_ANHO_INICIAL_FILE_NAME = "facturacion_servicios.pdf";
	public static final String CERTIFICADO_FACTURACION_PUBLICIDAD_ANHO_INICIAL_FILE_NAME = "facturacion_publicidad.pdf";
	public static final String CERTIFICADO_FACTURACION_SERVICIOS_ANHO_FINAL_FILE_NAME = "facturacion_servicios.pdf";
	public static final String CERTIFICADO_FACTURACION_PUBLICIDAD_ANHO_FINAL_FILE_NAME = "facturacion_publicidad.pdf";
}
