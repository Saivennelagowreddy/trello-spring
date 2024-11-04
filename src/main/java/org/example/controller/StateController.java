package org.example.controller;

import org.example.model.State;
import org.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/states")
public class StateController {

    @Autowired
    private StateServiceImplementation stateService;

    @GetMapping
    public List<State> getAllStates() {
        return stateService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<State> getStateById(@PathVariable Long id) {
        return stateService.findById(Math.toIntExact(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<State> createState(@RequestBody State state) {
        // Ensure stateName is set
        if (state.getStateName() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        State savedState = stateService.save(state);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedState);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteState(@PathVariable Long id) {
        stateService.deleteById(Math.toIntExact(id));
        return ResponseEntity.noContent().build();
    }
}
