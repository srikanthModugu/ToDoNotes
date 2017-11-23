package com.bridgeit.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.dao.UserDAO;
import com.bridgeit.model.Login;
import com.bridgeit.model.User;

public class UserService  {
    
	@Autowired
	private UserDAO dao;
    
	@Transactional
	public Boolean addPerson(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(user.getPassWord());
		//BCrypt.hashpw(user.getPassWord(), BCrypt.gensalt(10));
		user.setPassWord(hashedPassword);
		return dao.addPerson(user);
	}

	@Transactional
	public User authPerson(Login login) {
	
		return dao.authPerson(login);
	}

	@Transactional
	public Boolean authPerson(String email) {
		
		return dao.authPerson(email);
	}


	@Transactional
	public void updatePassWord(Login login) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(login.getPassWord());
		login.setPassWord(hashedPassword);
		 dao.updatePassWord(login);
	}
	
	@Transactional
	public Boolean addPasswordOTP(Login login) 
	{
		
		return dao.addPasswordOTP(login);
	}


	@Transactional
	public Boolean verifyOTP(Login login) {
		
		return dao.verifyOTP(login);
	}

}
