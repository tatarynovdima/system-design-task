package com.example.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public abstract class BaseRepository<T> implements PanacheRepository<T> {

    @Transactional
    public Optional<T> findById(Long id) {
        return Optional.ofNullable(find("id", id).firstResult());
    }

    @Transactional
    public Optional<T> findByName(String name) {
        return Optional.ofNullable(find("name", name).firstResult());
    }
}