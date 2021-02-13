package com.ebl.personmanagement.security;

public abstract class SecurityConstants {
	private SecurityConstants() {}
    public static final String SIGN_UP_URL = "/users";
    public static final String KEY = "q3t6w9z$C&F)J@NcQfTjWnZr4u7x!A%D*G-KaPdSgUkXp2s5v8y/B?E(H+MbQeTh";
    public static final String HEADER_NAME = "X-Access-Token";
    public static final Long EXPIRATION_TIME = 1000L*60*10;
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String JWT_STR = "jwt";
}
