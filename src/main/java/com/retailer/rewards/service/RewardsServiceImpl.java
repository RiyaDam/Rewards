package com.retailer.rewards.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailer.rewards.constants.Constants;
import com.retailer.rewards.entity.Transaction;
import com.retailer.rewards.exception.NullInputException;
import com.retailer.rewards.exception.TransactionNotFoundException;
import com.retailer.rewards.model.Rewards;
import com.retailer.rewards.repository.TransactionRepository;

/**
 * Implementation of the RewardsService interface responsible for calculating reward points
 * based on transaction history.
 */
@Service
public class RewardsServiceImpl implements RewardsService {
	
	private static final Logger logger = LoggerFactory.getLogger(RewardsService.class);
	
	@Autowired
	TransactionRepository transactionRepository;
	
	/**
     * Retrieves and calculates the reward points for a given customer based on transaction history.
     *
     * @param customerId The unique ID of the customer.
     * @return A {@link Rewards} object containing reward details.
     * @throws NullInputException If customerId is null.
     * @throws TransactionNotFoundException If no transactions are found.
     */
	public Rewards getRewardsByCustomerId(Long customerId) {
		
		if(customerId == null) {
			throw new NullInputException("Customer ID cannot be null");
		}

		Map<String, Long> monthlyRewards = new LinkedHashMap<>();
		long totalRewards = 0L;

		// Loop through the last 3 months
		for (int i = 1; i <= 3; i++) {
			Timestamp startDate = getDateBasedOnOffSetDays(i * Constants.daysInMonths);
			Timestamp endDate = (i == 1) ? Timestamp.from(Instant.now()) : getDateBasedOnOffSetDays((i - 1) * Constants.daysInMonths);

			List<Transaction> transactions = transactionRepository.findAllByCustomerIdAndTransactionDateBetween(
					customerId, startDate, endDate);

			Long rewardPoints = getRewardsPerMonth(transactions);
			if (!transactions.isEmpty()) {
				String monthYear = getMonthYearFormat(startDate);
				monthlyRewards.put(monthYear, rewardPoints);
			}

			totalRewards += rewardPoints;
		}

		if (monthlyRewards.isEmpty()) {
			logger.warn("No transactions found for customer ID: {}", customerId);
			throw new TransactionNotFoundException("No Transactions found for customer ID: " + customerId);
		} else {
			logger.debug("Transactions fetched successfully for customer ID: {}", customerId);
		}

		Rewards customerRewards = new Rewards();
		customerRewards.setCustomerId(customerId);
		customerRewards.setPoints(monthlyRewards);
		customerRewards.setTotalRewards(totalRewards);

		logger.info("Rewards calculated successfully for Customer ID: {}", customerId);
		return customerRewards;
	}

	/**
     * Calculates the total reward points for a given list of transactions.
     *
     * @param transactions List of transactions within a specific month.
     * @return Total reward points for the transactions.
     * @throws NullInputException If transactions list is null.
     */
	private Long getRewardsPerMonth(List<Transaction> transactions) {
		if (transactions == null) {
			logger.error("Transaction list is null. Cannot calculate rewards.");
			throw new NullInputException("Transaction List cannot be null");
		}
		logger.debug("Calculating rewards for {} transactions.", transactions.size());
		return transactions.stream().map(transaction -> calculateRewards(transaction))
				.collect(Collectors.summingLong(r -> r.longValue()));
	}

	/**
     * Determines the reward points based on a transaction amount.
     *
     * @param t The transaction object.
     * @return Reward points earned for the given transaction.
     * @throws NullInputException If the transaction or its amount is null.
     */
	private Long calculateRewards(Transaction t) {
		if (t == null) {
			logger.error("Transaction is null. Cannot calculate rewards.");
			throw new NullInputException("Transaction cannot be null");
		}
		if (t.getTransactionAmount() == null) {
			logger.error("Transaction amount is null. Cannot calculate rewards.");
			throw new NullInputException("Transaction amount cannot be null");
		}
		if (t.getTransactionAmount() > Constants.firstRewardLimit && t.getTransactionAmount() <= Constants.secondRewardLimit) {
			return Math.round(t.getTransactionAmount() - Constants.firstRewardLimit);
		} else if (t.getTransactionAmount() > Constants.secondRewardLimit) {
			return Math.round(t.getTransactionAmount() - Constants.secondRewardLimit) * 2
					+ (Constants.secondRewardLimit - Constants.firstRewardLimit);
		} else
			return 0L;
	}

    /**
     * Gets a timestamp based on an offset in days.
     *
     * @param days Number of days to subtract from the current date.
     * @return Timestamp representing the calculated date.
     */
	public Timestamp getDateBasedOnOffSetDays(int days) {
		Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().minusDays(days));
        logger.debug("Generated timestamp: {} for {} days offset", timestamp, days);
        return timestamp;
	}

	 /**
     * Formats timestamp into 'MMM-yyyy' (e.g., Feb-2025).
     *
     * @param timestamp Timestamp object.
     * @return Formatted date string.
     */
	private String getMonthYearFormat(Timestamp timestamp) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-yyyy");
        String formattedDate = timestamp.toLocalDateTime().format(formatter);
        logger.debug("Formatted timestamp {} to month-year: {}", timestamp, formattedDate);
        return formattedDate;
	}
}
