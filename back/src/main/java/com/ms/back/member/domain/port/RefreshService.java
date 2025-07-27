package com.ms.back.member.domain.port;

import com.ms.back.member.domain.model.RefreshEntity;

public interface RefreshService {
    boolean existsByRefresh(String refresh);
    void deleteByRefresh(String refresh);
    void deleteByUsername(String username);
    RefreshEntity save(RefreshEntity refreshEntity);
}
