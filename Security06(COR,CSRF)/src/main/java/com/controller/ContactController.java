package com.controller;

import java.sql.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.Contact;
import com.repo.ContactRepository;

@RestController
public class ContactController {

	@Autowired
	private ContactRepository contactRepository;

	@PostMapping("/contact")
	public Contact saveContactInquiryDetails(@RequestBody Contact contact) {
		contact.setContactId(getSerivceReqNumber());
		contact.setCreateDt(new Date(System.currentTimeMillis()));
		return contactRepository.save(contact);
	}

	public String getSerivceReqNumber() {
		Random random = new Random();
		int ranNum = random.nextInt(999999999 - 9999) + 9999;
		return "SR" + ranNum;
	}
}
