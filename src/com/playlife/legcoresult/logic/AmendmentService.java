package com.playlife.legcoresult.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.playlife.legcoresult.persistence.daos.IAmendmentDAO;
import com.playlife.legcoresult.persistence.domainobjects.Amendment;
import com.playlife.legcoresult.persistence.domainobjects.Counsel;

public class AmendmentService {

	@Autowired
	IAmendmentDAO amendmentDAO;

	public List<Amendment> getByCounsel(Counsel counsel) {
		return amendmentDAO.find_all_byCounsel(counsel.getId());
	}

	public List<Amendment> getById(Collection<Long> amendmentId) {
		return amendmentDAO.find_all(new ArrayList<Long>(amendmentId));
	}

	public Amendment getById(Long id) {
		return amendmentDAO.get(id);
	}

	public List<Amendment> getByPreviousAmendment(
		Amendment previousAmendment) {
		return amendmentDAO
			.find_all_byPreviousAmendment(previousAmendment.getId());
	}

	public boolean hasNextAmendment(Amendment amendment) {
		return !getByPreviousAmendment(amendment).isEmpty();
	}

	public Amendment save(Amendment amendment) {
		return amendmentDAO.save(amendment);
	}
}
