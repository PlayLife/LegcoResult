package com.playlife.legcoresult.persistence.daos;

import java.util.List;

import com.playlife.legcoresult.persistence.daos.generic.IGenericDAO;
import com.playlife.legcoresult.persistence.daos.generic.JDOQL;
import com.playlife.legcoresult.persistence.daos.generic.P;
import com.playlife.legcoresult.persistence.domainobjects.Attendance;

public interface IAttendanceDAO extends IGenericDAO<Attendance, Long> {
	@JDOQL (value = "p_id.contains(id)")
	public List<Attendance> find_all(@P (name = "p_id") List<Long> id);

	@JDOQL (value = "committeeId == p_committeeId")
	public List<Attendance> find_all_byCommitteeId(
		@P (name = "p_committeeId") Long committeeId);

	@JDOQL (value = "counselId == p_counselId")
	public List<Attendance> find_all_byCounselId(
		@P (name = "p_counselId") Long counselId);

	@JDOQL (value = "committeeId == p_committeeId && counselId == p_counselId")
	public
		Attendance find_one_byCommitteeIdAndCounselId(
			@P (name = "p_committeeId") Long committeeId,
			@P (name = "p_counselId") Long counselId);
}
