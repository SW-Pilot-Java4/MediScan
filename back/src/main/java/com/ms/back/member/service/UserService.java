package com.ms.back.member.service;

import com.ms.back.member.entity.UserEntity;

public interface UserService {
    UserEntity findByUsername(String username);
    void saveUser(UserEntity user);
}
