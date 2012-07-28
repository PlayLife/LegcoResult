package com.playlife.legcoresult.persistence.domainobjects;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.springframework.beans.factory.annotation.Autowired;

import com.playlife.legcoresult.logic.AttitudeService;
import com.playlife.legcoresult.logic.MemberService;

@PersistenceCapable (identityType = IdentityType.APPLICATION)
public class Attitude {
	public enum Decision {
		AGAINST, FOR, ABSENT, GIVEUP, NONE;
	};

	/*
	 * DB Field
	 */
	@PrimaryKey
	@Persistent (valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Decision decide;

	@Persistent (defaultFetchGroup = "true")
	private Long memberId;

	@Persistent (defaultFetchGroup = "true")
	private Long amendmentId;

	@Autowired
	private AttitudeService attitudeService;
	@Autowired
	private MemberService memberService;

	private Attitude(){
		attitudeService.save(this);
	}
	public Attitude(Amendment amendment, Member member) {
		this();
		setMember(member, true);
		setAmendment(amendment, true);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Decision getDecide() {
		return decide;
	}

	public void setDecide(Decision decide) {
		this.decide = decide;
	}

	private void setMember(Member member, boolean save) {
		this.memberId = member.getId();
	}

	private void setAmendment(Amendment amendment, boolean save) {
		this.amendmentId = amendment.getId();
		amendment.addMemberAttitude(this, save);
		if (save) attitudeService.save(this);
	}
	
	void updateMemberFinalTopicAttitude(Long oldAmendment){
		memberService.getById(memberId).updateFinalTopicAttitude(oldAmendment, getId());
	}
}
