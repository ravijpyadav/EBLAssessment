package com.ebl.personmanagement.security.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class JwtToken {
	private String jwt;
	
	@JsonProperty("refresh_token")
	private String refreshToken;
	
	@JsonCreator
	public JwtToken(@JsonProperty("jwt") String jwt,@JsonProperty("refresh_token") String refreshToken) {
		this.jwt = jwt;
		this.refreshToken = refreshToken;
	}
	public String getJwt() {
		return jwt;
	}
	public String getRefreshToken() {
		return refreshToken;
	}

}
