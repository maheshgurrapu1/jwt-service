package com.prokarma.jwt.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

@ExtendWith(SpringExtension.class)
// @ExtendWith(MockitoExtension.class)
class JwtTokenServiceImplTest {

  @Spy
  private JwtTokenServiceImpl jwtTokenService;

  private User user = new User("mahesh", "mahesh", new ArrayList<GrantedAuthority>());


  @BeforeEach
  public void statsetUp() {
    ReflectionTestUtils.setField(jwtTokenService, "secret", "cYh-@f_fvRbW9s5p");
    ReflectionTestUtils.setField(jwtTokenService, "jwtTokenValidityInMinutes", 15L);
  }

  @Test
  void testGenerateToken() {
    String token = jwtTokenService.generateToken(user);
    assertThat(token).isNotBlank().contains(".", ".", ".");
  }

  @Test
  void testGetUsernameFromToken_withValidToken() {
    String token = jwtTokenService.generateToken(user);
    assertThat(jwtTokenService.getUsernameFromToken(token)).isNotBlank()
        .isEqualTo(user.getUsername());
  }

  @Test
  void testGetUsernameFromToken_withExpiredToken() {
    String token =
        "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYWhlc2giLCJleHAiOjE1NzQwNjI2MzUsImlhdCI6MTU3NDA2MTczNX0.3QuIiaFn2cBD50c_icPcvZTPeO8b3x7H_z-5m4Tuq791Kui4_vIQ1hT9CqVkILIcz6e1WF07NsljG4KCBqCCyA";
    assertThrows(ExpiredJwtException.class, () -> jwtTokenService.getUsernameFromToken(token));
  }

  @Test
  void testGetUsernameFromToken_withInvalidToken() {
    String token =
        "eyJhbGciOiJIUzUxMiJ9.eyJzdTiioiJtYWhlc2giLCJleHAiOjE1NzQwNjI2MzUsImlhdCI6MTU3NDA2MTczNX0.3QuIiaFn2cBD50c_icPcvZTPeO8b3x7H_z-5m4Tuq791Kui4_vIQ1hT9CqVkILIcz6e1WF07NsljG4KCBqCCyA";
    assertThrows(JwtException.class, () -> jwtTokenService.getUsernameFromToken(token));
  }

  @Test
  void testGetUsernameFromToken_withInvalidToken_part1() {
    String token =
        "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYWhlc2giLCJleHAiOjE1NzQxNTcyNzUsImlhdCI6MTU3NDE1NjM3NX0ukE501wTds4MmXbXHMf6WHQV5CMqdjHN-p1RXTRxTRHiaUTHeEJanVTEppIyGL9OV2igKHY73AKmm74__nR44A";
    assertThrows(JwtException.class, () -> jwtTokenService.getUsernameFromToken(token));
  }


  @Test
  void testValidateToken() {
    assertThat(jwtTokenService.validateToken(jwtTokenService.generateToken(user), user)).isTrue();
  }

  // TODO:
  // @Test
  // void testValidateToken_withExpiredToken() {
  // String token =
  // "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYWhlc2giLCJleHAiOjE1NzQwNjI2MzUsImlhdCI6MTU3NDA2MTczNX0.3QuIiaFn2cBD50c_icPcvZTPeO8b3x7H_z-5m4Tuq791Kui4_vIQ1hT9CqVkILIcz6e1WF07NsljG4KCBqCCyA";
  // // when(jwtTokenService.getUsernameFromToken(Mockito.anyString())).thenReturn("mahesh");
  // doReturn("mahesh").when(jwtTokenService).getUsernameFromToken(token);
  // assertThat(jwtTokenService.validateToken(token, user)).isFalse();
  // }

}
