package com.btork.toolrental.serviceimpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

import com.btork.toolrental.domain.CheckoutData;
import com.btork.toolrental.exceptions.InvalidCheckoutDataException;

public class CheckoutDataReaderImplTest {

	private CheckoutDataReaderImpl checkoutDataReader = new CheckoutDataReaderImpl();

	/**
	 * Test a set of valid input
	 */
	@Test
	void testValidInput() {
		StringReader reader = new StringReader("CHNS\n10\n25\n9/1/24");

		CheckoutData checkoutData = checkoutDataReader.read(reader, false);

		assertThat(checkoutData.getToolCode()).isEqualTo("CHNS");
		assertThat(checkoutData.getRentalDayCount()).isEqualTo(10);
		assertThat(checkoutData.getDiscountPercent()).isEqualTo(25);
		assertThat(checkoutData.getCheckoutDate()).isEqualTo(LocalDate.of(2024, 9, 1));
	}

	@Test
	void testEmptyToolType() {
		StringReader reader = new StringReader("\n10\n25\n9/1/24");

		CheckoutData checkoutData = checkoutDataReader.read(reader, false);

		assertThat(checkoutData.getToolCode()).isEqualTo("");
		assertThat(checkoutData.getRentalDayCount()).isEqualTo(10);
		assertThat(checkoutData.getDiscountPercent()).isEqualTo(25);
		assertThat(checkoutData.getCheckoutDate()).isEqualTo(LocalDate.of(2024, 9, 1));
	}

	/**
	 * Test invalid toolType. NOTE: This passes the input phase. The invalid input
	 * is detected later in the application.
	 */
	@Test
	void testInvalidToolType() {
		StringReader reader = new StringReader("###\n10\n25\n9/1/24");

		CheckoutData checkoutData = checkoutDataReader.read(reader, false);

		assertThat(checkoutData.getToolCode()).isEqualTo("###");
		assertThat(checkoutData.getRentalDayCount()).isEqualTo(10);
		assertThat(checkoutData.getDiscountPercent()).isEqualTo(25);
		assertThat(checkoutData.getCheckoutDate()).isEqualTo(LocalDate.of(2024, 9, 1));
	}

	// -----------------------------------------------------------------------

	@Test
	void testInvalidRentalDayCount_missing() {
		StringReader reader = new StringReader("CHNS\n\n25\n9/1/24");

		InvalidCheckoutDataException exception = assertThrows(InvalidCheckoutDataException.class,
				() -> checkoutDataReader.read(reader, false));

		assertThat(exception.getMessage()).isEqualTo("Invalid Rental Day Count. Actual is ");
	}

	/**
	 * Test malformed RentalDayCount. NOTE: This passes the input phase. This
	 * invalid input is detected later in the application.
	 */
	@Test
	void testInvalidRentalDayCount_malformed() {
		StringReader reader = new StringReader("CHNS\nABC\n25\n9/1/24");

		InvalidCheckoutDataException exception = assertThrows(InvalidCheckoutDataException.class,
				() -> checkoutDataReader.read(reader, false));

		assertThat(exception.getMessage()).isEqualTo("Invalid Rental Day Count. Actual is ABC");
	}

	/**
	 * Test too small of a RentalDayCount. NOTE: This passes the input phase. This
	 * invalid input is detected later in the application.
	 */
	@Test
	void testInvalidRentalDayCount_tooSmall() {
		StringReader reader = new StringReader("CHNS\n-1\n25\n9/1/24");

		CheckoutData checkoutData = checkoutDataReader.read(reader, false);

		assertThat(checkoutData.getToolCode()).isEqualTo("CHNS");
		assertThat(checkoutData.getRentalDayCount()).isEqualTo(-1);
		assertThat(checkoutData.getDiscountPercent()).isEqualTo(25);
		assertThat(checkoutData.getCheckoutDate()).isEqualTo(LocalDate.of(2024, 9, 1));
	}

	/**
	 * Test too small of a RentalDayCount. NOTE: This passes the input phase. This
	 * invalid input is detected later in the application.
	 */
	@Test
	void testInvalidRentalDayCount_tooSmall2() {
		StringReader reader = new StringReader("CHNS\n0\n25\n9/1/24");

		CheckoutData checkoutData = checkoutDataReader.read(reader, false);

		assertThat(checkoutData.getToolCode()).isEqualTo("CHNS");
		assertThat(checkoutData.getRentalDayCount()).isEqualTo(0);
		assertThat(checkoutData.getDiscountPercent()).isEqualTo(25);
		assertThat(checkoutData.getCheckoutDate()).isEqualTo(LocalDate.of(2024, 9, 1));
	}

	/**
	 * Test large RentalDayCount. NOTE: there is a cap at 1000 days.
	 */
	@Test
	void testInvalidRentalDayCount_large() {
		StringReader reader = new StringReader("CHNS\n1000\n25\n9/1/24");

		CheckoutData checkoutData = checkoutDataReader.read(reader, false);

		assertThat(checkoutData.getToolCode()).isEqualTo("CHNS");
		assertThat(checkoutData.getRentalDayCount()).isEqualTo(1000);
		assertThat(checkoutData.getDiscountPercent()).isEqualTo(25);
		assertThat(checkoutData.getCheckoutDate()).isEqualTo(LocalDate.of(2024, 9, 1));
	}

	/**
	 * Test too large of a RentalDayCount. There is a cap of 1000 days. NOTE: This
	 * passes the input phase. This invalid input is detected later in the
	 * application.
	 */
	@Test
	void testInvalidRentalDayCount_tooLarge() {
		StringReader reader = new StringReader("CHNS\n1001\n25\n9/1/24");

		CheckoutData checkoutData = checkoutDataReader.read(reader, false);

		assertThat(checkoutData.getToolCode()).isEqualTo("CHNS");
		assertThat(checkoutData.getRentalDayCount()).isEqualTo(1001);
		assertThat(checkoutData.getDiscountPercent()).isEqualTo(25);
		assertThat(checkoutData.getCheckoutDate()).isEqualTo(LocalDate.of(2024, 9, 1));
	}

	// -------------------------------------------------------------------------

	@Test
	void testInvalidDiscountPercent_missing() {
		StringReader reader = new StringReader("CHNS\n10\n\n9/1/24");

		NumberFormatException exception = assertThrows(NumberFormatException.class,
				() -> checkoutDataReader.read(reader, false));

		assertThat(exception.getMessage()).isEqualTo("For input string: \"\"");
	}

	/**
	 * Test malformed DiscountPercent. NOTE: This passes the input phase. This
	 * invalid input is detected later in the application.
	 */
	@Test
	void testInvalidDiscountPercent_malformed() {
		StringReader reader = new StringReader("CHNS\n10\nABC\n9/1/24");

		NumberFormatException exception = assertThrows(NumberFormatException.class,
				() -> checkoutDataReader.read(reader, false));

		assertThat(exception.getMessage()).isEqualTo("For input string: \"ABC\"");
	}

	/**
	 * Test too small of a DiscountPercent. NOTE: This passes the input phase. This
	 * invalid input is detected later in the application.
	 */
	@Test
	void testInvalidDiscountPercent_tooSmall() {
		StringReader reader = new StringReader("CHNS\n10\n-1\n9/1/24");

		CheckoutData checkoutData = checkoutDataReader.read(reader, false);

		assertThat(checkoutData.getToolCode()).isEqualTo("CHNS");
		assertThat(checkoutData.getRentalDayCount()).isEqualTo(10);
		assertThat(checkoutData.getDiscountPercent()).isEqualTo(-1);
		assertThat(checkoutData.getCheckoutDate()).isEqualTo(LocalDate.of(2024, 9, 1));
	}

	/**
	 * Test valid DiscountPercent of 0.
	 */
	@Test
	void testValidDiscountPercent_zero() {
		StringReader reader = new StringReader("CHNS\n10\n0\n9/1/24");

		CheckoutData checkoutData = checkoutDataReader.read(reader, false);

		assertThat(checkoutData.getToolCode()).isEqualTo("CHNS");
		assertThat(checkoutData.getRentalDayCount()).isEqualTo(10);
		assertThat(checkoutData.getDiscountPercent()).isEqualTo(0);
		assertThat(checkoutData.getCheckoutDate()).isEqualTo(LocalDate.of(2024, 9, 1));
	}

	/**
	 * Test largest valid DiscountPercent.
	 */
	@Test
	void testValidDiscountPercent_largest() {
		StringReader reader = new StringReader("CHNS\n10\n100\n9/1/24");

		CheckoutData checkoutData = checkoutDataReader.read(reader, false);

		assertThat(checkoutData.getToolCode()).isEqualTo("CHNS");
		assertThat(checkoutData.getRentalDayCount()).isEqualTo(10);
		assertThat(checkoutData.getDiscountPercent()).isEqualTo(100);
		assertThat(checkoutData.getCheckoutDate()).isEqualTo(LocalDate.of(2024, 9, 1));
	}

	/**
	 * Test too large DiscountPercent. NOTE: This passes the input phase. This
	 * invalid input is detected later in the application.
	 */
	@Test
	public void testInvalidDiscountPercent_large() {
		StringReader reader = new StringReader("CHNS\n10\n101\n9/1/24");

		CheckoutData checkoutData = checkoutDataReader.read(reader, false);

		assertThat(checkoutData.getToolCode()).isEqualTo("CHNS");
		assertThat(checkoutData.getRentalDayCount()).isEqualTo(10);
		assertThat(checkoutData.getDiscountPercent()).isEqualTo(101);
		assertThat(checkoutData.getCheckoutDate()).isEqualTo(LocalDate.of(2024, 9, 1));
	}

	// ---------------------------------------------------------------------------------

	@Test
	public void testInvalidCheckoutDate_missing() {
		StringReader reader = new StringReader("CHNS\n10\n0\n\n");

		DateTimeParseException exception = assertThrows(DateTimeParseException.class,
				() -> checkoutDataReader.read(reader, false));

		assertThat(exception.getMessage()).isEqualTo("Text '' could not be parsed at index 0");
	}

	/**
	 * Test malformed CheckoutDate
	 */
	@Test
	public void testInvalidCheckoutDate_malformed() {
		StringReader reader = new StringReader("CHNS\n10\n0\nABC");

		DateTimeParseException exception = assertThrows(DateTimeParseException.class,
				() -> checkoutDataReader.read(reader, false));

		assertThat(exception.getMessage()).isEqualTo("Text 'ABC' could not be parsed at index 0");
	}

	/**
	 * Test malformed CheckoutDate. Date should be two digits.
	 */
	@Test
	public void testInvalidCheckoutDate_malformed2() {
		StringReader reader = new StringReader("CHNS\n10\n0\n1/20/2022");

		DateTimeParseException exception = assertThrows(DateTimeParseException.class,
				() -> checkoutDataReader.read(reader, false));

		assertThat(exception.getMessage())
				.isEqualTo("Text '1/20/2022' could not be parsed, unparsed text found at index 7");
	}

	/**
	 * Test valid checkout date
	 */
	@Test
	public void testValidCheckoutDate() {
		StringReader reader = new StringReader("CHNS\n40\n25\n12/31/23");

		CheckoutData checkoutData = checkoutDataReader.read(reader, false);

		assertThat(checkoutData.getToolCode()).isEqualTo("CHNS");
		assertThat(checkoutData.getRentalDayCount()).isEqualTo(40);
		assertThat(checkoutData.getDiscountPercent()).isEqualTo(25);
		assertThat(checkoutData.getCheckoutDate()).isEqualTo(LocalDate.of(2023, 12, 31));
	}

	/**
	 * Test valid checkout date way in the past. There should probably be a guard
	 * against this however the two digit year comes with a 100 year boundary.
	 */
	@Test
	public void testValidCheckoutDate_past() {
		StringReader reader = new StringReader("CHNS\n40\n25\n02/01/00");

		CheckoutData checkoutData = checkoutDataReader.read(reader, false);

		assertThat(checkoutData.getToolCode()).isEqualTo("CHNS");
		assertThat(checkoutData.getRentalDayCount()).isEqualTo(40);
		assertThat(checkoutData.getDiscountPercent()).isEqualTo(25);
		assertThat(checkoutData.getCheckoutDate()).isEqualTo(LocalDate.of(2000, 2, 1));
	}

	/**
	 * Test valid checkout date way in the future. There should probably be a guard
	 * against this however the two digit year comes with a 100 year boundary.
	 */
	@Test
	public void testValidCheckoutDate_future() {
		StringReader reader = new StringReader("CHNS\n40\n25\n12/31/99");

		CheckoutData checkoutData = checkoutDataReader.read(reader, false);

		assertThat(checkoutData.getToolCode()).isEqualTo("CHNS");
		assertThat(checkoutData.getRentalDayCount()).isEqualTo(40);
		assertThat(checkoutData.getDiscountPercent()).isEqualTo(25);
		assertThat(checkoutData.getCheckoutDate()).isEqualTo(LocalDate.of(2099, 12, 31));
	}

}
