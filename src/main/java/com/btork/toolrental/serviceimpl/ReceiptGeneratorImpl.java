package com.btork.toolrental.serviceimpl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.btork.toolrental.domain.RentalAgreement;
import com.btork.toolrental.service.ReceiptGenerator;

@Service
public class ReceiptGeneratorImpl implements ReceiptGenerator {

	public String generate(RentalAgreement rentalAgreement) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yy");
		StringBuilder sb = new StringBuilder();

		sb.append("Rental Agreement").append(System.lineSeparator());
		sb.append("Tool code: ").append(rentalAgreement.getToolCode()).append(System.lineSeparator());
		sb.append("Tool type: ").append(rentalAgreement.getToolType()).append(System.lineSeparator());
		sb.append("Tool brand: ").append(rentalAgreement.getToolBrand()).append(System.lineSeparator());
		sb.append("Rental days: ").append(rentalAgreement.getRentalDays()).append(System.lineSeparator());
		sb.append("Check out date: ").append(dtf.format(rentalAgreement.getCheckoutDate()))
				.append(System.lineSeparator());
		sb.append("Due date: ").append(dtf.format(rentalAgreement.getDueDate())).append(System.lineSeparator());
		sb.append("Daily rental charge: ").append(formatDollars(rentalAgreement.getDailyRentalCharge()))
				.append(System.lineSeparator());
		sb.append("Charge days: ").append(rentalAgreement.getChargeDays()).append(System.lineSeparator());
		sb.append("Pre-discount charge: ").append(formatDollars(rentalAgreement.getPrediscountCharge()))
				.append(System.lineSeparator());
		sb.append("Discount percent: ").append(formatPercent(rentalAgreement.getDiscountPercent()))
				.append(System.lineSeparator());
		sb.append("Discount amount: ").append(formatDollars(rentalAgreement.getDiscountAmount()))
				.append(System.lineSeparator());
		sb.append("Final charge: ").append(formatDollars(rentalAgreement.getFinalCharge()))
				.append(System.lineSeparator());

		return sb.toString();
	}

	private String formatDollars(BigDecimal value) {
		return NumberFormat.getCurrencyInstance().format(value);
	}

	private String formatPercent(int value) {
		return value + "%";
	}
}
