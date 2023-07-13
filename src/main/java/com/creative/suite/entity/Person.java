package com.creative.suite.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Entity
public class Person {

    @Id
    @Size(max=13)
    private String ssn;
    @Size(min=2, max=30)
    @NotEmpty(message = "Name may not be null")
    private String name;
    @Size(min=2, max=30)
    @NotEmpty(message = "Name may not be null")
    private String wifeName;

    public Person() {

    }

    public Person(String ssn, String name, String wifeName) {
        this.ssn = ssn;
        this.name = name;
        this.wifeName = wifeName;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    @JsonManagedReference
    private List<Child> children;

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWifeName() {
        return wifeName;
    }

    public void setWifeName(String wifeName) {
        this.wifeName = wifeName;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
        if(children != null) {
            for(Child child : children) {
                child.setPerson(this);
            }
        }
    }
}
