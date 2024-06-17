package com.example.repository;

import com.example.entity.GamingPlatform;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GamingPlatformRepository implements PanacheRepository<GamingPlatform> {
    public GamingPlatform findById(Long id) {
        return find("id", id).firstResult();
    }
    public GamingPlatform findByName(String name){
        return find("name",name).firstResult();
    }
}