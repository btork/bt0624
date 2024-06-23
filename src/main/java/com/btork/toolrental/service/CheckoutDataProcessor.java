package com.btork.toolrental.service;

import com.btork.toolrental.domain.CheckoutData;
import com.btork.toolrental.domain.RentalAgreement;

/**
 * Encapsulates the process of performing a checkout for a checkout data
 */
public interface CheckoutDataProcessor {

	/**
	 * Performs the checkout process. The main function is to create a
	 * RentalAgreement
	 * 
	 * @param checkoutData the data from the checkout process
	 * @return a RentalAgreement that summarizes the transaction
	 */
	public RentalAgreement performCheckout(CheckoutData checkoutData);

}
