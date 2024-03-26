package com.TournamentTracker.domain.sport;

import com.TournamentTracker.domain.sport.dto.SportDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/sports")
class SportController {
    private final SportService sportService;

    @GetMapping
    public List<SportDto> getAllSports(){
        return sportService.getAll();
    }

    @GetMapping("/{id}")
    public SportDto getSportById(@PathVariable Long id){
        return sportService.getById(id);
    }

    @PostMapping()
    public SportDto addSport(@RequestBody SportDto sportDto){
        return sportService.create(sportDto);
    }

    @PutMapping("/{id}")
    public SportDto editSport(@RequestBody SportDto sportDto, @PathVariable Long id){
        return sportService.update(sportDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteSport(@PathVariable Long id){
        sportService.deleteById(id);
    }
}
