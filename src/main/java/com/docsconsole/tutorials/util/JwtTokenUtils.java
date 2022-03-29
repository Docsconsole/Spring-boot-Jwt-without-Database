package com.docsconsole.tutorials.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Optional;


@Component
public class JwtTokenUtils {

	@Value("${jwt.secret}")
	private String jwtSecret;

	public Optional<Jws<Claims>> getClaimsFromJwtToken(String jwtToken){
		Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(jwtToken);
		return Optional.ofNullable(claims);
	}
	public Optional<String> getJwtFromRequest(HttpServletRequest request){
		String bearerToken = request.getHeader("Authentication-Data");
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			return Optional.of(bearerToken.substring(7));
		}
		return Optional.of(null);
	}
}
