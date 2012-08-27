package com.playlife.legcoresult.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.playlife.legcoresult.persistence.daos.ICouncilDAO;
import com.playlife.legcoresult.persistence.domainobjects.Council;

public class CouncilService {
	@Autowired
	private ICouncilDAO councilDAO;

	public List<Council> getById(Collection<Long> id) {
		return councilDAO.find_all(new ArrayList<Long>(id));
	}

	public Council getById(Long id) {
		return councilDAO.get(id);
	}

	public List<Council> getById(Long... id) {
		return councilDAO.find_all(Arrays.asList(id));
	}

	public void save(Council council) {
		councilDAO.save(council);
	}
}
