package com.retailer.rewards.entity;

import jakarta.persistence.*;

/**
 * Represents a customer in the system.
 */
@Entity
@Table(name = "CUSTOMER")
@Access(AccessType.FIELD)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID")
    private Long customerId;
    @Column(name = "CUSTOMER_NAME")
    private String customerName;

}
