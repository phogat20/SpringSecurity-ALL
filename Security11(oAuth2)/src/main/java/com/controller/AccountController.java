package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.Accounts;
import com.model.Customer;
import com.repo.AccountsRepository;
import com.repo.CustomerRepo;

@RestController
public class AccountController {

	@Autowired
	private AccountsRepository accountsRepository;

	@Autowired
	private CustomerRepo customerRepo;

	@GetMapping("/myAccount")
	public Accounts getAccountDetails(@RequestParam String email) {
		List<Customer> customers = customerRepo.findByEmail(email);
		if (customers != null && !customers.isEmpty()) {
			Accounts accounts = accountsRepository.findByCustomerId(customers.get(0).getId());
			if (accounts != null) {
				return accounts;
			}
		}
		return null;
	}

}
