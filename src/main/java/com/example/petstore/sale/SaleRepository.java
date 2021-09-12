package com.example.petstore.sale;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByCustomer_NameContainsIgnoreCase(String name);

}
