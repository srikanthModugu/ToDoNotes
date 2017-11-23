package com.bridgeit.dao;

import com.bridgeit.model.Login;
import com.bridgeit.model.User;


public interface UserDAO {

	
	public Boolean addPerson(User user);
	public User authPerson(Login login);
	public Boolean authPerson(String email);
	public void updatePassWord(Login login);
	public Boolean addPasswordOTP(Login login);
	public Boolean verifyOTP(Login login);
	
}
