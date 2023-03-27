package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.Customer;
import com.repo.CustomerRepo;

@RestController
public class LoginController {

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
		Customer savedCustomer = null;
		ResponseEntity<String> response = null;
		try {
			String hashPwd = passwordEncoder.encode(customer.getPwd());
			customer.setPwd(hashPwd);
			savedCustomer = customerRepo.save(customer);
			if (savedCustomer.getId() > 0) {
				response = ResponseEntity.status(HttpStatus.CREATED)
						.body("given user details are successfully register");
			}
		} catch (Exception e) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An exception occured due to " + e.getMessage());
		}

		return response;
	}

}
