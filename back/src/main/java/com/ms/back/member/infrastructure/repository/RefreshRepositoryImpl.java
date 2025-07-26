package com.ms.back.member.infrastructure.repository;

import com.ms.back.member.domain.model.RefreshEntity;
import com.ms.back.member.domain.port.RefreshService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RefreshRepositoryImpl implements RefreshService {

    private final RefreshRepository refreshRepository;

    public RefreshRepositoryImpl(RefreshRepository refreshRepository) {
        this.refreshRepository = refreshRepository;
    }

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
