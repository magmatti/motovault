package com.uken.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    @Size(min = 3, max = 32, message = "Name must contain between 3 and 32 characters.")
    private String name;
    @Size(min = 3, max = 32, message = "Last name must contain between 3 and 32 characters")
    private String lastName;
    @Email(message = "Email should be valid.")
    @Size(max = 100, message = "Email must not exceed 100 characters.")
    private String email;

    public User(){}

    public User(String name, String lastName, String email) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
