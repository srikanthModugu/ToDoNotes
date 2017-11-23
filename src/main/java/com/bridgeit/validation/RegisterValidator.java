package com.bridgeit.validation;



import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.bridgeit.model.User;

public class RegisterValidator implements Validator {
	private final String emailRegex = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
	
	
	public boolean supports(Class<?> arg0) {
	
		return false;
	}


	
	public void validate(Object arg0, Errors errors) {
		User user = (User) arg0;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"email", "register.email.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"passWord" , "register.password.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"phnNo" , "register.name.required");
		
		
		if (!Pattern.matches(emailRegex, user.getEmail())) {
			errors.rejectValue("email","register.email.patren");
		}
		
		if(user.getPassWord().length()<=8 || user.getPassWord().length()>16){
		
			errors.rejectValue("passWord","register.password.patren");
		}
		
		System.out.println(errors.getErrorCount());
	 }
		
	}


