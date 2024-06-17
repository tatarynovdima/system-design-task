package com.example.service.implementations;

import com.example.dto.GameDto;
import com.example.dto.GamingPlatformDto;
import com.example.dto.mapper.GameMapper;
import com.example.dto.mapper.GamingPlatformMapper;
import com.example.entity.Game;
import com.example.entity.GamingPlatform;
import com.example.entity.type.Role;
import com.example.repository.GameRepository;
import com.example.repository.GamingPlatformRepository;
import com.example.service.interfaces.AutoDataCreatingService;
import com.example.util.PasswordEncoderUtil;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.Startup;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class AutoDataCreatingServiceImpl implements AutoDataCreatingService {
    @Inject
    GameRepository gameRepository;
    @Inject
    GamingPlatformRepository gamingPlatformRepository;

    private void forceEagerInitialization(@Observes Startup startup){

    }
    @PostConstruct
    public void createOnStartup(){
        System.out.println("Application has started!");
        Game game = gameRepository.findByName("Game 1");
        System.out.println(game);
        if (game==null){
            create();
        }
    }

    @Override
    @Transactional
    public void create() {
        gameRepository.persist(GameMapper.INSTANCE.convertGameDtoToGame(new GameDto("Game 1")));
        gameRepository.persist(GameMapper.INSTANCE.convertGameDtoToGame(new GameDto("Game 2")));
        GamingPlatform gamingPlatform = GamingPlatformMapper.INSTANCE.convertGamingPlatformDtoToGamingPlatform(new GamingPlatformDto("Gaming Platform"));
        List<Game> games=new ArrayList<>();
        games.add(gameRepository.findByName("Game 1"));
        gamingPlatform.setAllowedGames(games);
        gamingPlatform.setPassword(PasswordEncoderUtil.securePassword("admin"));
        gamingPlatform.setRole(Set.of(Role.ADMIN));
        gamingPlatformRepository.persist(gamingPlatform);
    }
}
