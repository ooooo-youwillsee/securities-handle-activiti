package com.ooooo.activiti.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static java.util.Arrays.asList;

/**
 * @author leizhijie
 * @since 1.0.0
 */
@Slf4j
@Configuration
public class ActivitySecurityConfiguration {
	
	
	@Bean
	public UserDetailsService myUserDetailsService() {
		
		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
		
		String[][] usersGroupsAndRoles = {
				{"bob", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},
				{"john", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},
				{"hannah", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},
				{"other", "password", "ROLE_ACTIVITI_USER", "GROUP_otherTeam"},
				{"system", "password", "ROLE_ACTIVITI_USER"},
				{"admin", "password", "ROLE_ACTIVITI_ADMIN"},
				};
		
		for (String[] user : usersGroupsAndRoles) {
			List<String> authoritiesStrings = asList(Arrays.copyOfRange(user, 2, user.length));
			log.info("> Registering new user: " + user[0] + " with the following Authorities[" + authoritiesStrings + "]");
			inMemoryUserDetailsManager.createUser(new User(user[0], passwordEncoder().encode(user[1]),
			                                               authoritiesStrings.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())));
		}
		
		return inMemoryUserDetailsManager;
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
