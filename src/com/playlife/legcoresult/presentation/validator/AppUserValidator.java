package com.playlife.legcoresult.presentation.validator;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.playlife.legcoresult.logic.AppUserService;
import com.playlife.legcoresult.utility.ValidationException;

@Component
public class AppUserValidator {
	@Autowired
	AppUserService appUserService;
	
	public void validateEmail(String email) {
		if (email.trim().isEmpty())
			throw new ValidationException(-50003);
		if (!Pattern.compile(".+@.+\\.[a-z]+").matcher(email).matches())
			throw new ValidationException(-50004);
		if (appUserService.checkEmailExists(email))
			throw new ValidationException(-50005);
	}
	
	public void validateUsername(String username){
		if (username.trim().isEmpty())
			throw new ValidationException(-50000);
		if (username.length() < 3 || username.length() > 20)
			throw new ValidationException(-50001);
	}
}