package com.bridgeit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.dao.UserDAO;
import com.bridgeit.model.Login;
import com.bridgeit.model.User;
import com.bridgeit.token.Token;

public class UserService  {

	@Autowired
	private UserDAO userDao;

	@Transactional
	public int addPerson(User user) {
		if(user.getPassWord() != null){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(user.getPassWord());
		user.setPassWord(hashedPassword);
		}
		// BCrypt.hashpw(user.getPassWord(), BCrypt.gensalt(10));
		
		return userDao.addPerson(user);
	}

	@Transactional
	public String authUser(Login login) {

		User user = userDao.authPerson(login);
		
		String token = null;
		
		if(user != null && user.getIsActive() ){
			token = Token.generateToken(String.valueOf(user.getUserId()));
			return token;
		}
	

		return token;
		
	}

	@Transactional
	public Boolean authPerson(String email) {

		return userDao.authPerson(email);
	}

	@Transactional
	public void updatePassWord(Login login) {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(login.getPassWord());
		login.setPassWord(hashedPassword);
		userDao.updatePassWord(login);
	}

	
	@Transactional
	public Boolean addPasswordOTP(Login login) {

		return userDao.addPasswordOTP(login);
	}

	
	@Transactional
	public Boolean verifyOTP(Login login) {

		return userDao.verifyOTP(login);
	}

	
	@Transactional
	public void updateUser(int id) {
		 userDao.updateUser(id);
	}

	
	@Transactional
	public User getUserById(int id) {
		return userDao.getUserById(id);
	}

	@Transactional
	public User getuserByEmail(String email) {
		
		return userDao.getuserByEmail(email);
	}
	
	

}
