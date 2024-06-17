package com.example.service.interfaces;

import com.example.dto.GameDto;
import jakarta.ws.rs.core.Response;

public interface GameService {
    Response create(GameDto gameDto);
}
