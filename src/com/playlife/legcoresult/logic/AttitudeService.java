package com.playlife.legcoresult.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.playlife.legcoresult.persistence.daos.IAttitudeDAO;
import com.playlife.legcoresult.persistence.domainobjects.Attitude;
import com.playlife.legcoresult.persistence.domainobjects.Member;
import com.playlife.legcoresult.persistence.domainobjects.Person;
import com.playlife.legcoresult.persistence.domainobjects.Topic;

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

	public List<Attitude> getByMember(Member member) {
		return attitudeDAO.find_all_byMemberId(member.getId());
	}

	public List<Attitude> getFinalAttitudeByTopic(Topic topic) {
		return attitudeDAO.find_all_byAmendmentId(topic.getFinalAmendmentId());
	}

	public List<Attitude> getByTopic(Topic topic) {
		return attitudeDAO.find_all_byAmendmentId(new ArrayList<Long>(topic.getAmendmentId()));
	}
	
	public Attitude getByAmendmentIdAndPerson(Long amendmentId, Person person){
		return attitudeDAO.find_one_byAmendmentIdAndMemberId(amendmentId, new ArrayList<Long>(person.getMemberId()));
	}
	
	public void save(Attitude attitude){
		attitudeDAO.save(attitude);
	}
}
