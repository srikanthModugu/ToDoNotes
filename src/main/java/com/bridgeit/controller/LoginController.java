package com.bridgeit.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.bridgeit.model.CustomeResponse;
import com.bridgeit.model.Login;
import com.bridgeit.model.User;
import com.bridgeit.service.EmailService;
import com.bridgeit.service.UserService;
import com.bridgeit.token.Token;
import com.bridgeit.utility.GenereateOtp;
import com.bridgeit.validation.LoginValidator;


@RestController
public class LoginController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private LoginValidator logVal;
	

	
	@Autowired
	private EmailService emailService;
	
	@RequestMapping(value="/login",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomeResponse> login(@RequestBody Login login,BindingResult result,HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		System.out.println("the values"+login);
		
		CustomeResponse myResponse = new CustomeResponse();
		try{
			
		logVal.validate(login, result);
		if(!result.hasErrors())
		{
			User user = userService.authPerson(login);
		
			if(user !=null && user.getIsActive() == true)
			{
				String token = Token.generateToken(String.valueOf(user.getUserId()));
				response.setHeader("Authorization", token);
				System.out.println("Token  "+token);
				myResponse.setMessage("Login SucessFully");
				myResponse.setStatus(1);
				return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.OK);

			}
	        myResponse.setMessage("Invalid  Email/password or is not activated");
	        myResponse.setStatus(-1);
	        return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.UNAUTHORIZED);	
		}
		
        System.out.println(result.getAllErrors());
		myResponse.setMessage(result.toString());
		myResponse.setStatus(-1);
		return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.NOT_ACCEPTABLE);
		}
		catch (Exception e) {
			myResponse.setMessage(result.getAllErrors().toString());
			myResponse.setStatus(-1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}  
	
	 
	
	
	@RequestMapping(value="/logout",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomeResponse> logout(HttpServletRequest request){
		CustomeResponse myResponse = new CustomeResponse();
		myResponse.setMessage("Logout SucessFully");
		myResponse.setStatus(1);
		return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomeResponse> forget(@RequestBody Login login) {
		
		CustomeResponse myResponse = new CustomeResponse();
        
		if (!logVal.validateEmail(login.getEmail())) {
			
			myResponse.setMessage("Email is not valid");
			myResponse.setStatus(-1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.UNAUTHORIZED);

		}

		String otpNum = GenereateOtp.OtpNumber();

		
		login.setOtp(otpNum);
		
		if (!userService.addPasswordOTP(login)) {
			
			myResponse.setMessage("Some Thing went Wrong...Please try Later");
			myResponse.setStatus(-1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.INTERNAL_SERVER_ERROR);  
		}
		emailService.sendOTP(login.getEmail(), otpNum);
		myResponse.setMessage("OTP is Generated");
		myResponse.setStatus(1);
		return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.OK);

	}
	
	
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomeResponse> resetPassword(@RequestBody Login login,HttpServletResponse response) {
		
		CustomeResponse myResponse = new CustomeResponse();
		try{
		if(!userService.verifyOTP(login))
		{
			
			myResponse.setMessage("OTP iS MissMatching");
			myResponse.setStatus(-1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.NOT_ACCEPTABLE);
		}
		
		
		userService.updatePassWord(login);
		
		myResponse.setStatus(1);
		myResponse.setMessage("PassWord Reset Sucessfully");
		return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.OK);
	   }
		catch (Exception e) {
			myResponse.setMessage("Some Thing went Wrong...Please try Later");
			myResponse.setStatus(-1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	

	
}
