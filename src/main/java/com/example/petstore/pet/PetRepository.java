package com.example.petstore.pet;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByNameContainsIgnoreCase(String name);
}
