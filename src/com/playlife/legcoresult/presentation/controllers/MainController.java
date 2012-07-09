package com.playlife.legcoresult.presentation.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.playlife.legcoresult.persistence.domainobjects.AppUser;
import com.playlife.legcoresult.utility.LocaleService;

@Controller
public class MainController {
	
	@RequestMapping(value="/")
	protected String homeRequest(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		LocaleService.resolve(request, response);
		AppUser user = (AppUser)request.getSession().getAttribute("user");
		model.put("user", user);
		return "home";
	}
	
	@RequestMapping(value="/{action}")
	protected String mainRequest(@PathVariable String action, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		LocaleService.resolve(request, response);
		AppUser user = (AppUser)request.getSession().getAttribute("user");
		model.put("user", user);
		
		if (action.trim().equalsIgnoreCase("create"))
			return "create";
		
		if (action.trim().equalsIgnoreCase("logout") && user != null){
			HttpSession session = request.getSession(false);
			if(session!=null) {
				session.setAttribute("user", null);
			}
			response.sendRedirect("home");
			return null;
		}
		
		return "home";
	}
}