package com.prokarma.jwt.repository;

import org.springframework.data.repository.CrudRepository;
import com.prokarma.jwt.domain.User;

/**
 * @author mgurrapu
 */
public interface UserRepository extends CrudRepository<User, Integer> {

  User findByUsername(String username);

}
