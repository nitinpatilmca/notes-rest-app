package org.gotprint.service;

import org.apache.log4j.Logger;
import org.gotprint.domain.Users;
import org.gotprint.persistence.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	protected static final Logger LOGGER = Logger.getLogger(UserDetailsService.class);
	
	@Autowired
	private UserDetailsRepository userDetailsRepository;
	
	public Users loginValidation(String username,String password) {		
		Users userDetails = userDetailsRepository.loginValidation(username,password);
		return userDetails;
	}
	
   @Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {		
		
		LOGGER.debug(" ==========================      Got user id: "+userId);
		Users userDetails = userDetailsRepository.findOne(userId);
		
		LOGGER.debug(userDetails);
		
		if(userDetails == null){
			LOGGER.error("User "+userId+" not found");
			throw new UsernameNotFoundException("User "+userId+" is not registered. Please contact support."); 
		}
		return userDetails;		
	}

	public Users findUser(String userName) {
		return userDetailsRepository.findOne(userName);
	}
}
