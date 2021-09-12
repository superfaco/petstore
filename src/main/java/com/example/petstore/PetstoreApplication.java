package com.example.petstore;

import com.example.petstore.customer.Customer;
import com.example.petstore.customer.CustomerRepository;
import com.example.petstore.pet.Pet;
import com.example.petstore.pet.PetRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class PetstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetstoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(CustomerRepository customerRepository, PetRepository petRepository){
        return args -> {
            customerRepository.save(new Customer("Fernando Alfonso Caldera Olivas", LocalDate.of(1997, 3, 11), "Male"));
            customerRepository.save(new Customer("Gerardo Antonio Caldera Olivas", LocalDate.of(1996, 5, 7), "Male"));

            petRepository.save(new Pet("Fido", LocalDate.now().minusMonths(3)));
            petRepository.save(new Pet("Firulais", LocalDate.now().minusMonths(5)));
        };
    }

}
