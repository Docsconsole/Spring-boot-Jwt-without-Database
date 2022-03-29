package com.docsconsole.tutorials.security;

import com.docsconsole.tutorials.service.UserService;
import com.docsconsole.tutorials.util.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtTokenUtils jwtTokenUtils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authentication-Data");

		Optional<String> optionalJwtToken = jwtTokenUtils.getJwtFromRequest(request);
		if (optionalJwtToken.isPresent() && SecurityContextHolder.getContext().getAuthentication() == null) {

			String jwtToken = optionalJwtToken.get();
			Optional<Jws<Claims>> OptionalClaims = jwtTokenUtils.getClaimsFromJwtToken(jwtToken);
			if (OptionalClaims.isPresent()) {

				Jws<Claims> jwsClaims = OptionalClaims.get();
				Claims claimsBody = jwsClaims.getBody();
				String userName = (String) claimsBody.get("userName");
				String displayName = (String) claimsBody.get("displayName");
				String roleName = (String) claimsBody.get("roleName");
				String isEnabled = (String) claimsBody.get("isEnabled");
				String email = (String) claimsBody.get("email");
				String password = (String) claimsBody.get("password");

				UserDetails userDetails = this.userService.loadUser(userName,displayName,roleName,isEnabled,email,password);
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response);
	}

}
