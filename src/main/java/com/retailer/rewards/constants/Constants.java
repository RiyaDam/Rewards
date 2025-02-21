package com.retailer.rewards.constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Constants used in the rewards calculation.
 */
public class Constants {
	private static final Logger logger = LoggerFactory.getLogger(Constants.class);
	 
    public static final int daysInMonths = 30;
    public static int firstRewardLimit = 50;
    public static int secondRewardLimit = 100;
    
    static {
        logger.info("Constants initialized: DAYS_IN_MONTHS={}, FIRST_REWARD_LIMIT={}, SECOND_REWARD_LIMIT={}",
        		daysInMonths, firstRewardLimit, secondRewardLimit);
    }
}
