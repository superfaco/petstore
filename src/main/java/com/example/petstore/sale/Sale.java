package com.example.petstore.sale;

import com.example.petstore.customer.Customer;
import com.example.petstore.pet.Pet;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(
        name = "Sale",
        indexes = {
                @Index(unique = false, name = "idxCustomer", columnList = "customer_id"),
                @Index(unique = false, name = "idxPet", columnList = "pet_id")
        }
)
@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime saleDate;

    @ManyToOne(cascade = CascadeType.DETACH, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToOne(cascade = CascadeType.DETACH, optional = false)
    @JoinColumn(name = "pet_id", nullable = false, unique = true)
    private Pet pet;

    public Sale() {
    }

    public Sale(LocalDateTime saleDate, Customer customer, Pet pet) {
        this.saleDate = saleDate;
        this.customer = customer;
        this.pet = pet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
