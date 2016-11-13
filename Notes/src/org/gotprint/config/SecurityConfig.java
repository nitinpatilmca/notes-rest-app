package org.gotprint.config;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.gotprint.domain.Users;
import org.gotprint.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	protected static final Logger LOGGER = Logger.getLogger(SecurityConfig.class);

	//private static String REALM="MY_TEST_REALM";
	
	@Autowired
	private UserDetailsService userDetailsService;

	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/rest/**");
	}
	
/*	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		AuthenticationFailureHandler authenticationFailureHandler = new AuthenticationFailureHandlerImpl(); 
		http
		.authorizeRequests()
		.antMatchers("/rest/**").access("hasRole('ROLE_APP_USER')")
		.antMatchers("/app/**").access("hasRole('ROLE_APP_USER')")
		.and()
		.formLogin()
		.loginPage("/login")
		.usernameParameter("userName")
		.passwordParameter("password")
		.defaultSuccessUrl("/app", true)
		.failureUrl("/loginerror")
		.failureHandler(authenticationFailureHandler)
		.and()
		.logout()
		.logoutUrl("/logout")
		.logoutSuccessUrl("/")
		.invalidateHttpSession(true)
		.logoutSuccessUrl("/login")
		.and()
		.exceptionHandling()
		.accessDeniedPage("/accessDenied")
		.and()
		.userDetailsService(userDetailsService);

		//http.csrf().csrfTokenRepository(csrfTokenRepository());

	}*/


/*	@Override
    protected void configure(HttpSecurity http) throws Exception {
  
      http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/rest/**").hasRole("APP_USER")
        .and().httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//We don't need sessions to be created.
    }
     
    @Bean
    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
        return new CustomBasicAuthenticationEntryPoint();
    }
     
     To allow Pre-flight [OPTIONS] request from browser 
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }
    */
	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	
	@Bean
	public Md5PasswordEncoder passwordEncoder() {
		return new Md5PasswordEncoder();
	}


	private CsrfTokenRepository csrfTokenRepository() 	{ 
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository(); 
		repository.setSessionAttributeName("_csrf");
		return repository; 
	}


	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());
	}

	class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2) throws IOException, ServletException {

			Enumeration<String> e = request.getParameterNames();
			while(e.hasMoreElements()){
				String nm = e.nextElement();
				LOGGER.debug("PARAMETER " + nm + " " + request.getParameter(nm));
			}

			// UPDATE FAILED ATTEMPT USING userDetailsService 
			Users user = userDetailsService.findUser(request.getParameter("userName"));
			
			if(user == null)
				request.getRequestDispatcher("/accessDenied").forward(request, response);
			
		}

	}




}
