package com.creative.suite.repository;

import com.creative.suite.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, String> {

    @Query(nativeQuery = true,
            value = "SELECT p.* FROM Person p INNER JOIN Child c ON p.ssn = c.person_ssn GROUP BY p.ssn, p.name, p.wife_name ORDER BY MAX(c.age) DESC LIMIT 1")
    Optional<Person> findPersonWithOldestChild();
}
