package com.btork.toolrental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * An application that calculates the charge associated with a rental item. The
 * charge is based on a daily rate and the number of chargeable days that the
 * rental item was on loan.
 * 
 * This class starts the program. See TransactionExecutorImpl for the main
 * logic.
 */
@SpringBootApplication
public class ToolRentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToolRentalApplication.class, args);
	}

}
