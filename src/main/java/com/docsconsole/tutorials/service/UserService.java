package com.docsconsole.tutorials.service;

import com.docsconsole.tutorials.model.security.RoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

	private String name;
	private String userName;
	private String roleName;
	private String isEnabled;
	private String email;
	private String password;

	public UserDetails loadUser(String name, String userName,String roleName,String isEnabled,String email,String password){
		this.name = name;
		this.userName = userName;
		this.roleName = roleName;
		this.isEnabled = isEnabled;
		this.email = email;
		this.password = password;
		return loadUserByUsername(userName);

	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		List<GrantedAuthority> authorities = new ArrayList<>();
		if("user".equalsIgnoreCase(this.roleName))
			authorities.add(new SimpleGrantedAuthority(RoleType.ROLE_USER.toString()));
		else if("admin".equalsIgnoreCase(this.roleName))
		authorities.add(new SimpleGrantedAuthority(RoleType.ROLE_ADMIN.toString()));
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(this.email, this.password, authorities);
		return userDetails;

	}

}