package com.example.demo.repositories;

import com.example.demo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Set<Person> findByIdIn(Collection<Long> ids);

    Optional<Person> findByUsername(String username);
}
