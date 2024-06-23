package com.btork.toolrental.dao;

import com.btork.toolrental.domain.ToolCost;

/**
 * Simple DAO to support the reading of tool cost data.
 * 
 * Note: Full CRUD behavior is not needed in this application.
 */
public interface ToolCostDao {

	public ToolCost getToolCostByToolType(String toolType);

}
