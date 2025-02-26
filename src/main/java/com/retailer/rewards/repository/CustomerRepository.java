package com.retailer.rewards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.retailer.rewards.entity.Customer;

/**
 * Repository interface for performing CRUD operations on the {@link Customer} entity.
 * Extends {@link JpaRepository} to provide built-in database interaction methods.
 */
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    public Customer findByCustomerId(Long customerId);

}
