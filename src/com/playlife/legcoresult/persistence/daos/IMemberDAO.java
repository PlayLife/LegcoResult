package com.playlife.legcoresult.persistence.daos;

import com.playlife.legcoresult.persistence.daos.generic.IGenericDAO;
import com.playlife.legcoresult.persistence.daos.generic.JDOQL;
import com.playlife.legcoresult.persistence.daos.generic.P;
import com.playlife.legcoresult.persistence.domainobjects.Member;

public interface IMemberDAO extends IGenericDAO<Member, Long> {
	@JDOQL (value = "name == p_name")
	public Member find_one_byName(@P (name = "p_name") String name);
}
