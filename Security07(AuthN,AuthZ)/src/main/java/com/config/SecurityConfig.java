package com.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.filter.CsrfCookieFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class SecurityConfig {

	// <---------------------- For Authorization ----------------->
	// For Authorization we use HttpSecurity class.
	// TO go from more restrictive to less restrictive URL role.

	@Bean
	SecurityFilterChain deafaultSecurityFilterChain(HttpSecurity http) throws Exception {
// <---------------------------Custom security---------------------------------------->
		CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
		requestHandler.setCsrfRequestAttributeName("_csrf");

		http.securityContext().requireExplicitSave(false).and()
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))

				.cors().configurationSource(new CorsConfigurationSource() {

					@Override
					public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
						CorsConfiguration config = new CorsConfiguration();
						config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
						config.setAllowedMethods(Collections.singletonList("*"));
						config.setAllowCredentials(true);
						config.setAllowedHeaders(Collections.singletonList("*"));
						config.setMaxAge(3600L);

						return config;
					}
				}).and()
				.csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler)
						.ignoringRequestMatchers("/contact", "/register")
						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
				.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)

				.authorizeHttpRequests()
//      <-------------- for authority based Authorization ---->
//					.requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
//					.requestMatchers("/myBalance").hasAnyAuthority("VIEWACCOUNT","VIEWBALANCE")
//					.requestMatchers("/myCards").hasAuthority("VIEWLOANS")
//					.requestMatchers("/myLoans").hasAuthority("VIEWCARDS")
//					

//				<-------------- for ROLE based Authorization ---->
//				The ROLE_ prefix is automatically added. so we should not start with ROLE_.
				.requestMatchers("/myAccount").hasRole("USER").requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
				.requestMatchers("/myCards").hasRole("USER").requestMatchers("/myLoans").hasRole("USER")

				.requestMatchers("/user").authenticated().requestMatchers("/contact", "/notices", "/register")
				.permitAll().and().formLogin().and().httpBasic();

		return http.build();
	}

//	<---- Password encoder->
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
