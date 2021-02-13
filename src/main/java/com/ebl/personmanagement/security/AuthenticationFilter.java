package com.ebl.personmanagement.security;

import static com.ebl.personmanagement.security.SecurityConstants.HEADER_NAME;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ebl.personmanagement.dao.model.ApplicationUser;
import com.ebl.personmanagement.security.dto.JwtToken;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		setFilterProcessesUrl("/access-tokens");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			String method = req.getMethod();

			ApplicationUser applicationUser = null;
			String userName = null;
			String password = null;
			if (!"delete".equalsIgnoreCase(method)) {
				applicationUser = new ObjectMapper().readValue(req.getInputStream(), ApplicationUser.class);
				userName = applicationUser.getEmail();
				password = applicationUser.getPassword();
			} else {
				String tokenStr = req.getHeader(HEADER_NAME);
				if (tokenStr != null) {
					userName = JwtUtil.verifyToken(tokenStr);
				}
				return new UsernamePasswordAuthenticationToken(userName, password);
			}
			return authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(userName, password, new ArrayList<>()));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		String method = req.getMethod();
		JwtToken token = null;
		if ("delete".equalsIgnoreCase(method)) {
			token = new ObjectMapper().readValue(req.getInputStream(), JwtToken.class);
			req.getServletContext().removeAttribute(token.getRefreshToken());
			res.setStatus(HttpServletResponse.SC_NO_CONTENT);
		}else{
		String username = ((User) auth.getPrincipal()).getUsername();
		JwtToken resJson = JwtUtil.generateToken(username);
		req.getServletContext().setAttribute(resJson.getRefreshToken(), username);
		res.setStatus(HttpServletResponse.SC_CREATED);
		res.getWriter().write(new ObjectMapper().writeValueAsString(resJson));
		res.getWriter().flush();
		}

	}

}
