package com.playlife.legcoresult.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.playlife.legcoresult.persistence.daos.ICounselDAO;
import com.playlife.legcoresult.persistence.domainobjects.Counsel;

@Component
public class CounselService {
	@Autowired
	ICounselDAO counselDAO;

	public Counsel getById(Long id) {
		return counselDAO.get(id);
	}

	public void save(Counsel counsel) {
		counselDAO.save(counsel);
	}
}
