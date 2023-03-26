package com.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	// <---------------------- For Authorization ----------------->
	// For Authorization we use HttpSecurity class.
	// TO go from more restrictive to less restrictive URL role.

	@Bean
	SecurityFilterChain deafaultSecurityFilterChain(HttpSecurity http) throws Exception {

		// <---------------------------Custom
		// security---------------------------------------->
		http.csrf().disable().authorizeHttpRequests()
				.requestMatchers("/myAccount/**", "/myBalance", "/myCards", "/myLoans").authenticated()
				.requestMatchers("/contact", "/notices", "/register").permitAll().and().formLogin().and().httpBasic();

		return http.build();
	}

//	@Bean
//	public UserDetailsService userDetailsService(DataSource dataSource) {
//		return new JdbcUserDetailsManager(dataSource);
//	}

//	<----------------------Authentication------------------------>

//	<---- Password encoder->
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}
