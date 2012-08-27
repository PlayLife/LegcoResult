package com.playlife.legcoresult.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.playlife.legcoresult.persistence.daos.IAttendanceDAO;
import com.playlife.legcoresult.persistence.domainobjects.Attendance;
import com.playlife.legcoresult.persistence.domainobjects.Committee;
import com.playlife.legcoresult.persistence.domainobjects.Counsel;

public class AttendanceService {
	@Autowired
	private IAttendanceDAO attendanceDAO;

	public List<Attendance> getByCommittee(Committee committee) {
		return attendanceDAO.find_all_byCommitteeId(committee.getId());
	}

	public Attendance getByCommitteeAndCounsel(Committee committee,
		Counsel counsel) {
		return attendanceDAO.find_one_byCommitteeIdAndCounselId(
			committee.getId(), counsel.getId());
	}

	public Attendance getByCommitteeIdAndCounselId(Long committeeId,
		Long counselId) {
		return attendanceDAO.find_one_byCommitteeIdAndCounselId(
			committeeId, counselId);
	}

	public List<Attendance> getByCounsel(Counsel counsel) {
		return attendanceDAO.find_all_byCounselId(counsel.getId());
	}

	public List<Attendance> getById(Collection<Long> id) {
		return attendanceDAO.find_all(new ArrayList<Long>(id));
	}

	public Attendance getById(Long id) {
		return attendanceDAO.get(id);
	}

	public List<Attendance> getById(Long... id) {
		return attendanceDAO.find_all(Arrays.asList(id));
	}

	public void save(Attendance attendance) {
		attendanceDAO.save(attendance);
	}
}
