package com.btork.toolrental.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.btork.toolrental.domain.RentalAgreement;
import com.btork.toolrental.service.ReceiptGenerator;

public class ReceiptGeneratorImplTest {

	private ReceiptGenerator receiptGenerator = new ReceiptGeneratorImpl();

	@Test
	public void testGenerateRentalAgreement() {
		RentalAgreement rentalAgreement = new RentalAgreement();
		rentalAgreement.setChargeDays(123);
		rentalAgreement.setCheckoutDate(LocalDate.of(2024, 6, 22));
		rentalAgreement.setDailyRentalCharge(new BigDecimal("5.3"));
		rentalAgreement.setDiscountAmount(new BigDecimal("10.11"));
		rentalAgreement.setDiscountPercent(15);
		rentalAgreement.setDueDate(LocalDate.of(2024, 7, 5));
		rentalAgreement.setFinalCharge(new BigDecimal("777"));
		rentalAgreement.setPrediscountCharge(new BigDecimal("1234.56"));
		rentalAgreement.setRentalDays(18);
		rentalAgreement.setToolBrand("ToolBrand");
		rentalAgreement.setToolCode("ToolCode");
		rentalAgreement.setToolType("ToolType");

		StringBuilder expectedSb = new StringBuilder();
		expectedSb.append("Rental Agreement").append(System.lineSeparator());
		expectedSb.append("Tool code: ToolCode").append(System.lineSeparator());
		expectedSb.append("Tool type: ToolType").append(System.lineSeparator());
		expectedSb.append("Tool brand: ToolBrand").append(System.lineSeparator());
		expectedSb.append("Rental days: 18").append(System.lineSeparator());
		expectedSb.append("Check out date: 06/22/24").append(System.lineSeparator());
		expectedSb.append("Due date: 07/05/24").append(System.lineSeparator());
		expectedSb.append("Daily rental charge: $5.30").append(System.lineSeparator());
		expectedSb.append("Charge days: 123").append(System.lineSeparator());
		expectedSb.append("Pre-discount charge: $1,234.56").append(System.lineSeparator());
		expectedSb.append("Discount percent: 15%").append(System.lineSeparator());
		expectedSb.append("Discount amount: $10.11").append(System.lineSeparator());
		expectedSb.append("Final charge: $777.00").append(System.lineSeparator());
		String expected = expectedSb.toString();

		String actual = receiptGenerator.generate(rentalAgreement);

		assertEquals(expected, actual);
	}

}
