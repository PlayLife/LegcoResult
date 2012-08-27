package com.playlife.legcoresult.persistence.daos;

import java.util.List;

import com.playlife.legcoresult.persistence.daos.generic.IGenericDAO;
import com.playlife.legcoresult.persistence.daos.generic.JDOQL;
import com.playlife.legcoresult.persistence.daos.generic.P;
import com.playlife.legcoresult.persistence.domainobjects.Committee;

public interface ICommitteeDAO extends IGenericDAO<Committee, Long> {
	@JDOQL (value = "p_committeeId.contains(committeeId")
	public List<Committee> find_all(
		@P (name = "p_committeeId") List<Long> committeeId);

	@JDOQL (value = "name == p_name")
	public Committee find_one_byName(@P (name = "p_name") String name);
}
