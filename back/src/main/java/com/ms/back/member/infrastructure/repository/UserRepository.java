package com.ms.back.member.infrastructure.repository;

import com.ms.back.member.domain.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Boolean existsByUsername(String username);

    UserEntity findByUsername(String username);
    //UserEntity findByEmail(String email);
}
