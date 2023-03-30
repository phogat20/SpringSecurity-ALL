package com.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
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
//	@PreFilter("filterObject.contactName!='Test'")
	@PostFilter("filterObject.contactName!='Test'")
	public List<Contact> saveContactInquiryDetails(@RequestBody List<Contact> contacts) {
		Contact contact = contacts.get(0);
		contact.setContactId(getSerivceReqNumber());
		contact.setCreateDt(new Date(System.currentTimeMillis()));
		contact = contactRepository.save(contact);
		List<Contact> returnContacts = new ArrayList<>();
		returnContacts.add(contact);
		return returnContacts;
		
	}

	public String getSerivceReqNumber() {
		Random random = new Random();
		int ranNum = random.nextInt(999999999 - 9999) + 9999;
		return "SR" + ranNum;
	}
}
