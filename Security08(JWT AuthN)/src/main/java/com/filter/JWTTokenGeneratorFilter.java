package com.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.boot.convert.Delimiter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.constants.SecurityConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null != authentication) { 
			SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
			String jwt = Jwts.builder().setIssuer("bank").setSubject("JWT Token")
					.claim("username", authentication.getName())
					.claim("authorities", populateAuthorities(authentication.getAuthorities()))
					.setIssuedAt(new Date())
					.setExpiration(new Date((new Date()).getTime() + 300000000))
					.signWith(key).compact();
			response.setHeader(SecurityConstants.JWT_HEADER, jwt);
		}
		filterChain.doFilter(request, response);
	}

	//based on condition it will not execute the user 
	//Should only executed during login process 
	//so, token will not be generated again and again
	// request.getServletPath().equals("/user")- if this is the path then condition is
	// and ! condition will be false
	//so this will only be available for /user and not for other request path.
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return !request.getServletPath().equals("/user");
	}
	
	private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {

		Set<String> authoritiesSet = new HashSet<>();
		for (GrantedAuthority authority : collection) {
			authoritiesSet.add(authority.getAuthority());
		}
		return String.join(",", authoritiesSet);

	}

}
