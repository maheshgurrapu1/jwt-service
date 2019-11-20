package com.prokarma.jwt.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import com.prokarma.jwt.domain.User;
import com.prokarma.jwt.model.UserDTO;
import com.prokarma.jwt.service.JwtTokenService;
import com.prokarma.jwt.service.impl.UserDetailsServiceImpl;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

  @InjectMocks
  private AuthenticationController jwtAuthenticationController;

  @Spy
  private AuthenticationManager authenticationManager;

  @Mock
  private JwtTokenService jwtTokenUtil;

  @Mock
  private UserDetailsServiceImpl userDetailsService;

  @Mock
  private UserDTO userDTO;

  @Mock
  private User user;

  @Mock
  private UserDetails userDetails;

  @Test
  void testCreateAuthenticationToken_success() {
    given(userDetailsService.loadUserByUsername(any())).willReturn(userDetails);
    String jwtToken =
        "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYWhlc2giLCJleHAiOjE1NzQwNjI2MzUsImlhdCI6MTU3NDA2MTczNX0.3QuIiaFn2cBD50c_icPcvZTPeO8b3x7H_z-5m4Tuq791Kui4_vIQ1hT9CqVkILIcz6e1WF07NsljG4KCBqCCyA";
    given(jwtTokenUtil.generateToken(userDetails)).willReturn(jwtToken);

    assertThat(jwtAuthenticationController.createAuthenticationToken(userDTO).getStatusCode())
        .isEqualTo(HttpStatus.OK);
    assertThat(jwtAuthenticationController.createAuthenticationToken(userDTO).getBody()).isNotNull()
        .hasFieldOrPropertyWithValue("jwtToken", jwtToken);
  }

  @Test
  void testCreateAuthenticationToken_withBadCredentials() {

    given(authenticationManager.authenticate(any())).willThrow(BadCredentialsException.class);

    assertThrows(BadCredentialsException.class,
        () -> jwtAuthenticationController.createAuthenticationToken(userDTO));

  }

  @Test
  void testCreateAuthenticationToken_withDisabledUser() {

    given(authenticationManager.authenticate(any())).willThrow(DisabledException.class);

    assertThrows(DisabledException.class,
        () -> jwtAuthenticationController.createAuthenticationToken(userDTO));

  }

  @Test
  void testSaveUser() {
    given(userDetailsService.save(userDTO)).willReturn(user);
    assertThat(jwtAuthenticationController.saveUser(userDTO).getStatusCode())
        .isEqualTo(HttpStatus.OK);
  }

}
