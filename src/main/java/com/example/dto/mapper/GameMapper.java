package com.example.dto.mapper;

import com.example.dto.GameDto;
import com.example.entity.Game;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GameMapper {

    GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

    Game convertGameDtoToGame(GameDto gameDto);

    GameDto convertGameToGameDto(Game game);
}
