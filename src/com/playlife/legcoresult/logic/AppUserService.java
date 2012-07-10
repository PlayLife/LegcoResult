package com.playlife.legcoresult.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.playlife.legcoresult.persistence.daos.IAppUserDAO;
import com.playlife.legcoresult.persistence.domainobjects.AppUser;
import com.playlife.legcoresult.persistence.domainobjects.Type_UserRole;
import com.playlife.legcoresult.utility.LogicException;

@Component
public class AppUserService {
	@Autowired
	IAppUserDAO appUserDAO;
	
	public AppUser create(String googleUserId, String username, String email, Type_UserRole type){
		try {
			AppUser user = new AppUser(googleUserId, username, email, type);
			user.addSearchText(user.getUsername()).addSearchText(user.getEmail()).addSearchText(user.getUserRole().toString());
			appUserDAO.save(user);
			
			return user;
		} catch (Exception ex){
			throw new LogicException(-9999, ex);
		}
	}
	
	public AppUser getByEmail(String email){
		try {
			return appUserDAO.find_one_byEmail(email);
		} catch (Exception ex){
			throw new LogicException(-9999, ex);
		}
	}
	
	public boolean checkEmailExists(String email){
		try {
			AppUser user = appUserDAO.find_one_byEmail(email);
			return user != null;
		} catch (Exception ex){
			throw new LogicException(-9999, ex);
		}
	}
	
	public AppUser getByGoogleUserId(String userId){
		try {
			return appUserDAO.find_one_byGoogleUserId(userId);
		} catch (Exception ex){
			throw new LogicException(-9999, ex);
		}
	}
	
	public boolean isInit(){
		try {
			return appUserDAO.count_byUserRole(Type_UserRole.ADMIN) > 0;
		} catch (Exception ex){
			throw new LogicException(-9999, ex);
		}
	}
	
	public List<AppUser> getAll(String search, int start, int end){
		try {			
			return appUserDAO.find_all(start, end, null, search, search + "\ufffd");
		} catch (Exception ex){
			throw new LogicException(-9999, ex);
		}
	}
	
	public int getAllCount(String search){
		try {		
			return appUserDAO.count_all(search, search + "\uFFFD");
		} catch (Exception ex){
			throw new LogicException(-9999, ex);
		}
	}
}
