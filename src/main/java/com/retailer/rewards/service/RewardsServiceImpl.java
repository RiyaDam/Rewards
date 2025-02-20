package com.retailer.rewards.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailer.rewards.constants.Constants;
import com.retailer.rewards.entity.Transaction;
import com.retailer.rewards.model.Rewards;
import com.retailer.rewards.repository.TransactionRepository;

@Service
public class RewardsServiceImpl implements RewardsService {
	
	private static final Logger logger = LoggerFactory.getLogger(RewardsService.class);
	
	@Autowired
	TransactionRepository transactionRepository;
	
	/**
	 * Calculates the reward points for a given customer based on transaction history.
	 * @param customerId The unique ID of the customer.
	 * @return A {@link Rewards} object containing reward details. 
	 */
	public Rewards getRewardsByCustomerId(Long customerId) {

		Timestamp lastMonthTimestamp = getDateBasedOnOffSetDays(Constants.daysInMonths);
		Timestamp lastSecondMonthTimestamp = getDateBasedOnOffSetDays(2*Constants.daysInMonths);
		Timestamp lastThirdMonthTimestamp = getDateBasedOnOffSetDays(3*Constants.daysInMonths);

		List<Transaction> lastMonthTransactions = transactionRepository.findAllByCustomerIdAndTransactionDateBetween(
				customerId, lastMonthTimestamp, Timestamp.from(Instant.now()));
		List<Transaction> lastSecondMonthTransactions = transactionRepository
				.findAllByCustomerIdAndTransactionDateBetween(customerId, lastSecondMonthTimestamp, lastMonthTimestamp);
		List<Transaction> lastThirdMonthTransactions = transactionRepository
				.findAllByCustomerIdAndTransactionDateBetween(customerId, lastThirdMonthTimestamp,
						lastSecondMonthTimestamp);
		logger.debug("Transactions fetched successfully");

		Long lastMonthRewardPoints = getRewardsPerMonth(lastMonthTransactions);
		Long lastSecondMonthRewardPoints = getRewardsPerMonth(lastSecondMonthTransactions);
		Long lastThirdMonthRewardPoints = getRewardsPerMonth(lastThirdMonthTransactions);

		Rewards customerRewards = new Rewards();
		customerRewards.setCustomerId(customerId);
		customerRewards.setLastMonthRewardPoints(lastMonthRewardPoints);
		customerRewards.setLastSecondMonthRewardPoints(lastSecondMonthRewardPoints);
		customerRewards.setLastThirdMonthRewardPoints(lastThirdMonthRewardPoints);
		customerRewards.setTotalRewards(lastMonthRewardPoints + lastSecondMonthRewardPoints + lastThirdMonthRewardPoints);

		logger.info("Rewards calculated successfully for Customer ID: {}", customerId);
		return customerRewards;

	}

	/**
	 * Calculates the reward points for a given list of transaction. 
	 * @param transactions List of transactions within a specific month.
	 * @return Total reward points for the transactions. 
	 */
	private Long getRewardsPerMonth(List<Transaction> transactions) {
		return transactions.stream().map(transaction -> calculateRewards(transaction))
				.collect(Collectors.summingLong(r -> r.longValue()));
	}

	/**
	 * Determines reward points based on transaction amount. 
	 * @param transaction The transaction object.
	 * @return Reward points earned for the given transaction. 
	 */
	private Long calculateRewards(Transaction t) {
		if (t.getTransactionAmount() > Constants.firstRewardLimit && t.getTransactionAmount() <= Constants.secondRewardLimit) {
			return Math.round(t.getTransactionAmount() - Constants.firstRewardLimit);
		} else if (t.getTransactionAmount() > Constants.secondRewardLimit) {
			return Math.round(t.getTransactionAmount() - Constants.secondRewardLimit) * 2
					+ (Constants.secondRewardLimit - Constants.firstRewardLimit);
		} else
			return 0l;

	}

	/**
	 * Gets a timestamp based on an offset in days. 
	 * @param daysOffset Number of days to subtract from the current date.
	 * @return timestamp representing the calculated date. 
	 */
	public Timestamp getDateBasedOnOffSetDays(int days) {
		return Timestamp.valueOf(LocalDateTime.now().minusDays(days));
	}

}
