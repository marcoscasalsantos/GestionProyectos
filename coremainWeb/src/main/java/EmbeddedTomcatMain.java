import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.ProtectionDomain;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

/**
 * @author rbeede
 * @see http://www.rodneybeede.com/Embedding_Tomcat_7_in_a_war_file.html
 *
 * Supports Tomcat 7
 * 
 */
public class EmbeddedTomcatMain {
	public static void main(final String[] args) throws ServletException, LifecycleException, URISyntaxException, IOException {
		final Tomcat tomcat = new Tomcat();
		
		
		// Extract the pre-packaged SSL keys
		//final File[] certificateStores = extractCertificateStores();
		
		
		
		tomcat.setPort(8080);  // Default connector
		
		//addConnector(82, false, tomcat, null);
		//addConnector(443, true, tomcat, certificateStores);
		
        
		// Load the war (assumes this class is in root of war file)
		final ProtectionDomain domain = EmbeddedTomcatMain.class.getProtectionDomain();
		final URL location = domain.getCodeSource().getLocation();

		System.out.println("Using webapp at " + location.toExternalForm());

		tomcat.addWebapp("/coremainWeb", location.toURI().getPath());
		tomcat.start();
		tomcat.getServer().await();
	}
	
	/*
	 a�ade las conectores para conexiones seguras
	private static void addConnector(final int port, final boolean https, final Tomcat tomcat, final File[] certificateStores) throws IOException {
		final Connector connector = new Connector();
		connector.setScheme((https) ? "https" : "http");
		connector.setPort(port);
		connector.setProperty("maxPostSize", "0");  // unlimited
		connector.setProperty("xpoweredBy", "true");
		if(https) {
			connector.setSecure(true);
			connector.setProperty("SSLEnabled","true");
			connector.setProperty("keyPass", "123456");
			connector.setProperty("keystoreFile", certificateStores[0].getCanonicalPath());
			connector.setProperty("keystorePass", "123456");
			connector.setProperty("truststoreFile", certificateStores[1].getCanonicalPath());
			connector.setProperty("truststorePass", "123456");
		}
		tomcat.getService().addConnector(connector);
	}
	*/
	
	/**
	 * @param tomcat
	 * @return 0 = keystoreFile, 1 = truststoreFile
	 * @throws IOException 
	 */
	/*
	private static File[] extractCertificateStores() throws IOException {
		
		//FIXME Not secure in creation because Java 6 and before provide no platform independent API for setting file permissions & ownership.  Java 7 will.
		final File keystoreFile = File.createTempFile("ETM", null);
		final File truststoreFile = File.createTempFile("ETM", null);
		
		keystoreFile.deleteOnExit();
		truststoreFile.deleteOnExit();
		
		final FileOutputStream fosKeystore = new FileOutputStream(keystoreFile);
		final FileOutputStream fosTruststore = new FileOutputStream(truststoreFile);
		
		// Assumes .jks files were in root of project resources which causes them to be under /WEB-INF/classes/ inside the war
		IOUtils.copy(EmbeddedTomcatMain.class.getResourceAsStream("/WEB-INF/classes/keyStore.jks"), fosKeystore);
		IOUtils.copy(EmbeddedTomcatMain.class.getResourceAsStream("/WEB-INF/classes/trustStore.jks"), fosTruststore);
		
		fosKeystore.close();
		fosTruststore.close();
		
		
		return new File[] {keystoreFile, truststoreFile};
	}
	*/
}