package com.example.petstore.customer;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "Customer", indexes = {
        @Index(name = "idx_gender", columnList = "gender"),
        @Index(name = "idx_name", columnList = "name")
})
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false)
    private String gender;

    public Customer() {
    }

    public Customer(String name, LocalDate birthDate, String gender) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass().equals(this.getClass()) && ((Customer)obj).getId().equals(this.id);
    }
}
