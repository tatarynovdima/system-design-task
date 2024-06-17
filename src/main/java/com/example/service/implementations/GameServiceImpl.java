package com.example.service.implementations;

import com.example.dto.GameDto;
import com.example.dto.mapper.GameMapper;
import com.example.entity.Game;
import com.example.repository.GameRepository;
import com.example.service.interfaces.GameService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class GameServiceImpl implements GameService {

    private static final Logger logger = Logger.getLogger(GameServiceImpl.class.getName());

    @Inject
    private GameRepository gameRepository;

    @Inject
    private GameMapper gameMapper;

    @Override
    @Transactional
    public Response create(GameDto gameDto) {
        try {
            Game game = gameMapper.convertGameDtoToGame(gameDto);
            gameRepository.persist(game);

            logger.log(Level.INFO, "Created game with id {0}", game.getId());

            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to create game", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to create game: " + e.getMessage())
                    .build();
        }
    }
}
