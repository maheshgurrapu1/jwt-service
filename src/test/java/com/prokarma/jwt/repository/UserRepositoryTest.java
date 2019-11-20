package com.prokarma.jwt.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.prokarma.jwt.domain.User;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  void testFindByUsername_thenRetrunUser() {
    Optional<User> user = userRepository.findByUsername("mahesh");
    assertThat(user.get()).isNotNull();
    assertThat(user.get().getUsername()).isEqualTo("mahesh");
  }


  @Test
  void testFindByUsername_withNull() {
    assertThrows(NoSuchElementException.class, () -> userRepository.findByUsername(null).get());
  }

  @Test
  void testFindByUsername_thenThrowNoSuchElementException() {
    assertThrows(NoSuchElementException.class,
        () -> userRepository.findByUsername("maheshXyz").get());
  }
}
