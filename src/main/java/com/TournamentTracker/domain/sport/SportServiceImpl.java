package com.TournamentTracker.domain.sport;

import com.TournamentTracker.domain.sport.dto.SportDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class SportServiceImpl implements SportService{
    private final SportRepository sportRepository;
    @Autowired
    private final SportMapper sportMapper;

    public List<SportDto> getAll(){
        return sportMapper.toDtoList(sportRepository.findAll());
    }

    public SportDto getById(Long id){
        return sportRepository.findById(id)
                .map(sportMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Sport with id " + id + "not found"));
    }

    public SportDto create(SportDto sportDto){
        return sportMapper.toDto(sportRepository.save(sportMapper.toEntity(sportDto)));
    }
    public SportDto update(SportDto sportDto, Long id){
        return sportRepository.findById(id)
            .map(editSport -> {
                editSport.setSportName(sportDto.getSportName());
                return sportMapper.toDto(sportRepository.save(sportMapper.toEntity(sportDto)));
            }).orElseThrow(() -> new EntityNotFoundException("Sport with id " + id + "not found"));
    }

    public void deleteById(Long id) {
        getById(id);
        sportRepository.deleteById(id);
    }


}
