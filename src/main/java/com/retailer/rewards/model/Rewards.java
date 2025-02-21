package com.retailer.rewards.model;

import java.util.Map;

/**
 * Model class representing rewards earned by a customer.
 */
public class Rewards {

	private Long customerId;
	private Map<String, Long> points;
	private Long totalRewards;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Map<String, Long> getPoints() {
		return points;
	}

	public void setPoints(Map<String, Long> points) {
		this.points = points;
	}

	public Long getTotalRewards() {
		return totalRewards;
	}

	public void setTotalRewards(Long totalRewards) {
		this.totalRewards = totalRewards;
	}
}
