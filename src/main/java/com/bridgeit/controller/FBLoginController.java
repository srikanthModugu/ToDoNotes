package com.bridgeit.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bridgeit.model.User;
import com.bridgeit.service.UserService;
import com.bridgeit.socialLogin.FaceBookLogin;
import com.bridgeit.token.Token;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class FBLoginController {
	
	@Autowired
	private FaceBookLogin fbLogin;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/faceBookLogin")
	public void facebookConnection(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String unid=UUID.randomUUID().toString();
		request.getSession().setAttribute("STATE", unid);
	
		String faceBookLogInURL= fbLogin.getFaceBookURL(unid);
        System.out.println("its coming here");
		response.sendRedirect(faceBookLogInURL);
		
}
	
	@RequestMapping(value="/connectFaceBook")
	public void redirectFromfacebook(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		
		String sessionState = (String) request.getSession().getAttribute("STATE");
		String fbstate = request.getParameter("state");
		if (sessionState == null || !sessionState.equals(fbstate)) {

			response.sendRedirect("faceBookLogin");
			return;

		}
		String error = request.getParameter("error");
		if (error != null && error.trim().isEmpty()) {
			System.out.println("login");
			//response.sendRedirect("login");
			return;
		}
		
		String authCode = request.getParameter("code");
		String fbaccessToken = fbLogin.getAccessToken(authCode);
		JsonNode profile = fbLogin.getUserProfile(fbaccessToken);
		User user = userService.getuserByEmail(profile.get("email").asText());

		if (user == null) 
		{
			user = new User();
			user.setEmail(profile.get("email").asText());
			user.setIsActive(true);
			int id = userService.addPerson(user);
		    String token =	Token.generateToken(String.valueOf(id));
	        System.out.println("token at Fb "+token);
			System.out.println("send to home");
			response.sendRedirect("http://localhost:8080/ToDoNotes/user/home");
			return;
				
		} 
		else{
		 String token=Token.generateToken(String.valueOf(user.getUserId()));
		 System.out.println("token at Fb "+token);
		 System.out.println("send to home");
		response.sendRedirect("http://localhost:8080/ToDoNotesApp/user/home");
		 return;
		}

}

}
