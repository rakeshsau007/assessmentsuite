package com.creative.suite.service;

import com.creative.suite.entity.Person;
import com.creative.suite.repository.PersonRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private static final Logger LOGGER = LogManager.getLogger(PersonServiceImpl.class);

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> findAll() {
        try {
            List<Person> personList = personRepository.findAll();
            LOGGER.info("Retrieved {} persons", personList.size());
            return personList;
        } catch (Exception e) {
            LOGGER.error("Failed to retrieve persons: ", e);
            throw new RuntimeException("Unable to retrieve persons", e);
        }
    }

    @Override
    public Person save(Person person) {
        try {
            Person savedPerson = personRepository.save(person);
            LOGGER.info("Saved person with SSN: {}", savedPerson.getSsn());
            return savedPerson;
        } catch (Exception e) {
            LOGGER.error("Failed to save person: ", e);
            throw new RuntimeException("Unable to save person", e);
        }
    }

    public Optional<Person> findPersonWithOldestChild() {
        try {
            Optional<Person> person = personRepository.findPersonWithOldestChild();
            LOGGER.info("Found person with oldest child. Person SSN: {}", person.get().getSsn());
            return person;
        } catch (Exception e) {
            LOGGER.error("Failed to find person with oldest child: ", e);
            throw new RuntimeException("Unable to find person with oldest child", e);
        }
    }
}
