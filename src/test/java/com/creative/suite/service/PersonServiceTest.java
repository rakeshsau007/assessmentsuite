package com.creative.suite.service;

import com.creative.suite.entity.Person;
import com.creative.suite.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personServiceImpl;

    @Test
    public void testFindAllPersons() {
        Person personOne = new Person("123", "John", "Jane");
        Person personTwo = new Person("456", "Michael", "Michelle");

        when(personRepository.findAll()).thenReturn(Arrays.asList(personOne, personTwo));

        assertEquals("Size should be 2", 2, personServiceImpl.findAll().size());
    }

    @Test
    public void testFindPersonWithOldestChild() {
        Person person = new Person("123", "John", "Jane");

        when(personRepository.findPersonWithOldestChild()).thenReturn(Optional.of(person));

        Optional<Person> result = personServiceImpl.findPersonWithOldestChild();
        assertTrue("Should be true", result.isPresent());
        assertEquals("Should be 123", "123", result.get().getSsn());
    }

    @Test
    public void testSavePerson() {
        Person person = new Person("123", "John", "Jane");

        when(personRepository.save(person)).thenReturn(person);

        Person result = personServiceImpl.save(person);

        assertNotNull(result);
        assertEquals("Should be 123","123", result.getSsn());

        Mockito.verify(personRepository, Mockito.times(1)).save(person);
    }

}
