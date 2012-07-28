package com.playlife.legcoresult.persistence.domainobjects;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.springframework.beans.factory.annotation.Autowired;

import com.playlife.legcoresult.logic.AttitudeService;
import com.sun.corba.se.impl.ior.OldPOAObjectKeyTemplate;

@PersistenceCapable (identityType = IdentityType.APPLICATION, detachable = "true")
public class Person {
	/********************************
	 * * DB Field * *
	 ********************************/
	@PrimaryKey
	@Persistent (valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent (defaultFetchGroup = "true")
	private Set<Long> amendmentFinalAttitudeId = new HashSet<Long>();
	@Persistent (defaultFetchGroup = "true")
	private Set<Long> memberId = new HashSet<Long>();
	
	@Autowired
	private AttitudeService attitudeService;
	
	void updateAmendmentFinalAttitude(Long oldAmendmentId, Long newAttitudeId){
		updateAmendmentFinalAttitude(oldAmendmentId, newAttitudeId, true);
	}
	
	void updateAmendmentFinalAttitude(Long oldAmendmentId, Long newAttitudeId, boolean save){
		if (oldAmendmentId!=null){
			Attitude oldAttitude = attitudeService.getByAmendmentIdAndPerson(oldAmendmentId, this);
			amendmentFinalAttitudeId.remove(oldAttitude.getId());
		}
		if (!amendmentFinalAttitudeId.contains(newAttitudeId)){
			amendmentFinalAttitudeId.add(newAttitudeId);
		}
	}
	
	public Set<Long> getMemberId(){
		return Collections.unmodifiableSet(this.memberId);
	}
}
