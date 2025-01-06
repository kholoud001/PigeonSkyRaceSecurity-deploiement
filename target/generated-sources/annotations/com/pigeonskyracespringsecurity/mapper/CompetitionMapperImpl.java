package com.pigeonskyracespringsecurity.mapper;

import com.pigeonskyracespringsecurity.DTO.CompetitionDTO;
import com.pigeonskyracespringsecurity.model.entity.Competition;
import com.pigeonskyracespringsecurity.model.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-26T14:34:47+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class CompetitionMapperImpl implements CompetitionMapper {

    @Override
    public Competition toEntity(CompetitionDTO competitionDTO) {
        if ( competitionDTO == null ) {
            return null;
        }

        Competition competition = new Competition();

        competition.setUser( competitionDTOToUser( competitionDTO ) );
        competition.setName( competitionDTO.getName() );
        competition.setLatitude( competitionDTO.getLatitude() );
        competition.setLongitude( competitionDTO.getLongitude() );
        competition.setDepartureTime( competitionDTO.getDepartureTime() );
        competition.setPigeonCount( competitionDTO.getPigeonCount() );
        competition.setPercentage( competitionDTO.getPercentage() );
        competition.setReleasePlace( competitionDTO.getReleasePlace() );

        return competition;
    }

    @Override
    public CompetitionDTO toDto(Competition competition) {
        if ( competition == null ) {
            return null;
        }

        CompetitionDTO competitionDTO = new CompetitionDTO();

        competitionDTO.setUserId( competitionUserId( competition ) );
        competitionDTO.setName( competition.getName() );
        competitionDTO.setLatitude( competition.getLatitude() );
        competitionDTO.setLongitude( competition.getLongitude() );
        competitionDTO.setDepartureTime( competition.getDepartureTime() );
        competitionDTO.setPigeonCount( competition.getPigeonCount() );
        competitionDTO.setPercentage( competition.getPercentage() );
        competitionDTO.setReleasePlace( competition.getReleasePlace() );

        return competitionDTO;
    }

    protected User competitionDTOToUser(CompetitionDTO competitionDTO) {
        if ( competitionDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( competitionDTO.getUserId() );

        return user;
    }

    private Long competitionUserId(Competition competition) {
        if ( competition == null ) {
            return null;
        }
        User user = competition.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
