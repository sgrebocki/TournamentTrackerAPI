package com.TournamentTracker.domain.game;

import com.TournamentTracker.config.CustomMapperConfig;
import com.TournamentTracker.domain.game.model.Game;
import com.TournamentTracker.domain.game.model.GameCreateDto;
import com.TournamentTracker.domain.game.model.GameDto;
import com.TournamentTracker.domain.game.model.GameTournamentDto;
import com.TournamentTracker.domain.team.model.Team;
import com.TournamentTracker.domain.team.model.TeamDto;
import com.TournamentTracker.domain.tournament.model.Tournament;
import com.TournamentTracker.domain.tournament.model.TournamentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = CustomMapperConfig.class)
public interface GameMapper {
    GameDto toDto(Game game);
    @Mapping(target = "tournament", ignore = true)
    List<GameDto> toDtoList(List<Game> gameList);
    Game toEntity(GameDto gameDto);

    @Mapping(source = "tournamentId", target = "tournament.id")
    Game toEntity(GameCreateDto gameDto);

    @Mapping(source = "homeTeam.id", target = "homeTeamId")
    @Mapping(source = "guestTeam.id", target = "guestTeamId")
    @Mapping(source = "tournament.id", target = "tournamentId")
    GameCreateDto toCreateDto(Game game);

    @Mapping(source = "tournament.id", target = "tournamentId")
    GameTournamentDto toGameTournamentDto(Game game);
    List<GameTournamentDto> toGameTournamentDtoList(List<Game> gamesList);
}
