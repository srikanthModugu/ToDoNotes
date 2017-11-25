package com.bridgeit.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.model.User;
import com.bridgeit.token.Token;
import com.bridgeit.utility.SendEmailUtility;



public class EmailService {

	@Autowired
	private SendEmailUtility email;
	
	public void sendWelcomeEmail(User user,String url)
	{ 
		
		String token=Token.generateToken(String.valueOf(user.getUserId()));
		url=url.substring(0, url.lastIndexOf("/"))+"/activate/"+token;
		String message = " you have Sucessfully registered in My app Thankyou\n"+"Click the link to activate\n"+url; 
		String subject = "Registertion sucessfull";
		email.sendEmail(user.getEmail(), message, subject);
	}
	public void sendOTP(String emailTo,String otp)
	{
		String message = "OTP for Reset PassWord/n "+otp; 
		String subject = "Reset Password";
		email.sendEmail(emailTo, message, subject);
	}

}
