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
import com.playlife.legcoresult.logic.MemberService;

@PersistenceCapable (identityType = IdentityType.APPLICATION, detachable = "true")
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
	private Set<Long> memberId = new HashSet<Long>();

	@Autowired
	private AttitudeService attitudeService;
	@Autowired
	private MemberService memberService;

	public boolean addMember(Member member) {
		if (member == null) return false;
		if (memberId.contains(member.getId())) return true;
		return memberId.add(member.getId());
	}

	public List<Attitude> getFinalAmendmentAttitude() {
		return attitudeService.getById(finalAmendmentAttitudeId);
	}

	public Long getId() {
		return id;
	}

	public List<Member> getMember() {
		return memberService.getById(memberId);
	}

	public Set<Long> getMemberId() {
		return Collections.unmodifiableSet(this.memberId);
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
