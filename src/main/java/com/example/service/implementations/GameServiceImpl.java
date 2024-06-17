package com.example.service.implementations;

import com.example.dto.GameDto;
import com.example.dto.mapper.GameMapper;
import com.example.entity.Game;
import com.example.repository.GameRepository;
import com.example.service.interfaces.GameService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class GameServiceImpl implements GameService {
    @Inject
    GameRepository gameRepository;

    @Override
    public Response create(GameDto gameDto) {
        Game game = GameMapper.INSTANCE.convertGameDtoToGame(gameDto);
        gameRepository.persist(game);

        return Response.status(Response.Status.CREATED).build();
    }
}
