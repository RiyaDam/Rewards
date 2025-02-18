package com.retailer.rewards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.retailer.rewards.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    public Customer findByCustomerId(Long customerId);
}
