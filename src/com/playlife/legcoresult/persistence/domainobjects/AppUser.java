package com.playlife.legcoresult.persistence.domainobjects;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class AppUser {
	/********************************
	 * 								*
	 * 			DB Field			*
	 * 								*
	 ********************************/ 
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long userId;
	
	@Persistent
    private String googleUserId;
		
	@Persistent
    private String email;
	
	@Persistent
    private String username;
	
	@Persistent
	private Type_UserRole userRole;
	
	@Persistent
	private boolean isLogin;
	
	@Persistent
	private boolean isDisabled;
	
	public String s_userRole;
	
	@Persistent
	private List<String> searchText;
	
	/********************************
	 * 								*
	 * 			Constructor			*
	 * 								*
	 ********************************/
	public AppUser(String googleUserId, String username, String email, Type_UserRole userRole){
		this.setGoogleUserId(googleUserId);
		this.setUsername(username);
		this.setEmail(email);
		this.setUserRole(userRole);
		this.setSearchText(new ArrayList<String>());
		this.setLogin(false);
		this.setDisabled(false);
	}
	public AppUser(){
		
	}
	
	/********************************
	 * 								*
	 * 		Getter and Setter		*
	 * 								*
	 ********************************/ 
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public Type_UserRole getUserRole() {
		return userRole;
	}
	
	public void setUserRole(Type_UserRole userRole) {
		this.userRole = userRole;
	}
	
	public String getS_userRole() {
		return s_userRole;
	}
	
	public void setS_userRole(String s_userRole) {
		this.s_userRole = s_userRole;
	}
	public List<String> getSearchText() {
		return searchText;
	}
	public void setSearchText(List<String> searchText) {
		this.searchText = searchText;
	}
	public AppUser addSearchText(String s_searchText) {
		this.getSearchText().add(s_searchText);
		return this;
	}
	public String getGoogleUserId() {
		return googleUserId;
	}
	public void setGoogleUserId(String googleUserId) {
		this.googleUserId = googleUserId;
	}
	public boolean isLogin() {
		return isLogin;
	}
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	public boolean isDisabled() {
		return isDisabled;
	}
	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}
	
}
