package com.bridgeit.token;


import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;




public class Token{
	
	private static String key = "xyz123";
	
	public static String generateToken(String id) {
		Calendar calendar = Calendar.getInstance();
		Date currentTime = calendar.getTime();
		calendar.add(Calendar.HOUR,1 );
		Date expirationDate = calendar.getTime();

		String token = Jwts.builder()
				.setSubject("mytoken")
				.setId(id)
				.setExpiration(expirationDate)
				.setIssuedAt(currentTime)
				.signWith(SignatureAlgorithm.HS512, key)
				.compact();
		return token ;
}

	
	public static String verfyToken(String token) throws IOException {
		try {
			System.out.println("printing token here  "+token);
			 Claims claims = Jwts.parser()
					 .setSigningKey(key)
					 .parseClaimsJws(token).getBody();
			System.out.println("Token is ok");
			return claims.getId();
		} catch (Exception ex) {
				System.out.println(token);
				ex.printStackTrace();
			
			
				return null;
				
			}

	
	}

}
