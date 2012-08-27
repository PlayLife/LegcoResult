package com.playlife.legcoresult.persistence.domainobjects;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.springframework.beans.factory.annotation.Autowired;

import com.playlife.legcoresult.logic.AttendanceService;
import com.playlife.legcoresult.logic.AttitudeService;
import com.playlife.legcoresult.logic.CommitteeService;
import com.playlife.legcoresult.logic.CounselService;

@PersistenceCapable (identityType = IdentityType.APPLICATION)
public class Attendance {

	@PrimaryKey
	@Persistent (valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	private Type_AttitudeDecision attendance;

	// @Persistent
	// private Type_AttitudeDecision finalDecide;

	@Persistent (defaultFetchGroup = "true")
	private Long committeeId;

	@Persistent (defaultFetchGroup = "true")
	private Long counselId;

	// @Persistent (defaultFetchGroup = "true")
	// private Long finalAttitudeId;

	@Persistent (defaultFetchGroup = "true")
	private Set<Long> attitudeId = new HashSet<Long>();

	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private CommitteeService committeeService;
	@Autowired
	private CounselService counselService;
	@Autowired
	private AttitudeService attitudeService;

	public Attendance(Committee committee, Counsel counsel,
		Type_AttitudeDecision attendance) {
		this();
		setCommittee(committee);
		setCounsel(counsel);
		setAttendance(attendance);
	}

	private Attendance() {
		attendanceService.save(this);
	}

	public boolean addAttitude(Attitude newAttitude) {
		if (newAttitude == null || newAttitude.getId() == null) return false;
		if (attitudeId.contains(newAttitude.getId())) return true;
		return attitudeId.add(newAttitude.getId());
	}

	public Type_AttitudeDecision getAttendance() {
		return attendance;
	}

	public List<Attitude> getAttitude() {
		return attitudeService.getByAttendance(this);
	}

	public Committee getCommittee() {
		return committeeService.getById(committeeId);
	}

	public Counsel getCounsel() {
		return counselService.getById(counselId);
	}

	public Attitude getFinalAttitude() {
		return attitudeService.getByAmendmentIdAndAttendanceId(
			getCounsel().getFinalAmendmentId(), id);
	}

	// public Type_AttitudeDecision getFinalDecide() {
	// return finalDecide;
	// }

	public Long getId() {
		return id;
	}

	public boolean removeAttitude(Attitude attitude) {
		return attitudeId.remove(attitude.getId());
	}

	public void setAttendance(Type_AttitudeDecision attendance) {
		switch (attendance) {
			case AGAINST:
			case APPROVE:
			case FORFEIT:
				attendance = Type_AttitudeDecision.ATTEND;
			default:
				this.attendance = attendance;
		}
	}

	private void setCommittee(Committee committee) {
		this.committeeId = committee.getId();
		committee.addAttendance(this);
	}

	private void setCounsel(Counsel counsel) {
		counselId = counsel.getId();
		counsel.addAttendance(this);
	}

	// public void setFinalDecide(Type_AttitudeDecision finalDecide) {
	// this.finalDecide = finalDecide;
	// }

	public void setId(Long id) {
		this.id = id;
	}
	//
	// void clearFinalAttitude() {
	// finalAttitudeId = null;
	// setFinalDecide(this.attendance);
	// }
	//
	// void updateFinalAttitude(Attitude finalAttitude) {
	// finalAttitudeId = finalAttitude.getId();
	// setFinalDecide(finalAttitude.getDecide());
	// }
}
