package com.bridgeit.socialLogin;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GoogleLogin {
	
	private static final String googleClientId="394396314983-1g5h9gsqc2s1ce952danmekdl7nlu107.apps.googleusercontent.com";
	private static final String googleSecretId="XDiuz-Zx6lLpCzuFg38HIK5d";
	public static final String Redirect_URI = "http://localhost:8080/ToDoNotes/connectGoogle";
    public String GmailGetUserUrl = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    
  

	public String getGoogleURL(String unid) {

		String googleLoginURL = "";

		try {
			
			googleLoginURL = "https://accounts.google.com/o/oauth2/auth?client_id=" + googleClientId + "&redirect_uri="
					+ URLEncoder.encode(Redirect_URI, "UTF-8") + "&state=" + unid
					+ "&response_type=code&scope=profile email&approval_prompt=force&access_type=offline";
		
		} catch (UnsupportedEncodingException e) {
			
			
			e.printStackTrace();
		
		}
		
		
		return googleLoginURL;
	}
	
	public String getAccessToken(String authCode) {
		
		String accessTokenURL = "https://accounts.google.com/o/oauth2/token";

		ResteasyClient restCall = new ResteasyClientBuilder().build();
		ResteasyWebTarget target=restCall.target(accessTokenURL);
		
		Form form=new Form();
		
		form.param("client_id", googleClientId);
		form.param("client_secret", googleSecretId);
		form.param("redirect_uri", Redirect_URI);
		form.param("code", authCode);
		form.param("grant_type", "authorization_code");
		
		Response response = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.form(form));
		
		String token = response.readEntity(String.class);
		
		ObjectMapper mapper=new ObjectMapper();
		String acc_token = null;
		
		try {
			
			acc_token = mapper.readTree(token).get("access_token").asText();
			
			
		} catch (IOException e) {
			
			
			e.printStackTrace();
		
		}
		
		restCall.close();
		
		return acc_token;	
	
	}
	
	public JsonNode getUserProfile(String googleaccessToken) {
		
		System.out.println("gmail details " + GmailGetUserUrl);
		GmailGetUserUrl = GmailGetUserUrl + googleaccessToken;
		ResteasyClient restCall = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = restCall.target(GmailGetUserUrl);

		String headerAuth = "Bearer " + googleaccessToken;
		Response response = target.request()
				
				.header("Authorization", headerAuth)
				.accept(MediaType.APPLICATION_JSON)
				.get();

		String profile=response.readEntity(String.class);
		
		ObjectMapper mapper=new ObjectMapper();
		
		JsonNode googleprofile = null;
		
		try {
			
			googleprofile = mapper.readTree(profile);
		
		} catch (IOException e) {
			
			e.printStackTrace();
		
		}
		
		restCall.close();
		System.out.println("sending google profile "+googleprofile);
		return googleprofile;
	}

}


