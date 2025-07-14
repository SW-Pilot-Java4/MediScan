package com.ms.back.repository;

import com.ms.back.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Boolean existsByUsername(String username);

    UserEntity findByUsername(String username);
    //UserEntity findByEmail(String email);
}
