package com.playlife.legcoresult.utility;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Request {
	/************************
	 * 						*
	 * 		Primitive		* 
	 * 						*
	 ************************/
	public static String getString(HttpServletRequest request, String paraName){
		String s_return = "";
		try {
			s_return = request.getParameter(paraName).toString();
		} catch (Exception ex){
			throw new PresentationException(-40002, ex, paraName);
		}
		return s_return;
	}
	public static String getStringAllowNull(HttpServletRequest request, String paraName){
		String s_return = "";
		try {
			if (request.getParameter(paraName) == null)
				return null;
			else s_return = request.getParameter(paraName).toString();
		} catch (Exception ex){
			throw new PresentationException(-40003, ex, paraName);
		}
		return s_return;
	}
	
	public static <T> T getObject(HttpServletRequest request, String paraName, Class<T> classOf) {
		try {
			if (request.getParameter(paraName) == null)
				throw new PresentationException(-40011, paraName);
			
			Gson gson = new GsonBuilder().setDateFormat("MM/dd/yyyy HH:mm").create();;
			return gson.fromJson(request.getParameter(paraName), classOf);
		} catch (Exception ex){
			throw new PresentationException(-40011, ex, paraName);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> getArrayObject(HttpServletRequest request, String paraName, Class<T> classOf) {
		try {
			if (request.getParameter(paraName) == null)
				throw new PresentationException(-40011, paraName);

			String s_array = request.getParameter(paraName);
			return (JSONArray)JSONSerializer.toJSON(s_array);		
		} catch (Exception ex){
			throw new PresentationException(-40011, ex, paraName);
		}
	}
	
	/************************
	 * 						*
	 * 		 Session		* 
	 * 						*
	 ************************/
	public static Long getSessionLong(HttpServletRequest request, String paraName){
		Long l_return = -1L;
		try {
			HttpSession session = request.getSession(true); 
			l_return = Long.parseLong(session.getAttribute(paraName).toString());
		} catch (Exception ex){
			throw new PresentationException(-99999, ex, paraName);
		}
		return l_return;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getSession(HttpServletRequest request, String paraName, Class<T> classOf){
		T obj_return = null;
		try {
			HttpSession session = request.getSession(true);
			
			if (session.getAttribute(paraName) == null)
				throw new PresentationException(-99999);
			
			obj_return = (T)(session.getAttribute(paraName));
		} catch (Exception ex){
			throw new PresentationException(-99999, ex, paraName);
		}
		return obj_return;
	}
}
