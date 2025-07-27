package com.ms.back.member.domain.port;

import com.ms.back.member.domain.model.MemberEntity;

public interface MemberRepository {
    MemberEntity findByUsername(String username);
    MemberEntity saveUser(MemberEntity user);
    boolean existsByUsername(String username);
}
