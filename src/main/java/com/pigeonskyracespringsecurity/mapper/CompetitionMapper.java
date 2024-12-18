package com.pigeonskyracespringsecurity.mapper;

import com.pigeonskyracespringsecurity.DTO.CompetitionDTO;
import com.pigeonskyracespringsecurity.model.entity.Competition;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CompetitionMapper {
    @Mapping(target = "user.id", source = "userId")
    Competition toEntity(CompetitionDTO competitionDTO);

    @Mapping(target = "userId", source = "user.id")
    CompetitionDTO toDto(Competition competition);
}
