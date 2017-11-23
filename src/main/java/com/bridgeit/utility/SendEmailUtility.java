package com.bridgeit.utility;

import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class SendEmailUtility {


	public  void sendEmail(String emailTo,String message,String subject)
	{
	
		try 
		{
			Properties props = System.getProperties();
			InputStream in = this.getClass().getResourceAsStream("sendEmail.properties");
			props.load(in);
			
			Session mailSession = Session.getDefaultInstance(props, null);
			mailSession.setDebug(true);

			Message mailMessage = new MimeMessage(mailSession);
			mailMessage.setFrom(new InternetAddress(props.getProperty("fromEmail")));
			mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
			mailMessage.setText(message);
			mailMessage.setSubject(subject);

			Transport transport = mailSession.getTransport("smtp");
			transport.connect("smtp.gmail.com", props.getProperty("fromEmail"), props.getProperty("password"));

			transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
