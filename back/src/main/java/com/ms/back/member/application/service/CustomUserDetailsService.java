package com.ms.back.member.application.service;

import com.ms.back.member.application.dto.CustomUserDetails;
import com.ms.back.member.domain.model.MemberEntity;
import com.ms.back.member.domain.port.MemberRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(@Lazy MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity userData = memberRepository.findByUsername(username);

        if (userData != null) {
            return new CustomUserDetails(userData);
        }

        throw new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다: " + username);
    }
}
