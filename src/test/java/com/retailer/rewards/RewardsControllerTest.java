package com.retailer.rewards;

import com.retailer.rewards.model.Rewards;
import com.retailer.rewards.service.RewardsService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
public class RewardsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RewardsService rewardsService;

    // Positive Test: Valid Customer ID
    @Test
    @DisplayName("Test Get Rewards by Customer ID - Success")
    public void testGetRewardsByCustomerId_Success() throws Exception {
        Long customerId = 1L;
        Rewards mockRewards = new Rewards();

        when(rewardsService.getRewardsByCustomerId(customerId)).thenReturn(mockRewards);

        mockMvc.perform(get("/customers/{customerId}/rewards", customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // Negative Test: Customer Not Found (Invalid ID)
    @Test
    @DisplayName("Test Get Rewards by Customer ID - Customer Not Found")
    public void testGetRewardsByCustomerId_CustomerNotFound() throws Exception {
        Long customerId = 999L;
        when(rewardsService.getRewardsByCustomerId(customerId)).thenReturn(null);

        mockMvc.perform(get("/customers/{customerId}/rewards", customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
