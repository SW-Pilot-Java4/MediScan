package com.ms.back.member.policy;

import com.ms.back.member.application.dto.JoinDTO;
import com.ms.back.member.application.dto.LoginRequestDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MemberPolicy {

    public void validateJoin(JoinDTO joinDTO) {
        if (!StringUtils.hasText(joinDTO.getUsername())) {
            throw new IllegalArgumentException("아이디는 필수 입력값입니다.");
        }
        if (!StringUtils.hasText(joinDTO.getPassword()) || joinDTO.getPassword().length() < 8) {
            throw new IllegalArgumentException("비밀번호는 최소 8자 이상이어야 합니다.");
        }
        if (!StringUtils.hasText(joinDTO.getEmail()) || !joinDTO.getEmail().contains("@")) {
            throw new IllegalArgumentException("유효한 이메일을 입력하세요.");
        }
    }

    public void validateLogin(LoginRequestDTO loginDTO) {
        if (!StringUtils.hasText(loginDTO.getUsername()) || !StringUtils.hasText(loginDTO.getPassword())) {
            throw new IllegalArgumentException("아이디와 비밀번호를 모두 입력하세요.");
        }
    }

    public void checkUsernameDuplicate(boolean exists) {
        if (exists) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }
}
