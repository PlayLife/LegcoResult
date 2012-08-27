package com.playlife.legcoresult.persistence.domainobjects;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.springframework.beans.factory.annotation.Autowired;

import com.playlife.legcoresult.logic.CommitteeService;
import com.playlife.legcoresult.logic.PersonService;

@PersistenceCapable (identityType = IdentityType.APPLICATION)
public class Person {
	/********************************
	 * * DB Field * *
	 ********************************/
	@PrimaryKey
	@Persistent (valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String name;

	@Persistent (defaultFetchGroup = "true")
	private Set<Long> committeeId = new HashSet<Long>();

	@Autowired
	private PersonService personService;
	@Autowired
	private CommitteeService committeeService;

	public Person(String name) {
		this();
		setName(name);
	}

	private Person() {
		personService.save(this);
	}

	public boolean addCommittee(Committee committee) {
		if (committee == null || committee.getId() == null) return false;
		if (committeeId.contains(committee.getId())) return true;
		return committeeId.add(committee.getId());
	}

	public List<Committee> getCommittee() {
		return committeeService.getById(committeeId);
	}

	public Set<Long> getCommitteeId() {
		return Collections.unmodifiableSet(this.committeeId);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
