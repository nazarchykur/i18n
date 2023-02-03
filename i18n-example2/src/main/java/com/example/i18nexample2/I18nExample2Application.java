package com.example.i18nexample2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class I18nExample2Application {

	public static void main(String[] args) {
		SpringApplication.run(I18nExample2Application.class, args);
	}

}

/*
	Internationalization or I18N is a process that makes your application adaptable to different languages and regions 
	without engineering changes on the source code. You can display messages, currencies, date, time etc. according to 
	the specific region or language, likewise you can say internationalization is a readiness of localization.
	
	
	LocaleConfig
						@Configuration
						public class LocaleConfig {
							@Bean
							public LocaleResolver localeResolver() {
								final AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
								resolver.setDefaultLocale(Locale.US);
								return resolver;
							}
						
							@Bean
							public MessageSource messageSource() {
								final ResourceBundleMessageSource source = new ResourceBundleMessageSource();
								source.setBasename("i18n/message");
								return source;
							}
						}

		To determine which locale is currently being used by the application, we need to add a LocaleResolver bean.
	
		The LocaleResolver interface has implementations that determine the current locale based on the session, cookies, 
		he Accept-Language header, or a fixed value.
		
		In our example, we have used the AcceptHeaderLocaleResolver to retrieve locale based on the Accept-Language passed 
		as a parameter.


		The ResourceBundleMessageSource bean in Line 20 is used here to resolve text messages from properties file 
		based on different locales.
		
		You will now require a controller class to accept the incoming request and return a response to the user.
		
				@GetMapping("/message")
				public String getLocaleMessage(
						@RequestHeader(name = "Accept-Language", required = false) final Locale locale,
						@RequestParam(name = "username", defaultValue = "Mr Incognito", required = false) final String username) {
					log.info("Returning greetings for locale = {}", locale);
					if (ObjectUtils.isEmpty(locale)) {
						
					}
					return messageSource.getMessage(greetings, new Object[]{username}, locale);
				}
    
    
    	Here, we have just defined a simple get method which accepts Accept-Language as a RequestHeader parameter and 
    	username as the query parameter.
    	
    	
    	
Defining the Message Sources

	Spring Boot application by default takes the message sources from src/main/resources folder under the classpath. 
	The default locale message file name should be message.properties and files for each locale should name as 
	messages_XX.properties. The “XX” represents the locale code.
	
	The message properties are in key pair values. If any properties are not found on the locale, the application uses 
	the default property from messages.properties file.
	
				server.port=8091
				placeholder.greetings=welcome.message
				
				
	resources/i18n/message.properties
				welcome.message=Greetings {0}
				
	resources/i18n/message_fr.properties
				welcome.message=Bonjour {0}
				
	resources/i18n/message_de.properties
				welcome.message=Hallo {0}
				
				
				
 */



/*

    Spring Boot Internationalization — i18n
    
    Configurations
    
    One of the basic requirements for any web application is to be able to support multiple languages and have 
    localized messages. 
    To achieve this by default, Spring Boot looks for the presence of a messages.properties resource bundle at the 
    root of the classpath.


    These files contain a set of key-value rows for each text that needed to be supported in multiple languages just 
    like the following example:
    
            message.properties
                    welcome=Hello! this is a test message!
                    lang.change=Change the language
                    lang.eng=English
                    lang.fr=French
                
           message_fr.properties
                    welcome=Bonjour! Ceci est un message test!
                    lang.change=Changer la langue
                    lang.eng=English
                    lang.fr=French

    
    Of course, there are several attributes like basename that you can use to change the resource bundle configuration 
    using spring.messages namespace in application.properties or application.yaml.
    
                spring.messages.basename=messages, i18n.list
                spring.messages.fallback-to-system-locale=false





    Message Retrieve
    
        They are a couple of ways to get the proper localized message using the key provided in the resource bundle files. 
        You can explicitly mention the locale type and use ResourceBundleMessageSource or you can utilize a locale 
        LocaleResolver which is more efficient and functional.
        
    
        ResourceBundleMessageSource
            The easiest way to get the related messages from the resource bundle is using the ResourceBundleMessageSource.
                    @Component
                    public class ApplicationConfiguration {
                    
                        @Autowired
                        ResourceBundleMessageSource messageSource;
                    
                        @PostConstruct
                        public void init(){
                            String welcome = messageSource.getMessage("welcome", null, Locale.FRANCE));
                        }
                    }
                    
            The above code will result in setting the french version of the welcome key message in the welcome string.

            
       LocaleResolver
            Another way to determine which locale is currently being used is to use LocaleResolver. 
            This interface has different implementations that determine the current locale based on the session, cookies, 
            the Accept-Language header, or a fixed value.
            
            
            > FixedLocaleResolver: 
                This implementation is used mostly for debugging purposes and you will always have a single fixed 
                language set up in the project application properties file
                        @Bean
                        public LocaleResolver localeResolver() {
                           FixedLocaleResolver lr = new FixedLocaleResolver();
                           lr.setDefaultLocale(Locale.US);
                           return lr;
                        }
                        
            > AcceptHeaderLocaleResolver: 
                For web applications, one of the options is to use the “accept-language” HTTP header on the request. 
                This implementation will take the value from the HTTP status header and apply it.
                        @Bean
                        public LocaleResolver localeResolver() {
                           AcceptHeaderLocaleResolver lr = new AcceptHeaderLocaleResolver();
                           lr.setDefaultLocale(Locale.US);
                           return lr;
                        }
            
            > SessionLocaleResolver: 
                Another approach for web applications is to use Session to get the local for the user visiting the 
                application. In this implementation, the value will store inside the session and will be available 
                during the session’s lifetime.
                        @Bean
                        public LocaleResolver localeResolver() {
                            SessionLocaleResolver lr = new SessionLocaleResolver();
                            lr.setDefaultLocale(Locale.US);
                            return lr;
                        }

            > CookieLocaleResolver: 
                Cookies are also another option that has an implementation available. They are stored on the user’s 
                machine and as long as browser cookies aren’t cleared by the user, once resolved the resolved locale 
                data will last even between sessions.

                        @Bean
                        public LocaleResolver localeResolver() {
                            CookieLocaleResolver lr = new CookieLocaleResolver();
                            lr.setDefaultLocale(Locale.US);
                            return lr;
                        }

        Note: 
            For SessionLocaleResolver and CookieLocaleResolver we need to have an interceptor to get the parameter 
            sent from the user for the preferred language and store it in session or cookie. 
            Thanks to spring this has been done already don and you can use LocaleChangeInterceptor and just pass 
            the name of the property to set the locale.

                    @Bean
                    public WebMvcConfigurer configurer(){
                         return new WebMvcConfigurerAdapter() {
                         @Override
                         public void addInterceptors (InterceptorRegistry registry) {
                            LocaleChangeInterceptor l = new LocaleChangeInterceptor();
                            l.setParamName("lang");
                            registry.addInterceptor(l);
                           }
                        };
                    }

                    ========================================================================                
                    
                    @Configuration
                    public class LocaleConfig implements WebMvcConfigurer {
                        
                         // without this bean also working, because  
                         // By default, Spring Boot looks for the presence of a messages resource bundle at the root of the classpath.
                         // This bean is responsible for resolving texts from message_XX.properties files
                         
                        @Bean
                        public ResourceBundleMessageSource messageSource() {
                            ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
                            resourceBundleMessageSource.setBasenames("messages");
                            resourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
                            resourceBundleMessageSource.setDefaultLocale(Locale.US);
                            resourceBundleMessageSource.setDefaultEncoding("UTF-8");
                        
                            return resourceBundleMessageSource;
                        }
                    
                        
                        // This bean is responsible for setting a default locale for webpages
                         
                        @Bean
                        public LocaleResolver localeResolver() {
                            SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
                            sessionLocaleResolver.setDefaultLocale(Locale.US);
                    
                            return sessionLocaleResolver;
                        }
                    
                        
                        // This bean is responsible for resolving locale from 'lang' parameter in URL
                        // Example:
                        //      /greeting  = default = any other not defined languages = (English language, messages.properties)
                        //      /greeting?lang=fr (French language, messages_fr.properties)
                         
                        @Bean
                        public LocaleChangeInterceptor localeChangeInterceptor() {
                            LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
                            localeChangeInterceptor.setParamName("lang");
                    
                            return localeChangeInterceptor;
                        }
                    
                    
                         // This method is responsible for registering localeChangeInterceptor, so it can be used in the application
                         // Overrides WebMvcConfigurer.addInterceptors method
                         // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/config/annotation/WebMvcConfigurer.html#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
                         
                        @Override
                        public void addInterceptors(InterceptorRegistry registry) {
                            registry.addInterceptor(localeChangeInterceptor());
                        }
                    }
                    
                    
 */