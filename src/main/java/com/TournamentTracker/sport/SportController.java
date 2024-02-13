package com.TournamentTracker.sport;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/sports")
public class SportController {
    private final SportService sportService;

    @GetMapping
    public List<Sport> getAllSports(){
        return sportService.getAllSports();
    }

    @GetMapping("/{id}")
    public Sport getSportById(@PathVariable Long id){
        return sportService.getSportById(id);
    }

    @PostMapping()
    public Sport addSport(@RequestBody Sport sport){
        return sportService.addSport(sport);
    }

    @PutMapping("/{id}")
    public Sport editSport(@RequestBody Sport sport, @PathVariable Long id){
        return sportService.editSport(sport, id);
    }

    @DeleteMapping("/{id}")
    public void deleteSport(@PathVariable Long id){
        sportService.deleteSport(id);
    }
}
