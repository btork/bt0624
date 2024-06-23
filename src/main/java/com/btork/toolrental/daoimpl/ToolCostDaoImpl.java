package com.btork.toolrental.daoimpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.btork.toolrental.dao.ToolCostDao;
import com.btork.toolrental.domain.ToolCost;

/**
 * DAO implementation for tool cost data. The data is implemented as constants.
 * In the future the data could be put into an external DB when CRUD operations
 * are needed.
 */
@Repository
public class ToolCostDaoImpl implements ToolCostDao {

	private Map<String, ToolCost> toolCosts = new HashMap<>();

	{
		toolCosts.put("Ladder", new ToolCost(new BigDecimal("1.99"), true, true, false));
		toolCosts.put("Chainsaw", new ToolCost(new BigDecimal("1.49"), true, false, true));
		toolCosts.put("Jackhammer", new ToolCost(new BigDecimal("2.99"), true, false, false));
	};

	/**
	 * @returns the ToolCost for the toolType or null if it does not exist
	 */
	@Override
	public ToolCost getToolCostByToolType(String toolType) {
		return toolCosts.get(toolType);
	}

}
