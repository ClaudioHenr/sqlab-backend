package br.com.net.sqlab_backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.net.sqlab_backend.repositories.exercises.ExampleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("api/exercices/")
public class ExampleController {

    @Autowired
    ExampleRepository exampleRepository;
    
    @PostMapping("/insert")
    public String postMethodName() { 
        exampleRepository.insert("Testando", 192);       
        return "test";
    }
    

}
