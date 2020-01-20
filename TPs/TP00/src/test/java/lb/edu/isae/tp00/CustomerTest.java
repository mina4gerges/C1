package lb.edu.isae.tp00;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import lb.edu.isae.tp00.Customer;

/**
 * This class tests the (Big)Customer class
 */
public final class CustomerTest extends TestCase {
	// ======================================
	// =             Attributes             =
	// ======================================
	Customer _validCustomer;

	// ======================================
	// =            Constructors            =
	// ======================================
	public CustomerTest(final String s) {
		super(s);
	}

	public void setUp() {
		// Creates a valid customer
		_validCustomer = new Customer("bill000", "Bill", "Gates", "tel", "street1", "street2", "city", "state", "zipcode", "country", "bilou@microsoft.fr");
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
		assertEquals("Bill", _validCustomer.getFirstname());
		assertEquals("Gates", _validCustomer.getLastname());
		boolean b = _validCustomer.checkId(_validCustomer.getId());
		assertTrue("Customer id is OK!", b);
		b = _validCustomer.checkData();
		assertTrue("Customer data is OK!", b);
	}

	/**
	 * This test tries to create objects with invalid values.
	 */
	public void testCreateInvalidCustomer() {
		// Creates objects with empty values
		Customer customer = new Customer("", "Bill", "Gates");
		boolean b = customer.checkData();
		assertFalse("Customer id should not be empty!", b);
		customer = new Customer(null, "Bill", "Gates");
		b = customer.checkData();
		assertFalse("Customer id should not be null!", b);
		customer = new Customer("bill000", "", "Gates");
		b = customer.checkData();
		assertFalse("Customer first name should not be empty!", b);
		customer = new Customer("bill000", "Bill", null);
		b = customer.checkData();
		assertFalse("Customer last name should not be null!", b);
	}

	/**
	 * This test tries to create an object with invalid values.
	 */
	public void testCreateInvalidCustomerErrorMessages() {
		// Creates objects with empty values
		Customer customer = new Customer("", "Bill", "Gates");
		String s = customer.getCheckDataError();
		assertEquals("Invalid id", s);
		customer = new Customer(null, "Bill", "Gates");
		s = customer.getCheckDataError();
		assertEquals("Invalid id", s);
		customer = new Customer("bill000", "", "Gates");
		s = customer.getCheckDataError();
		assertEquals("Invalid first name", s);
		customer = new Customer("bill000", "Bill", "");
		s = customer.getCheckDataError();
		assertEquals("Invalid last name", s);
	}

	/**
	 * This test use each getter
	 */
	public void testAllGetters() {
		assertEquals("bill000", _validCustomer.getId());
		assertEquals("Bill", _validCustomer.getFirstname());
		assertEquals("Gates", _validCustomer.getLastname());
		assertEquals("tel", _validCustomer.getTelephone());
		assertEquals("street1", _validCustomer.getStreet1());
		assertEquals("street2", _validCustomer.getStreet2());
		assertEquals("city", _validCustomer.getCity());
		assertEquals("state", _validCustomer.getState());
		assertEquals("zipcode", _validCustomer.getZipcode());
		assertEquals("country", _validCustomer.getCountry());
		assertEquals("bilou@microsoft.fr", _validCustomer.getMail());
	}

	/**
	 * This test use each setter
	 */
	public void testAllSetters() {
		_validCustomer.setFirstname("Bill2");
		_validCustomer.setLastname("Gates2");
		_validCustomer.setTelephone("tel2");
		_validCustomer.setStreet1("street12");
		_validCustomer.setStreet2("street22");
		_validCustomer.setCity("city2");
		_validCustomer.setState("state2");
		_validCustomer.setZipcode("zipcode2");
		_validCustomer.setCountry("country2");
		_validCustomer.setMail("anotherMail");
		assertEquals("Bill2", _validCustomer.getFirstname());
		assertEquals("Gates2", _validCustomer.getLastname());
		assertEquals("tel2", _validCustomer.getTelephone());
		assertEquals("street12", _validCustomer.getStreet1());
		assertEquals("street22", _validCustomer.getStreet2());
		assertEquals("city2", _validCustomer.getCity());
		assertEquals("state2", _validCustomer.getState());
		assertEquals("zipcode2", _validCustomer.getZipcode());
		assertEquals("country2", _validCustomer.getCountry());
		assertEquals("anotherMail", _validCustomer.getMail());
	}

	/**
	 * The following tests mail contents
	 */
	public void testCheckMailWithValidLength() {
		Customer customer = _validCustomer;
	    boolean b = customer.checkMail();
	    assertEquals(customer.getMail() + " est une adresse de longueur valide", true, b);
	}

	public void testCheckMailWithTooShortLength() {
		Customer customer = _validCustomer;
	    customer.setMail("x@x.fr");
	    boolean b = customer.checkMail();
	    assertEquals("x@x.fr est une adresse trop courte => Echec", false, b);
	}
	
	public void testCheckMailWithTooLongLength() {
		Customer customer = _validCustomer;
	    customer.setMail("engagelejeuquejelegagne@duel-de-mots.fr");
	    boolean b = customer.checkMail();
	    assertEquals(customer.getMail() + " est une adresse trop longue => Echec", false, b);
	}
	
	public void testCheckMailWithArrobas() {
		Customer customer = _validCustomer;
	    boolean b = customer.checkMail();
	    assertEquals(true, b);
    }

	public void testCheckMailWithoutArrobas() {
		Customer customer = _validCustomer;
	    customer.setMail("nobody.nowhere");
	    boolean b = customer.checkMail();
	    assertEquals("Adresse sans @ => Echec", false, b);
    }

	public void testCheckMailsWithValidDomain() {
		Customer customer = _validCustomer;
		System.out.println(customer.checkMail());
	    boolean b = customer.checkMail();
	    assertEquals("Adresse should be OK!", true, b);
	    customer.setMail("anyone@cnam.fr");
	    assertEquals("Adresse should be OK!", true, b);
	    customer.setMail("anyone@x.ue");
	    assertEquals("Adresse should be OK!", true, b);
	}
	
	public void testCheckMailsWithInvalidDomain() {
		Customer customer = _validCustomer;
	    customer.setMail("nobody@x.com");
	    boolean b = customer.checkMail();
	    assertFalse("Forbidden domain!", !b);
	    customer.setMail("nobody@x.us");
	    b = customer.checkMail();
	    assertFalse("Forbidden country!", !b);
    }

	//==================================
	//=     Persistence test cases     =
	//==================================
	/**
	 * This test tries to find an object with an invalid identifier.
	 */
	public void testFindCustomerWithInvalidValues() {

		// Finds an object with an empty identifier
		if ( Customer.find(new String()) != null )
			fail("Object with empty id should not be found");

		// Finds an object with a null identifier
		if ( Customer.find(null) != null )
			fail("Object with null id should not be found");

		// Finds an object with an unknown identifier
		final int id = getUniqueId();
		Customer customer = findCustomer(id);
		if ( customer != null )
			fail("Object with unknonw id should not be found");
	}

	/**
	 * This method ensures that creating an object works. It first finds the object,
	 * makes sure it doesn't exist, creates it and checks it then exists.
	 */
	public void testCreateCustomer() {
		final int id = getUniqueId();
		Customer customer = findCustomer(id);

		// Ensures that the object doesn't exist
		if ( customer != null )
			fail("Object has not been created yet it shouldn't be found");

		// Creates an object
		createCustomer(id);

		// Ensures that the object exists
		customer = findCustomer(id);
		if ( customer == null )
			fail("Object has been created it should be found");

		// Checks that it's the right object
		checkCustomer(customer, id);

		// Cleans the test environment
		removeCustomer(id);

		customer = findCustomer(id);
		if ( customer != null)
			fail("Object has been deleted it shouldn't be found");
	}

	/**
	 * This test ensures that the system cannont remove an unknown object
	 */
	public void testDeleteUnknownCustomer() {
		// Removes an unknown object
		int id = getUniqueId();
		String sid = "custo" + id;
		if ( Customer.remove(sid) )
			fail("Deleting an unknown object should break");
	}

	//==================================
	//=         Private Methods        =
	//==================================
	private Customer findCustomer(final int id) {
		final Customer customer = Customer.find("custo" + id);
		return customer;
	}

	private boolean createCustomer(final int id)  {
		final Customer customer = new Customer("custo" + id, "firstname" + id, "lastname" + id);
		customer.setCity("city" + id);
		customer.setCountry("cnty" + id);
		customer.setState("state" + id);
		customer.setStreet1("street1" + id);
		customer.setStreet2("street2" + id);
		customer.setTelephone("phone" + id);
		customer.setZipcode("zip" + id);
		return Customer.insert(customer);
	}

	private boolean removeCustomer(final int id) {
		final String sid = "custo" + id;
		return Customer.remove(sid);
	}

	private void checkCustomer(final Customer customer, final int id) {
		assertEquals("firstname", "firstname" + id, customer.getFirstname());
		assertEquals("lastname", "lastname" + id, customer.getLastname());
		assertEquals("city", "city" + id, customer.getCity());
		assertEquals("country", "cnty" + id, customer.getCountry());
		assertEquals("state", "state" + id, customer.getState());
		assertEquals("street1", "street1" + id, customer.getStreet1());
		assertEquals("street2", "street2" + id, customer.getStreet2());
		assertEquals("telephone", "phone" + id, customer.getTelephone());
		assertEquals("zipcode", "zip" + id, customer.getZipcode());
	}

	private int getUniqueId() {
		String s = null;
		int result = 0;
		do {
			result = (int) (Math.random() * 100000);
			s = Integer.toString(result);
			if ( Customer.find(s) == null )
				return result;
		} while ( true );
	}

}
