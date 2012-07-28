package com.playlife.legcoresult.persistence.daos;

import com.playlife.legcoresult.persistence.daos.generic.IGenericDAO;
import com.playlife.legcoresult.persistence.daos.generic.JDOQL;
import com.playlife.legcoresult.persistence.daos.generic.P;
import com.playlife.legcoresult.persistence.domainobjects.Topic;

public interface ITopicDAO extends IGenericDAO<Topic, Long> {

	@JDOQL (value = "name == p_name")
	public Topic find_one_byname(@P (name = "p_name") String name);
	
}
