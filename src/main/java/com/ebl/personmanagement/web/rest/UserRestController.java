package com.ebl.personmanagement.web.rest;

import static com.ebl.personmanagement.security.SecurityConstants.REFRESH_TOKEN;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ebl.personmanagement.dao.UserRepository;
import com.ebl.personmanagement.dao.model.ApplicationUser;
import com.ebl.personmanagement.security.JwtUtil;
import com.ebl.personmanagement.security.dto.JwtToken;
import com.ebl.personmanagement.web.exception.AlreadyExists;

@RestController
public class UserRestController {
	private static final Logger log = LogManager.getLogger(UserRestController.class);
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostMapping("/access-tokens/refresh")
	public JwtToken refresh(HttpServletRequest req) {
		String email = (String) req.getServletContext().getAttribute(REFRESH_TOKEN);
		if(email != null && !email.isEmpty()){
			log.debug("Creating token for email : {}", email);
			JwtToken token = JwtUtil.generateToken(email);
	    	req.getServletContext().setAttribute(token.getRefreshToken(), email);
	    	return new JwtToken(token.getJwt(), null);
		}
		log.debug("No existing Token: {} found");
		return null;
	}

	@GetMapping("/me")
	public ApplicationUser currentUser(Principal principal) {
		String name = principal.getName();
		ApplicationUser user = userRepository.findByEmail(name);
		
		user.setCreatedAt(null);
		user.setId(null);
		user.setPassword(null);
		return user;
	}

	@PostMapping("/users")
	@ResponseStatus(value = HttpStatus.CREATED)
	public JwtToken save(@RequestBody ApplicationUser user, HttpServletRequest req) throws AlreadyExists {
		ApplicationUser findByEmail = userRepository.findByEmail(user.getEmail());
		if(findByEmail !=null){
			throw new AlreadyExists("User Already Exists");
		}
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		JwtToken resJson = JwtUtil.generateToken(user.getEmail());
    	req.getServletContext().setAttribute(resJson.getRefreshToken(), user.getEmail());
		return resJson;
	}

	@GetMapping("/users")
	@ResponseStatus(value = HttpStatus.OK)
	public List<ApplicationUser> getUsers() {
		return userRepository.findAll();
	}
}
