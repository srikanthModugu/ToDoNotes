package com.bridgeit.controller;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bridgeit.model.User;
import com.bridgeit.service.UserService;
import com.bridgeit.socialLogin.GoogleLogin;
import com.bridgeit.token.Token;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class GoogleLoginController {


	@Autowired
	private GoogleLogin googleLogin;
	
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="/googleLogin")
	public void googleConnection(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String unid=UUID.randomUUID().toString();
		request.getSession().setAttribute("STATE", unid);
		String googleLogInURL= googleLogin.getGoogleURL(unid);
		response.sendRedirect(googleLogInURL);
		return;
	}

	@RequestMapping(value="/connectGoogle")
	public void redirectFromGoogle(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {
		
		String sessionState = (String) request.getSession().getAttribute("STATE");
		String googlestate = request.getParameter("state");

		if (sessionState == null || !sessionState.equals(googlestate)) {

			response.sendRedirect("googleLogin");

		}

	
		String error = request.getParameter("error");

	

		if (error != null && error.trim().isEmpty()) {

			response.sendRedirect("login");

		}

		String authCode=request.getParameter("code");
	

		String googleaccessToken = googleLogin.getAccessToken(authCode);
	

		JsonNode profile = googleLogin.getUserProfile(googleaccessToken);

		System.out.println(profile.get("email").asText());
		User user = userService.getuserByEmail(profile.get("email").asText());
        
		if (user == null) 
		{
			user = new User();
			user.setEmail(profile.get("email").asText());
			user.setIsActive(true);
			int id = userService.addPerson(user);
		 
		    if(id>0){
		    	   String token =	Token.generateToken(String.valueOf(id));
				
		    		
				    session = request.getSession();
				response.setHeader("Authorization", token);
				session.setAttribute("token", token);
				response.sendRedirect("http://localhost:8080/ToDoNotes/#!/dummy");
				return;
			}
			else{
			
				response.sendRedirect("http://localhost:8080/ToDoNotes/#!/login");
				return;
			}
	}
	else{
		
		String token =	Token.generateToken(String.valueOf(user.getUserId()));
		
	    session = request.getSession();
		session.setAttribute("token", token);
		response.sendRedirect("http://localhost:8080/ToDoNotes/#!/dummy");
		return;
	
        }

}
	
}
