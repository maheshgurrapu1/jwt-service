package com.prokarma.jwt.model;

import java.io.Serializable;

/**
 * @author mgurrapu
 */
public class AuthenticationResponse implements Serializable {

  private static final long serialVersionUID = 1L;
  private final String jwtToken;

  public AuthenticationResponse(String jwtToken) {
    this.jwtToken = jwtToken;
  }

  public String getJwtToken() {
    return this.jwtToken;
  }
}
