package com.playlife.legcoresult.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.playlife.legcoresult.persistence.daos.IMemberDAO;
import com.playlife.legcoresult.persistence.domainobjects.Member;

@Component
public class MemberService {
	@Autowired
	IMemberDAO memberDAO;

	public Member getById(Long id){
		return memberDAO.get(id);
	}
	public List<Member> getById(Long... id){
		return memberDAO.find_all(Arrays.asList(id));
	}
	public List<Member >getById(Collection<Long> id){
		return memberDAO.find_all(new ArrayList<Long>(id));
	}
}
