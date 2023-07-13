package com.creative.suite.controller;

import com.creative.suite.service.PersonService;
import com.creative.suite.entity.Child;
import com.creative.suite.entity.Person;
import com.creative.suite.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class PersonControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testGetPerson() throws Exception {
        Person person = new Person("123", "John", "Jane");

        List<Person> allPersons = List.of(person);
        given(personService.findAll()).willReturn(allPersons);

        mockMvc.perform(get("/api/person")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'ssn': '123','name': 'John','wifeName': 'Jane'}]"));
    }


    @Test
    public void testSavePerson() throws Exception {
        mockMvc.perform(post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"ssn\": \"123\", \"name\": \"John\", \"wifeName\": \"Jane\" }"))
                .andExpect(status().isOk())
                .andDo(print());

        Mockito.verify(personService, Mockito.times(1)).save(Mockito.any(Person.class));
    }

    @Test
    public void testGetPersonWithOldestChild() throws Exception {
        Person person = new Person("123", "John", "Jane");
        Child child = new Child("Child1", 10, person);
        person.setChildren(List.of(child));
        Optional<Person> optionalPerson = Optional.of(person);

        Mockito.when(personService.findPersonWithOldestChild()).thenReturn(optionalPerson);

        mockMvc.perform(get("/api/person/oldest")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ssn").value("123"))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.wifeName").value("Jane"))
                .andExpect(jsonPath("$.children[0].name").value("Child1"))
                .andExpect(jsonPath("$.children[0].age").value(10))
                .andDo(print());

        Mockito.verify(personService, Mockito.times(1)).findPersonWithOldestChild();
    }
}
