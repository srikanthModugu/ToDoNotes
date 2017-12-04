package com.bridgeit.validation;



import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bridgeit.model.Login;
import com.bridgeit.service.UserService;

@Component
public class LoginValidator implements Validator
 {
	
private final String emailRegex = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";

@Autowired
private UserService service;

	
	public boolean supports(Class<?> arg0) 
	{
		
		return false;
	}
     
	public boolean validateEmail(String email)
	{
		
         System.out.println("email "+email);
		 
		System.out.println("its coming");
		if(email!="" &&  service.authPerson(email) && Pattern.matches(emailRegex, email))
		{
			
			return true;
		}
		
		return false;
	}

	
	public void validate(Object arg0, Errors errors) 
	{
		Login user = (Login) arg0;
	 	ValidationUtils.rejectIfEmptyOrWhitespace(errors,"email", "login.email.required");
	 	if (!Pattern.matches(emailRegex, user.getEmail()) && errors.getErrorCount()==0) {
			errors.rejectValue("email","login.email.patren");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"passWord" , "login.password.required");
		if((user.getPassWord().length()<=8 || user.getPassWord().length()>16)){
			System.out.println("haiiii");
			errors.rejectValue("passWord","login.password.patren");
		}

	 }
		
	}


