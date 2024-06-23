package com.btork.toolrental.service;

import java.time.LocalDate;

import com.btork.toolrental.domain.ToolCost;

/**
 * A calculator for computing the number of days to charge for a rental
 */
public interface ChargeDayCalculator {

	/**
	 * Calculates the number of days that a rental should be charged based on the
	 * days of the rental and the calculation criteria.
	 * 
	 * @param startDate the first day of the rental period
	 * @param duration  the number of days in the rental period
	 * @param toolCost  the pricing configuration
	 * @return the number of days to charge for the rental
	 */
	public int calculateChargeDays(LocalDate startDate, int duration, ToolCost toolCost);

}
