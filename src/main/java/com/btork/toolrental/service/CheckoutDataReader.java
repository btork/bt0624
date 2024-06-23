package com.btork.toolrental.service;

import java.io.Reader;

import com.btork.toolrental.domain.CheckoutData;

/**
 * A mechanism for processing/reading the raw input and creating a CheckoutData
 */
public interface CheckoutDataReader {

	/**
	 * Reads the checkout data from the supplied reader
	 * 
	 * @param reader         the input source for the data
	 * @param includePrompts if true, prompts are given for the input data
	 * @return the details of data given at checkout
	 */
	public CheckoutData read(Reader reader, boolean includePrompts);

}
