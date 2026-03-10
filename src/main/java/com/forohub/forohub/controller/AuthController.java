package com.forohub.forohub.controller;

import com.forohub.forohub.dto.LoginRequest;
import com.forohub.forohub.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthenticationManager authManager;
  private final JwtService jwtService;

  public AuthController(AuthenticationManager authManager, JwtService jwtService) {
    this.authManager = authManager;
    this.jwtService = jwtService;
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) {
    System.out.println(new BCryptPasswordEncoder().encode("123456"));
    try {
      // Authenticate the user (username + password)
      authManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              request.username(),
              request.password()
          )
      );

      // Generate JWT token for authenticated user
      String token = jwtService.generateToken(request.username());

      // Return token in response
      return ResponseEntity.ok(token);

    } catch (BadCredentialsException ex) {
      // Wrong username/password
      return ResponseEntity.status(401).body("Invalid username or password");
    } catch (AuthenticationException ex) {
      // Any other authentication exception
      return ResponseEntity.status(401).body("Authentication failed");
    }
  }
}
