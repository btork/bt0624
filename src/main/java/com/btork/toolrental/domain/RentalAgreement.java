package com.btork.toolrental.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RentalAgreement {

	private String toolType;
	private String toolCode;
	private String toolBrand;
	private int rentalDays;
	private LocalDate checkoutDate;
	private LocalDate dueDate;
	private BigDecimal dailyRentalCharge;
	private int chargeDays;
	private BigDecimal prediscountCharge;
	private int discountPercent;
	private BigDecimal discountAmount;
	private BigDecimal finalCharge;
	
	public RentalAgreement() {}

	public RentalAgreement(String toolType, String toolCode, String toolBrand, int rentalDays, LocalDate checkoutDate,
			LocalDate dueDate, BigDecimal dailyRentalCharge, int chargeDays, BigDecimal prediscountCharge,
			int discountPercent, BigDecimal discountAmount, BigDecimal finalCharge) {
		this.toolType = toolType;
		this.toolCode = toolCode;
		this.toolBrand = toolBrand;
		this.rentalDays = rentalDays;
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
		this.dailyRentalCharge = dailyRentalCharge;
		this.chargeDays = chargeDays;
		this.prediscountCharge = prediscountCharge;
		this.discountPercent = discountPercent;
		this.discountAmount = discountAmount;
		this.finalCharge = finalCharge;
	}

	public String getToolType() {
		return toolType;
	}

	public void setToolType(String toolType) {
		this.toolType = toolType;
	}

	public String getToolCode() {
		return toolCode;
	}

	public void setToolCode(String toolCode) {
		this.toolCode = toolCode;
	}

	public String getToolBrand() {
		return toolBrand;
	}

	public void setToolBrand(String toolBrand) {
		this.toolBrand = toolBrand;
	}

	public int getRentalDays() {
		return rentalDays;
	}

	public void setRentalDays(int rentalDays) {
		this.rentalDays = rentalDays;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getDailyRentalCharge() {
		return dailyRentalCharge;
	}

	public void setDailyRentalCharge(BigDecimal dailyRentalCharge) {
		this.dailyRentalCharge = dailyRentalCharge;
	}

	public int getChargeDays() {
		return chargeDays;
	}

	public void setChargeDays(int chargeDays) {
		this.chargeDays = chargeDays;
	}

	public BigDecimal getPrediscountCharge() {
		return prediscountCharge;
	}

	public void setPrediscountCharge(BigDecimal prediscountCharge) {
		this.prediscountCharge = prediscountCharge;
	}

	public int getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(int discountPercent) {
		this.discountPercent = discountPercent;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public BigDecimal getFinalCharge() {
		return finalCharge;
	}

	public void setFinalCharge(BigDecimal finalCharge) {
		this.finalCharge = finalCharge;
	}

	@Override
	public String toString() {
		return "RentalAgreement [toolType=" + toolType + ", toolCode=" + toolCode + ", toolBrand=" + toolBrand
				+ ", rentalDays=" + rentalDays + ", checkoutDate=" + checkoutDate + ", dueDate=" + dueDate
				+ ", dailyRentalCharge=" + dailyRentalCharge + ", chargeDays=" + chargeDays + ", prediscountCharge="
				+ prediscountCharge + ", discountPercent=" + discountPercent + ", discountAmount=" + discountAmount
				+ ", finalCharge=" + finalCharge + "]";
	}

}
