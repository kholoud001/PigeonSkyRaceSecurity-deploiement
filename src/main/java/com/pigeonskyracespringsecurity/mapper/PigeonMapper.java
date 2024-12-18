package com.pigeonskyracespringsecurity.mapper;

import com.pigeonskyracespringsecurity.DTO.PigeonDTO;
import com.pigeonskyracespringsecurity.model.entity.Pigeon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PigeonMapper {

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "competition.id", source = "competitionId")
    Pigeon toEntity(PigeonDTO pigeonDTO);

    // Mapping from Entity to DTO
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "competitionId", source = "competition.id")
    PigeonDTO toDto(Pigeon pigeon);


}
