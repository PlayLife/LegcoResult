package com.playlife.legcoresult.presentation.validator;

import org.springframework.stereotype.Component;

import com.playlife.legcoresult.utility.ValidationException;

@Component
public class AppUserValidator {
	
	public void validateUsername(String username){
		if (username.trim().isEmpty())
			throw new ValidationException(-50000);
		if (username.length() < 3 || username.length() > 20)
			throw new ValidationException(-50001);
	}
}