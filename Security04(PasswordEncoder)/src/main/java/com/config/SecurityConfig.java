package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	// <---------------------- For Authorization ----------------->
	// For Authorization we use HttpSecurity class.
	// TO go from more restrictive to less restrictive URL role.

	@Bean
	SecurityFilterChain deafaultSecurityFilterChain(HttpSecurity http) throws Exception {
// <---------------------------Custom security---------------------------------------->
		http.csrf().disable().authorizeHttpRequests()
				.requestMatchers("/myAccount/**", "/myBalance", "/myCards", "/myLoans").authenticated()
				.requestMatchers("/contact", "/notices", "/register").permitAll().and().formLogin().and().httpBasic();

		return http.build();
	}

//	<---- Password encoder->
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
//		return new Argon2PasswordEncoder(0, 0, 0, 0, 0)
	}

}
