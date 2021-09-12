package com.example.petstore.pet;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDate birthDate;

    public Pet() {
    }

    public Pet(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
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

    public void setBirthDate(LocalDate birthDay) {
        this.birthDate = birthDay;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass().equals(this.getClass()) && ((Pet)obj).getId().equals(this.getId());
    }
}
