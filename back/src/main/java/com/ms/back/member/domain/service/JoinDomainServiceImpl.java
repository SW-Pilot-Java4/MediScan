package com.ms.back.member.domain.service;

import com.ms.back.member.application.dto.JoinDTO;
import com.ms.back.member.domain.model.MemberEntity;
import com.ms.back.member.application.port.JoinDomainService;
import com.ms.back.member.domain.port.MemberRepository;
import com.ms.back.member.policy.MemberPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class JoinDomainServiceImpl implements JoinDomainService {

    private final MemberRepository memberRepository;  // 도메인 포트 인터페이스
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberPolicy memberPolicy;

    @Override
    public boolean join(JoinDTO joinDTO) {
        memberPolicy.validateJoin(joinDTO);

        memberPolicy.checkUsernameDuplicate(
                memberRepository.existsByUsername(joinDTO.getUsername())
        );

        MemberEntity user = joinDTO.toEntity(bCryptPasswordEncoder);
        user.setRole("ROLE_USER");

        memberRepository.saveUser(user);
        return true;
    }
}