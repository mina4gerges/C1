package com.yaps.petstore;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This class tests the Customer class
 */
public final class CustomerTest extends TestCase {

	public CustomerTest(final String s) {
		super(s);
	}

	public static TestSuite suite() {
		return new TestSuite(CustomerTest.class);
	}

	//==================================
	//=            Test cases          =
	//==================================

	/**
	 * This test tries to create an object with valid values.
	 */
	public void testCreateValidCustomer() {

		// Creates a valid customer
		try {
			final Customer customer = new Customer("bill000", "Bill", "Gates");
			assertEquals("Bill", customer.getFirstname());
			assertEquals("Gates", customer.getLastname());
			customer.checkData();
		} catch (CustomerCheckException e) {
			fail("Custumer data is OK!");
		}
	}

	/**
	 * This test tries to create an object with invalid values.
	 */
	public void testCreateCustomerWithInvalidValues() throws Exception {

		// Creates objects with empty values
		try {
			final Customer customer = new Customer("1234", "", "Gates");
			customer.checkData();
			fail("Object with empty values should not be created");
		} catch (CustomerCheckException e) {
			assertEquals("Invalid customer first name", e.getMessage());
		}
		try {
			final Customer customer = new Customer("1234", "Bill", "");
			customer.checkData();
			fail("Object with empty values should not be created");
		} catch (CustomerCheckException e) {
			assertEquals("Invalid customer last name", e.getMessage());
		}

		// Creates objects with null values
		try {
			final Customer customer = new Customer("1234", null, "Gates");
			customer.checkData();
			fail("Object with null values should not be created");
		} catch (CustomerCheckException e) {
			assertEquals("Invalid customer first name", e.getMessage());
		}
		try {
			final Customer customer = new Customer("1234", "Bill", null);
			customer.checkData();
			fail("Object with null values should not be created");
		} catch (CustomerCheckException e) {
			assertEquals("Invalid customer last name", e.getMessage());
		}
	}

}
