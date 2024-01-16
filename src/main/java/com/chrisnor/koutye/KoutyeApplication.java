package com.chrisnor.koutye;

import java.util.Arrays;
import java.util.Collections;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.chrisnor.koutye.service.EmailService;

//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@Configuration
public class KoutyeApplication {
	@Autowired
	private EmailService emailService;

	public static void main(String[] args) {
		SpringApplication.run(KoutyeApplication.class, args);
	}
    
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void triggerMail(){
		emailService.sendEmail("jeanedmasherley@gmail.com", "Je test l'email de mon application chherie", "Test Email");
	}
	
	/*
	@Bean
	public CorsFilter corsFilter()
	{
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
		corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:8100"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin",
				"Content-Type", "Accept","Jwt-Token","Authorization","Origin,Accept","X-Requested-With",
				"Access-Control-Request-Method","Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("origin","Content-Type","Accept","Jwt-Token","Aurhorization",
				"Access-Control-Allow-Origin","Access-control-Allow-Credentials","File-Name"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
		corsConfiguration.applyPermitDefaultValues();
		
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
	
		return new CorsFilter(urlBasedCorsConfigurationSource);
		
	}
	*/
	
}
