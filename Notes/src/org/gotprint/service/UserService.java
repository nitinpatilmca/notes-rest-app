package org.gotprint.service;

import org.gotprint.domain.Users;
import org.gotprint.exceptions.UserException;

public interface UserService {
	public void addUser(Users user) throws UserException; 
}
