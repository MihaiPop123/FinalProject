package com.example.FinalProject.controller;

import com.example.FinalProject.dto.request.AddUsersAccountRequest;
import com.example.FinalProject.dto.request.SignInRequest;
import com.example.FinalProject.dto.response.SignInResponse;
import com.example.FinalProject.security.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/final_project/register")
    public ResponseEntity<Void> register(@RequestBody @Valid AddUsersAccountRequest addUsersAccountRequest)
    {
        authService.getUserByEmail(addUsersAccountRequest.getEmail());
        authService.registerUser(addUsersAccountRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/final_project/sign_in")
    public ResponseEntity<SignInResponse> signIn(@RequestBody @Valid SignInRequest signInRequest)
    {
        SignInResponse response = authService.signIn(signInRequest);

        return ResponseEntity.ok().body(response);
    }


}
