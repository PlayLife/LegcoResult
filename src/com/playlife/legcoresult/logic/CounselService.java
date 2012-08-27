package com.playlife.legcoresult.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	
	public List<Counsel> getById(Collection<Long> id){
		return counselDAO.find_all(new ArrayList<Long>(id));
	}

	public void save(Counsel counsel) {
		counselDAO.save(counsel);
	}
}
