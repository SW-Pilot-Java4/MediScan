package com.ms.back.member.service;

import com.ms.back.member.dto.JoinDTO;
import com.ms.back.member.entity.UserEntity;
import com.ms.back.member.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean joinProcess(JoinDTO joinDTO) {
        String username = joinDTO.getUsername();

        if (userRepository.existsByUsername(username)) {
            return false;
        }

        UserEntity data = new UserEntity();
        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        data.setEmail(joinDTO.getEmail());
        data.setAddress(joinDTO.getAddress());
        data.setPhone(joinDTO.getPhone());
        data.setRole("ROLE_USER");

        UserEntity saved = userRepository.save(data);
        System.out.println("저장된 유저 ID: " + saved.getId());

        return true;
    }
}