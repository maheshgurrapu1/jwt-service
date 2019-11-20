package com.prokarma.jwt.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.prokarma.jwt.domain.User;
import com.prokarma.jwt.model.UserDTO;
import com.prokarma.jwt.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserDetailsServiceImpl userDetailsService;


  @Test
  public void testLoadUserByUsername() {

    User user = new User();
    user.setUsername("mahesh");
    user.setPassword("password");
    BDDMockito.given(userRepository.findByUsername(BDDMockito.anyString()))
        .willReturn(Optional.of(user));

    UserDetails userDetails = userDetailsService.loadUserByUsername("mahesh");
    assertThat(userDetails).isNotNull();
    assertThat(userDetails.getUsername()).isEqualTo(user.getUsername());
    assertThat(userDetails.getPassword()).isEqualTo(user.getPassword());

  }

  @Test
  public void testLoadUserByUsername_withInvalidUserName() {
    BDDMockito.given(userRepository.findByUsername(BDDMockito.anyString()))
        .willReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class,
        () -> userDetailsService.loadUserByUsername("mahesh"));

  }

  @Test
  public void testSave() {
    BDDMockito.given(userRepository.save(BDDMockito.any())).willReturn(new User());
    assertThat(userDetailsService.save(new UserDTO())).isNotNull();
  }


}
