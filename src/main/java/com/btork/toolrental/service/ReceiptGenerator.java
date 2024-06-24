package com.btork.toolrental.service;

import com.btork.toolrental.domain.RentalAgreement;

/**
 * A strategy for displaying the rental agreement
 */
public interface ReceiptGenerator {

	/**
	 * Generates a Receipt from a RentalAgreement
	 * 
	 * @param rentalAgreement the rentalAgreement used to generate a receipt
	 * @return the receipt
	 */
	public String generate(RentalAgreement rentalAgreement);

}
