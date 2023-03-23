package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	// <---------------------- For Authorization ----------------->
	// For Authorization we  use HttpSecurity class.
	// TO go from more restrictive to less restrictive URL role.

	@Bean
	SecurityFilterChain deafaultSecurityFilterChain(HttpSecurity http) throws Exception {

		// <---------------------------Custom
		// security---------------------------------------->
		http.authorizeHttpRequests().requestMatchers("/myAccount/**", "/myBalance", "/myCards", "/myLoans")
				.authenticated().requestMatchers("/contact", "/notices").permitAll().and().formLogin().and()
				.httpBasic();

		return http.build();
	}
	
	
//	<----------------------Authentication------------------------>
	@Bean
	public InMemoryUserDetailsManager userDetailsService() {
		
//		<---------------With Default password encoder----------------->
//		UserDetails admin = User.withDefaultPasswordEncoder()
//								.username("admin")
//								.password("123456")
//								.authorities("admin")
//								.build();
//		UserDetails user = User.withDefaultPasswordEncoder()
//								.username("user")
//								.password("123456")
//								.authorities("read")
//								.build();
//		return new InMemoryUserDetailsManager(admin,user);
		
//		<---------------With No Op password encoder----------------->
		UserDetails admin = User.withUsername("admin")
								.password("123456")
								.authorities("admin")
								.build();
		UserDetails user = User.withUsername("user")
								.password("user")
								.authorities("user")
								.build();
		return new InMemoryUserDetailsManager(admin,user);					
	}
	
	
//	<---- Password encoder->
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}


