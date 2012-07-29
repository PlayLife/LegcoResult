package com.playlife.legcoresult.persistence.domainobjects;

import java.util.Collection;
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
import com.playlife.legcoresult.logic.AttitudeService;
import com.playlife.legcoresult.logic.MemberService;
import com.playlife.legcoresult.logic.TopicService;

@PersistenceCapable (identityType = IdentityType.APPLICATION)
public class Amendment {

	@PrimaryKey
	@Persistent (valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long topicId;

	@Persistent
	private Long previousAmendmentId;

	@Persistent
	private Long revisorId;

	@Persistent (defaultFetchGroup = "true")
	private Type_TopicStatus status = Type_TopicStatus.WAITING;

	@Persistent
	private String docUrl;

	@Persistent (defaultFetchGroup = "true")
	private Set<Long> memberAttitudeId = new HashSet<Long>();

	@Autowired
	private TopicService topicService;

	@Autowired
	private AmendmentService amendmentService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private AttitudeService attitudeService;

	public Amendment(Topic topic, Member revisor) {
		this();
		setTopic(topic);
		setRevisor(revisor);
	}

	private Amendment() {
		amendmentService.save(this);
	}

	public boolean addMemberAttitude(Attitude newAttitude) {
		if (newAttitude == null || newAttitude.getId() == null) return false;
		if (this.memberAttitudeId.contains(newAttitude.getId())) return true;
		return this.memberAttitudeId.add(newAttitude.getId());
	}

	public Collection<Attitude> addMemberAttitude(Collection<Attitude> newAttitude) {
		Collection<Attitude> failAttitude = new HashSet<Attitude>();
		for (Attitude attitude : newAttitude) {
			if (!addMemberAttitude(attitude)) {
				failAttitude.add(attitude);
			}
		}
		return failAttitude;
	}

	public void destoryAmendment() {
		updateStatus(Type_TopicStatus.WAITING);
		getTopic().removeAmendment(this);
		List<Attitude> memberAttitude = getMemberAttitude();
		for (Attitude attitude : memberAttitude) {
			attitude.destroyAttitude();
		}
	}

	public String getDocUrl() {
		return docUrl;
	}

	public Long getId() {
		return id;
	}

	public List<Attitude> getMemberAttitude() {
		return attitudeService.getById(this.memberAttitudeId);
	}

	public List<Amendment> getNextAmendment() {
		return amendmentService.getByPreviousAmendment(this);
	}

	public Member getRevisor() {
		return memberService.getById(revisorId);
	}

	public Type_TopicStatus getStatus() {
		return status;
	}

	public Topic getTopic() {
		return topicService.getById(topicId);
	}

	public boolean isPassed() {
		return status == Type_TopicStatus.PASSED;
	}

	public boolean removeMemberAttitude(Attitude memberAttitude) {
		return this.memberAttitudeId.remove(memberAttitude.getId());
	}

	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRevisor(Member revisor) {
		this.revisorId = revisor.getId();
	}

	public void updateStatus(Type_TopicStatus status) {
		updateStatus(status, null);
	}

	public void updateStatus(Type_TopicStatus status, Amendment extraAmendment) {
		if (this.isPassed() && status != Type_TopicStatus.PASSED) {
			// correct Status FROM : PASSED TO : OTHERS
			this.status = status;
			if (extraAmendment == null) {
				try {
					throw new IllegalArgumentException(
						"Assume this one as only and last one that was passed before");
				}
				catch (IllegalArgumentException iae) {
					iae.printStackTrace();
				}
				extraAmendment = this;
			}
			if (amendmentService.hasNextAmendment(this)) {
				List<Amendment> nextAmendment = amendmentService.getByPreviousAmendment(this);
				for (Amendment amendment : nextAmendment) {
					amendment.setPreviousAmendmentId(this.previousAmendmentId);
				}
			}
			else {
				Topic topic = getTopic();
				if (this.previousAmendmentId == null) {
					topic.setStatus(status);
					topic.setFinalAmendment(extraAmendment);
				}
				else {
					topic.setFinalAmendmentId(this.previousAmendmentId);
				}
			}
		}
		else if (this.status == Type_TopicStatus.FAILED && status == Type_TopicStatus.PASSED) {
			// correct Status FROM : FAILED TO : PASSED
			this.status = status;
			Topic topic = getTopic();
			if (extraAmendment == null) {
				try {
					throw new IllegalArgumentException("Assume this one as the last amendment to be passed");
				}
				catch (IllegalArgumentException iae) {
					iae.printStackTrace();
				}
				extraAmendment = topic.getFinalAmendment();
				topic.setFinalAmendment(this);
			}
			if (extraAmendment != null) {
				List<Amendment> nextAmendment = extraAmendment.getNextAmendment();
				for (Amendment amendment : nextAmendment) {
					amendment.setPreviousAmendment(this);
				}
			}
			// REMIND : must update the old next Amentment first,
			// because after setPreviousAmendment this will appear
			// in getNextAmendment()
			setPreviousAmendment(extraAmendment);
		}
		else if (this.status == Type_TopicStatus.WAITING) {
			if (extraAmendment != null) {
				try {
					throw new IllegalArgumentException(
						"this is just waiting Amendment, next time you should use updateStatus() instead");
				}
				catch (IllegalArgumentException iae) {
					iae.printStackTrace();
				}
			}
			setStatus(status);
		}
	}

	private void setPreviousAmendment(Amendment previousAmendment) {
		if (previousAmendment == null) {
			this.previousAmendmentId = null;
			return;
		}
		if (previousAmendment.status != Type_TopicStatus.PASSED) {
			try {
				throw new IllegalArgumentException("this topic still don't have passed amendment!!");
			}
			catch (IllegalArgumentException iae) {
				iae.printStackTrace();
			}
			return;
		}
		if (previousAmendment.equals(this)) {
			try {
				throw new IllegalArgumentException("recurrsion point is occured, please check carefully");
			}
			catch (IllegalArgumentException iae) {
				iae.printStackTrace();
			}
			return;
		}
		this.previousAmendmentId = previousAmendment.id;
	}

	private void setPreviousAmendmentId(Long previousAmendmentId) {
		if (previousAmendmentId != null) {
			setPreviousAmendment(amendmentService.getById(previousAmendmentId));
		}
		else {
			this.previousAmendmentId = null;
		}
	}

	private void setStatus(Type_TopicStatus status) {
		this.status = status;
		Topic topic = getTopic();

		if (status != Type_TopicStatus.WAITING) {
			setPreviousAmendment(topic.getFinalAmendment());
		}

		if (this.isPassed() || !topic.isPassed()) {
			topic.setFinalAmendment(this);
		}
	}

	private void setTopic(Topic topic) {
		this.topicId = topic.getId();
		topic.addAmendment(this);
	}

	void updateMemberFinalAttitude(Long oldAmendmentId) {
		// TODO update member that hold the lastAttitude from the old
		// amendment to this amendment
		List<Attitude> memberAttitude = attitudeService.getByAmendment(this);
		for (Attitude attitude : memberAttitude) {
			attitude.updateMemberFinalTopicAttitude(oldAmendmentId);
		}
	}
}
