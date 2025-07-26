package com.ms.back.member.application.dto;

import com.ms.back.member.domain.model.MemberEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
@ToString
public class JoinDTO {
    private String username;
    private String password;
    private String email;
    private String address;
    private String phone;

    public MemberEntity toEntity(BCryptPasswordEncoder encoder) {
        MemberEntity user = new MemberEntity();
        user.setUsername(this.username);
        user.setPassword(encoder.encode(this.password));
        user.setEmail(this.email);
        user.setAddress(this.address);
        user.setPhone(this.phone);
        user.setRole("ROLE_USER");
        return user;
    }
}
