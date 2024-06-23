package com.btork.toolrental.domain;

import java.math.BigDecimal;

/**
 * A ToolCost specifies the daily charge for a tool and the rules for which the
 * charge applies.
 * 
 * NOTE: This class could be broken into two classes, one for the charge and
 * another for the rules associated with applying the charge.
 */
public class ToolCost {

	private BigDecimal dailyCost;
	private boolean chargedOnWeekday;
	private boolean chargedOnWeekend;
	private boolean chargedOnHoliday;

	public ToolCost(BigDecimal dailyCost, boolean chargedOnWeekday, boolean chargedOnWeekend,
			boolean chargedOnHoliday) {
		this.dailyCost = dailyCost;
		this.chargedOnWeekday = chargedOnWeekday;
		this.chargedOnWeekend = chargedOnWeekend;
		this.chargedOnHoliday = chargedOnHoliday;
	}

	/**
	 * @return the price to rent the tool for one day
	 */
	public BigDecimal getDailyCost() {
		return dailyCost;
	}

	/**
	 * Sets the price to rent the tool for one day
	 * 
	 * @param dailyCost
	 */
	public void setDailyCost(BigDecimal dailyCost) {
		this.dailyCost = dailyCost;
	}

	/**
	 * @return true if there is a charge to rent the tool on a weekday
	 */
	public boolean isChargedOnWeekday() {
		return chargedOnWeekday;
	}

	/**
	 * sets whether there is a charge to rent the tool on a weekday
	 */
	public void setChargedOnWeekday(boolean chargedOnWeekday) {
		this.chargedOnWeekday = chargedOnWeekday;
	}

	/**
	 * @return true if there is a charge to rent the tool on a weekend day
	 */
	public boolean isChargedOnWeekend() {
		return chargedOnWeekend;
	}

	/**
	 * sets whether there is a charge to rent the tool on a weekend day
	 */
	public void setChargedOnWeekend(boolean chargedOnWeekend) {
		this.chargedOnWeekend = chargedOnWeekend;
	}

	/**
	 * @return true if there is a charge to rent the tool on a holiday
	 */
	public boolean isChargedOnHoliday() {
		return chargedOnHoliday;
	}

	/**
	 * sets whether there is a charge to rent the tool on a holiday
	 */
	public void setChargedOnHoliday(boolean chargedOnHoliday) {
		this.chargedOnHoliday = chargedOnHoliday;
	}

	@Override
	public String toString() {
		return "ToolCost [dailyCost=" + dailyCost + ", chargedOnWeekday=" + chargedOnWeekday + ", chargedOnWeekend="
				+ chargedOnWeekend + ", chargedOnHoliday=" + chargedOnHoliday + "]";
	}

}
