package com.playlife.legcoresult.persistence.daos;

import java.util.List;

import com.playlife.legcoresult.persistence.daos.generic.IGenericDAO;
import com.playlife.legcoresult.persistence.daos.generic.JDOQL;
import com.playlife.legcoresult.persistence.daos.generic.P;
import com.playlife.legcoresult.persistence.domainobjects.Counsel;

public interface ICounselDAO extends IGenericDAO<Counsel, Long> {

	@JDOQL (value = "p_id.contains(id)")
	public List<Counsel> find_all(@P (name = "p_id") List<Long> id);

	@JDOQL (value = "name == p_name")
	public Counsel find_one_byname(@P (name = "p_name") String name);
	
}
