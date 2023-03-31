package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.Accounts;
import com.model.Cards;
import com.model.Customer;
import com.repo.CardsRepository;
import com.repo.CustomerRepo;

@RestController
public class CardsController {

	@Autowired
	private CardsRepository cardsRepository;

	@Autowired
	private CustomerRepo customerRepo;

	@GetMapping("/myCards")
	public List<Cards> getCardDetails(@RequestParam String email) {
		List<Customer> customers = customerRepo.findByEmail(email);
		if (customers != null && !customers.isEmpty()) {
			List<Cards> cards = cardsRepository.findByCustomerId(customers.get(0).getId());
			if (cards != null) {
				return cards;
			}
		}
		return null;
	}
}
