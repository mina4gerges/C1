package com.yaps.petstore;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This class tests the CustomerDAO class
 */
public final class CustomerDAOTest extends TestCase {

    private final CustomerDAO _dao = new CustomerDAO();

    public CustomerDAOTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(CustomerDAOTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     */
    public void testFindCustomerWithInvalidValues() throws Exception {

        // Finds an object with a unknown identifier
        final int id = _dao.getUniqueId();
        try {
            findCustomer(id);
            fail("Object with unknonw id should not be found");
        } catch (CustomerNotFoundException e) {
        }

        // Finds an object with an empty identifier
        try {
            _dao.find(new String());
            fail("Object with empty id should not be found");
        } catch (CustomerNotFoundException e) {
        }

        // Finds an object with a null identifier
        try {
            _dao.find(null);
            fail("Object with null id should not be found");
        } catch (CustomerNotFoundException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     */
    public void testCreateCustomer() throws Exception {
        final int id = _dao.getUniqueId();
        Customer customer = null;

        // Ensures that the object doesn't exist
        try {
            customer = findCustomer(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (CustomerNotFoundException e) {
        }

        // Creates an object
        createCustomer(id);

        // Ensures that the object exists
        try {
            customer = findCustomer(id);
        } catch (CustomerNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCustomer(customer, id);

        // Creates an object with the same identifier. An exception has to be thrown
        try {
            createCustomer(id);
            fail("An object with the same id has already been created");
        } catch (CustomerDuplicateKeyException e) {
        }

        // Cleans the test environment
        removeCustomer(id);

        try {
            findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (CustomerNotFoundException e) {
        }
    }

    /**
     * This test tries to update an unknown object.
     */
    public void testUpdateUnknownCustomer() throws Exception {
        // Updates an unknown object
        try {
            _dao.update(new Customer());
            fail("Updating a none existing object should break");
        } catch (CustomerCheckException e) {
        }
    }

    /**
     * This test tries to update an object with a invalid values.
     */
    public void testUpdateCustomerWithInvalidValues() throws Exception {

        // Creates an object
        final int id = _dao.getUniqueId();
        createCustomer(id);

        // Ensures that the object exists
        Customer customer = null;
        try {
            customer = findCustomer(id);
        } catch (CustomerNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Updates the object with empty values
        try {
            customer.setFirstname(new String());
            customer.setLastname(new String());
            _dao.update(customer);
            fail("Updating an object with empty values should break");
        } catch (CustomerCheckException e) {
        }

        // Updates the object with null values
        try {
            customer.setFirstname(null);
            customer.setLastname(null);
            _dao.update(customer);
            fail("Updating an object with null values should break");
        } catch (CustomerCheckException e) {
        }

        // Ensures that the object still exists
        try {
            customer = findCustomer(id);
        } catch (CustomerNotFoundException e) {
            fail("Object should be found");
        }

        // Cleans the test environment
        removeCustomer(id);

        try {
            findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (CustomerNotFoundException e) {
        }
    }

    /**
     * This test make sure that updating an object success
     */
    public void testUpdateCustomer() throws Exception {
        final int id = _dao.getUniqueId();

        // Creates an object
        createCustomer(id);

        // Ensures that the object exists
        Customer customer = null;
        try {
            customer = findCustomer(id);
        } catch (CustomerNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCustomer(customer, id);

        // Updates the object with new values
        updateCustomer(customer, id + 1);

        // Ensures that the object still exists
        Customer customerUpdated = null;
        try {
            customerUpdated = findCustomer(id);
        } catch (CustomerNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkCustomer(customerUpdated, id + 1);

        // Cleans the test environment
        removeCustomer(id);

        try {
            findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (CustomerNotFoundException e) {
        }
    }

    /**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testDeleteUnknownCustomer() throws Exception {
        // Removes an unknown object
        try {
        	int id = _dao.getUniqueId();
        	String sid = "custo" + id;
            _dao.remove(sid);
            fail("Deleting an unknown object should break");
        } catch (CustomerNotFoundException e) {
        }
    }

    //==================================
    //=         Private Methods        =
    //==================================
    private Customer findCustomer(final int id) throws CustomerFinderException, CustomerCheckException {
        final Customer customer = _dao.find("custo" + id);
        return customer;
    }

    private void createCustomer(final int id) throws CustomerCreateException, CustomerCheckException {
        final Customer customer = new Customer("custo" + id, "firstname" + id, "lastname" + id);
        customer.setCity("city" + id);
        customer.setCountry("cnty" + id);
        customer.setState("state" + id);
        customer.setStreet1("street1" + id);
        customer.setStreet2("street2" + id);
        customer.setTelephone("phone" + id);
        customer.setZipcode("zip" + id);
        _dao.insert(customer);
    }

    private void updateCustomer(final Customer customer, final int id) 
    throws CustomerNotFoundException, CustomerDuplicateKeyException, CustomerCheckException {
        customer.setFirstname("firstname" + id);
        customer.setLastname("lastname" + id);
        customer.setCity("city" + id);
        customer.setCountry("cnty" + id);
        customer.setState("state" + id);
        customer.setStreet1("street1" + id);
        customer.setStreet2("street2" + id);
        customer.setTelephone("phone" + id);
        customer.setZipcode("zip" + id);
 		_dao.update(customer);
    }

    private void removeCustomer(final int id) throws CustomerNotFoundException {
        final String sid = "custo" + id;
        _dao.remove(sid);
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

}
