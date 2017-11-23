package com.bridgeit.dao;

import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import com.bridgeit.model.Login;
import com.bridgeit.model.User;

public class UserDaoImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Boolean addPerson(User user) {
		Session s = sessionFactory.getCurrentSession();
		int id = (int) s.save(user);
		if (id != 0) {
			return true;
		}
		return false;
	}

	public User authPerson(Login login) {

		Session session = sessionFactory.getCurrentSession();

		Query query = session.createQuery("FROM com.bridgeit.model.User where user_email = :email");
		query.setParameter("email", login.getEmail());

		List<User> ls = query.getResultList();
		if (ls.size() == 1 ) {
			
			User user = ls.get(0);
			if( BCrypt.checkpw(login.getPassWord(), user.getPassWord()))
			{
				return user;
			}
		}
	return null;
	}

	public Boolean authPerson(String email) {

		Session session = sessionFactory.getCurrentSession();

		Query query = session.createQuery("FROM com.bridgeit.model.User where user_email = :email");
		query.setParameter("email", email);
		List<User> ls = query.getResultList();

		if (ls.size() != 1) {
			return false;
		}

		return true;

	}

	public void updatePassWord(Login login) {

		Session session = sessionFactory.getCurrentSession();

		Query query = session
				.createQuery("UPDATE com.bridgeit.model.User set user_password=:password where user_email = :email");
		query.setParameter("email", login.getEmail());
		query.setParameter("password", login.getPassWord());
		query.executeUpdate();
	}

	@Override
	public Boolean addPasswordOTP(Login login) {

		Session session =sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM com.bridgeit.model.Login where user_email = :email");
		query.setParameter("email", login.getEmail());
		System.out.println("haiiaiia");
		List<User> ls = query.getResultList();
		
		if(ls.size() == 0)
		{
			session.save(login);
			return true;
		}
		query = session
				.createQuery("UPDATE com.bridgeit.model.Login set password_OTP=:otp where user_email = :email");
		query.setParameter("email", login.getEmail());
		query.setParameter("otp", login.getOtp());
		query.executeUpdate();
		return true;

	}

	@Override
	public Boolean verifyOTP(Login login) {

		Session session = sessionFactory.getCurrentSession();

		
		Query query = session.createQuery("FROM com.bridgeit.model.Login where user_email = :email");
		query.setParameter("email", login.getEmail());
		Login persistedObj = (Login) query.getSingleResult();

		return persistedObj.getOtp().equals(login.getOtp());

	}

}
