package com.retailer.rewards;

import com.retailer.rewards.service.RewardsService;
import com.retailer.rewards.model.Rewards;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit tests for the Retailer Customer Rewards application.
 * This class contains test cases to verify the correctness of reward calculations
 * based on different transaction amounts.
 */
@SpringBootTest
class RetailerCustomerRewardsRestApplicationTests {
    
    @Autowired
    RewardsService rewardsService;
    
    /**
     * Ensures that the Spring Boot application context loads successfully.
     */
    @Test
    void contextLoads() {
    }
    
    @Test
    void testCalculateRewardsForBelowFifty() {
        Rewards rewards = rewardsService.getRewardsByCustomerId(5L);
        Assertions.assertNotNull(rewards, "Rewards object is null");
        System.out.println("Test Customer :::: "+ rewards.getCustomerId());
        Assertions.assertEquals(0, rewards.getTotalRewards());
    }

    @Test
    void testCalculateRewardsForAmountAboveFiftyBelowHundred() {
        Rewards rewards = rewardsService.getRewardsByCustomerId(6L);
        Assertions.assertNotNull(rewards, "Rewards object is null");
        System.out.println("Test Customer :::: "+ rewards.getCustomerId());
        Assertions.assertEquals(5, rewards.getTotalRewards());
    }

    @Test
    void testCalculateRewardsForHundred() {
        Rewards rewards = rewardsService.getRewardsByCustomerId(7L);
        Assertions.assertNotNull(rewards, "Rewards object is null");
        System.out.println("Test Customer :::: "+ rewards.getCustomerId());
        Assertions.assertEquals(50, rewards.getTotalRewards());
    }

    @Test
    void testCalculateRewardsForAmountAboveHundred() {
        Rewards rewards = rewardsService.getRewardsByCustomerId(4L);
        Assertions.assertNotNull(rewards, "Rewards object is null");
        System.out.println("Test Customer :::: "+ rewards.getCustomerId());
        Assertions.assertEquals(90, rewards.getTotalRewards());
    }

}
