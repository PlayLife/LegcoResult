package com.playlife.legcoresult.utility;

import java.util.Locale;

import org.springframework.context.MessageSource;


public class LogicException extends RuntimeException  {
	private static final long serialVersionUID = 1L;
	public int errorCode = -1;
	public Exception exception = null;
	
	public LogicException(int errorCode, Exception exception){
		this.exception = exception;
		this.errorCode = errorCode;
	}
	public LogicException(int errorCode){
		this.errorCode = errorCode;
	}	
		
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
		
	public String getErrorMessage(MessageSource messageSource, Locale locale){
		try {
			String errorName = "validation." + Math.abs(errorCode);
			return messageSource.getMessage(errorName, null, locale) + " (Error Code : " + errorCode + ")";
		} catch (Exception ex){
			return messageSource.getMessage("error.unknown", null, locale) + " (Error Code : " + errorCode + ")";
		}
	}
}
