package com.pigeonskyracespringsecurity.mapper;

import com.pigeonskyracespringsecurity.DTO.PigeonDTO;
import com.pigeonskyracespringsecurity.model.entity.Competition;
import com.pigeonskyracespringsecurity.model.entity.Pigeon;
import com.pigeonskyracespringsecurity.model.entity.User;
import com.pigeonskyracespringsecurity.model.enums.Gender;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-19T13:20:34+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class PigeonMapperImpl implements PigeonMapper {

    @Override
    public Pigeon toEntity(PigeonDTO pigeonDTO) {
        if ( pigeonDTO == null ) {
            return null;
        }

        Pigeon pigeon = new Pigeon();

        pigeon.setUser( pigeonDTOToUser( pigeonDTO ) );
        pigeon.setCompetition( pigeonDTOToCompetition( pigeonDTO ) );
        pigeon.setRingNumber( pigeonDTO.getRingNumber() );
        if ( pigeonDTO.getGender() != null ) {
            pigeon.setGender( Enum.valueOf( Gender.class, pigeonDTO.getGender() ) );
        }
        pigeon.setAge( pigeonDTO.getAge() );
        pigeon.setColor( pigeonDTO.getColor() );

        return pigeon;
    }

    @Override
    public PigeonDTO toDto(Pigeon pigeon) {
        if ( pigeon == null ) {
            return null;
        }

        PigeonDTO pigeonDTO = new PigeonDTO();

        pigeonDTO.setUserId( pigeonUserId( pigeon ) );
        pigeonDTO.setCompetitionId( pigeonCompetitionId( pigeon ) );
        pigeonDTO.setRingNumber( pigeon.getRingNumber() );
        if ( pigeon.getGender() != null ) {
            pigeonDTO.setGender( pigeon.getGender().name() );
        }
        pigeonDTO.setAge( pigeon.getAge() );
        pigeonDTO.setColor( pigeon.getColor() );

        return pigeonDTO;
    }

    protected User pigeonDTOToUser(PigeonDTO pigeonDTO) {
        if ( pigeonDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( pigeonDTO.getUserId() );

        return user;
    }

    protected Competition pigeonDTOToCompetition(PigeonDTO pigeonDTO) {
        if ( pigeonDTO == null ) {
            return null;
        }

        Competition competition = new Competition();

        competition.setId( pigeonDTO.getCompetitionId() );

        return competition;
    }

    private Long pigeonUserId(Pigeon pigeon) {
        if ( pigeon == null ) {
            return null;
        }
        User user = pigeon.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long pigeonCompetitionId(Pigeon pigeon) {
        if ( pigeon == null ) {
            return null;
        }
        Competition competition = pigeon.getCompetition();
        if ( competition == null ) {
            return null;
        }
        Long id = competition.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
