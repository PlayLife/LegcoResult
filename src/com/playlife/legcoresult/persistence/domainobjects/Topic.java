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
import com.playlife.legcoresult.logic.MemberService;
import com.playlife.legcoresult.logic.TopicService;
import com.playlife.legcoresult.utility.PersistenceException;

@PersistenceCapable (identityType = IdentityType.APPLICATION)
public class Topic {
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
	private TopicService topicService;
	@Autowired
	private AmendmentService amendmentService;
	@Autowired
	private MemberService memberService;

	private Topic(){
		topicService.save(this);
	}
	
	public Topic(String title, Member initiator) {
		this();
		this.setTitle(title,false);
		this.setInitiator(initiator,true);
	}

	public boolean addAmendment(Amendment newAmendment, boolean save) {
		if (newAmendment == null || newAmendment.getId() == null) return false;
		if (this.amendmentId.contains(newAmendment.getId())) return true;
		boolean result = this.amendmentId.add(newAmendment.getId());
		if (save) topicService.save(this);
		return result;
	}

	public Collection<Amendment> addAmendment(Collection<Amendment> newAmendment, boolean save) {
		Collection<Amendment> failAmendment = new HashSet<Amendment>();
		for (Amendment amendment : newAmendment) {
			if (!addAmendment(amendment,false)) {
				failAmendment.add(amendment);
			}
		}
		if (save) topicService.save(this);
		return failAmendment;
	}

	public List<Amendment> getAmendment() {
		return amendmentService.getByTopic(this);
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
		return amendmentService.getById(finalAmendmentId).getMemberAttitude();
	}

	public Long getId() {
		return id;
	}

	public Member getInitiator() {
		return memberService.getById(initiatorId);
	}

	public String getTitle() {
		return title;
	}

	public boolean isPassed() {
		return status == Type_TopicStatus.PASSED;
	}

	public boolean removeAmendment(Amendment removeAmendment, boolean save){
		boolean result = this.amendmentId.remove(removeAmendment.getId());
		if (save) topicService.save(this);
		return result;
	}

	public void setId(Long id, boolean save) {
		this.id = id;
		if (save) topicService.save(this);
	}

	public void setInitiator(Member initiator, boolean save) {
		this.initiatorId = initiator.getId();
		if (save) topicService.save(this);
	}

	public void setTitle(String title, boolean save) {
		this.title = title;
		if (save) topicService.save(this);
	}

	Type_TopicStatus getStatus() {
		return status;
	}

	void setFinalAmendment(Amendment finalAmendment, boolean save) {
		if (isPassed() && !finalAmendment.isPassed()) return;
		this.setStatus(finalAmendment.getStatus(), false);
		finalAmendment.updateMemberFinalAttitude(this.finalAmendmentId);
		this.finalAmendmentId = finalAmendment.getId();
		if (save) topicService.save(this);
	}

	void setFinalAmendmentId(Long finalAmendmentId, boolean save) {
		setFinalAmendment(amendmentService.getById(finalAmendmentId), save);
	}
	
	void setStatus(Type_TopicStatus status, boolean save) {
		this.status = status;
		if (save) topicService.save(this);
	}
	public Long getFinalAmendmentId(){
		return this.finalAmendmentId;
	}
	public Set<Long> getAmendmentId(){
		return Collections.unmodifiableSet(this.amendmentId);
	}
}
