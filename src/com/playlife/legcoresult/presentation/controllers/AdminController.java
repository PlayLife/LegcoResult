package com.playlife.legcoresult.presentation.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.playlife.legcoresult.logic.AppUserService;
import com.playlife.legcoresult.persistence.domainobjects.AppUser;
import com.playlife.legcoresult.persistence.domainobjects.Type_UserRole;
import com.playlife.legcoresult.presentation.validator.AppUserValidator;
import com.playlife.legcoresult.utility.JSONConverter;
import com.playlife.legcoresult.utility.LocaleService;
import com.playlife.legcoresult.utility.PresentationException;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	AppUserService appUserService;
	
	@Autowired
	AppUserValidator appUserValidator;
	
	@RequestMapping(value="/")
	protected String registerRequest(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		LocaleService.resolve(request, response);
		return redirect(request, "admin/dashboard", false);
	}
	
	@RequestMapping(value="/{action}")
	protected String mainRequest(@PathVariable String action, HttpServletRequest request, HttpServletResponse response) throws IOException {
		LocaleService.resolve(request, response);
		
		if (action.equalsIgnoreCase("nav"))
			return redirect(request, "admin/nav", false);
		else
			return redirect(request, "admin/dashboard", false);
	}
	
	@RequestMapping(value="/user/{action}")
	protected String user_mainRequest(@PathVariable String action, HttpServletRequest request, HttpServletResponse response) throws IOException {
		LocaleService.resolve(request, response);
		
		if (action.equalsIgnoreCase("create"))
			return redirect(request, "admin/user/create", false);
		else 
			return redirect(request, "admin/user/userList", false);
	}
	
	@RequestMapping(value="/user/userList.json")
	@ResponseBody
	protected String user_listRequest(@RequestParam(value="search", required=false) String search, int start, int end, @RequestParam(value="order", required=false) String order, @RequestParam(value="orderBy", required=false) String orderBy, HttpServletRequest request, HttpServletResponse response) throws IOException {
		LocaleService.resolve(request, response);
		redirect(request, "", true);
		
		if (order == null)
			order = "";
		if (orderBy == null)
			orderBy = "";
		
		JSONObject obj_return = new JSONObject();
		List<AppUser> users = appUserService.getAll(search, start, end);
		
		obj_return.put("users", users);
		obj_return.put("count", appUserService.getAllCount(search));
		obj_return.put("status", "ok");
		
		return obj_return.toString();
	}
	

	@RequestMapping(value="/user/create.json")
	@ResponseBody
	protected String user_listRequest(AppUser user, HttpServletRequest request, HttpServletResponse response) throws IOException {
		LocaleService.resolve(request, response);
		redirect(request, "", true);
				
		JSONObject obj_return = new JSONObject();
		appUserService.create("", user.getUsername(), user.getEmail(), Type_UserRole.fromString(user.getS_userRole()));
		obj_return.put("status", "ok");
		
		return obj_return.toString();
	}
			
	@RequestMapping(value="/error/{action}")
	protected String error_mainRequest(@PathVariable String action, HttpServletRequest request, HttpServletResponse response) throws IOException {
		LocaleService.resolve(request, response);
		
		return redirect(request, "admin/error/report", false);
	}
	
	private String redirect(HttpServletRequest request, String path, boolean throwException){
		AppUser user = (AppUser)request.getSession().getAttribute("user");
		if (user == null || user.getUserRole() != Type_UserRole.ADMIN){
			if (throwException)
				throw new PresentationException(-9999);
			return "home";
		} else
			return path;
	}
	
	@ExceptionHandler(PresentationException.class)
	@ResponseBody
	public String handlerException(HttpServletRequest request, PresentationException ex){
		JSONObject obj_return = new JSONObject();
		obj_return.put("error", JSONConverter.constructError(ex, messageSource, LocaleService.getLocale(request)));
		obj_return.put("status", "error");
		return obj_return.toString();
	}
}
