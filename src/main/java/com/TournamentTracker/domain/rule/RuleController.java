package com.TournamentTracker.domain.rule;


import com.TournamentTracker.domain.rule.model.RuleDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/rules")
class RuleController {
    private final RuleService ruleService;

    @GetMapping
    public ResponseEntity<List<RuleDto>> getAllRules(){
        List<RuleDto> rulesList = ruleService.getAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(rulesList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RuleDto> getRuleById(@PathVariable Long id){
        RuleDto rule = ruleService.getById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(rule);
    }

    @PostMapping()
    public ResponseEntity<RuleDto> addRule(@RequestBody RuleDto rule){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ruleService.create(rule));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RuleDto> editRule(@RequestBody RuleDto rule, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ruleService.update(rule, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id){
        ruleService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
