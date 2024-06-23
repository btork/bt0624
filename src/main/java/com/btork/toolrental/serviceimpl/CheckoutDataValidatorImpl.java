package com.btork.toolrental.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btork.toolrental.dao.ToolSpecDao;
import com.btork.toolrental.domain.CheckoutData;
import com.btork.toolrental.domain.ToolSpec;
import com.btork.toolrental.exceptions.InvalidCheckoutDataException;
import com.btork.toolrental.exceptions.InvalidDiscountPercentException;
import com.btork.toolrental.exceptions.InvalidRentalDayCountException;
import com.btork.toolrental.service.CheckoutDataValidator;

@Service
public class CheckoutDataValidatorImpl implements CheckoutDataValidator {
	public static final int MIN_RENTAL_DAY_COUNT = 1;
	public static final int MAX_RENTAL_DAY_COUNT = 1000;

	public static final int MIN_DISCOUNT_PERCENT = 0;
	public static final int MAX_DISCOUNT_PERCENT = 100;

	private ToolSpecDao toolSpecDao;

	@Autowired
	public CheckoutDataValidatorImpl(ToolSpecDao toolSpecDao) {
		this.toolSpecDao = toolSpecDao;
	}

	@Override
	public void validate(CheckoutData checkoutData)
			throws InvalidRentalDayCountException, InvalidDiscountPercentException {

		if (checkoutData.getToolCode() == null || checkoutData.getToolCode().length() == 0) {
			throw new InvalidCheckoutDataException("Tool Code is required");
		}

		ToolSpec toolSpec = toolSpecDao.getToolSpecByToolCode(checkoutData.getToolCode());
		if (toolSpec == null) {
			throw new InvalidCheckoutDataException("Unknown Tool Code: " + checkoutData.getToolCode());
		}

		if (checkoutData.getRentalDayCount() < MIN_RENTAL_DAY_COUNT) {
			throw new InvalidCheckoutDataException("Rental Day Count must be " + MIN_RENTAL_DAY_COUNT
					+ " or more. Actual is " + checkoutData.getRentalDayCount());
		}
		if (checkoutData.getRentalDayCount() > MAX_RENTAL_DAY_COUNT) {
			throw new InvalidCheckoutDataException("Rental Day Count must be " + MAX_RENTAL_DAY_COUNT
					+ " or less. Actual is " + checkoutData.getRentalDayCount());
		}

		if (checkoutData.getDiscountPercent() < MIN_DISCOUNT_PERCENT
				|| checkoutData.getDiscountPercent() > MAX_DISCOUNT_PERCENT) {
			throw new InvalidCheckoutDataException("Discount Percent must be between " + MIN_DISCOUNT_PERCENT + " and "
					+ MAX_DISCOUNT_PERCENT + ". Actual is " + checkoutData.getDiscountPercent());
		}
	}

}
