package com.docsconsole.tutorials.service;



import com.docsconsole.tutorials.model.response.ValidateTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class JWTAuthService {

    public ResponseEntity<?> validateUserToken(){
        return ResponseEntity.ok(new ValidateTokenResponse("succeeded"));
    }

    public ResponseEntity<?> validateAdminToken(){
        return ResponseEntity.ok(new ValidateTokenResponse("succeeded"));
    }
}
