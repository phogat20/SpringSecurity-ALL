package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


	//<---------------------- For Authorization ----------------->
	// For Authorization we override configure method and use HttpSecurity class.
	// TO go from more restrictive to less restrictive URL role.	
	

	@Bean
	SecurityFilterChain deafaultSecurityFilterChain(HttpSecurity http) throws Exception {
		
		//<-------------Default Security--------->
//		http.authorizeHttpRequests()
//			.anyRequest().authenticated();
//			http.formLogin();
//			http.httpBasic();

	
		
		//<---------------------------Custom security---------------------------------------->			
		http.authorizeHttpRequests()
			.requestMatchers("/myAccount/**", "/myBalance", "/myCards", "/myLoans")
				.authenticated()
				.requestMatchers("/contact", "/notices").permitAll()
				.and()
				.formLogin()
				.and()
				.httpBasic();
			//	.formLogin and http.formLogin are same 
			// 	.httpBasic and http.httpBasic are same
			//		http.formLogin();
			//		http.httpBasic();

		//<---------------------------Deny All security---------------------------------------->			
//		http.authorizeHttpRequests()
//			.anyRequest().denyAll()
//			.and()
//			.formLogin()
//			.and()
//			.httpBasic();
		
		//<---------------------------Permit All security---------------------------------------->			
//		http.authorizeHttpRequests()
//			.anyRequest().permitAll()
//			.and()
//			.formLogin()
//			.and()
//			.httpBasic();
		
		
		return http.build();
	}

}


//** - means any path with /myAccount will be authenticated.

