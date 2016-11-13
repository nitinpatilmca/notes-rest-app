package org.gotprint.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gotprint.domain.Users;
import org.gotprint.exceptions.UserException;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private static final String PASSWORD_PATTERN =  "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,8})";

	private Pattern emailPattern  = Pattern.compile(EMAIL_PATTERN);
	private Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);;
	
	/*
	 * Validate User Email Id
	 */
	public boolean validEmail(Users user) throws UserException {
		Matcher matcher = emailPattern.matcher(user.getEmail());
		if(!matcher.matches()){
			throw new UserException("Invalid email id  - " + user.getEmail());
		}
		return true; 
	}

	/*
	 * Validate User Passwrod
	 */
	public boolean validPassword(Users user) throws UserException {
		Matcher matcher = passwordPattern.matcher(user.getPassword());
		if(!matcher.matches()){
			throw new UserException("Invalid password, length must be {8} any character - " + user.getPassword());
		}
		return true; 
	}
	
	/*
	 * Returns true if user of object is null
	 */
	public boolean isUniqueEmailId(Users user) throws UserException {		
		if(user != null){
			throw new UserException("Email is already taken by other user, Please choose valid - " + user.getEmail());
		}
		return true; 
	}
	
}
