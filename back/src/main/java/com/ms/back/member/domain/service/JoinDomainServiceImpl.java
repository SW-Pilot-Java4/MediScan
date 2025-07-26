package com.ms.back.member.domain.service;

import com.ms.back.member.application.dto.JoinDTO;
import com.ms.back.member.domain.model.MemberEntity;
import com.ms.back.member.application.port.JoinDomainService;
import com.ms.back.member.domain.port.MemberService;
import com.ms.back.member.policy.MemberPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class JoinDomainServiceImpl implements JoinDomainService {

    private final MemberService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberPolicy memberPolicy;  // 주입

    @Override
    public boolean joinProcess(JoinDTO joinDTO) {
        // 1) 기본 검증 정책 위임
        memberPolicy.validateJoin(joinDTO);

        // 2) 중복 아이디 여부 체크 (UserService에 위임)
        boolean exists = userService.existsByUsername(joinDTO.getUsername());
        memberPolicy.checkUsernameDuplicate(exists);

        // 3) 유저 생성
        MemberEntity user = joinDTO.toEntity(bCryptPasswordEncoder);
        user.setUsername(joinDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        user.setEmail(joinDTO.getEmail());
        user.setAddress(joinDTO.getAddress());
        user.setPhone(joinDTO.getPhone());
        user.setRole("ROLE_USER");

        userService.saveUser(user);
        return true;
    }
}
