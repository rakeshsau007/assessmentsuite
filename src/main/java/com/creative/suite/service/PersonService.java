package com.creative.suite.service;

import com.creative.suite.entity.Person;
import com.creative.suite.entity.Person;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PersonService {

    List<Person> findAll();

    Person save(Person person);

    Optional<Person> findPersonWithOldestChild();
}
