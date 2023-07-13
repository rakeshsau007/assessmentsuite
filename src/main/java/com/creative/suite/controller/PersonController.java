package com.creative.suite.controller;

import com.creative.suite.entity.Person;
import com.creative.suite.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PersonController {

    private static final Logger LOGGER = LogManager.getLogger(PersonController.class);

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/person")
    public List<Person> getPerson() {

        LOGGER.info("Received request to get all persons");

        try {
            return personService.findAll();
        } catch(Exception e) {
            LOGGER.error("Failed to retrieve persons: ", e);
            throw e;
        }
    }

    @PostMapping("/person")
    public ResponseEntity<String> savePerson(@RequestBody Person person) {

        LOGGER.info("Received request to save person with SSN: " + person.getSsn());
        try {
            String personSSN = person.getSsn();
            if (validateSSN(personSSN)) {
                personService.save(person);
                return new ResponseEntity<>("Person was successfully saved!",HttpStatus.OK);
            }else{
                return new ResponseEntity<>("SSN is not valid",HttpStatus.BAD_REQUEST);
            }
        } catch(Exception e) {
            LOGGER.error("Failed to save person: ", e);
            throw e;
        }
    }

    @GetMapping("/person/oldest")
    public Optional<Person> oldestChild() {
        LOGGER.info("Received request to get the person with the oldest child");

        try {
            return personService.findPersonWithOldestChild();
        } catch(Exception e) {
            LOGGER.error("Failed to find the person with the oldest child: ", e);
            throw e;
        }
    }

    Boolean validateSSN(String personSSN){
        boolean isValid= false;
        String regex = "^\\d{6}(?:\\d{2})?[-\\s]?\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(personSSN);

        if(matcher.matches()){
            isValid = true;
        }
        return isValid;
    }
}
