package com.example.service.implementations;

import com.example.dto.AuthDto;
import com.example.dto.AuthenticationResponse;
import com.example.dto.GamingPlatformDto;
import com.example.dto.mapper.GamingPlatformMapper;
import com.example.entity.GamingPlatform;
import com.example.repository.GamingPlatformRepository;
import com.example.service.interfaces.GamingPlatformService;
import com.example.util.PasswordEncoderUtil;
import com.example.util.TokenUtils;
import io.smallrye.jwt.auth.principal.JWTCallerPrincipal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.math.BigDecimal;

@ApplicationScoped
public class GamingPlatformServiceImpl implements GamingPlatformService {
    private static final long EXPIRATION_TIME_FOR_ACCESS_TOKEN = 18000;
    private static final String ISSUER = "https://example.com";

    @Inject
    GamingPlatformRepository gamingPlatformRepository;

    @Inject
    SecurityContext securityContext;

    @Override
    public Response create(GamingPlatformDto gamingPlatformDto) {
        GamingPlatform gamingPlatform = GamingPlatformMapper.INSTANCE.convertGamingPlatformDtoToGamingPlatform(gamingPlatformDto);
        gamingPlatformRepository.persist(gamingPlatform);
        return Response.status(Response.Status.CREATED).build();
    }

    @Override
    public Response gamingPlatformLogin(AuthDto authDto) {
        GamingPlatform gamingPlatform = gamingPlatformRepository.findByName(authDto.getLogin());

        if (gamingPlatform != null && PasswordEncoderUtil.validatePassword(gamingPlatform.getPassword(), authDto.getPassword())) {
            try {
                String token = TokenUtils.generateToken(gamingPlatform.getName(), gamingPlatform.getRoles(), EXPIRATION_TIME_FOR_ACCESS_TOKEN, ISSUER);
                return Response.ok(new AuthenticationResponse(token)).build();
            } catch (Exception e) {
                throw new RuntimeException("Failed to generate token", e);
            }
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid username or password").build();
    }

    @Override
    public Response redirectToTheGame(String userNickName, BigDecimal userBalance, Integer gameId) {
        JWTCallerPrincipal jwtCallerPrincipal = getCallerPrincipal();
        if (jwtCallerPrincipal == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String gamingPlatformName = jwtCallerPrincipal.getName();
        GamingPlatform gamingPlatform = gamingPlatformRepository.findByName(gamingPlatformName);
        if (gamingPlatform == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if (!checkAllowedGames(gameId, gamingPlatform)) {
            return Response.status(Response.Status.FORBIDDEN).entity("This game is not allowed").build();
        }

        return Response.status(Response.Status.OK).entity("User " + userNickName + " logged in").build();
    }

    private JWTCallerPrincipal getCallerPrincipal() {
        if (securityContext != null && securityContext.getUserPrincipal() instanceof JWTCallerPrincipal) {
            return (JWTCallerPrincipal) securityContext.getUserPrincipal();
        }
        return null;
    }

    private boolean checkAllowedGames(int gameId, GamingPlatform gamingPlatform) {
        return gamingPlatform.getAllowedGames().stream().anyMatch(game -> game.getId() == gameId);
    }
}
