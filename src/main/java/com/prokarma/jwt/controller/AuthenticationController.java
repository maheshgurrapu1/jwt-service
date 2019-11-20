package com.prokarma.jwt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.prokarma.jwt.domain.User;
import com.prokarma.jwt.model.AuthenticationResponse;
import com.prokarma.jwt.model.UserDTO;
import com.prokarma.jwt.service.JwtTokenService;
import com.prokarma.jwt.service.impl.UserDetailsServiceImpl;

@RestController
@CrossOrigin
public class AuthenticationController {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);


  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenService jwtTokenService;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;


  @PostMapping(value = "/authenticate")
  public ResponseEntity<AuthenticationResponse> createAuthenticationToken(
      @RequestBody UserDTO authenticationRequest) {

    authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

    final UserDetails userDetails =
        userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

    final String token = jwtTokenService.generateToken(userDetails);

    return ResponseEntity.ok(new AuthenticationResponse(token));
  }

  @PostMapping(value = "/register")
  public ResponseEntity<User> saveUser(@RequestBody UserDTO user) {
    return ResponseEntity.ok(userDetailsService.save(user));
  }

  private void authenticate(String username, String password) {
    try {
      authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException | BadCredentialsException exception) {
      LOGGER.error("Error While authenticating", exception);
      throw exception;
    }
  }
}
