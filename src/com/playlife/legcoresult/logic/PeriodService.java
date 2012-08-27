package com.playlife.legcoresult.logic;

import org.springframework.beans.factory.annotation.Autowired;

import com.playlife.legcoresult.persistence.daos.IPeriodDAO;
import com.playlife.legcoresult.persistence.domainobjects.Period;

public class PeriodService {
	@Autowired
	private IPeriodDAO periodDAO;

	public Period getById(Long id) {
		return periodDAO.get(id);
	}

	public void save(Period period) {
		periodDAO.save(period);
	}
}
