package com.btork.toolrental;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.btork.toolrental.dao.ToolCostDao;
import com.btork.toolrental.dao.ToolSpecDao;
import com.btork.toolrental.daoimpl.ToolCostDaoImpl;
import com.btork.toolrental.daoimpl.ToolSpecDaoImpl;
import com.btork.toolrental.domain.CheckoutData;
import com.btork.toolrental.domain.RentalAgreement;
import com.btork.toolrental.exceptions.InvalidCheckoutDataException;
import com.btork.toolrental.service.ChargeDayCalculator;
import com.btork.toolrental.service.CheckoutDataProcessor;
import com.btork.toolrental.service.CheckoutDataValidator;
import com.btork.toolrental.serviceimpl.ChargeDayCalculatorImpl;
import com.btork.toolrental.serviceimpl.CheckoutDataProcessorImpl;
import com.btork.toolrental.serviceimpl.CheckoutDataValidatorImpl;

/**
 * These integration tests verify the 6 scenarios from the spec
 */
public class ToolRentalIntegrationTest {

	// manually create and wire the pieces
	private ToolSpecDao toolSpecDao = new ToolSpecDaoImpl();
	private ToolCostDao toolCostDao = new ToolCostDaoImpl();
	private CheckoutDataValidator checkoutDataValidator = new CheckoutDataValidatorImpl(toolSpecDao);
	private ChargeDayCalculator chargeDayCalculator = new ChargeDayCalculatorImpl();
	private CheckoutDataProcessor checkoutDataProcessor = new CheckoutDataProcessorImpl(checkoutDataValidator,
			toolSpecDao, toolCostDao, chargeDayCalculator);

	@Test
	public void testIntegrationCase1() {
		CheckoutData checkoutData = new CheckoutData();
		checkoutData.setToolCode("JAKR");
		checkoutData.setCheckoutDate(LocalDate.of(2015, 9, 3));
		checkoutData.setRentalDayCount(5);
		checkoutData.setDiscountPercent(101);

		InvalidCheckoutDataException exception = assertThrows(InvalidCheckoutDataException.class,
				() -> checkoutDataProcessor.performCheckout(checkoutData));
		assertThat(exception.getMessage()).isEqualTo("Discount Percent must be between 0 and 100. Actual is 101");
	}

	@Test
	public void testIntegrationCase2() {
		CheckoutData checkoutData = new CheckoutData();
		checkoutData.setToolCode("LADW");
		checkoutData.setCheckoutDate(LocalDate.of(2020, 7, 2));
		checkoutData.setRentalDayCount(3);
		checkoutData.setDiscountPercent(10);

		RentalAgreement rentalAgreement = checkoutDataProcessor.performCheckout(checkoutData);

		assertThat(rentalAgreement.getToolType()).isEqualTo("Ladder");
		assertThat(rentalAgreement.getToolCode()).isEqualTo("LADW");
		assertThat(rentalAgreement.getToolBrand()).isEqualTo("Werner");
		assertThat(rentalAgreement.getRentalDays()).isEqualTo(3);
		assertThat(rentalAgreement.getCheckoutDate()).isEqualTo(LocalDate.of(2020, 7, 2));
		assertThat(rentalAgreement.getDueDate()).isEqualTo(LocalDate.of(2020, 7, 4));
		assertThat(rentalAgreement.getDailyRentalCharge()).isEqualTo(new BigDecimal("1.99"));
		assertThat(rentalAgreement.getChargeDays()).isEqualTo(2);
		assertThat(rentalAgreement.getPrediscountCharge()).isEqualTo(new BigDecimal("3.98"));
		assertThat(rentalAgreement.getDiscountPercent()).isEqualTo(10);
		assertThat(rentalAgreement.getDiscountAmount()).isEqualTo(new BigDecimal("0.40"));
		assertThat(rentalAgreement.getFinalCharge()).isEqualTo(new BigDecimal("3.58"));
	}

	@Test
	public void testIntegrationCase3() {
		CheckoutData checkoutData = new CheckoutData();
		checkoutData.setToolCode("CHNS");
		checkoutData.setRentalDayCount(5);
		checkoutData.setDiscountPercent(25);
		checkoutData.setCheckoutDate(LocalDate.of(2015, 7, 2));

		RentalAgreement rentalAgreement = checkoutDataProcessor.performCheckout(checkoutData);

		assertThat(rentalAgreement.getToolCode()).isEqualTo("CHNS");
		assertThat(rentalAgreement.getToolType()).isEqualTo("Chainsaw");
		assertThat(rentalAgreement.getToolBrand()).isEqualTo("Stihl");
		assertThat(rentalAgreement.getRentalDays()).isEqualTo(5);
		assertThat(rentalAgreement.getCheckoutDate()).isEqualTo(LocalDate.of(2015, 7, 2));
		assertThat(rentalAgreement.getDueDate()).isEqualTo(LocalDate.of(2015, 7, 6));
		assertThat(rentalAgreement.getDailyRentalCharge()).isEqualTo(new BigDecimal("1.49"));
		assertThat(rentalAgreement.getChargeDays()).isEqualTo(3);
		assertThat(rentalAgreement.getPrediscountCharge()).isEqualTo(new BigDecimal("4.47"));
		assertThat(rentalAgreement.getDiscountPercent()).isEqualTo(25);
		assertThat(rentalAgreement.getDiscountAmount()).isEqualTo(new BigDecimal("1.12"));
		assertThat(rentalAgreement.getFinalCharge()).isEqualTo(new BigDecimal("3.35"));
	}

	@Test
	public void testIntegrationCase4() {
		CheckoutData checkoutData = new CheckoutData();
		checkoutData.setToolCode("JAKD");
		checkoutData.setRentalDayCount(6);
		checkoutData.setDiscountPercent(0);
		checkoutData.setCheckoutDate(LocalDate.of(2015, 9, 3));

		RentalAgreement rentalAgreement = checkoutDataProcessor.performCheckout(checkoutData);

		assertThat(rentalAgreement.getToolCode()).isEqualTo("JAKD");
		assertThat(rentalAgreement.getToolType()).isEqualTo("Jackhammer");
		assertThat(rentalAgreement.getToolBrand()).isEqualTo("DeWalt");
		assertThat(rentalAgreement.getRentalDays()).isEqualTo(6);
		assertThat(rentalAgreement.getCheckoutDate()).isEqualTo(LocalDate.of(2015, 9, 3));
		assertThat(rentalAgreement.getDueDate()).isEqualTo(LocalDate.of(2015, 9, 8));
		assertThat(rentalAgreement.getDailyRentalCharge()).isEqualTo(new BigDecimal("2.99"));
		assertThat(rentalAgreement.getChargeDays()).isEqualTo(3);
		assertThat(rentalAgreement.getPrediscountCharge()).isEqualTo(new BigDecimal("8.97"));
		assertThat(rentalAgreement.getDiscountPercent()).isEqualTo(0);
		assertThat(rentalAgreement.getDiscountAmount()).isEqualTo(new BigDecimal("0.00"));
		assertThat(rentalAgreement.getFinalCharge()).isEqualTo(new BigDecimal("8.97"));
	}

	@Test
	public void testIntegrationCase5() {
		CheckoutData checkoutData = new CheckoutData();
		checkoutData.setToolCode("JAKR");
		checkoutData.setRentalDayCount(9);
		checkoutData.setDiscountPercent(0);
		checkoutData.setCheckoutDate(LocalDate.of(2015, 7, 2));

		RentalAgreement rentalAgreement = checkoutDataProcessor.performCheckout(checkoutData);

		assertThat(rentalAgreement.getToolCode()).isEqualTo("JAKR");
		assertThat(rentalAgreement.getToolType()).isEqualTo("Jackhammer");
		assertThat(rentalAgreement.getToolBrand()).isEqualTo("Ridgid");
		assertThat(rentalAgreement.getRentalDays()).isEqualTo(9);
		assertThat(rentalAgreement.getCheckoutDate()).isEqualTo(LocalDate.of(2015, 7, 2));
		assertThat(rentalAgreement.getDueDate()).isEqualTo(LocalDate.of(2015, 7, 10));
		assertThat(rentalAgreement.getDailyRentalCharge()).isEqualTo(new BigDecimal("2.99"));
		assertThat(rentalAgreement.getChargeDays()).isEqualTo(6);
		assertThat(rentalAgreement.getPrediscountCharge()).isEqualTo(new BigDecimal("17.94"));
		assertThat(rentalAgreement.getDiscountPercent()).isEqualTo(0);
		assertThat(rentalAgreement.getDiscountAmount()).isEqualTo(new BigDecimal("0.00"));
		assertThat(rentalAgreement.getFinalCharge()).isEqualTo(new BigDecimal("17.94"));
	}

	@Test
	public void testIntegrationCase6() {
		CheckoutData checkoutData = new CheckoutData();
		checkoutData.setToolCode("JAKR");
		checkoutData.setRentalDayCount(4);
		checkoutData.setDiscountPercent(50);
		checkoutData.setCheckoutDate(LocalDate.of(2020, 7, 2));

		RentalAgreement rentalAgreement = checkoutDataProcessor.performCheckout(checkoutData);

		assertThat(rentalAgreement.getToolCode()).isEqualTo("JAKR");
		assertThat(rentalAgreement.getToolType()).isEqualTo("Jackhammer");
		assertThat(rentalAgreement.getToolBrand()).isEqualTo("Ridgid");
		assertThat(rentalAgreement.getRentalDays()).isEqualTo(4);
		assertThat(rentalAgreement.getCheckoutDate()).isEqualTo(LocalDate.of(2020, 7, 2));
		assertThat(rentalAgreement.getDueDate()).isEqualTo(LocalDate.of(2020, 7, 5));
		assertThat(rentalAgreement.getDailyRentalCharge()).isEqualTo(new BigDecimal("2.99"));
		assertThat(rentalAgreement.getChargeDays()).isEqualTo(1);
		assertThat(rentalAgreement.getPrediscountCharge()).isEqualTo(new BigDecimal("2.99"));
		assertThat(rentalAgreement.getDiscountPercent()).isEqualTo(50);
		assertThat(rentalAgreement.getDiscountAmount()).isEqualTo(new BigDecimal("1.50"));
		assertThat(rentalAgreement.getFinalCharge()).isEqualTo(new BigDecimal("1.49"));
	}

}
