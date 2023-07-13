package com.creative.suite.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Child {

    @Id
    private String name;
    private int age;

    public Child() {

    }

    public Child(String name, int age, Person person) {
        this.name = name;
        this.age = age;
        this.person = person;
    }

    @ManyToOne
    @JsonBackReference
    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
