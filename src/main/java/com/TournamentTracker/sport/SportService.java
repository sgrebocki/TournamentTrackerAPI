package com.TournamentTracker.sport;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SportService {
    private SportRepository sportRepository;

    public List<Sport> getAllSports(){
        return sportRepository.findAll();
    }

    public Sport getSportById(Long id){
        return sportRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Sport with id " + id + "not found"));
    }

    public Sport addSport(Sport sport){
        return sportRepository.save(sport);
    }

    public Sport editSport(Sport sport, Long id){
        return sportRepository.findById(id)
            .map(editSport -> {
                editSport.setName(sport.getName());
                return sportRepository.save(sport);
            }).orElseThrow(() -> new EntityNotFoundException("Sport with id " + id + "not found"));
    }

    public void deleteSport(Long id){
        sportRepository.deleteById(id);
    }
}
