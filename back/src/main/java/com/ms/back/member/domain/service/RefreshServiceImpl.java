package com.ms.back.member.domain.service;

import com.ms.back.member.domain.model.RefreshEntity;
import com.ms.back.member.domain.port.RefreshService;
import com.ms.back.member.infrastructure.repository.RefreshRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshServiceImpl implements RefreshService {

    private final RefreshRepository refreshRepository;

    @Override
    public boolean existsByRefresh(String refresh) {
        return refreshRepository.existsByRefresh(refresh);
    }

    @Override
    public void deleteByRefresh(String refresh) {
        refreshRepository.deleteByRefresh(refresh);
    }

    @Override
    public void deleteByUsername(String username) {
        refreshRepository.deleteByUsername(username);
    }

    @Override
    public RefreshEntity save(RefreshEntity refreshEntity) {
        return refreshRepository.save(refreshEntity);
    }
}
