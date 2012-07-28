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

	private Amendment() {
		amendmentService.save(this);
	}

	public Amendment(Topic topic, Member revisor) {
		this();
		setTopic(topic,true);
		setRevisor(revisor,true);
	}

	public boolean addMemberAttitude(Attitude newAttitude, boolean save) {
		if (newAttitude == null || newAttitude.getId() == null) return false;
		if (this.memberAttitudeId.contains(newAttitude.getId())) return true;
		boolean result = this.memberAttitudeId.add(newAttitude.getId());
		if (save) amendmentService.save(this);
		return result;
	}

	public Collection<Attitude> addMemberAttitude(Collection<Attitude> newAttitude,boolean save) {
		Collection<Attitude> failAttitude = new HashSet<Attitude>();
		for (Attitude attitude : newAttitude) {
			if (!addMemberAttitude(attitude, false)) {
				failAttitude.add(attitude);
			}
		}
		if (save) amendmentService.save(this);
		return failAttitude;
	}

	public void destoryAmendment(boolean save) {
		updateStatus(Type_TopicStatus.WAITING,save);
		getTopic().removeAmendment(this, save);
		if (save) amendmentService.save(this);
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

	public void setDocUrl(String docUrl, boolean save) {
		this.docUrl = docUrl;
		if (save) amendmentService.save(this);
	}

	public void setId(Long id, boolean save) {
		this.id = id;
		if (save) amendmentService.save(this);
	}

	public void setRevisor(Member revisor, boolean save) {
		this.revisorId = revisor.getId();
		if (save) amendmentService.save(this);
	}

	public void updateStatus(Type_TopicStatus status, boolean save) {
		updateStatus(status, null,save);
	}

	public void updateStatus(Type_TopicStatus status, Amendment extraAmendment, boolean save) {
		if (this.isPassed() && status != Type_TopicStatus.PASSED) {
			//correct Status FROM : PASSED TO : OTHERS
			this.status = status;
			if (extraAmendment==null){
				try {
					throw new IllegalArgumentException("Assume this one as only and last one that was passed before");
				}
				catch (IllegalArgumentException iae) {
					iae.printStackTrace();
				}
				extraAmendment=this;
			}
			if (amendmentService.hasNextAmendment(this)) {
				List<Amendment> nextAmendment = amendmentService.getByPreviousAmendment(this);
				for (Amendment amendment : nextAmendment) {
					amendment.setPreviousAmendmentId(this.previousAmendmentId,save);
				}
			}
			else {
				Topic topic = getTopic();
				if (this.previousAmendmentId == null) {
					topic.setStatus(status,false);
					topic.setFinalAmendment(extraAmendment,save);
				}
				else {
					topic.setFinalAmendmentId(this.previousAmendmentId,save);
				}
			}
		}
		else if (this.status == Type_TopicStatus.FAILED && status == Type_TopicStatus.PASSED) {
			//correct Status FROM : FAILED TO : PASSED
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
				topic.setFinalAmendment(this,save);
			}
			if (extraAmendment != null) {
				List<Amendment> nextAmendment = extraAmendment.getNextAmendment();
				for (Amendment amendment : nextAmendment) {
					amendment.setPreviousAmendment(this,save);
				}
			}
			// REMIND : must update the old next Amentment first,
			// because after setPreviousAmendment this will appear
			// in getNextAmendment()
			setPreviousAmendment(extraAmendment,false);
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
			setStatus(status, false);
		}
		if (save) amendmentService.save(this);
	}

	private void setPreviousAmendment(Amendment previousAmendment, boolean save) {
		if (previousAmendment == null) {
			this.previousAmendmentId = null;
			if (save) amendmentService.save(this);
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
			return ;
		}
		this.previousAmendmentId = previousAmendment.id;
		if (save) amendmentService.save(this);
	}

	private void setPreviousAmendmentId(Long previousAmendmentId, boolean save) {
		if (previousAmendmentId != null) {
			setPreviousAmendment(amendmentService.getById(previousAmendmentId),save);
		}
		else {
			this.previousAmendmentId = null;
			if (save) amendmentService.save(this);
		}
	}

	private void setStatus(Type_TopicStatus status, boolean save) {
		this.status = status;
		Topic topic = getTopic();

		if (status != Type_TopicStatus.WAITING) {
			setPreviousAmendment(topic.getFinalAmendment(),save);
		}

		if (this.isPassed() || !topic.isPassed()) {
			topic.setFinalAmendment(this,save);
		}
		if (save) amendmentService.save(this);
	}

	private void setTopic(Topic topic, boolean save) {
		this.topicId = topic.getId();
		topic.addAmendment(this,save);
		if (save) amendmentService.save(this);
	}

	void updateMemberFinalAttitude(Long oldAmendment) {
		// TODO update member that hold the lastAttitude from the old
		// amendment to this amendment

	}
}
