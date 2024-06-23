package com.btork.toolrental.daoimpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.btork.toolrental.dao.ToolSpecDao;
import com.btork.toolrental.domain.ToolSpec;

/**
 * DAO implementation for tool specification data. The data is implemented as
 * constants. In the future the data could be put into an external DB when CRUD
 * operations are needed.
 */
@Repository
public class ToolSpecDaoImpl implements ToolSpecDao {

	private Map<String, ToolSpec> toolSpecs = new HashMap<>();

	{
		toolSpecs.put("CHNS", new ToolSpec("CHNS", "Chainsaw", "Stihl"));
		toolSpecs.put("LADW", new ToolSpec("LADW", "Ladder", "Werner"));
		toolSpecs.put("JAKD", new ToolSpec("JAKD", "Jackhammer", "DeWalt"));
		toolSpecs.put("JAKR", new ToolSpec("JAKR", "Jackhammer", "Ridgid"));
	};

	/**
	 * @returns the ToolSpec for the toolCode or null if there is no match
	 */
	@Override
	public ToolSpec getToolSpecByToolCode(String toolCode) {
		return toolSpecs.get(toolCode);
	}

}
