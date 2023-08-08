package com.example.demo.controller;

import com.example.demo.dto.PersonInput;
import com.example.demo.model.Person;
import com.example.demo.model.UserRole;
import com.example.demo.repositories.PersonRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public PersonController(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(path = "/persons")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "BasicAuth")
    private Person createNewPerson(@RequestBody PersonInput personInput) {
        String hashedPassword = passwordEncoder.encode(personInput.getPassword());
        Person newPerson = new Person(personInput.getUsername(), hashedPassword, UserRole.USER);

        return personRepository.save(newPerson);
    }
}
