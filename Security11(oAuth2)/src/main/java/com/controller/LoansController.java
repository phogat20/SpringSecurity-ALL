package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.Accounts;
import com.model.Customer;
import com.model.Loans;
import com.repo.CustomerRepo;
import com.repo.LoanRepository;

@RestController
public class LoansController {

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private CustomerRepo customerRepo;

	@GetMapping("/myLoans")
	@PostAuthorize("hasRole('USER')")
	public List<Loans> getLoanDetails(@RequestParam String email) {
		List<Customer> customers = customerRepo.findByEmail(email);
		if (customers != null && !customers.isEmpty()) {
			List<Loans> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(customers.get(0).getId());
			if (loans != null) {
				return loans;
			}
		}
		return null;

	}
}
