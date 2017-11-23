package com.bridgeit.utility;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.model.User;



public class EmailService {

	@Autowired
	private SendEmailUtility email;
	
	public void sendWelcomeEmail(User user)
	{
		String message = " you have Sucessfully registered in My app Thankyou"; 
		String subject = "Registertion sucessfull";
		email.sendEmail(user.getEmail(), message, subject);
	}
	public void sendOTP(String emailTo,String otp)
	{
		String message = "OTP for Reset PassWord "+otp; 
		String subject = "Reset Password";
		email.sendEmail(emailTo, message, subject);
	}

}
