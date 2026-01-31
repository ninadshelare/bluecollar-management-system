package com.bluecollar.management.repository;

import com.bluecollar.management.entity.Customer;
import com.bluecollar.management.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    Optional<Customer> findByUser(User user);
    
}
