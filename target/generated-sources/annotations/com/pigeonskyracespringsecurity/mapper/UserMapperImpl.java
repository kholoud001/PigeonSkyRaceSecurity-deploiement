package com.pigeonskyracespringsecurity.mapper;

import com.pigeonskyracespringsecurity.DTO.CompetitionDTO;
import com.pigeonskyracespringsecurity.DTO.UserDTO;
import com.pigeonskyracespringsecurity.model.entity.Competition;
import com.pigeonskyracespringsecurity.model.entity.Role;
import com.pigeonskyracespringsecurity.model.entity.User;
import com.pigeonskyracespringsecurity.model.enums.RoleType;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-26T14:34:48+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setRole( roleTypeToRole( userDTO.getRoleType() ) );
        user.setUsername( userDTO.getUsername() );
        user.setPassword( userDTO.getPassword() );
        user.setCompetitions( competitionDTOListToCompetitionList( userDTO.getCompetitions() ) );

        return user;
    }

    @Override
    public UserDTO toUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setRoleType( userRoleRoleType( user ) );
        userDTO.setUsername( user.getUsername() );
        userDTO.setPassword( user.getPassword() );
        userDTO.setCompetitions( competitionListToCompetitionDTOList( user.getCompetitions() ) );

        return userDTO;
    }

    protected Role roleTypeToRole(RoleType roleType) {
        if ( roleType == null ) {
            return null;
        }

        Role role = new Role();

        role.setRoleType( roleType );

        return role;
    }

    protected Competition competitionDTOToCompetition(CompetitionDTO competitionDTO) {
        if ( competitionDTO == null ) {
            return null;
        }

        Competition competition = new Competition();

        competition.setName( competitionDTO.getName() );
        competition.setLatitude( competitionDTO.getLatitude() );
        competition.setLongitude( competitionDTO.getLongitude() );
        competition.setDepartureTime( competitionDTO.getDepartureTime() );
        competition.setPigeonCount( competitionDTO.getPigeonCount() );
        competition.setPercentage( competitionDTO.getPercentage() );
        competition.setReleasePlace( competitionDTO.getReleasePlace() );

        return competition;
    }

    protected List<Competition> competitionDTOListToCompetitionList(List<CompetitionDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Competition> list1 = new ArrayList<Competition>( list.size() );
        for ( CompetitionDTO competitionDTO : list ) {
            list1.add( competitionDTOToCompetition( competitionDTO ) );
        }

        return list1;
    }

    private RoleType userRoleRoleType(User user) {
        if ( user == null ) {
            return null;
        }
        Role role = user.getRole();
        if ( role == null ) {
            return null;
        }
        RoleType roleType = role.getRoleType();
        if ( roleType == null ) {
            return null;
        }
        return roleType;
    }

    protected CompetitionDTO competitionToCompetitionDTO(Competition competition) {
        if ( competition == null ) {
            return null;
        }

        CompetitionDTO competitionDTO = new CompetitionDTO();

        competitionDTO.setName( competition.getName() );
        competitionDTO.setLatitude( competition.getLatitude() );
        competitionDTO.setLongitude( competition.getLongitude() );
        competitionDTO.setDepartureTime( competition.getDepartureTime() );
        competitionDTO.setPigeonCount( competition.getPigeonCount() );
        competitionDTO.setPercentage( competition.getPercentage() );
        competitionDTO.setReleasePlace( competition.getReleasePlace() );

        return competitionDTO;
    }

    protected List<CompetitionDTO> competitionListToCompetitionDTOList(List<Competition> list) {
        if ( list == null ) {
            return null;
        }

        List<CompetitionDTO> list1 = new ArrayList<CompetitionDTO>( list.size() );
        for ( Competition competition : list ) {
            list1.add( competitionToCompetitionDTO( competition ) );
        }

        return list1;
    }
}
