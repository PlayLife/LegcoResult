package com.playlife.legcoresult.persistence.daos;

import java.util.List;

import com.playlife.legcoresult.persistence.daos.generic.IGenericDAO;
import com.playlife.legcoresult.persistence.daos.generic.JDOQL;
import com.playlife.legcoresult.persistence.daos.generic.P;
import com.playlife.legcoresult.persistence.domainobjects.Member;

public interface IMemberDAO extends IGenericDAO<Member, Long> {
	@JDOQL (value = "name == p_name")
	public Member find_one_byName(@P (name = "p_name") String name);
	
	@JDOQL (value = "p_memberId.contains(memberId")
	public List<Member> find_all(@P(name = "p_memberId")List<Long> memberId);
}
