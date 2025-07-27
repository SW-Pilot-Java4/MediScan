package com.ms.back.member.infrastructure.repository;

import com.ms.back.member.domain.model.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberJPARepository extends JpaRepository<MemberEntity, Integer> {
    MemberEntity findByUsername(String username);
    boolean existsByUsername(String username);
}
