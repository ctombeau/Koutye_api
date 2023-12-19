package com.chrisnor.koutye.config;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.chrisnor.koutye.service.serviceimpl.UserDetailServiceImpl;
import com.chrisnor.koutye.service.serviceimpl.UtilisateurServiceImpl;
import com.nimbusds.jose.jwk.source.ImmutableSecret;

import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled=true)
@NoArgsConstructor
@AllArgsConstructor
public class SecurityConfig{
	
	
	@Value("${jwt.secret}")
	private String secretKey;
	
	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;
	
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		
		return http
				.sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.csrf(csrf->csrf.disable())
				.authorizeRequests(ar->ar.requestMatchers("/api/login/**").permitAll())
				.authorizeRequests(ar->ar.requestMatchers("/api/user/add/**").permitAll())
				.authorizeRequests(ar->ar.anyRequest().authenticated())
				//.httpBasic(Customizer.withDefaults())
				//.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
				
				//ou
				.oauth2ResourceServer(oa->oa.jwt(Customizer.withDefaults()))
				//.userDetailsService(userDetailServiceImpl)
				.build();
	}
	
	
	


	@Bean
	JwtEncoder jwtEncoder()
	{
		return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey.getBytes()));
	}
	

	
	@Bean
	JwtDecoder jwtDecoder()
	{
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "RSA");
		return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
	{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService)
	{
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());
		return daoAuthenticationProvider;
	}
	
	/*
	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService userDetailService)
	{
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(userDetailServiceImpl);
		return new ProviderManager(daoAuthenticationProvider);
	}
	*/

}
 