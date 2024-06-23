package com.btork.toolrental.service;

import com.btork.toolrental.domain.CheckoutData;

/**
 * A strategy pattern for validating a CheckoutData. Note, some validations are
 * performed when the CheckoutData is created.
 */
public interface CheckoutDataValidator {

	/**
	 * Performs field validations on a CheckoutData
	 * 
	 * @param checkoutData the CheckoutData to be validated
	 */
	public void validate(CheckoutData checkoutData);

}
