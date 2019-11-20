package com.prokarma.jwt.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.prokarma.jwt.domain.User;

/**
 * @author mgurrapu
 */
public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByUsername(String username);

}
