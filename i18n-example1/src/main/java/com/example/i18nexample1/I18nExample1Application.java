package com.example.i18nexample1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class I18nExample1Application {  
    public static void main(String[] args) {
        SpringApplication.run(I18nExample1Application.class, args);
    }

}

/*
    Internationalization (i18n) is the process of designing your application to be adapted to various languages, 
    regional peculiarities, and technical requirements of a target market. 
    Internationalization is a crucial step in the process of localizing your product. 
    In this tutorial, we will show you how to use Spring Boot to create a simple internationalized application, 
    how to get translated messages from messages_xx.properties files, render translated HTML from HTML template 
    using Thymeleaf and how to use LocaleResolver to change the language of the application.
    
    
    
    
    Spring Boot Internationalization — i18n
    
    Configurations
    
    One of the basic requirements for any web application is to be able to support multiple languages and have localized messages. 
    To achieve this by default, Spring Boot looks for the presence of a messages.properties resource bundle at the root of the classpath.


    These files contain a set of key-value rows for each text that needed to be supported in multiple languages just like the following example:
    
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
                            l.setParamName("localeParmName");
                            registry.addInterceptor(l);
                           }
                        };
                    }

 */