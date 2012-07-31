package com.playlife.legcoresult.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.playlife.legcoresult.persistence.daos.IAttitudeDAO;
import com.playlife.legcoresult.persistence.domainobjects.Amendment;
import com.playlife.legcoresult.persistence.domainobjects.Attitude;
import com.playlife.legcoresult.persistence.domainobjects.Committee;
import com.playlife.legcoresult.persistence.domainobjects.Person;
import com.playlife.legcoresult.persistence.domainobjects.Counsel;

@Component
public class AttitudeService {

	@Autowired
	IAttitudeDAO attitudeDAO;
	public Attitude getById(Long id){
		return attitudeDAO.get(id);
	}

	public List<Attitude> getById(Long... id) {
		return attitudeDAO.find_all(Arrays.asList(id));
	}
	
	public List<Attitude> getById(Collection<Long> id) {
		return attitudeDAO.find_all(new ArrayList<Long>(id));
	}

	public List<Attitude> getByCommittee(Committee counsel) {
		return attitudeDAO.find_all_byCommitteeId(counsel.getId());
	}
	
	public List<Attitude> getByAmendment(Amendment amendment){
		return attitudeDAO.find_all_byAmendmentId(amendment.getId());
	}

	public List<Attitude> getFinalAttitudeByCounsel(Counsel counsel) {
		return attitudeDAO.find_all_byAmendmentId(counsel.getFinalAmendmentId());
	}

	public List<Attitude> getByCounsel(Counsel counsel) {
		return attitudeDAO.find_all_byAmendmentId(new ArrayList<Long>(counsel.getAmendmentId()));
	}
	
	public Attitude getByAmendmentIdAndPerson(Long amendmentId, Person person){
		return attitudeDAO.find_one_byAmendmentIdAndCommitteeId(amendmentId, new ArrayList<Long>(person.getCommitteeId()));
	}
	
	public void save(Attitude attitude){
		attitudeDAO.save(attitude);
	}
}
