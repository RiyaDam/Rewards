package com.retailer.rewards;

import com.retailer.rewards.controller.RewardsController;
import com.retailer.rewards.entity.Customer;
import com.retailer.rewards.exception.CustomerNotFoundException;
import com.retailer.rewards.model.Rewards;
import com.retailer.rewards.service.RewardsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(SpringExtension.class)
public class RewardsControllerTest {

    @InjectMocks
    private RewardsController rewardsController;

    @Mock
    private RewardsService rewardsService;

    @Mock
    private com.retailer.rewards.repository.CustomerRepository customerRepository;

    // Positive Test: Valid Customer ID
    @Test
    public void testGetRewardsByCustomerId_Success() {
        // Mock data
        Long customerId = 1L;
        Customer mockCustomer = new Customer(customerId, "Alice");
        Rewards mockRewards = new Rewards();
        
        // Mock behavior
        when(customerRepository.findByCustomerId(customerId)).thenReturn(mockCustomer);
        when(rewardsService.getRewardsByCustomerId(customerId)).thenReturn(mockRewards);

        // Call the method
        ResponseEntity<Rewards> response = rewardsController.getRewardsByCustomerId(customerId);

        // Assertions
        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(mockRewards, response.getBody());
    }

    // Negative Test: Customer Not Found (Invalid ID)
    @Test
    public void testGetRewardsByCustomerId_CustomerNotFound() {
        // Mock behavior: Customer ID does not exist
        Long customerId = 999L;
        when(customerRepository.findByCustomerId(customerId)).thenReturn(null);

        // Expect exception
        Exception exception = assertThrows(CustomerNotFoundException.class, 
            () -> rewardsController.getRewardsByCustomerId(customerId));

        // Validate exception message
        String expectedMessage = "Invalid / Missing customer Id: " + customerId;
        assertEquals(expectedMessage, exception.getMessage());
    }

    // Negative Test: Service Failure/Internal Server Error
    @Test
    public void testGetRewardsByCustomerId_InternalServerError() {
        // Mock data
        Long customerId = 2L;
        Customer mockCustomer = new Customer(customerId, "Bob");

        // Mock behavior
        when(customerRepository.findByCustomerId(customerId)).thenReturn(mockCustomer);
        when(rewardsService.getRewardsByCustomerId(customerId)).thenThrow(new RuntimeException("Service failure"));

        // Expect exception
        Exception exception = assertThrows(RuntimeException.class, 
            () -> rewardsController.getRewardsByCustomerId(customerId));

        // Validate exception message
        assertEquals("Service failure", exception.getMessage());
    }
}
