package com.TournamentTracker.domain.team;

import com.TournamentTracker.domain.team.model.TeamDto;
import com.TournamentTracker.domain.team.model.TeamTournamentDto;
import com.TournamentTracker.domain.tournament.TournamentMapper;
import com.TournamentTracker.domain.tournament.TournamentService;
import com.TournamentTracker.domain.tournament.model.TournamentTeamDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
class TeamTournamentServiceImpl implements TeamTournamentService {
    private final TeamRepository teamRepository;
    private final TeamService teamService;
    private final TeamMapper teamMapper;
    private final TeamTournamentMapper teamTournamentMapper;
    private final TournamentService tournamentService;
    private final TournamentMapper tournamentMapper;

    public List<TeamDto> getAll(){
        List<TeamDto> teamList = teamMapper.toDtoList(teamRepository.findAll());
        return teamList;
    }

    public TeamTournamentDto update(TeamDto teamDto, Long id) {
        return teamRepository.findById(id)
                .map(editTeam -> {
                    editTeam.setId(id);
                    editTeam.setName(teamDto.getName());
                    editTeam.setTournament(tournamentMapper.toEntity(tournamentService.getById(teamDto.getTournament().getId())));
                    return teamTournamentMapper.toDto(teamRepository.save(teamMapper.toEntity(teamDto)));
                }).orElseThrow(() -> new EntityNotFoundException("Team with id " + id + " not found"));
    }
}
