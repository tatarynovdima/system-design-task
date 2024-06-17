package com.example.contoller;

import com.example.dto.GameDto;
import com.example.service.interfaces.GameService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path(("/games"))
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GameController {
    @Inject
    GameService gameService;

    @POST
    @Transactional
    public Response create(@Valid GameDto gameDto){
        return gameService.create(gameDto);
    }
}
