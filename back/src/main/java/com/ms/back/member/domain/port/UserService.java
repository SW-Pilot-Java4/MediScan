package com.ms.back.member.domain.port;

import com.ms.back.member.domain.model.UserEntity;

public interface UserService {
    UserEntity findByUsername(String username);
    void saveUser(UserEntity user);
}
