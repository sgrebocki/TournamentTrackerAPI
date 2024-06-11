package com.TournamentTracker.domain.sport;

import com.TournamentTracker.domain.sport.model.SportCreateDto;
import com.TournamentTracker.domain.sport.model.SportDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.TournamentTracker.util.ExceptionMessages.SPORT_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
class SportServiceImpl implements SportService{
    private final SportRepository sportRepository;
    private final SportMapper sportMapper;

    public List<SportDto> getAll(){
        return sportMapper.toDtoList(sportRepository.findAll());
    }

    public SportDto getById(Long id){
        return sportRepository.findById(id)
                .map(sportMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format(SPORT_NOT_FOUND, id)));
    }

    public SportDto create(SportCreateDto sportDto){
        return sportMapper.toDto(sportRepository.save(sportMapper.toEntity(sportDto)));
    }

    public SportDto update(SportDto sportDto, Long id){
        return sportRepository.findById(id)
            .map(editSport -> {
                editSport.setId(id);
                editSport.setSportName(sportDto.getSportName());
                return sportMapper.toDto(sportRepository.save(editSport));
            }).orElseThrow(() -> new EntityNotFoundException(String.format(SPORT_NOT_FOUND, id)));
    }

    public void deleteById(Long id) {
        sportRepository.deleteById(id);
    }
}
