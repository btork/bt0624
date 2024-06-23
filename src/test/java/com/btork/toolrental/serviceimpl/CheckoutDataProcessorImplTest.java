package com.btork.toolrental.serviceimpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.btork.toolrental.dao.ToolCostDao;
import com.btork.toolrental.dao.ToolSpecDao;
import com.btork.toolrental.domain.CheckoutData;
import com.btork.toolrental.domain.RentalAgreement;
import com.btork.toolrental.domain.ToolCost;
import com.btork.toolrental.domain.ToolSpec;
import com.btork.toolrental.service.ChargeDayCalculator;
import com.btork.toolrental.service.CheckoutDataProcessor;

@ExtendWith(MockitoExtension.class)
public class CheckoutDataProcessorImplTest {

	@Mock
	private ToolSpecDao toolSpecDao;
	@Mock
	private ToolCostDao toolCostDao;
	@InjectMocks
	private CheckoutDataValidatorImpl checkoutDataValidator;
	@Mock
	private ChargeDayCalculator chargeDayCalculator;

	/**
	 * Test basic checkout. Most of the functionality is delegated so there's not
	 * much to check here.
	 */
	@Test
	public void testCheckout() {
		ToolSpec toolSpec = new ToolSpec("DRL", "Drill", "Venger");
		when(toolSpecDao.getToolSpecByToolCode("DRL")).thenReturn(toolSpec);
		ToolCost toolCost = new ToolCost(new BigDecimal("2.99"), true, false, false);
		when(toolCostDao.getToolCostByToolType("Drill")).thenReturn(toolCost);
		when(chargeDayCalculator.calculateChargeDays(any(LocalDate.class), eq(5), any(ToolCost.class))).thenReturn(2);
		CheckoutDataProcessor checkoutDataProcessor = new CheckoutDataProcessorImpl(checkoutDataValidator, toolSpecDao,
				toolCostDao, chargeDayCalculator);

		CheckoutData checkoutData = new CheckoutData();
		checkoutData.setToolCode("DRL");
		checkoutData.setCheckoutDate(LocalDate.of(2015, 9, 3));
		checkoutData.setRentalDayCount(5);
		checkoutData.setDiscountPercent(10);

		RentalAgreement rentalAgreement = checkoutDataProcessor.performCheckout(checkoutData);

		verify(toolSpecDao, times(2)).getToolSpecByToolCode("DRL");
		verify(toolCostDao, times(1)).getToolCostByToolType("Drill");
		verify(chargeDayCalculator, times(1)).calculateChargeDays(any(LocalDate.class), eq(5), eq(toolCost));

		assertThat(rentalAgreement.getToolType()).isEqualTo("Drill");
		assertThat(rentalAgreement.getToolCode()).isEqualTo("DRL");
		assertThat(rentalAgreement.getToolBrand()).isEqualTo("Venger");
		assertThat(rentalAgreement.getRentalDays()).isEqualTo(5);
		assertThat(rentalAgreement.getCheckoutDate()).isEqualTo(LocalDate.of(2015, 9, 3));
		assertThat(rentalAgreement.getDueDate()).isEqualTo(LocalDate.of(2015, 9, 7));
		assertThat(rentalAgreement.getDailyRentalCharge()).isEqualTo(new BigDecimal("2.99"));
		assertThat(rentalAgreement.getChargeDays()).isEqualTo(2);
		assertThat(rentalAgreement.getPrediscountCharge()).isEqualTo(new BigDecimal("5.98"));
		assertThat(rentalAgreement.getDiscountPercent()).isEqualTo(10);
		assertThat(rentalAgreement.getDiscountAmount()).isEqualTo(new BigDecimal("0.60"));
		assertThat(rentalAgreement.getFinalCharge()).isEqualTo(new BigDecimal("5.38"));
	}

}
