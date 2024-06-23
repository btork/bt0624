package com.btork.toolrental.serviceimpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.btork.toolrental.domain.CheckoutData;
import com.btork.toolrental.domain.RentalAgreement;
import com.btork.toolrental.service.CheckoutDataProcessor;
import com.btork.toolrental.service.CheckoutDataReader;
import com.btork.toolrental.service.ReceiptGenerator;

@Service
public class TransactionExecutorImpl implements CommandLineRunner {

	@Autowired
	private CheckoutDataReader checkoutDataReader;
	@Autowired
	private CheckoutDataProcessor checkoutDataProcessor;
	@Autowired
	private ReceiptGenerator receiptGenerator;

	/**
	 * The main entry point from Spring
	 */
	@Override
	public void run(String... args) throws Exception {
		performTransaction();
	}

	/**
	 * Coordinates the activities of one Checkout transaction
	 */
	public void performTransaction() {
		Reader reader = new BufferedReader(new InputStreamReader(System.in));
		CheckoutData checkoutData = checkoutDataReader.read(reader, true);
		RentalAgreement rentalAgreement = checkoutDataProcessor.performCheckout(checkoutData);
		String receipt = receiptGenerator.generate(rentalAgreement);
		System.out.println(receipt);
	}

}
