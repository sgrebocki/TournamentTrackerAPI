package com.TournamentTracker.domain.team;

import com.TournamentTracker.domain.team.model.TeamCreateDto;
import com.TournamentTracker.domain.team.model.TeamDto;
import com.TournamentTracker.domain.team.model.TeamTournamentDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public List<TeamDto> getAll() {
        return teamMapper.toDtoList(teamRepository.findAll());
    }

    public TeamDto getById(Long id) {
        return teamRepository.findById(id)
            .map(teamMapper::toDto)
            .orElseThrow(() -> new EntityNotFoundException("Team with id " + id + " not found"));
    }

    public TeamDto create(TeamCreateDto teamCreateDto) {
        return teamMapper.toDto(teamRepository.save(teamMapper.toEntity(teamCreateDto)));
    }

    public TeamDto update(TeamDto teamDto, Long id) {
        return teamRepository.findById(id)
            .map(editTeam -> {
                editTeam.setId(id);
                editTeam.setName(teamDto.getName());
                return teamMapper.toDto(teamRepository.save(teamMapper.toEntity(teamDto)));
            }).orElseThrow(() -> new EntityNotFoundException("Team with id " + id + " not found"));
    }

    public void deleteById(Long id) {
        teamRepository.deleteById(id);
    }

    public List<TeamTournamentDto> getOnlyTeams() {
        return teamMapper.toTeamTournamentDtoList(teamRepository.findAll());
    }
}
