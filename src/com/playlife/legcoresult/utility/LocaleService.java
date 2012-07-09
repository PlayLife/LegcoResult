package com.playlife.legcoresult.utility;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.LocaleEditor;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

public class LocaleService {
	public static void resolve(HttpServletRequest request, HttpServletResponse response) {
		try {
	        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
	        if (localeResolver != null) {
	            String newLocaleName = ServletRequestUtils.getStringParameter(request, "locale");
	            
	            if (newLocaleName != null) {
	                LocaleEditor localeEditor = new LocaleEditor();
	                localeEditor.setAsText(newLocaleName);
	                
	                localeResolver.setLocale(request, response, (Locale) localeEditor.getValue());
	            }
	        }
		} catch (Exception ex){
			throw new PresentationException(-9999 ,ex);
		}
	}
	
	public static Locale getLocale(HttpServletRequest request){
		try {
			for (Cookie cookie : request.getCookies()){
				if (cookie.getName().equalsIgnoreCase("locale")){
	                LocaleEditor localeEditor = new LocaleEditor();
	                localeEditor.setAsText(cookie.getValue());
	                return (Locale) localeEditor.getValue();
				}
			}
			
			return Locale.ENGLISH;
		} catch (Exception ex){
			return Locale.ENGLISH;
		}
	}
}
