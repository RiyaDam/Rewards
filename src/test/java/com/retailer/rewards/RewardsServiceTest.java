package com.retailer.rewards;

import com.retailer.rewards.service.RewardsService;
import com.retailer.rewards.model.Rewards;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.retailer.rewards.entity.Customer;
import com.retailer.rewards.exception.CustomerNotFoundException;
import com.retailer.rewards.exception.NullInputException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.retailer.rewards.repository.TransactionRepository;
import com.retailer.rewards.repository.CustomerRepository;


/**
 * Unit tests for the Retailer Customer Rewards application.
 * This class contains test cases to verify the correctness of reward calculations
 * based on different transaction amounts.
 */
@ExtendWith(MockitoExtension.class)  // Enable Mockito Annotations
@SpringBootTest
class RewardsServiceTest {
	
	@Mock
    private CustomerRepository customerRepository; 
	
	@Mock
    private TransactionRepository transactionRepository;  
    
    @Autowired
    RewardsService rewardsService;
    
    /**
     * Ensures that the Spring Boot application context loads successfully.
     */
    @Test
    @DisplayName("Testing if Spring Boot application context loads")
    void contextLoads() {
    }
    
    @Test
    @DisplayName("Testing rewards calculation for amount below fifty")
    void testCalculateRewardsForBelowFifty() {
        Rewards rewards = rewardsService.getRewardsByCustomerId(5L);
        Assertions.assertNotNull(rewards, "Rewards object is null");
        Assertions.assertEquals(0, rewards.getTotalRewards());
    }

    @Test
    @DisplayName("Testing rewards calculation for amount above fifty but below hundred")
    void testCalculateRewardsForAmountAboveFiftyBelowHundred() {
        Rewards rewards = rewardsService.getRewardsByCustomerId(6L);
        Assertions.assertNotNull(rewards, "Rewards object is null");
        Assertions.assertEquals(5, rewards.getTotalRewards());
    }

    @Test
    @DisplayName("Testing rewards calculation for exact hundred")
    void testCalculateRewardsForHundred() {
        Rewards rewards = rewardsService.getRewardsByCustomerId(7L);
        Assertions.assertNotNull(rewards, "Rewards object is null");
        Assertions.assertEquals(50, rewards.getTotalRewards());
    }

    @Test
    @DisplayName("Testing rewards calculation for amount above hundred")
    void testCalculateRewardsForAmountAboveHundred() {
        Rewards rewards = rewardsService.getRewardsByCustomerId(4L);
        Assertions.assertNotNull(rewards, "Rewards object is null");
        Assertions.assertEquals(90, rewards.getTotalRewards());
    }
    
    @Test
    @DisplayName("Testing rewards calculation for invalid customer ID")
    void testCalculateRewardsForInvalidCustomerId() {
    	Long invalidCustomerId = 999L; // Customer does not exist
    	Exception exception = Assertions.assertThrows(CustomerNotFoundException.class, () -> {
            rewardsService.getRewardsByCustomerId(invalidCustomerId);
        });
    	String expectedMessage = "Customer ID " + invalidCustomerId + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage), "Unexpected exception message: " + actualMessage);
    }

    @Test
    @DisplayName("Testing rewards calculation for null customer ID")
    void testCalculateRewardsForNullCustomerId() {
    	NullInputException exception = Assertions.assertThrows(NullInputException.class, () -> {
            rewardsService.getRewardsByCustomerId(null);
        });

        Assertions.assertEquals("Customer ID cannot be null", exception.getMessage());
    }
    

    @Test
    @DisplayName("Testing rewards calculation for a customer with no transactions")
    void testCalculateRewardsForNoTransaction() {
        Long customerIdWithNoTransactions = 8L;

        // Mock behavior: Customer exists but has no transactions
        Rewards rewards = rewardsService.getRewardsByCustomerId(customerIdWithNoTransactions);
        
        Assertions.assertNotNull(rewards, "Rewards object is null");
        Assertions.assertEquals(0, rewards.getTotalRewards(), "Expected rewards to be zero for no transactions");
    }
}
