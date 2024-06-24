package com.btork.toolrental.serviceimpl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.springframework.stereotype.Service;

import com.btork.toolrental.domain.ToolCost;
import com.btork.toolrental.service.ChargeDayCalculator;

/**
 * Calculates the number of chargeable days there are within the rental period.
 * The calculation of days is based on rules based on the ToolType.
 * Specifically, different ToolTypes charge based on weekdays, weekends and
 * holidays (Independence Day (Observed) and Labor Day).
 * 
 * This implementation assumes a relatively small rental period. If more
 * performance is needed, an algorithm that precalculates rental days for
 * specific years (or months) could be implemented.
 */
@Service
public class ChargeDayCalculatorImpl implements ChargeDayCalculator {

	/**
	 * Calculates the number of days the customer is charged for a tool over the
	 * specified interval defined by startDate and duration. The toolCost specifies
	 * which types of days are charged.
	 */
	@Override
	public int calculateChargeDays(LocalDate startDate, int duration, ToolCost toolCost) {
		int chargeDays = 0;

		LocalDate examineDate = startDate;
		for (int i = 0; i < duration; i++, examineDate = examineDate.plusDays(1)) {
			if (isHoliday(examineDate)) {
				if (toolCost.isChargedOnHoliday()) {
					chargeDays++;
				}
			} else if (isWeekend(examineDate)) {
				if (toolCost.isChargedOnWeekend()) {
					chargeDays++;
				}
			} else if (!isWeekend(examineDate)) {
				if (toolCost.isChargedOnWeekday()) {
					chargeDays++;
				}
			}
		}
		return chargeDays;
	}

	/**
	 * Calculates if the date is a holiday
	 * 
	 * @param date the target date
	 * @return true if date is a holiday
	 */
	private boolean isHoliday(LocalDate date) {
		return isFourthOfJulyHoliday(date) || isLaborDayHoliday(date);
	}

	/**
	 * @return true if the date is a 4th of july holiday. Note: the holiday may be
	 *         observed on the 3rd or 5th if the 4th falls on a weekend.
	 */
	private boolean isFourthOfJulyHoliday(LocalDate date) {
		Month month = date.getMonth();
		if (!Month.JULY.equals(month)) {
			return false;
		}
		int dayOfMonth = date.getDayOfMonth();
		if (dayOfMonth < 3 || dayOfMonth > 5) {
			return false;
		}
		DayOfWeek dayOfWeek = date.getDayOfWeek();
		// if is 7/3 && friday, true
		if (dayOfMonth == 3 && DayOfWeek.FRIDAY.equals(dayOfWeek)) {
			return true;
		}
		// if is 7/5 && monday, true
		if (dayOfMonth == 5 && DayOfWeek.MONDAY.equals(dayOfWeek)) {
			return true;
		}
		// if is 7/4 && weekday, true
		if (!isWeekend(date) && dayOfMonth == 4) {
			return true;
		}
		return false;
	}

	/**
	 * @return true if the date is labor day. Labor day is the first Monday in
	 *         September.
	 */
	private boolean isLaborDayHoliday(LocalDate date) {
		Month month = date.getMonth();
		if (!Month.SEPTEMBER.equals(month)) {
			return false;
		}
		DayOfWeek dayOfWeek = date.getDayOfWeek();
		if (!DayOfWeek.MONDAY.equals(dayOfWeek)) {
			return false;
		}
		int dayOfMonth = date.getDayOfMonth();
		if (dayOfMonth <= 7) {
			return true;
		}

		return false;
	}

	/**
	 * @return true if date is a weekend
	 */
	private boolean isWeekend(LocalDate date) {
		DayOfWeek dayOfWeek = date.getDayOfWeek();
		return DayOfWeek.SATURDAY.equals(dayOfWeek) || DayOfWeek.SUNDAY.equals(dayOfWeek);
	}

}
