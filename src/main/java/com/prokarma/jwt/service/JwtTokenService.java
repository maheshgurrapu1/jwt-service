package com.prokarma.jwt.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenService {

  String generateToken(UserDetails userDetails);

  String getUsernameFromToken(String token);

  Boolean validateToken(String token, UserDetails userDetails);

}
