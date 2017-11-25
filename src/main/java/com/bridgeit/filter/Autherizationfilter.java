package com.bridgeit.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bridgeit.token.Token;


public class Autherizationfilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
 System.out.println("it si coming here");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
	
		
		try {

			String token = request.getHeader("token");
			String userId = Token.verfyToken(token);
			System.out.println("id value "+userId);
			if (userId != null) {
				request.setAttribute("userid", userId);
				chain.doFilter(req, res);
				return;
			}
			response.setContentType("application/json");
			String jsonResp = "{\"status\":\"404\",\"errorMessage\":\"Exception Invalid token....\"}";
			response.getWriter().write(jsonResp);
			return;

		} 
		
		catch (Exception e) {

			response.setContentType("application/json");
			String jsonResp = "{\"status\":\"500\",\"errorMessage\":\"Exception Invalid token....\"}";
			response.getWriter().write(jsonResp);
			return;
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	
		
	}

}
