package com.docsconsole.tutorials.controller;


import com.docsconsole.tutorials.model.request.ValidateTokenRequest;
import com.docsconsole.tutorials.service.JWTAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/jwt")
public class JwtAuthController {

	@Autowired
	private JWTAuthService jWTAuthService;

	@PreAuthorize("hasRole('USER')")
	@PostMapping(value = "/validate/userToken", produces = "application/json")
	public ResponseEntity<?> validateUserToken(@RequestBody ValidateTokenRequest validateTokenRequest) {
		return jWTAuthService.validateUserToken();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/validate/adminToken", produces = "application/json")
	public ResponseEntity<?> validateAdminToken(@RequestBody ValidateTokenRequest validateTokenRequest) {
		return jWTAuthService.validateAdminToken();
	}

}
