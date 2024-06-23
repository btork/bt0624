package com.btork.toolrental.dao;

import com.btork.toolrental.domain.ToolSpec;

/**
 * Simple DAO to support the reading of tool specification data.
 * 
 * Note: Full CRUD behavior is not needed in this application.
 */
public interface ToolSpecDao {

	public ToolSpec getToolSpecByToolCode(String toolCode);

}
