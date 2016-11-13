package org.gotprint.controller;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	protected static final Logger LOGGER = Logger.getLogger(LoginController.class);	
	
	@RequestMapping({"/", "/login"})
	public ModelAndView loginPage() {
		LOGGER.debug("User Logging");
		ModelAndView model = new ModelAndView();
		model.setViewName("login");
		return model;
	}
		
	@RequestMapping({"/accessDenied"})
	public ModelAndView accessDenied() {
		ModelAndView model = new ModelAndView();
		model.addObject("error", "Authorization Failed. Please contact system admin.");
		model.setViewName("login");
		return model;
	}
	
	@RequestMapping("/app")
	public ModelAndView homePage(Locale locale, HttpServletRequest httpServletRequest) throws JsonGenerationException, JsonMappingException, IOException {

		ModelAndView model = new ModelAndView();
		String username = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			  username = ((UserDetails)principal).getUsername();
		} else {
			  username = principal.toString();
		}		
		model.setViewName("default");	
        
		return model;

	}
}