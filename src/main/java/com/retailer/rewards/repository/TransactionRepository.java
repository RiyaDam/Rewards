package com.retailer.rewards.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.retailer.rewards.entity.Transaction;

/**
 * Repository interface for performing CRUD operations on the {@link Transaction} entity.
 * Extends {@link JpaRepository} to provide built-in database interaction methods.
 */
@Repository
@Transactional
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    public List<Transaction> findAllByCustomerId(Long customerId);

    public List<Transaction> findAllByCustomerIdAndTransactionDateBetween(Long customerId, Timestamp from,Timestamp to);
}
