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

/**
 * Processes the CheckoutData to create the RentalAgreement
 */
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

	/**
	 * Takes the rental data specified in the checkoutData and creates a
	 * RentalAgreement that contains the details of the tool rental.
	 */
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
		rentalAgreement.setDueDate(calculateDueDate(checkoutData.getCheckoutDate(), checkoutData.getRentalDayCount()));

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

	/**
	 * computes the end date of the rental
	 * 
	 * @param startDate the first day of the rental
	 * @param duration  the number of days in the rental
	 * @return the end date
	 */
	private LocalDate calculateDueDate(LocalDate startDate, int duration) {
		return startDate.plusDays(duration - 1);
	}

	/**
	 * Calculates the number of days to charge for the rental
	 * 
	 * @param startDate The start date of the rental
	 * @param duration  the duration of the rental
	 * @param toolCost  the cost parameters including daily cost and which days are
	 *                  chargeable.
	 * @return the number of chargeable days
	 */
	private int calculateChargeDays(LocalDate startDate, int duration, ToolCost toolCost) {
		return chargeDayCalculator.calculateChargeDays(startDate, duration, toolCost);
	}

	/**
	 * Calculates the cost of the rental before a discount is applied
	 * 
	 * @param dailyCost The cost for a day or rental
	 * @param numDays   The number of days rented
	 * @return The cost for the rental period
	 */
	private BigDecimal calculatePrediscountCharge(BigDecimal dailyCost, int numDays) {
		return dailyCost.multiply(new BigDecimal(numDays));
	}

	/**
	 * Calculates the discount amount
	 * 
	 * @param prediscountAmount The gross amount of the rental
	 * @param discountPercent   The discount percentage
	 * @return the total amount of the discount
	 */
	private BigDecimal calculateDiscountAmount(BigDecimal prediscountAmount, int discountPercent) {
		return prediscountAmount.multiply(new BigDecimal(discountPercent).divide(new BigDecimal(100))).setScale(2,
				RoundingMode.HALF_UP);
	}
}
