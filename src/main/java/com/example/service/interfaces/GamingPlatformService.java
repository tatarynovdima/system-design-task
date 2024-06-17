package com.example.service.interfaces;

import com.example.dto.AuthDto;
import com.example.dto.GamingPlatformDto;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;


public interface GamingPlatformService {
    Response create(GamingPlatformDto gamingPlatformDto);
    Response gamingPlatformLogin(AuthDto authDto);
    Response redirectToTheGame(String userNickName, BigDecimal userBalance, Integer gameId);
}
