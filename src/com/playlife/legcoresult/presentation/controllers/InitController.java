package com.playlife.legcoresult.presentation.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.playlife.legcoresult.logic.AppUserService;
import com.playlife.legcoresult.persistence.domainobjects.AppUser;
import com.playlife.legcoresult.persistence.domainobjects.Type_UserRole;
import com.playlife.legcoresult.presentation.validator.AppUserValidator;
import com.playlife.legcoresult.utility.JSONConverter;
import com.playlife.legcoresult.utility.LocaleService;
import com.playlife.legcoresult.utility.PresentationException;

@Controller
@RequestMapping(value="/init")
public class InitController {
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	AppUserValidator appUserValidator;
	
	@Autowired
	AppUserService appUserService;
	
	@RequestMapping(value="/*")
	protected String mainRequest(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		if (appUserService.isInit())
			return "home";
		else {
			model.put("loginUrl", UserServiceFactory.getUserService().createLoginURL("/init/register"));
			return "init/index";
		}
	}
	
	@RequestMapping(value="/register")
	protected String successRequest(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		User user = UserServiceFactory.getUserService().getCurrentUser();
		if (user == null)
			return "init/fail";
		
		model.put("userId", user.getUserId());
		model.put("email", user.getEmail());
		model.put("username", user.getNickname());
		return "init/register";
	}
	
	@RequestMapping(value="/create.json")
	@ResponseBody
	protected String createRequest(String username, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		try {
			User user = UserServiceFactory.getUserService().getCurrentUser();
			if (user == null)
				throw new PresentationException(-9999);
			
			if (appUserService.isInit())
				throw new PresentationException(-9999);
			
			appUserValidator.validateUsername(username);
			
			JSONObject obj_return = new JSONObject();
			AppUser appUser = appUserService.create(user.getUserId(), username, user.getEmail(), Type_UserRole.ADMIN);
			obj_return.put("user", appUser);
			obj_return.put("status", "ok");
			request.getSession(true).setAttribute("user", appUser);
			return obj_return.toString();
		} catch (Exception ex){
			throw new PresentationException(-9999, ex);
		}
	}
		
	@ExceptionHandler(PresentationException.class)
	@ResponseBody
	public String handlerException(HttpServletRequest request, Exception ex){
		JSONObject obj_return = new JSONObject();
		obj_return.put("error", JSONConverter.constructError(ex, messageSource, LocaleService.getLocale(request)));
		obj_return.put("status", "error");
		return obj_return.toString();
	}
}
