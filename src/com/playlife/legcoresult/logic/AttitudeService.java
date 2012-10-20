package com.playlife.legcoresult.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.playlife.legcoresult.persistence.daos.IAttitudeDAO;
import com.playlife.legcoresult.persistence.domainobjects.Amendment;
import com.playlife.legcoresult.persistence.domainobjects.Attendance;
import com.playlife.legcoresult.persistence.domainobjects.Attitude;
import com.playlife.legcoresult.persistence.domainobjects.Committee;
import com.playlife.legcoresult.persistence.domainobjects.Counsel;

@Component
public class AttitudeService {

	@Autowired
	IAttitudeDAO attitudeDAO;

	public List<Attitude> getByAmendment(Amendment amendment) {
		return attitudeDAO.find_all_byAmendmentId(amendment.getId());
	}

	public List<Attitude> getByAttendance(Attendance attendance) {
		return attitudeDAO.find_all_byAttendanceId(attendance.getId());
	}

	public List<Attitude> getByCounsel(Counsel counsel) {
		return attitudeDAO.find_all_byAmendmentId(new ArrayList<Long>(
			counsel.getAmendmentId()));
	}

	public List<Attitude> getById(Collection<Long> id) {
		return attitudeDAO.find_all(new ArrayList<Long>(id));
	}

	public Attitude getById(Long id) {
		return attitudeDAO.get(id);
	}

	public List<Attitude> getById(Long... id) {
		return attitudeDAO.find_all(Arrays.asList(id));
	}

	public List<Attitude> getFinalAttitudeByCounsel(Counsel counsel) {
		return attitudeDAO.find_all_byAmendmentId(counsel
			.getFinalAmendmentId());
	}
	
	public List<Attitude> getFinalAttitudeByCommittee(Committee committee){
		List<Attitude> finalAttitude = new ArrayList<Attitude>();
		List<Attendance> attendanceList = committee.getAttendance();
		for (Attendance attendance : attendanceList) {
			finalAttitude.add(getFinalAttitudeByAttendance(attendance));
		}
		return Collections.unmodifiableList(finalAttitude);
	}
	
	public Attitude getFinalAttitudeByAttendance(Attendance attendance) {
		return getByAmendmentIdAndAttendanceId(attendance.getCounsel().getFinalAmendmentId(), attendance.getId());
	}

	public Attitude getByAmendmentAndAttendance(Amendment amendment, Attendance attendance) {
		return getByAmendmentIdAndAttendanceId(amendment.getId(), attendance.getId());
	}
	
	public Attitude getByAmendmentIdAndAttendanceId(Long amendmentId, Long attendanceId) {
		return attitudeDAO.find_one_byAmendmentIdAndAttendanceId(amendmentId, attendanceId);
	}

	public void save(Attitude attitude) {
		attitudeDAO.save(attitude);
	}
}
