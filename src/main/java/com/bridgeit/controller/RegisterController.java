package com.bridgeit.controller;

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
import com.bridgeit.model.User;
import com.bridgeit.service.UserService;
import com.bridgeit.utility.EmailService;
import com.bridgeit.validation.RegisterValidator;


@RestController
public class RegisterController {

	@Autowired
	private UserService service;
	
	@Autowired
	private RegisterValidator regVal;
	
	@Autowired
	private EmailService emailService;
	
	
	@RequestMapping(value="/register",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomeResponse>  Register(@RequestBody User user,BindingResult result)  {
		CustomeResponse myResponse = new CustomeResponse();
		regVal.validate(user, result);
	
		if(!result.hasErrors())
		{
			if(!service.authPerson(user.getEmail()))
			{
				
			  if(service.addPerson(user))
			   {
				  
				emailService.sendWelcomeEmail(user);
				myResponse.setMessage("Registration completed Sucessfully");
				return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.OK);
				
			    }
			    myResponse.setMessage("Registration  Failed");
				return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.BAD_REQUEST);
			}
			myResponse.setMessage("Already account Created on this mail");
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.CONFLICT);
		}
		myResponse.setMessage(result.toString());
		return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.NOT_ACCEPTABLE);
	}
}
