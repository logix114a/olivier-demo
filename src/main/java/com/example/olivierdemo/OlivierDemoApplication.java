package com.example.olivierdemo;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties.LocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class OlivierDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OlivierDemoApplication.class, args);
	}
	
	 @Bean
	   public SessionLocaleResolver localeResolver(){
	        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
	        localeResolver.setDefaultLocale(Locale.US);
	        return  localeResolver;
	    }

	    @Bean
	    public LocaleChangeInterceptor localeChangeInterceptor() {
	        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
	        localeChangeInterceptor.setParamName("lang");
	        return localeChangeInterceptor;
	    }

	    public void addInterceptors(InterceptorRegistry registry){
	        registry.addInterceptor(localeChangeInterceptor());
	    }
}

