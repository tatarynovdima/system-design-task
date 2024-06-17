package com.example.repository;

import com.example.entity.Game;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GameRepository implements PanacheRepository<Game> {
    public Game findById(Long id){
        return find("id", id).firstResult();
    }
    public Game findByName(String name){
        return find("name", name).firstResult();
    }
}