package com.playlife.legcoresult.persistence.daos;

import java.util.List;

import com.playlife.legcoresult.persistence.daos.generic.IGenericDAO;
import com.playlife.legcoresult.persistence.daos.generic.JDOQL;
import com.playlife.legcoresult.persistence.daos.generic.P;
import com.playlife.legcoresult.persistence.domainobjects.Attitude;

public interface IAttitudeDAO extends IGenericDAO<Attitude, Long> {

	@JDOQL (value = "p_id.contains(id)")
	public List<Attitude> find_all(@P (name = "p_id") List<Long> id);

	@JDOQL (value = "committeeId == p_committeeId")
	public List<Attitude> find_all_byCommitteeId(@P (name = "p_committeeId") Long committeeId);

	@JDOQL (value = "p_amendmentId==amendmentId")
	public List<Attitude> find_all_byAmendmentId(@P (name = "p_amendmentId") Long amendmentId);

	@JDOQL (value = "p_amendmentId.contains(amendmentId)")
	public List<Attitude> find_all_byAmendmentId(@P (name = "p_amendmentId") List<Long> amendmentId);

	@JDOQL (value = "amendmentId==p_amendmentId && p_committeeId.contains(committeeId)")
	public Attitude find_one_byAmendmentIdAndCommitteeId(@P (name = "p_amendmentId") Long amendmentId,
		@P (name = "p_committeeId") List<Long> committeeId);
}
