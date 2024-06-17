package com.example.dto.mapper;

import com.example.dto.GamingPlatformDto;
import com.example.entity.GamingPlatform;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GamingPlatformMapper {

    GamingPlatformMapper INSTANCE = Mappers.getMapper(GamingPlatformMapper.class);

    GamingPlatform convertGamingPlatformDtoToGamingPlatform(GamingPlatformDto gamingPlatformDto);

    GamingPlatformDto convertGamingPlatformToGamingPlatformDto(GamingPlatform gamingPlatform);
}