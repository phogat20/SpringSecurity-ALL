package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.AccountTransactions;
import com.model.Customer;
import com.repo.AccountTransactionsRepository;
import com.repo.CustomerRepo;

@RestController
public class BalanceController {

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private AccountTransactionsRepository accountTransactionsRepository;

	@GetMapping("/myBalance")
	public List<AccountTransactions> getBalanceDetails(@RequestParam String email) {
		List<Customer> customers = customerRepo.findByEmail(email);
		if (customers != null && !customers.isEmpty()) {
			List<AccountTransactions> accountTransactions = accountTransactionsRepository
					.findByCustomerIdOrderByTransactionDtDesc(customers.get(0).getId());
			if (accountTransactions != null) {
				return accountTransactions;
			}

		}
		return null;

	}
}
