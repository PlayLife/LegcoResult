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
import com.playlife.legcoresult.presentation.validator.AppUserValidator;
import com.playlife.legcoresult.utility.JSONConverter;
import com.playlife.legcoresult.utility.LocaleService;
import com.playlife.legcoresult.utility.PresentationException;

@Controller
@RequestMapping(value="/user")
public class UserController {
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	AppUserValidator appUserValidator;
	
	@Autowired
	AppUserService appUserService;
	
	@RequestMapping(value="/*")
	protected String loginRequest(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		LocaleService.resolve(request, response);
		AppUser appUser = (AppUser)request.getSession().getAttribute("user");
		if (appUser != null)
			return "home";
		
		User user = UserServiceFactory.getUserService().getCurrentUser();
		if (user == null)
			return "user/googleFail";
		
		appUser = appUserService.getByEmail(user.getEmail());
		if (appUser == null || appUser.isDisabled())
			return "user/fail";
		else {
			request.getSession(true).setAttribute("user", appUser);
			if (!appUser.isLogin()){
				appUser.setGoogleUserId(user.getUserId());
				model.put("userId", user.getUserId());
				model.put("email", user.getEmail());
				model.put("username", user.getNickname());
				return "user/login";
			} else
				return "home";
		}
	}
		
	@RequestMapping(value="/modify.json")
	@ResponseBody
	protected String create(String username, HttpServletRequest request) {
		try {
			User user = UserServiceFactory.getUserService().getCurrentUser();
			if (user == null)
				throw new PresentationException(-9999);
			
			AppUser appUser = appUserService.getByGoogleUserId(user.getUserId());
			if (appUser == null || appUser.isDisabled())
				throw new PresentationException(-9999);
						
			appUserValidator.validateUsername(username);
			appUser.setLogin(true);
			appUser.setUsername(username);
			
			JSONObject obj_return = new JSONObject();
			obj_return.put("user", appUser);
			obj_return.put("status", "ok");
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