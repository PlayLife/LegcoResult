package com.playlife.legcoresult.persistence.daos;

import java.util.List;

import com.playlife.legcoresult.persistence.daos.generic.IGenericDAO;
import com.playlife.legcoresult.persistence.daos.generic.JDOQL;
import com.playlife.legcoresult.persistence.daos.generic.P;
import com.playlife.legcoresult.persistence.domainobjects.Amendment;

public interface IAmendmentDAO extends IGenericDAO<Amendment, Long> {
	@JDOQL (value = "previousAmentmendId == p_previousAmentmentId")
	public List<Amendment> find_all_byPreviousAmendment(@P (name = "p_previousAmentmentId") Long previousAmentmentId);
	
	@JDOQL (value = "counselId == p_counselId")
	public List<Amendment> find_all_byCounsel(@P (name = "p_counselId") Long counselId);

	@JDOQL (value = "p_id.contains(id)")
	public List<Amendment> find_all(@P (name = "p_id") List<Long> id);
}
