package org.gotprint.service;

import org.gotprint.domain.Users;
import org.gotprint.exceptions.UserException;
import org.gotprint.persistence.UserDetailsRepository;
import org.gotprint.utils.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserValidator emailValidator;

	@Autowired
	private UserDetailsRepository userDetailsRepository;
	
	@Override
	@Transactional
	public void addUser(final Users user) throws UserException {
		emailValidator.validEmail(user);
		emailValidator.validPassword(user);
		emailValidator.isUniqueEmailId(userDetailsRepository.findUserByEmailId(user.getEmail()));
		userDetailsRepository.save(user);
	}

}
