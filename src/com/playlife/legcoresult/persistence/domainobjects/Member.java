package com.playlife.legcoresult.persistence.domainobjects;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.springframework.beans.factory.annotation.Autowired;

import com.playlife.legcoresult.logic.PersonService;

@PersistenceCapable (identityType = IdentityType.APPLICATION, detachable = "true")
public class Member {
	/********************************
	 * * DB Field * *
	 ********************************/
	@PrimaryKey
	@Persistent (valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Date startDate;

	@Persistent
	private Date endDate;

	@Persistent (defaultFetchGroup = "true")
	private Long personId;

	@Persistent (defaultFetchGroup = "true")
	private Long committeeId;

	@Persistent (defaultFetchGroup = "true")
	private Set<Long> amendmentAttitude = new HashSet<Long>();

	@Autowired
	private PersonService personService;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Person getPerson() {
		return personService.getById(personId);
	}

	public void setPerson(Person person) {
		this.personId = person.getId();
		person.addMember(this);
	}

	public Long getCommittee() {
		return committeeId;
	}

	public void setCommittee(Long committee) {
		this.committeeId = committee;
	}

	boolean addAmendmentAttitude(Attitude attitude) {
		if (attitude == null) return false;
		if (amendmentAttitude.contains(attitude.getId())) return true;
		return amendmentAttitude.add(attitude.getId());

	}

	public boolean removeAmendmentAttitude(Attitude attitude) {
		return amendmentAttitude.remove(attitude.getId());
	}

	void updateFinalTopicAttitude(Long oldAmendmentId, Attitude newAttitude) {
		personService.getById(personId).updateAmendmentFinalAttitude(oldAmendmentId, newAttitude.getId());
	}
}
