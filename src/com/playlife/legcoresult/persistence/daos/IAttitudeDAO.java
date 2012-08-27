package com.playlife.legcoresult.persistence.daos;

import java.util.List;

import com.playlife.legcoresult.persistence.daos.generic.IGenericDAO;
import com.playlife.legcoresult.persistence.daos.generic.JDOQL;
import com.playlife.legcoresult.persistence.daos.generic.P;
import com.playlife.legcoresult.persistence.domainobjects.Attitude;

public interface IAttitudeDAO extends IGenericDAO<Attitude, Long> {

	@JDOQL (value = "p_id.contains(id)")
	public List<Attitude> find_all(@P (name = "p_id") List<Long> id);

	@JDOQL (value = "p_amendmentId.contains(amendmentId)")
	public List<Attitude> find_all_byAmendmentId(
		@P (name = "p_amendmentId") List<Long> amendmentId);

	@JDOQL (value = "p_amendmentId==amendmentId")
	public List<Attitude> find_all_byAmendmentId(
		@P (name = "p_amendmentId") Long amendmentId);

	@JDOQL (value = "attendanceId == p_attendanceId")
	public List<Attitude> find_all_byAttendanceId(
		@P (name = "p_attendanceId") Long attendanceId);
	
	@JDOQL(value = "amendmentId == p_amendmentId && attendanceId == p_attendanceId")
	public Attitude find_one_byAmendmentIdAndAttendanceId(@P(name="p_amendmentId") Long amendmentId, @P(name = "p_attendanceId") Long attendanceId);
}
