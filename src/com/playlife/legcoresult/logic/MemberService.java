package com.playlife.legcoresult.logic;

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
}
