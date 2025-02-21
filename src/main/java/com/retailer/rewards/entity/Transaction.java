package com.retailer.rewards.entity;

import java.sql.Timestamp;

import jakarta.persistence.*;

/**
 * Represents a transaction in the rewards system.
 */
@Entity
@Table(name = "TRANSACTION")
@Access(AccessType.FIELD)
public class Transaction {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSACTION_ID")
    private Long transactionId;

    @Column(name="CUSTOMER_ID")
    private Long customerId;

    @Column(name = "TRANSACTION_DATE")
    private Timestamp transactionDate;

    @Column(name = "AMOUNT")
    private Double transactionAmount;

    // Default constructor (required by JPA)
    public Transaction() {
    }

    // Parameterized constructor
    public Transaction(Long customerId, Double transactionAmount, Timestamp transactionDate) {
        this.customerId = customerId;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
    }

    public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
}
