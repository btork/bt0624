package com.btork.toolrental.daoimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.btork.toolrental.dao.ToolCostDao;
import com.btork.toolrental.domain.ToolCost;

public class ToolCostDaoImplTest {

	@Test
	public void testValidLookup() {
		ToolCostDao toolCostDao = new ToolCostDaoImpl();
		ToolCost toolCost = toolCostDao.getToolCostByToolType("Ladder");
		assertNotNull(toolCost);
		assertEquals(new BigDecimal("1.99"), toolCost.getDailyCost());
		assertTrue(toolCost.isChargedOnWeekday());
		assertTrue(toolCost.isChargedOnWeekend());
		assertFalse(toolCost.isChargedOnHoliday());
	}

	@Test
	public void testInvalidLookup() {
		ToolCostDao toolCostDao = new ToolCostDaoImpl();
		ToolCost toolCost = toolCostDao.getToolCostByToolType("###");
		assertNull(toolCost);
	}
}
