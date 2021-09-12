package com.example.petstore.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByNameContainsIgnoreCase(String name);

    List<Customer> findByGender(Character gender);
}
