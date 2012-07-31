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

import com.playlife.legcoresult.logic.AttitudeService;
import com.playlife.legcoresult.logic.CommitteeService;

@PersistenceCapable (identityType = IdentityType.APPLICATION)
public class Person {
	/********************************
	 * * DB Field * *
	 ********************************/
	@PrimaryKey
	@Persistent (valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent (defaultFetchGroup = "true")
	private Set<Long> finalAmendmentAttitudeId = new HashSet<Long>();
	@Persistent (defaultFetchGroup = "true")
	private Set<Long> committeeId = new HashSet<Long>();

	@Autowired
	private AttitudeService attitudeService;
	@Autowired
	private CommitteeService memberService;

	public boolean addCommittee(Committee committee) {
		if (committee == null) return false;
		if (committeeId.contains(committee.getId())) return true;
		return committeeId.add(committee.getId());
	}

	public List<Attitude> getFinalAmendmentAttitude() {
		return attitudeService.getById(finalAmendmentAttitudeId);
	}

	public Long getId() {
		return id;
	}

	public List<Committee> getCommittee() {
		return memberService.getById(committeeId);
	}

	public Set<Long> getCommitteeId() {
		return Collections.unmodifiableSet(this.committeeId);
	}

	public void setId(Long id) {
		this.id = id;
	}

	boolean updateAmendmentFinalAttitude(Long oldAmendmentId, Long newAttitudeId) {
		if (oldAmendmentId != null) {
			Attitude oldAttitude = attitudeService.getByAmendmentIdAndPerson(oldAmendmentId, this);
			finalAmendmentAttitudeId.remove(oldAttitude.getId());
		}
		if (finalAmendmentAttitudeId.contains(newAttitudeId)) return true;
		return finalAmendmentAttitudeId.add(newAttitudeId);
	}
}
