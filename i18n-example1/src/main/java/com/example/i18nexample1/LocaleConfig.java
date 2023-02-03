package com.example.i18nexample1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class LocaleConfig implements WebMvcConfigurer {
    
    // without this bean also working, because  
    // By default, Spring Boot looks for the presence of a messages resource bundle at the root of the classpath.

    /**
     * This bean is responsible for resolving texts from message_XX.properties files
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasenames("messages");
        resourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
        resourceBundleMessageSource.setDefaultLocale(Locale.US);
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        
        return resourceBundleMessageSource;
    }

    /**
     * This bean is responsible for setting a default locale for webpages
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.US);
        
        return sessionLocaleResolver;
    }
    
    /**
     * This bean is responsible for resolving locale from 'lang' parameter in URL
     * Example:
     *    /greeting  = default = any other not defined languages = (English language, messages.properties)
     *    /greeting?lang=fr (French language, messages_fr.properties)
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        
        return localeChangeInterceptor;
    }

    /**
     * This method is responsible for registering localeChangeInterceptor, so it can be used in the application
     * Overrides WebMvcConfigurer.addInterceptors method
     * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/config/annotation/WebMvcConfigurer.html#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
