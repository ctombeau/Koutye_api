package com.chrisnor.koutye.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import com.google.common.base.Predicate;
//
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//import springfox.documentation.service.ApiInfo;
//import com.google.common.base.Predicate;
//import static springfox.documentation.builders.PathSelectors.regex;
//import static com.google.common.base.Predicates.or;

//@EnableSwagger2
@Configuration
public class SwaggerConfig {
//	@Bean
//    public Docket api() { 
//        return new Docket(DocumentationType.SWAGGER_2)  
//          .select()                                                
//          .paths(PathSelectors.ant("/api/**"))
//          .apis(RequestHandlerSelectors.basePackage("com.chrisnor.koutye.controller"))
//          .build();                                           
//    }
	
	//@Bean
//	public Docket api() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.useDefaultResponseMessages(false)
//				.select()
//				.apis(RequestHandlerSelectors.any())
//				.apis(RequestHandlerSelectors.basePackage("com.chrisnor.koutye.controller"))
//				.paths(paths()).build()
//				.apiInfo(apiInfo());
//	}
 
//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder()
//				.title("SPRING LABS")
//				.description("Created by IntelliTech")
//				.version("1.0")
//				.termsOfServiceUrl("http://localhost:5050/api/")
//				.build();
//	}
// 
//	@SuppressWarnings("unchecked")
//	private Predicate paths() {
//		return or(
//				regex("/user.*"),
//				regex("/role.*"),
//				regex("/login.*"));
//	}
}
