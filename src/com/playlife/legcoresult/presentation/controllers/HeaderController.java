package com.playlife.legcoresult.presentation.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.appengine.api.users.UserServiceFactory;
import com.playlife.legcoresult.persistence.domainobjects.AppUser;
import com.playlife.legcoresult.persistence.domainobjects.Type_UserRole;
import com.playlife.legcoresult.utility.LocaleService;

@Controller
@RequestMapping(value="/template")
public class HeaderController {
	private AppUser appUser;
	@ModelAttribute
	public AppUser getUser() {
		return appUser;
	}
	
	@RequestMapping(value="/userHeader")
	protected String mainRequest(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		LocaleService.resolve(request, response);
		appUser = (AppUser)request.getSession().getAttribute("user");
		model.put("loginUrl", UserServiceFactory.getUserService().createLoginURL("/user/login"));
		
		if (appUser == null)
			return "template/header_user_normal";
		else {
			if (appUser.getUserRole() == Type_UserRole.ADMIN)
				return "template/header_user_admin";
			else 
				return "template/header_user_loggedin";
		}
	}
}
