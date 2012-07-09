package com.playlife.legcoresult.utility;

import java.util.ArrayList;
import java.util.Locale;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.MessageSource;

public class JSONConverter {
	public static JSONObject constructError(Exception exception, MessageSource messageSource, Locale locale){
		JSONObject obj_return = new JSONObject();
		String s_displayMessage = "";
		
		ArrayList<PersistenceException> persistenceList = new ArrayList<PersistenceException>();
		ArrayList<LogicException> logicList = new ArrayList<LogicException>();
		ArrayList<PresentationException> presentationList = new ArrayList<PresentationException>();
		ArrayList<ValidationException> validatorList = new ArrayList<ValidationException>();
		ArrayList<Exception> exceptionList = new ArrayList<Exception>();
		
		while (exception != null){
			if (exception instanceof PersistenceException){
				persistenceList.add((PersistenceException) exception);
				exception = ((PersistenceException) exception).getException();
			} else if (exception instanceof LogicException) {
				logicList.add((LogicException) exception);
				exception = ((LogicException) exception).getException();
			} else if (exception instanceof ValidationException) {
				validatorList.add((ValidationException) exception);
				exception = ((ValidationException) exception).getException();
			} else if (exception instanceof PresentationException) {
				presentationList.add((PresentationException) exception);
				exception = ((PresentationException) exception).getException();
			} else { 
				exceptionList.add((Exception) exception);
				exception = null;
			}
		}
		
		
		JSONArray arr_validator = new JSONArray();
		for (ValidationException validatorException : validatorList){
			if (s_displayMessage.isEmpty() && validatorList.get(validatorList.size()-1).equals(validatorException))
				s_displayMessage = validatorException.getErrorMessage(messageSource, locale);
			JSONObject obj_validator = new JSONObject(); 
			obj_validator.put("errorCode", validatorException.getErrorCode());
			obj_validator.put("msg", validatorException.getErrorMessage(messageSource, locale));
			arr_validator.add(obj_validator);
		}
		
		JSONArray arr_presentation = new JSONArray();
		for (PresentationException presentationException : presentationList){
			if (s_displayMessage.isEmpty() && presentationList.get(presentationList.size()-1).equals(presentationException))
				s_displayMessage = presentationException.getErrorMessage(messageSource, locale);
			JSONObject obj_presentation = new JSONObject(); 
			obj_presentation.put("errorCode", presentationException.getErrorCode());
			obj_presentation.put("msg", presentationException.getErrorMessage(messageSource, locale));
			arr_presentation.add(obj_presentation);
		}
		
		JSONArray arr_logic = new JSONArray();
		for (LogicException logicException : logicList){
			if (s_displayMessage.isEmpty() && logicList.get(logicList.size()-1).equals(logicException))
				s_displayMessage = logicException.getErrorMessage(messageSource, locale);
			JSONObject obj_logic = new JSONObject(); 
			obj_logic.put("errorCode", logicException.getErrorCode());
			obj_logic.put("msg", logicException.getErrorMessage(messageSource, locale));
			arr_logic.add(obj_logic);
		}
		
		JSONArray arr_persistence = new JSONArray();
		for (PersistenceException persistenceException : persistenceList){
			if (s_displayMessage.isEmpty() && persistenceList.get(persistenceList.size()-1).equals(persistenceException))
				s_displayMessage = persistenceException.getErrorMessage(messageSource, locale);
			JSONObject obj_persistence = new JSONObject(); 
			obj_persistence.put("errorCode", persistenceException.getErrorCode());
			obj_persistence.put("msg", persistenceException.getErrorMessage(messageSource, locale));
			arr_persistence.add(obj_persistence);
		}
		
		JSONArray arr_exception = new JSONArray();
		for (Exception _exception : exceptionList){
			if (s_displayMessage.isEmpty())
				s_displayMessage = _exception.getStackTrace().toString();
			JSONObject obj_validator = new JSONObject(); 
			obj_validator.put("name", _exception.toString());
			obj_validator.put("msg", _exception.getMessage());
			obj_validator.put("trace", _exception.getStackTrace());
			arr_exception.add(obj_validator);
		}
				
		obj_return.put("persistence", arr_persistence);
		obj_return.put("logic", arr_logic);
		obj_return.put("presentation", arr_presentation);
		obj_return.put("validator", arr_validator);
		obj_return.put("exception", arr_exception);
		obj_return.put("displayMessage", s_displayMessage);
		return obj_return;
	}
}
