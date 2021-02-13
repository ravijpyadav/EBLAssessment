package com.ebl.personmanagement.service;

import java.util.Collection;
import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ebl.personmanagement.dao.UserRepository;
import com.ebl.personmanagement.dao.model.ApplicationUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private static final Logger log = LogManager.getLogger(UserDetailsServiceImpl.class);
    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = userRepository.findByEmail(username);
        if (applicationUser == null) {
        	log.error("User with usename : {} doesn not exist.", username);
            throw new UsernameNotFoundException(username);
        }
        Collection<? extends GrantedAuthority> emptyList = Collections.emptyList();
		return new User(applicationUser.getEmail(), applicationUser.getPassword(), emptyList);
    }


}
