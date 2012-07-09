package com.playlife.legcoresult.persistence.daos;

import java.util.List;

import com.playlife.legcoresult.persistence.daos.generic.IGenericDAO;
import com.playlife.legcoresult.persistence.daos.generic.JDOQL;
import com.playlife.legcoresult.persistence.daos.generic.P;
import com.playlife.legcoresult.persistence.domainobjects.AppUser;
import com.playlife.legcoresult.persistence.domainobjects.Type_UserRole;

public interface IAppUserDAO extends IGenericDAO<AppUser, Long>{
	@JDOQL(value="username == p_username")
	public AppUser find_one_byUsername(@P(name="p_username")String username);
	
	@JDOQL(value="email == p_email && password == p_password")
	public AppUser find_one_byEmailAndPassword(@P(name="p_email")String email, @P(name="p_password")String password);
	
	@JDOQL(value="email == p_email")
	public AppUser find_one_byEmail(@P(name="p_email")String email);
	
	@JDOQL(value="googleUserId == p_googleUserId")
	public AppUser find_one_byGoogleUserId(@P(name="p_googleUserId")String googleUserId);
	
	@JDOQL(value="userRole == p_userRole")
	public int count_byUserRole(@P(name="p_userRole")Type_UserRole userRole);
	
	public List<AppUser> find_all(@P(name="start")long start, @P(name="end")long end, @P(name="order")String order);

	@JDOQL(value="searchText >= p_start_searchText && searchText < p_end_searchText")
	public List<AppUser> find_all(@P(name="start")long start, @P(name="end")long end, @P(name="order")String order, @P(name="p_start_searchText")String start_searchText, @P(name="p_end_searchText")String end_searchText);
	
	@JDOQL(value="searchText >= p_start_searchText && searchText < p_end_searchText")
	public int count_all(@P(name="p_start_searchText")String start_searchText, @P(name="p_end_searchText")String end_searchText);
}