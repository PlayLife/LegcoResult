package com.playlife.legcoresult.persistence.domainobjects;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.springframework.beans.factory.annotation.Autowired;

import com.playlife.legcoresult.logic.AmendmentService;
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
	@Autowired
	private AmendmentService amendmentService;

	public Attitude(Amendment amendment, Member member) {
		this();
		setMember(member);
		setAmendment(amendment);
	}

	private Attitude() {
		attitudeService.save(this);
	}

	public Amendment getAmendment() {
		return amendmentService.getById(amendmentId);
	}

	public Decision getDecide() {
		return decide;
	}

	public Long getId() {
		return id;
	}

	public Member getMember() {
		return memberService.getById(memberId);
	}

	public void setDecide(Decision decide) {
		this.decide = decide;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private void setAmendment(Amendment amendment) {
		this.amendmentId = amendment.getId();
		amendment.addMemberAttitude(this);
	}

	private void setMember(Member member) {
		this.memberId = member.getId();
		member.addAmendmentAttitude(this);
	}

	void destroyAttitude() {
		getAmendment().removeMemberAttitude(this);
		getMember().removeAmendmentAttitude(this);
	}

	void updateMemberFinalTopicAttitude(Long oldAmendment) {
		getMember().updateFinalTopicAttitude(oldAmendment, this);
	}
}
