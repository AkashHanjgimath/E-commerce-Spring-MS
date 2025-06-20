package com.ecom.auth_service.repository;


import com.ecom.auth_service.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User>findByUsername(String username);
}
