package server.main.java.gift;





import java.io.File;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import server.main.auth.OAuth2SecurityConfiguration;
import server.main.java.gift.PhotoRepository;
import server.main.java.gift.ResourcesMapper;

import com.fasterxml.jackson.databind.ObjectMapper;



//Tell Spring to automatically inject any dependencies that are marked in
//our classes with @Autowired
@EnableAutoConfiguration
//Tell Spring to turn on WebMVC (e.g., it should enable the DispatcherServlet
//so that requests can be routed to our Controllers)
@EnableWebMvc
//Tell Spring that this object represents a Configuration for the
//application
@Configuration
@EnableJpaRepositories(basePackageClasses = PhotoRepository.class)
//Tell Spring to go and scan our controller package (and all sub packages) to
//find any Controllers or other components that are part of our applciation.
//Any class in this package that is annotated with @Controller is going to be
//automatically discovered and connected to the DispatcherServlet.
@ComponentScan
//@Import(OAuth2SecurityConfiguration.class)
public class Application extends RepositoryRestMvcConfiguration {
	
	// Tell Spring to launch our app!
	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	
	}
	
	// We are overriding the bean that RepositoryRestMvcConfiguration 
	// is using to convert our objects into JSON so that we can control
	// the format. The Spring dependency injection will inject our instance
	// of ObjectMapper in all of the spring data rest classes that rely
	// on the ObjectMapper. This is an example of how Spring dependency
	// injection allows us to easily configure dependencies in code that
	// we don't have easy control over otherwise.
	@Override
	public ObjectMapper halObjectMapper(){
		return new ResourcesMapper();
	}/*
	 @Bean
	    EmbeddedServletContainerCustomizer containerCustomizer(
	            @Value("${keystore.file:src/main/private/keystore}") String keystoreFile,
	            @Value("${keystore.pass:changeit}") final String keystorePass) throws Exception {

			// If you were going to reuse this class in another
			// application, this is one of the key sections that you
			// would want to change
	    	
	        final String absoluteKeystoreFile = new File(keystoreFile).getAbsolutePath();
	        
	        return new EmbeddedServletContainerCustomizer ( ) {

				@Override
				public void customize(ConfigurableEmbeddedServletContainer container) {
			            TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
			            tomcat.addConnectorCustomizers(
			                    new TomcatConnectorCustomizer() {
									@Override
									public void customize(Connector connector) {
										connector.setPort(8443);
				                        connector.setSecure(true);
				                        connector.setScheme("https");

				                        Http11NioProtocol proto = (Http11NioProtocol) connector.getProtocolHandler();
				                        proto.setSSLEnabled(true);
				                        proto.setKeystoreFile(absoluteKeystoreFile);
				                        proto.setKeystorePass(keystorePass);
				                        proto.setKeystoreType("JKS");
				                        proto.setKeyAlias("tomcat");
									}
			                    });
			    
				}
	        };
	       
	    }
	     */
}
