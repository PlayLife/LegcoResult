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
import com.playlife.legcoresult.logic.CommitteeService;
import com.playlife.legcoresult.logic.CounselService;

@PersistenceCapable (identityType = IdentityType.APPLICATION)
public class Amendment {

	@PrimaryKey
	@Persistent (valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long counselId;

	@Persistent
	private Long previousAmendmentId;

	@Persistent
	private Long revisorId;

	@Persistent (defaultFetchGroup = "true")
	private Type_TopicStatus status = Type_TopicStatus.WAITING;

	@Persistent
	private String docUrl;

	@Persistent (defaultFetchGroup = "true")
	private Set<Long> attitudeId = new HashSet<Long>();

	@Autowired
	private CounselService counselService;
	@Autowired
	private CommitteeService committeeService;
	@Autowired
	private AmendmentService amendmentService;
	@Autowired
	private AttitudeService attitudeService;

	public Amendment(Counsel counsel, Committee revisor) {
		this();
		setCounsel(counsel);
		setRevisor(revisor);
	}

	private Amendment() {
		amendmentService.save(this);
	}

	public boolean addAttitude(Attitude newAttitude) {
		if (newAttitude == null || newAttitude.getId() == null) return false;
		if (this.attitudeId.contains(newAttitude.getId())) return true;
		return this.attitudeId.add(newAttitude.getId());
	}

	public Collection<Attitude>
		addAttitude(Collection<Attitude> newAttitude) {
		Collection<Attitude> failAttitude = new HashSet<Attitude>();
		for (Attitude attitude : newAttitude) {
			if (!addAttitude(attitude)) {
				failAttitude.add(attitude);
			}
		}
		return failAttitude;
	}

	public void destoryAmendment() {
		updateStatus(Type_TopicStatus.WAITING);
		getCounsel().removeAmendment(this);
		List<Attitude> committeeAttitude = getCommitteeAttitude();
		for (Attitude attitude : committeeAttitude) {
			attitude.destroyAttitude();
		}
	}

	public List<Attitude> getCommitteeAttitude() {
		return attitudeService.getById(this.attitudeId);
	}

	public Counsel getCounsel() {
		return counselService.getById(counselId);
	}

	public Long getCounselId() {
		return counselId;
	}

	public String getDocUrl() {
		return docUrl;
	}

	public Long getId() {
		return id;
	}

	public List<Amendment> getNextAmendment() {
		return amendmentService.getByPreviousAmendment(this);
	}

	public Committee getRevisor() {
		return committeeService.getById(revisorId);
	}

	public Type_TopicStatus getStatus() {
		return status;
	}

	public boolean isPassed() {
		return status == Type_TopicStatus.PASSED;
	}

	public boolean removeAttitude(Attitude attitude) {
		return this.attitudeId.remove(attitude.getId());
	}

	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRevisor(Committee revisor) {
		this.revisorId = revisor.getId();
	}

	public void updateStatus(Type_TopicStatus status) {
		updateStatus(status, null);
	}

	/**
	 * @param extraAmendment
	 *                only need when change from fail to pass to set the
	 *                previous amendment for this.
	 */
	public void updateStatus(Type_TopicStatus status,
		Amendment extraAmendment) {
		if (this.isPassed() && status != Type_TopicStatus.PASSED) {
			// correct Status FROM : PASSED TO : OTHERS
			this.status = status;
			if (amendmentService.hasNextAmendment(this)) {
				List<Amendment> nextAmendment = amendmentService
					.getByPreviousAmendment(this);
				for (Amendment amendment : nextAmendment) {
					amendment
						.setPreviousAmendmentId(this.previousAmendmentId);
				}
			}
			else {
				Counsel counsel = getCounsel();
				if (this.previousAmendmentId == null) {
					counsel.setStatus(status);
					counsel.setFinalAmendmentId(id);
				}
				else {
					counsel.setFinalAmendmentId(previousAmendmentId);
				}
			}
		}
		else if (this.status == Type_TopicStatus.FAILED
			&& status == Type_TopicStatus.PASSED) {
			// correct Status FROM : FAILED TO : PASSED
			this.status = status;
			Counsel topic = getCounsel();
			if (extraAmendment == null) {
				try {
					throw new IllegalArgumentException(
						"Assume this one as the last amendment to be passed");
				}
				catch (IllegalArgumentException iae) {
					iae.printStackTrace();
				}
				extraAmendment = topic.getFinalAmendment();
				topic.setFinalAmendment(this);
			}
			if (extraAmendment != null) {
				List<Amendment> nextAmendment = extraAmendment
					.getNextAmendment();
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

	private void setCounsel(Counsel counsel) {
		this.counselId = counsel.getId();
		counsel.addAmendment(this);
	}

	private void setPreviousAmendment(Amendment previousAmendment) {
		if (previousAmendment == null) {
			this.previousAmendmentId = null;
			return;
		}
		if (previousAmendment.status != Type_TopicStatus.PASSED) {
			try {
				throw new IllegalArgumentException(
					"this topic still don't have passed amendment!!");
			}
			catch (IllegalArgumentException iae) {
				iae.printStackTrace();
			}
			return;
		}
		if (previousAmendment.equals(this)) {
			try {
				throw new IllegalArgumentException(
					"recurrsion point is occured, please check carefully");
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
			setPreviousAmendment(amendmentService
				.getById(previousAmendmentId));
		}
		else {
			this.previousAmendmentId = null;
		}
	}

	private void setStatus(Type_TopicStatus status) {
		this.status = status;
		Counsel topic = getCounsel();

		if (status != Type_TopicStatus.WAITING) {
			setPreviousAmendment(topic.getFinalAmendment());
		}

		if (this.isPassed() || !topic.isPassed()) {
			topic.setFinalAmendment(this);
		}
	}

	boolean hasAttitudeRecord() {
		return !this.attitudeId.isEmpty();
	}

//	void updateCommitteeFinalAttitude() {
//		List<Attitude> memberAttitude = attitudeService
//			.getByAmendment(this);
//		for (Attitude attitude : memberAttitude) {
//			attitude.updateFinalAttitude();
//		}
//	}
}
