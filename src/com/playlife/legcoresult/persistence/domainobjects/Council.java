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

import com.playlife.legcoresult.logic.CommitteeService;
import com.playlife.legcoresult.logic.CouncilService;
import com.playlife.legcoresult.logic.CounselService;
import com.playlife.legcoresult.logic.PeriodService;

@PersistenceCapable (identityType = IdentityType.APPLICATION)
public class Council {
	@PrimaryKey
	@Persistent (valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String name;

	@Persistent (defaultFetchGroup = "true")
	private Long periodId;

	@Persistent (defaultFetchGroup = "true")
	private Set<Long> committeeId = new HashSet<Long>();

	@Persistent (defaultFetchGroup = "true")
	private Set<Long> counselId = new HashSet<Long>();

	@Persistent (defaultFetchGroup = "true")
	private Set<Long> childCouncilId = new HashSet<Long>();

	@Persistent (defaultFetchGroup = "true")
	private Long parentCouncilId;

	@Autowired
	private PeriodService periodService;
	@Autowired
	private CouncilService councilService;
	@Autowired
	private CommitteeService committeeService;
	@Autowired
	private CounselService counselService;

	public Council(Period period) {
		this();
		setPeriod(period);
	}

	private Council() {
		councilService.save(this);
	}

	public boolean addCommittee(Committee committee) {
		if (committee == null || committee.getId() == null) return false;
		if (committeeId.contains(committee.getId())) return true;
		return committeeId.add(committee.getId());
	}

	public boolean addCounsel(Counsel counsel) {
		if (counsel == null || counsel.getId() == null) return false;
		if (counselId.contains(counsel.getId())) return true;
		return counselId.add(counsel.getId());
	}

	public Long getId() {
		return id;
	}

	public Period getPeriod() {
		return periodService.getById(periodId);
	}

	public void setPeriod(Period period) {
		periodId = period.getId();
		period.addCouncil(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Council getParentCouncil() {
		return councilService.getById(parentCouncilId);
	}

	public void setParentCouncil(Council parentCouncil) {
		this.parentCouncilId = parentCouncil.getId();
	}

	public List<Committee> getCommittee() {
		return committeeService.getById(committeeId);
	}

	public List<Counsel> getCounsel() {
		return counselService.getById(counselId);
	}

	public List<Council> getChildCouncil() {
		return councilService.getById(childCouncilId);
	}
}
