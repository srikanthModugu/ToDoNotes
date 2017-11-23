package com.bridgeit.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import com.bridgeit.service.UserService;
import com.bridgeit.token.Token;
import com.bridgeit.utility.EmailService;
import com.bridgeit.utility.GenereateOtp;
import com.bridgeit.validation.LoginValidator;


@RestController
public class LoginController {

	@Autowired
	private UserService service;
	
	@Autowired
	private LoginValidator logVal;
	

	
	@Autowired
	private EmailService emailService;
	
	@RequestMapping(value="/login",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomeResponse> login(@RequestBody Login login,BindingResult result,HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		CustomeResponse myResponse = new CustomeResponse();
		try{
		logVal.validate(login, result);
		if(!result.hasErrors())
		{
			User user = service.authPerson(login);
			if(user !=null)
			{
				String token = Token.generateToken(String.valueOf(user.getUserId()));
				System.out.println("Token  "+token);
				myResponse.setMessage("Login SucessFully");
				myResponse.setStatus(1);
				return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.OK);
			}
	        myResponse.setMessage("Invalid  Email or password");
	        myResponse.setStatus(-1);
	        return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.UNAUTHORIZED);	
		}
		

		myResponse.setMessage(result.toString());
		myResponse.setStatus(-1);
		return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.NOT_ACCEPTABLE);
		}
		catch (Exception e) {
			myResponse.setMessage(result.toString());
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
	@RequestMapping(value = "/forget", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomeResponse> forget(@RequestBody Login login) {
		
		CustomeResponse myResponse = new CustomeResponse();

		if (!logVal.validateEmail(login.getEmail())) {
			
			myResponse.setMessage("Email is not valid");
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.BAD_REQUEST);

		}

		String otpNum = GenereateOtp.OtpNumber();

		
		login.setOtp(otpNum);
		
		if (!service.addPasswordOTP(login)) {
			
			myResponse.setMessage("Some Thing went Wrong...Please try Later");
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.BAD_REQUEST);  
		}
		emailService.sendOTP(login.getEmail(), otpNum);
		myResponse.setMessage("OTP is Generated");
		return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomeResponse> resetPassword(@RequestBody Login login) {
		
		CustomeResponse myResponse = new CustomeResponse();
		if(!service.verifyOTP(login)){
			myResponse.setMessage("OTP iS MissMatching");
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.BAD_REQUEST);
		}
		
		service.updatePassWord(login);
		myResponse.setMessage("PassWord Reset Sucessfully");
		return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.OK);
	}

	
}
