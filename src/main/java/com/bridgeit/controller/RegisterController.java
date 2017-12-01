package com.bridgeit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.bridgeit.model.CustomeResponse;
import com.bridgeit.model.User;
import com.bridgeit.service.UserService;
import com.bridgeit.token.Token;
import com.bridgeit.service.EmailService;
import com.bridgeit.validation.RegisterValidator;

@RestController
public class RegisterController {

	@Autowired
	private UserService userService;

	@Autowired
	private RegisterValidator regVal;

	@Autowired
	private EmailService emailService;

	@RequestMapping(value = "/userRegister", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomeResponse> Register(@RequestBody User user, BindingResult result,
			HttpServletRequest request) {
		System.out.println("register values "+user);
		CustomeResponse myResponse = new CustomeResponse();
		try{
		regVal.validate(user, result);

		if (!result.hasErrors()) {
			if (!userService.authPerson(user.getEmail())) {
				user.setIsActive(false);
				int id = userService.addPerson(user);
                System.out.println("printing id value "+id);
				if (id != 0) {
					user.setUserId(id);
					emailService.sendWelcomeEmail(user, request.getRequestURL().toString());
					myResponse.setMessage("Registration completed Sucessfully");
					myResponse.setStatus(1);
					return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.OK);

				}
				myResponse.setMessage("Registration  Failed");
				myResponse.setStatus(-1);
				return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.BAD_REQUEST);
			}
			myResponse.setMessage("Already account Created on this mail");
			myResponse.setStatus(-1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.CONFLICT);
		}
		myResponse.setMessage(result.toString());
		myResponse.setStatus(-1);
		return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.NOT_ACCEPTABLE);
		}
		catch (Exception e) {
			myResponse.setMessage("problem in server");
			myResponse.setStatus(-1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	
	@RequestMapping(value = "/activate/{token:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomeResponse> activateUser(@PathVariable("token") String token,
			HttpServletResponse response) {
		
		CustomeResponse myResponse = new CustomeResponse();
		try {
			int id = Integer.valueOf(Token.verfyToken(token));
			
			if (id > 0) {
				User user = userService.getUserById(id);
				if (user == null) 
				{
					myResponse.setMessage("Link is expiered or not at use ");
					myResponse.setStatus(-1);
					return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.UNAUTHORIZED);
				}
				
				userService.updateUser(id);
					//response.sendRedirect("");
					myResponse.setMessage("Account activation is Done");
					myResponse.setStatus(1);
					return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.OK);
				
			}
			myResponse.setMessage("token is invalid");
			myResponse.setStatus(-1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.NOT_ACCEPTABLE);
		} 
		catch (Exception e) 
		{
			myResponse.setMessage("problem in server");
			myResponse.setStatus(-1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
