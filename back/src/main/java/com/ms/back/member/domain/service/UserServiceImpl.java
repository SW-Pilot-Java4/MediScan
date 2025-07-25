package com.ms.back.member.domain.service;

import com.ms.back.member.domain.model.UserEntity;
import com.ms.back.member.domain.port.UserService;
import com.ms.back.member.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void saveUser(UserEntity user) {
        userRepository.save(user);
    }
}
