package com.btork.toolrental.service;

import com.btork.toolrental.domain.RentalAgreement;

/**
 * A strategy for displaying the rental agreement
 */
public interface ReceiptGenerator {

	public String generate(RentalAgreement rentalAgreement);

}
