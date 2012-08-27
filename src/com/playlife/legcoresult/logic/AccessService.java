package com.playlife.legcoresult.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.playlife.legcoresult.persistence.daos.IAppUserDAO;
import com.playlife.legcoresult.persistence.domainobjects.AppUser;
import com.playlife.legcoresult.utility.LogicException;

@Component
public class AccessService {
	@Autowired
	IAppUserDAO appUserDAO;

	public AppUser getUser(AppUser user) {
		try {
			return appUserDAO.get(user.getUserId());
		}
		catch (Exception ex) {
			throw new LogicException(-9999, ex);
		}
	}
}
