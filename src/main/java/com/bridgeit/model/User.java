package com.bridgeit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author bridgeit User Bean Class to store the the details of the user as one
 *         Object.And making easy to pass the user objects or one user object.
 *
 */
@Entity
@Table(name = "FundooUser")
public class User {

	// user unique value given by database
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;

	// user unique mail id & userNmae
	@Column(name = "user_email")
	private String email;

	@Column(name = "user_password")
	private String passWord;

	@Column(name = "user_phnNo")
	private String phnNo;
    
	
	
	public User() {
		super();
	}

	public User(String email, String passWord, String phnNo) {
		super();
		this.email = email;
		this.passWord = passWord;
		this.phnNo = phnNo;
	}

	

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {

		return email;

	}

	public void setEmail(String email) {

		this.email = email;

	}

	public String getPassWord() {

		return passWord;

	}

	public void setPassWord(String passWord) {

		this.passWord = passWord;

	}

	public String getPhnNo() {

		return phnNo;

	}

	public void setPhnNo(String phnNo) {

		this.phnNo = phnNo;

	}


	@Override
	public String toString() {
		return "User [id=" + userId + ", email=" + email + ", passWord=" + passWord + ", phnNo=" + phnNo + "]";
	}

}
