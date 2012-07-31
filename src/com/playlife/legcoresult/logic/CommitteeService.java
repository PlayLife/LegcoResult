package com.playlife.legcoresult.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.playlife.legcoresult.persistence.daos.ICommitteeDAO;
import com.playlife.legcoresult.persistence.domainobjects.Committee;

@Component
public class CommitteeService {
	@Autowired
	ICommitteeDAO memberDAO;

	public Committee getById(Long id){
		return memberDAO.get(id);
	}
	public List<Committee> getById(Long... id){
		return memberDAO.find_all(Arrays.asList(id));
	}
	public List<Committee >getById(Collection<Long> id){
		return memberDAO.find_all(new ArrayList<Long>(id));
	}
}
