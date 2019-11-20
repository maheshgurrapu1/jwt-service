package com.prokarma.jwt.service.impl;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.prokarma.jwt.domain.User;
import com.prokarma.jwt.model.UserDTO;
import com.prokarma.jwt.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;


  @Override
  public UserDetails loadUserByUsername(String username) {

    Optional<User> user = userRepository.findByUsername(username);

    if (user.isPresent()) {
      return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
          user.get().getPassword(), new ArrayList<>());
    } else {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }

  }

  public User save(UserDTO user) {
    User newUser = new User();
    newUser.setUsername(user.getUsername());
    newUser.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(newUser);
  }
}
