package com.example.contoller;

import com.example.dto.AuthDto;
import com.example.dto.GamingPlatformDto;
import com.example.service.interfaces.GamingPlatformService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;

@Path(("/gamingPlatforms"))
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GamingPlatformController {
    @Inject
    GamingPlatformService gamingPlatformService;

    @POST
    @Transactional
    public Response create(@Valid GamingPlatformDto gamingPlatformDto) {
        return gamingPlatformService.create(gamingPlatformDto);
    }

    @POST
    @PermitAll
    @Path("/sign-in")
    public Response gamingPlatformLogin(@Valid AuthDto authDto){
        return gamingPlatformService.gamingPlatformLogin(authDto);
    }

    @GET
    @Path("/redirectToTheGame")
    @RolesAllowed("ADMIN")
    public Response redirectToTheGame(@QueryParam("userNickName") String userNickName,
                                      @QueryParam("userBalance") BigDecimal userBalance,
                                      @QueryParam("gameId") Integer gameId) {
        return gamingPlatformService.redirectToTheGame(userNickName, userBalance, gameId);
    }

}