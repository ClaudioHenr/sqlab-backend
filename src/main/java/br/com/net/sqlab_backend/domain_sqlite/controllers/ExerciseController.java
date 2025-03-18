package br.com.net.sqlab_backend.domain_sqlite.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.net.sqlab_backend.domain_sqlite.dto.QueryExerciseDTO;
import br.com.net.sqlab_backend.domain_sqlite.exercise.services.ExerciseExampleService;

@CrossOrigin
@RestController
@RequestMapping("api/exercises")
public class ExerciseController {
    
    @Autowired
    ExerciseExampleService exerciseExampleService;

    @PostMapping("/test")
    public List<Map<String, Object>> testSolveExercise(@RequestBody QueryExerciseDTO queryDTO) {
        List<Map<String, Object>> result = exerciseExampleService.getDataExerciseTest(queryDTO.query());
        return result;
    }
    

}
