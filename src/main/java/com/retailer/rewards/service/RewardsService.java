package com.retailer.rewards.service;

import com.retailer.rewards.model.Rewards;


/**
 * Service interface for calculating and retrieving reward points for customers.
 */
public interface RewardsService {
    public Rewards getRewardsByCustomerId(Long customerId);
}
