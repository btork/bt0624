package com.btork.toolrental.serviceimpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.btork.toolrental.dao.ToolSpecDao;
import com.btork.toolrental.domain.CheckoutData;
import com.btork.toolrental.domain.ToolSpec;
import com.btork.toolrental.exceptions.InvalidCheckoutDataException;

@ExtendWith(MockitoExtension.class)
public class CheckoutDataValidatorImplTest {

	@Mock
	private ToolSpecDao toolSpecDao;

	@InjectMocks
	private CheckoutDataValidatorImpl checkoutDataValidator = new CheckoutDataValidatorImpl(toolSpecDao);

	@Test
	public void testMock() {
		ToolSpec mockedToolSpec = new ToolSpec("DRL", "Drill", "Venger");
		when(toolSpecDao.getToolSpecByToolCode("DRL")).thenReturn(mockedToolSpec);

		ToolSpec toolSpec = toolSpecDao.getToolSpecByToolCode("DRL");
		assertNotNull(toolSpec);
		assertEquals("Drill", toolSpec.getType());
	}

	@Test
	public void testValidInput() {
		ToolSpec mockedToolSpec = new ToolSpec("ABC", "Hammer", "Clock");
		when(toolSpecDao.getToolSpecByToolCode("ABC")).thenReturn(mockedToolSpec);

		CheckoutData checkoutData = new CheckoutData("ABC", 1, 0, LocalDate.of(2024, 4, 1));
		checkoutDataValidator.validate(checkoutData);
	}

	// -----------------------------------------------------------------------

	@Test
	public void testInvalidToolType_null() {
		CheckoutData checkoutData = new CheckoutData(null, 1, 0, LocalDate.of(2024, 4, 1));
		InvalidCheckoutDataException exception = assertThrows(InvalidCheckoutDataException.class,
				() -> checkoutDataValidator.validate(checkoutData));
		assertThat(exception.getMessage()).isEqualTo("Tool Code is required");
	}

	@Test
	public void testInvalidToolType_empty() {
		CheckoutData checkoutData = new CheckoutData("", 1, 0, LocalDate.of(2024, 4, 1));
		InvalidCheckoutDataException exception = assertThrows(InvalidCheckoutDataException.class,
				() -> checkoutDataValidator.validate(checkoutData));
		assertThat(exception.getMessage()).isEqualTo("Tool Code is required");
	}

	@Test
	public void testInvalidToolType_notFound() {
		CheckoutData checkoutData = new CheckoutData("#$%", 1, 0, LocalDate.of(2024, 4, 1));
		InvalidCheckoutDataException exception = assertThrows(InvalidCheckoutDataException.class,
				() -> checkoutDataValidator.validate(checkoutData));
		assertThat(exception.getMessage()).isEqualTo("Unknown Tool Code: #$%");
	}

	// -----------------------------------------------------------------------

	@Test
	public void testInvalidRentalDayCount_tooSmall() {
		ToolSpec mockedToolSpec = new ToolSpec("WBRS", "Wheelbarrow", "Sling");
		when(toolSpecDao.getToolSpecByToolCode("WBRS")).thenReturn(mockedToolSpec);

		CheckoutData checkoutData = new CheckoutData("WBRS", -1, 0, LocalDate.of(2024, 4, 1));
		InvalidCheckoutDataException exception = assertThrows(InvalidCheckoutDataException.class,
				() -> checkoutDataValidator.validate(checkoutData));
		assertThat(exception.getMessage()).isEqualTo("Rental Day Count must be 1 or more. Actual is -1");
	}

	@Test
	public void testInvalidRentalDayCount_tooSmall2() {
		ToolSpec mockedToolSpec = new ToolSpec("WBRS", "Wheelbarrow", "Sling");
		when(toolSpecDao.getToolSpecByToolCode("WBRS")).thenReturn(mockedToolSpec);

		CheckoutData checkoutData = new CheckoutData("WBRS", 0, 5, LocalDate.of(2024, 4, 1));
		InvalidCheckoutDataException exception = assertThrows(InvalidCheckoutDataException.class,
				() -> checkoutDataValidator.validate(checkoutData));
		assertThat(exception.getMessage()).isEqualTo("Rental Day Count must be 1 or more. Actual is 0");
	}

	@Test
	public void testValidRentalDayCount_lowest() {
		ToolSpec mockedToolSpec = new ToolSpec("ABC", "Hammer", "Clock");
		when(toolSpecDao.getToolSpecByToolCode("ABC")).thenReturn(mockedToolSpec);

		CheckoutData checkoutData = new CheckoutData("ABC", 1, 0, LocalDate.of(2024, 4, 1));
		checkoutDataValidator.validate(checkoutData);
	}

	@Test
	public void testValidRentalDayCount_highest() {
		ToolSpec mockedToolSpec = new ToolSpec("ABC", "Hammer", "Clock");
		when(toolSpecDao.getToolSpecByToolCode("ABC")).thenReturn(mockedToolSpec);

		CheckoutData checkoutData = new CheckoutData("ABC", 1000, 0, LocalDate.of(2024, 4, 1));
		checkoutDataValidator.validate(checkoutData);
	}

	@Test
	public void testInvalidRentalDayCount_tooLarge() {
		ToolSpec mockedToolSpec = new ToolSpec("WBRS", "Wheelbarrow", "Sling");
		when(toolSpecDao.getToolSpecByToolCode("WBRS")).thenReturn(mockedToolSpec);

		CheckoutData checkoutData = new CheckoutData("WBRS", 1001, 5, LocalDate.of(2024, 4, 1));
		InvalidCheckoutDataException exception = assertThrows(InvalidCheckoutDataException.class,
				() -> checkoutDataValidator.validate(checkoutData));
		assertThat(exception.getMessage()).isEqualTo("Rental Day Count must be 1000 or less. Actual is 1001");
	}

	// -------------------------------------------------------------------------

	@Test
	public void testInvalidDiscountPercent_tooSmall() {
		ToolSpec mockedToolSpec = new ToolSpec("WBRS", "Wheelbarrow", "Sling");
		when(toolSpecDao.getToolSpecByToolCode("WBRS")).thenReturn(mockedToolSpec);

		CheckoutData checkoutData = new CheckoutData("WBRS", 15, -1, LocalDate.of(2023, 12, 31));
		InvalidCheckoutDataException exception = assertThrows(InvalidCheckoutDataException.class,
				() -> checkoutDataValidator.validate(checkoutData));
		assertThat(exception.getMessage()).isEqualTo("Discount Percent must be between 0 and 100. Actual is -1");
	}

	@Test
	public void testValidDiscountPercent_smallest() {
		ToolSpec mockedToolSpec = new ToolSpec("ABC", "Hammer", "Clock");
		when(toolSpecDao.getToolSpecByToolCode("ABC")).thenReturn(mockedToolSpec);

		CheckoutData checkoutData = new CheckoutData("ABC", 15, 0, LocalDate.of(2023, 12, 31));
		checkoutDataValidator.validate(checkoutData);
	}

	@Test
	public void testValidDiscountPercent_largest() {
		ToolSpec mockedToolSpec = new ToolSpec("XYZ", "Wrench", "Tuggo");
		when(toolSpecDao.getToolSpecByToolCode("XYZ")).thenReturn(mockedToolSpec);

		CheckoutData checkoutData = new CheckoutData("XYZ", 15, 100, LocalDate.of(2023, 12, 31));
		checkoutDataValidator.validate(checkoutData);
	}

	@Test
	public void testInvalidDiscountPercent_tooLarge() {
		ToolSpec mockedToolSpec = new ToolSpec("WBRS", "Wheelbarrow", "Sling");
		when(toolSpecDao.getToolSpecByToolCode("WBRS")).thenReturn(mockedToolSpec);

		CheckoutData checkoutData = new CheckoutData("WBRS", 15, 101, LocalDate.of(2023, 12, 31));
		InvalidCheckoutDataException exception = assertThrows(InvalidCheckoutDataException.class,
				() -> checkoutDataValidator.validate(checkoutData));
		assertThat(exception.getMessage()).isEqualTo("Discount Percent must be between 0 and 100. Actual is 101");
	}

}
