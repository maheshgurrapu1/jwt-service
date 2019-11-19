package com.prokarma.jwt.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.prokarma.jwt.domain.User;

/**
 * @author mgurrapu
 */
public interface UserRepository extends CrudRepository<User, Integer> {

  Optional<User> findByUsername(String username);

}
