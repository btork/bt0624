package com.btork.toolrental.serviceimpl;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.btork.toolrental.domain.ToolCost;
import com.btork.toolrental.service.ChargeDayCalculator;

public class ChargeDayCalculatorImplTest {

	private ChargeDayCalculator chargeDayCalculator = new ChargeDayCalculatorImpl();

	/**
	 * Test weekday charged
	 */
	@Test
	public void testWeekdayChargedCase2() {
		ToolCost toolCost = new ToolCost(null, true, false, false);
		int chargeDays = chargeDayCalculator.calculateChargeDays(LocalDate.of(2015, 9, 3), 6, toolCost);
		assertThat(chargeDays).isEqualTo(3);
	}

	/**
	 * Test weekday charged
	 */
	@Test
	public void testWeekdayCharged() {
		ToolCost toolCost = new ToolCost(null, true, false, false);
		int chargeDays = chargeDayCalculator.calculateChargeDays(LocalDate.of(2024, 6, 19), 2, toolCost);
		assertThat(chargeDays).isEqualTo(2);
	}

	/**
	 * Test weekday charged
	 */
	@Test
	public void testWeekdayNotCharged() {
		ToolCost toolCost = new ToolCost(null, false, false, false);
		int chargeDays = chargeDayCalculator.calculateChargeDays(LocalDate.of(2024, 6, 19), 2, toolCost);
		assertThat(chargeDays).isEqualTo(0);
	}

	/**
	 * Test weekend charged
	 */
	@Test
	public void testWeekendCharged() {
		ToolCost toolCost = new ToolCost(null, false, true, false);
		int chargeDays = chargeDayCalculator.calculateChargeDays(LocalDate.of(2024, 6, 22), 2, toolCost);
		assertThat(chargeDays).isEqualTo(2);
	}

	/**
	 * Test weekend not charged
	 */
	@Test
	public void testWeekendNotCharged() {
		ToolCost toolCost = new ToolCost(null, false, false, false);
		int chargeDays = chargeDayCalculator.calculateChargeDays(LocalDate.of(2024, 6, 22), 2, toolCost);
		assertThat(chargeDays).isEqualTo(0);
	}

	/**
	 * Test fourth celebrated on third not charged
	 */
	@Test
	public void testFourthOfJulyCelebratedOnThirdNotCharged() {
		ToolCost toolCost = new ToolCost(null, true, true, false);
		int chargeDays = chargeDayCalculator.calculateChargeDays(LocalDate.of(2020, 7, 3), 1, toolCost);
		assertThat(chargeDays).isEqualTo(0);
	}

	/**
	 * Test fourth celebrated on fifth not charged
	 */
	@Test
	public void testFourthOfJulyCelebratedOnFifthNotCharged() {
		ToolCost toolCost = new ToolCost(null, true, true, false);
		int chargeDays = chargeDayCalculator.calculateChargeDays(LocalDate.of(2021, 7, 5), 1, toolCost);
		assertThat(chargeDays).isEqualTo(0);
	}

	/**
	 * Test fourth on weekend charged
	 */
	@Test
	public void testFourthOfJulyOnWeekend() {
		ToolCost toolCost = new ToolCost(null, true, false, true);
		int chargeDays = chargeDayCalculator.calculateChargeDays(LocalDate.of(2021, 7, 4), 1, toolCost);
		assertThat(chargeDays).isEqualTo(0);
	}

	/**
	 * Test fourth on weekend
	 */
	@Test
	public void testFourthOfJulyOnWeekend2() {
		ToolCost toolCost = new ToolCost(null, true, true, false);
		int chargeDays = chargeDayCalculator.calculateChargeDays(LocalDate.of(2021, 7, 4), 1, toolCost);
		assertThat(chargeDays).isEqualTo(1);
	}

	/**
	 * Test fourth on weekday
	 */
	@Test
	public void testFourthOfJulyOnWeekday() {
		ToolCost toolCost = new ToolCost(null, true, true, false);
		int chargeDays = chargeDayCalculator.calculateChargeDays(LocalDate.of(2024, 7, 4), 1, toolCost);
		assertThat(chargeDays).isEqualTo(0);
	}

	/**
	 * Test Labor day on first not charged
	 */
	@Test
	public void testLaborDayOnFirstNotCharged() {
		ToolCost toolCost = new ToolCost(null, true, true, false);
		int chargeDays = chargeDayCalculator.calculateChargeDays(LocalDate.of(2014, 9, 1), 1, toolCost);
		assertThat(chargeDays).isEqualTo(0);
	}
	

	/**
	 * Test Labor day on first charged
	 */
	@Test
	public void testLaborDayOnFirstCharged() {
		ToolCost toolCost = new ToolCost(null, false, false, true);
		int chargeDays = chargeDayCalculator.calculateChargeDays(LocalDate.of(2014, 9, 1), 1, toolCost);
		assertThat(chargeDays).isEqualTo(1);
	}

	/**
	 * Test Labor day on seventh not charged
	 */
	@Test
	public void testLaborDayOnSeventhNotCharged() {
		ToolCost toolCost = new ToolCost(null, true, true, false);
		int chargeDays = chargeDayCalculator.calculateChargeDays(LocalDate.of(2015, 9, 7), 1, toolCost);
		assertThat(chargeDays).isEqualTo(0);
	}

	/**
	 * Test Labor day calc
	 */
	@Test
	public void testLaborDayCalc() {
		ToolCost toolCost = new ToolCost(null, true, true, false);
		int chargeDays = chargeDayCalculator.calculateChargeDays(LocalDate.of(2016, 9, 8), 1, toolCost);
		assertThat(chargeDays).isEqualTo(1);
	}

}
