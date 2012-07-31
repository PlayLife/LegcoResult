package com.playlife.legcoresult.persistence.domainobjects;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.springframework.beans.factory.annotation.Autowired;

import com.playlife.legcoresult.logic.AmendmentService;
import com.playlife.legcoresult.logic.AttitudeService;
import com.playlife.legcoresult.logic.CommitteeService;

@PersistenceCapable (identityType = IdentityType.APPLICATION)
public class Attitude {
	/*
	 * DB Field
	 */
	@PrimaryKey
	@Persistent (valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Type_AttitudeDecision decide;

	@Persistent (defaultFetchGroup = "true")
	private Long committeeId;

	@Persistent (defaultFetchGroup = "true")
	private Long amendmentId;

	@Autowired
	private AttitudeService attitudeService;
	@Autowired
	private CommitteeService committeeService;
	@Autowired
	private AmendmentService amendmentService;

	public Attitude(Amendment amendment, Committee committee) {
		this();
		setCommittee(committee);
		setAmendment(amendment);
	}

	private Attitude() {
		attitudeService.save(this);
	}

	public Amendment getAmendment() {
		return amendmentService.getById(amendmentId);
	}

	public Type_AttitudeDecision getDecide() {
		return decide;
	}

	public Long getId() {
		return id;
	}

	public Committee getCommittee() {
		return committeeService.getById(committeeId);
	}

	public void setDecide(Type_AttitudeDecision decide) {
		this.decide = decide;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private void setAmendment(Amendment amendment) {
		this.amendmentId = amendment.getId();
		amendment.addCommitteeAttitude(this);
	}

	private void setCommittee(Committee member) {
		this.committeeId = member.getId();
		member.addAmendmentAttitude(this);
	}

	void destroyAttitude() {
		getAmendment().removeCommitteeAttitude(this);
		getCommittee().removeAmendmentAttitude(this);
	}

	void updateCommitteeFinalCounselAttitude(Long oldAmendment) {
		getCommittee().updateFinalCounselAttitude(oldAmendment, this);
	}
}
