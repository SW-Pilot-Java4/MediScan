package com.ms.back.member.application.service;

import com.ms.back.member.application.dto.CustomUserDetails;
import com.ms.back.member.domain.model.MemberEntity;
import com.ms.back.member.domain.port.MemberService;
import com.ms.back.member.infrastructure.repository.MemberRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberService memberService;

    public CustomUserDetailsService(@Lazy MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity userData = memberService.findByUsername(username);

        if (userData != null) {
            return new CustomUserDetails(userData);
        }

        throw new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다: " + username);
    }
}
