package com.playlife.legcoresult.utility;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Request {
	/************************
	 * 						*
	 * 		Primitive		* 
	 * 						*
	 ************************/
	
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
