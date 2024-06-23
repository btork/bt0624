package com.btork.toolrental.domain;

import java.time.LocalDate;

public class CheckoutData {

	private String toolCode;
	private int rentalDayCount;
	private int discountPercent;
	private LocalDate checkoutDate;

	public CheckoutData() {
	}

	public CheckoutData(String toolCode, int rentalDayCount, int discountPercent, LocalDate checkoutDate) {
		this.toolCode = toolCode;
		this.rentalDayCount = rentalDayCount;
		this.discountPercent = discountPercent;
		this.checkoutDate = checkoutDate;
	}

	public String getToolCode() {
		return toolCode;
	}

	public void setToolCode(String toolCode) {
		this.toolCode = toolCode;
	}

	public int getRentalDayCount() {
		return rentalDayCount;
	}

	public void setRentalDayCount(int rentalDayCount) {
		this.rentalDayCount = rentalDayCount;
	}

	public int getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(int discountPercent) {
		this.discountPercent = discountPercent;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	@Override
	public String toString() {
		return "CheckoutData [toolCode=" + toolCode + ", rentalDayCount=" + rentalDayCount + ", discountPercent="
				+ discountPercent + ", checkoutDate=" + checkoutDate + "]";
	}

}
