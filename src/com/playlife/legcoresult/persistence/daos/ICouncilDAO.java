package com.playlife.legcoresult.persistence.daos;

import java.util.List;

import com.playlife.legcoresult.persistence.daos.generic.IGenericDAO;
import com.playlife.legcoresult.persistence.daos.generic.JDOQL;
import com.playlife.legcoresult.persistence.daos.generic.P;
import com.playlife.legcoresult.persistence.domainobjects.Council;

public interface ICouncilDAO extends IGenericDAO<Council, Long> {
	@JDOQL (value = "p_id.contains(id)")
	public List<Council> find_all(@P (name = "p_id") List<Long> id);
}
