package com.pigeonskyracespringsecurity.mapper;


import com.pigeonskyracespringsecurity.DTO.ColombierDTO;
import com.pigeonskyracespringsecurity.model.entity.Colombier;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ColombierMapper {
    Colombier toEntity(ColombierDTO colombierDTO);
    ColombierDTO toDto(Colombier colombier);
}
