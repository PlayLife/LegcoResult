package com.playlife.legcoresult.persistence.domainobjects;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.springframework.beans.factory.annotation.Autowired;

import com.playlife.legcoresult.logic.AmendmentService;
import com.playlife.legcoresult.logic.AttendanceService;
import com.playlife.legcoresult.logic.AttitudeService;

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
	private Long attendanceId;

	@Persistent (defaultFetchGroup = "true")
	private Long amendmentId;

	@Autowired
	private AttitudeService attitudeService;
	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private AmendmentService amendmentService;

	public Attitude(Amendment amendment, Committee committee) {
		this();
		setAmendment(amendment);
		setAttendance(attendanceService.getByCommitteeIdAndCounselId(
			committee.getId(), amendment.getCounselId()));
	}

	private Attitude() {
		attitudeService.save(this);
	}

	public void destroyAttitude() {
		getAmendment().removeAttitude(this);
		getAttendance().removeAttitude(this);
		amendmentId = 0L;
		attendanceId = 0L;
	}

	public Amendment getAmendment() {
		return amendmentService.getById(amendmentId);
	}

	public Attendance getAttendance() {
		return attendanceService.getById(attendanceId);
	}

	public Type_AttitudeDecision getDecide() {
		return decide;
	}

	public Long getId() {
		return id;
	}

	public void setDecide(Type_AttitudeDecision decide) {
		this.decide = decide;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private void setAmendment(Amendment amendment) {
		this.amendmentId = amendment.getId();
		amendment.addAttitude(this);
	}

	private void setAttendance(Attendance attendance) {
		this.attendanceId = attendance.getId();
		attendance.addAttitude(this);
	}

	// void updateFinalAttitude() {
	// getAttendance().updateFinalAttitude(this);
	// }
}
