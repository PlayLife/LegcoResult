package com.playlife.legcoresult.logic;

import org.springframework.beans.factory.annotation.Autowired;

import com.playlife.legcoresult.persistence.daos.IPersonDAO;
import com.playlife.legcoresult.persistence.domainobjects.Person;

public class PersonService {
	@Autowired
	IPersonDAO personDAO;

	public Person getById(Long id) {
		return personDAO.get(id);
	}

	public void save(Person person) {
		personDAO.save(person);
	}
}
