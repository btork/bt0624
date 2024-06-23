package com.btork.toolrental.domain;

/**
 * A specification of a tool type that includes it's code, type and brand.
 */
public class ToolSpec {
	private String code;
	private String type;
	private String brand;

	public ToolSpec(String code, String type, String brand) {
		this.code = code;
		this.type = type;
		this.brand = brand;
	}

	/**
	 * @return the unique code for the tool
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the unique code for the tool
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the type (category) of the tool
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the tool's type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the tool's brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * Sets the tool's brand
	 * 
	 * @param brand
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Override
	public String toString() {
		return "ToolType [code=" + code + ", type=" + type + ", brand=" + brand + "]";
	}

}