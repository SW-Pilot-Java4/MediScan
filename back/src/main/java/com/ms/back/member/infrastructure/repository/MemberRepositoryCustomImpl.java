package com.ms.back.member.infrastructure.repository;

import com.ms.back.member.domain.model.MemberEntity;
import com.ms.back.member.domain.port.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberRepositoryCustomImpl implements MemberService {
    private final MemberRepository memberRepository;  // JPA repository

    @Override
    public MemberEntity findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Override
    public MemberEntity saveUser(MemberEntity user) {
        return memberRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return memberRepository.existsByUsername(username);
    }
}