package com.btork.toolrental.serviceimpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btork.toolrental.dao.ToolCostDao;
import com.btork.toolrental.dao.ToolSpecDao;
import com.btork.toolrental.domain.CheckoutData;
import com.btork.toolrental.domain.RentalAgreement;
import com.btork.toolrental.domain.ToolCost;
import com.btork.toolrental.domain.ToolSpec;
import com.btork.toolrental.exceptions.InvalidToolTypeException;
import com.btork.toolrental.service.ChargeDayCalculator;
import com.btork.toolrental.service.CheckoutDataProcessor;
import com.btork.toolrental.service.CheckoutDataValidator;

@Service
public class CheckoutDataProcessorImpl implements CheckoutDataProcessor {

	private CheckoutDataValidator checkoutDataValidator;

	private ToolSpecDao toolSpecDao;

	private ToolCostDao toolCostDao;

	private ChargeDayCalculator chargeDayCalculator;

	@Autowired
	public CheckoutDataProcessorImpl(CheckoutDataValidator checkoutDataValidator, ToolSpecDao toolSpecDao,
			ToolCostDao toolCostDao, ChargeDayCalculator chargeDayCalculator) {
		this.checkoutDataValidator = checkoutDataValidator;
		this.toolSpecDao = toolSpecDao;
		this.toolCostDao = toolCostDao;
		this.chargeDayCalculator = chargeDayCalculator;
	}

	@Override
	public RentalAgreement performCheckout(CheckoutData checkoutData) {
		checkoutDataValidator.validate(checkoutData);

		ToolSpec toolSpec = toolSpecDao.getToolSpecByToolCode(checkoutData.getToolCode());
		ToolCost toolCost = toolCostDao.getToolCostByToolType(toolSpec.getType());
		if (toolCost == null) {
			throw new InvalidToolTypeException("Invalid Tool Type: " + toolSpec.getType());
		}

		RentalAgreement rentalAgreement = new RentalAgreement();

		rentalAgreement.setToolCode(checkoutData.getToolCode());
		rentalAgreement.setToolType(toolSpec.getType());
		rentalAgreement.setToolBrand(toolSpec.getBrand());

		rentalAgreement.setRentalDays(checkoutData.getRentalDayCount());
		rentalAgreement.setCheckoutDate(checkoutData.getCheckoutDate());
		rentalAgreement
				.setDueDate(calculateDueDate(checkoutData.getCheckoutDate(), checkoutData.getRentalDayCount() - 1));

		rentalAgreement.setDailyRentalCharge(toolCost.getDailyCost());
		rentalAgreement.setChargeDays(
				calculateChargeDays(checkoutData.getCheckoutDate(), checkoutData.getRentalDayCount(), toolCost));
		rentalAgreement.setPrediscountCharge(
				calculatePrediscountCharge(rentalAgreement.getDailyRentalCharge(), rentalAgreement.getChargeDays()));
		rentalAgreement.setDiscountPercent(checkoutData.getDiscountPercent());
		rentalAgreement.setDiscountAmount(
				calculateDiscountAmount(rentalAgreement.getPrediscountCharge(), rentalAgreement.getDiscountPercent()));
		rentalAgreement
				.setFinalCharge(rentalAgreement.getPrediscountCharge().subtract(rentalAgreement.getDiscountAmount()));

		return rentalAgreement;
	}

	private LocalDate calculateDueDate(LocalDate startDate, int duration) {
		return startDate.plusDays(duration);
	}

	private int calculateChargeDays(LocalDate startDate, int duration, ToolCost toolCost) {
		return chargeDayCalculator.calculateChargeDays(startDate, duration, toolCost);
	}

	private BigDecimal calculatePrediscountCharge(BigDecimal periodicCost, int numPeriods) {
		return periodicCost.multiply(new BigDecimal(numPeriods));
	}

	private BigDecimal calculateDiscountAmount(BigDecimal prediscountAmount, int discountPercent) {
		return prediscountAmount.multiply(new BigDecimal(discountPercent).divide(new BigDecimal(100))).setScale(2,
				RoundingMode.HALF_UP);
	}
}
