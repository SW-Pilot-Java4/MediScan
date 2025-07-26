package com.ms.back.member.infrastructure.repository;

import com.ms.back.member.domain.model.MemberEntity;
import com.ms.back.member.domain.port.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberRepositoryImpl implements MemberService {

    private final MemberRepository userRepository;

    @Override
    public MemberEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public MemberEntity saveUser(MemberEntity user) {
        return userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
