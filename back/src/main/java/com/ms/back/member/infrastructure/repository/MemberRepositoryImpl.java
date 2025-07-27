package com.ms.back.member.infrastructure.repository;

import com.ms.back.member.domain.model.MemberEntity;
import com.ms.back.member.domain.port.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberRepositoryImpl implements MemberRepository {
    private final MemberJPARepository memberJPARepository;

    @Override
    public MemberEntity findByUsername(String username) {
        return memberJPARepository.findByUsername(username);
    }

    @Override
    public MemberEntity saveUser(MemberEntity user) {
        return memberJPARepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return memberJPARepository.existsByUsername(username);
    }
}