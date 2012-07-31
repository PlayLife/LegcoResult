package com.playlife.legcoresult.persistence.domainobjects;

import java.util.Collection;
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

import com.playlife.legcoresult.logic.AmendmentService;
import com.playlife.legcoresult.logic.CommitteeService;
import com.playlife.legcoresult.logic.CounselService;
import com.playlife.legcoresult.utility.PersistenceException;

@PersistenceCapable (identityType = IdentityType.APPLICATION)
public class Counsel {
	/********************************
	 * * DB Field * *
	 ********************************/
	@PrimaryKey
	@Persistent (valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String title;

	@Persistent (defaultFetchGroup = "true")
	private Type_TopicStatus status;

	@Persistent (defaultFetchGroup = "true")
	private Long initiatorId;

	@Persistent (defaultFetchGroup = "true")
	private Long finalAmendmentId;

	@Persistent (defaultFetchGroup = "true")
	private Set<Long> amendmentId = new HashSet<Long>();

	@Autowired
	private CounselService counselService;
	@Autowired
	private AmendmentService amendmentService;
	@Autowired
	private CommitteeService committeeService;

	private Counsel() {
		counselService.save(this);
	}

	public Counsel(String title, Committee initiator) {
		this();
		this.setTitle(title);
		this.setInitiator(initiator);
	}

	public boolean addAmendment(Amendment newAmendment) {
		if (newAmendment == null || newAmendment.getId() == null) return false;
		if (this.amendmentId.contains(newAmendment.getId())) return true;
		return this.amendmentId.add(newAmendment.getId());
	}

	public Collection<Amendment> addAmendment(Collection<Amendment> newAmendment) {
		Collection<Amendment> failAmendment = new HashSet<Amendment>();
		for (Amendment amendment : newAmendment) {
			if (!addAmendment(amendment)) {
				failAmendment.add(amendment);
			}
		}
		return failAmendment;
	}

	public List<Amendment> getAmendment() {
		return amendmentService.getByCounsel(this);
	}

	public Amendment getFinalAmendment() {
		try {
			return amendmentService.getById(finalAmendmentId);
		}
		catch (PersistenceException pe) {
			if (finalAmendmentId != null) {
				pe.printStackTrace();
			}
			return null;
		}
	}

	public List<Attitude> getFinalAttitude() {
		return amendmentService.getById(finalAmendmentId).getCommitteeAttitude();
	}

	public Long getId() {
		return id;
	}

	public Committee getInitiator() {
		return committeeService.getById(initiatorId);
	}

	public String getTitle() {
		return title;
	}

	public boolean isPassed() {
		return status == Type_TopicStatus.PASSED;
	}

	public boolean removeAmendment(Amendment removeAmendment) {
		return this.amendmentId.remove(removeAmendment.getId());
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setInitiator(Committee initiator) {
		this.initiatorId = initiator.getId();
	}

	public void setTitle(String title) {
		this.title = title;
	}

	Type_TopicStatus getStatus() {
		return status;
	}

	void setFinalAmendment(Amendment finalAmendment) {
		if (isPassed() && !finalAmendment.isPassed()) return;
		this.setStatus(finalAmendment.getStatus());
		finalAmendment.updateCommitteeFinalAttitude(this.finalAmendmentId);
		this.finalAmendmentId = finalAmendment.getId();
	}

	void setFinalAmendmentId(Long finalAmendmentId) {
		setFinalAmendment(amendmentService.getById(finalAmendmentId));
	}

	void setStatus(Type_TopicStatus status) {
		this.status = status;
	}

	public Long getFinalAmendmentId() {
		return this.finalAmendmentId;
	}

	public Set<Long> getAmendmentId() {
		return Collections.unmodifiableSet(this.amendmentId);
	}
}
