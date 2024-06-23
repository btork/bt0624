package com.btork.toolrental.serviceimpl;

import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import com.btork.toolrental.domain.CheckoutData;
import com.btork.toolrental.exceptions.InvalidCheckoutDataException;
import com.btork.toolrental.service.CheckoutDataReader;

@Service
public class CheckoutDataReaderImpl implements CheckoutDataReader {

	@Override
	public CheckoutData read(Reader reader, boolean includePrompts) {
		CheckoutData checkoutData = new CheckoutData();

		Scanner scanner = new Scanner(reader);

		try {
			if (includePrompts) {
				System.out.print("Tool code: ");
			}
			String toolCode = scanner.nextLine();
			checkoutData.setToolCode(toolCode);

			if (includePrompts) {
				System.out.print("Rental day count: ");
			}
			String rentalDayCountString = scanner.nextLine();
			int rentalDayCount = 0;
			try {
				rentalDayCount = Integer.parseInt(rentalDayCountString);
			} catch (NumberFormatException e) {
				throw new InvalidCheckoutDataException("Invalid Rental Day Count. Actual is " + rentalDayCountString);
			}
			checkoutData.setRentalDayCount(rentalDayCount);

			if (includePrompts) {
				System.out.print("Discount percent: ");
			}
			String discountPercentString = scanner.nextLine();
			int discountPercent = 0;
			try {
				discountPercent = Integer.parseInt(discountPercentString);
			} catch (NumberFormatException e) {
				throw e;
			}
			checkoutData.setDiscountPercent(discountPercent);

			if (includePrompts) {
				System.out.print("Check out date (mm/dd/yy): ");
			}
			String checkoutDateString = scanner.nextLine();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yy");
			LocalDate checkoutDate = null;
			try {
				checkoutDate = LocalDate.parse(checkoutDateString, dtf);
			} catch (DateTimeParseException e) {
				throw e;
			}
			checkoutData.setCheckoutDate(checkoutDate);
		} finally {
			scanner.close();
		}

		return checkoutData;
	}

}
