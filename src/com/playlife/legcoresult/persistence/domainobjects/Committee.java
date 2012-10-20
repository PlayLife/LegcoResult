package com.playlife.legcoresult.persistence.domainobjects;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.springframework.beans.factory.annotation.Autowired;

import com.playlife.legcoresult.logic.AttendanceService;
import com.playlife.legcoresult.logic.CommitteeService;
import com.playlife.legcoresult.logic.CouncilService;
import com.playlife.legcoresult.logic.PersonService;

@PersistenceCapable (identityType = IdentityType.APPLICATION)
public class Committee {
	/********************************
	 * * DB Field * *
	 ********************************/
	@PrimaryKey
	@Persistent (valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Date startDate;

	@Persistent
	private Date endDate;

	@Persistent (defaultFetchGroup = "true")
	private Long personId;

	@Persistent (defaultFetchGroup = "true")
	private Long councilId;

	@Persistent (defaultFetchGroup = "true")
	private Set<Long> attendanceId = new HashSet<Long>();

	@Autowired
	private PersonService personService;
	@Autowired
	private CouncilService councilService;
	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private CommitteeService committeeService;

	public Committee(Person person, Council council) {
		this();
		setPerson(person);
		setCouncil(council);
	}

	private Committee() {
		committeeService.save(this);
	}

	public boolean addAttendance(Attendance attendance) {
		if (attendance == null || attendance.getId() == null) return false;
		if (attendanceId.contains(attendance.getId())) return true;
		return attendanceId.add(attendance.getId());
	}

	public List<Attendance> getAttendance() {
		return attendanceService.getById(attendanceId);
	}

	public Council getCouncil() {
		return councilService.getById(councilId);
	}

	public Date getEndDate() {
		return endDate;
	}

	public Long getId() {
		return id;
	}

	public Person getPerson() {
		return personService.getById(personId);
	}

	public Date getStartDate() {
		return startDate;
	}

	private void setCouncil(Council council) {
		this.councilId = council.getId();
		council.addCommittee(this);
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private void setPerson(Person person) {
		this.personId = person.getId();
		person.addCommittee(this);
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
}
