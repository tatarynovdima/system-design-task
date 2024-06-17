package com.example.repository;

import com.example.entity.Game;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class GameRepository extends BaseRepository<Game> {

    @Transactional
    public Optional<Game> findById(Long id) {
        return super.findById(id);
    }

    @Transactional
    public Optional<Game> findByName(String name) {
        return super.findByName(name);
    }
}