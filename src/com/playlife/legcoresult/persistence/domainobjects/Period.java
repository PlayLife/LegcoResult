package com.playlife.legcoresult.persistence.domainobjects;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.springframework.beans.factory.annotation.Autowired;

import com.playlife.legcoresult.logic.CouncilService;
import com.playlife.legcoresult.logic.PeriodService;

@PersistenceCapable (identityType = IdentityType.APPLICATION)
public class Period {
	/********************************
	 * * DB Field * *
	 ********************************/
	@PrimaryKey
	@Persistent (valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String name;

	@Persistent (defaultFetchGroup = "true")
	private Set<Long> councilId = new HashSet<Long>();

	@Autowired
	private PeriodService periodService;
	@Autowired
	private CouncilService councilService;

	public Period(String name) {
		this();
		this.setName(name);
	}

	private Period() {
		periodService.save(this);
	}

	public boolean addCouncil(Council council) {
		if (council == null || council.getId() == null) return false;
		if (councilId.contains(council.getId())) return true;
		return councilId.add(council.getId());
	}

	public List<Council> getCouncil() {
		return councilService.getById(councilId);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
