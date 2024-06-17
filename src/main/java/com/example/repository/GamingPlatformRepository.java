package com.example.repository;

import com.example.entity.GamingPlatform;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class GamingPlatformRepository extends BaseRepository<GamingPlatform> {

    @Transactional
    public Optional<GamingPlatform> findById(Long id) {
        return super.findById(id);
    }

    @Transactional
    public Optional<GamingPlatform> findByName(String name) {
        return super.findByName(name);
    }
}