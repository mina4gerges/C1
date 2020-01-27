package com.yaps.petstore.domain;

import com.yaps.petstore.persistence.CustomerDAO;
import com.yaps.petstore.domain.Customer;
import com.yaps.petstore.exception.*;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This class tests the Customer class
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
     * This test tries to find an object with an invalid identifier.
     */
    public void testDomainFindCustomerWithInvalidValues() throws Exception {

        // Finds an object with an unknown identifier
        final int id = _dao.getUniqueId();
        try {
            findCustomer(id);
            fail("Object with unknonw id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with an empty identifier
        try {
        	_dao.select(new String());
            fail("Object with empty id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with a null identifier
        try {
        	_dao.select(null);
            fail("Object with null id should not be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     */
    public void testDomainCreateCustomer() throws Exception {
    	final int id = _dao.getUniqueId();
        Customer customer = null;

        // Ensures that the object doesn't exist
        try {
            customer = findCustomer(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Creates an object
        createCustomer(id);

        // Ensures that the object exists
        try {
            customer = findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCustomer(customer, id);

        // Creates an object with the same identifier. An exception has to be thrown
        try {
            createCustomer(id);
            fail("An object with the same id has already been created");
        } catch (DuplicateKeyException e) {
        }

        // Cleans the test environment
        removeCustomer(id);

        try {
            findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testDomainCreateCustomerWithInvalidValues() throws Exception {

        // Creates an object with an empty values
        try {
            final Customer customer = new Customer(new String(), new String(), new String());
            _dao.insert(customer);
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an null values
        try {
            final Customer customer = new Customer(null, null, null);
            _dao.insert(customer);
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to update an unknown object.
     */
    public void testDomainUpdateUnknownCustomer() throws Exception {
        // Updates an unknown object
        try {
        	final Customer customer = new Customer();
            _dao.update(customer);
            fail("Updating a none existing object should break");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to update an object with a invalid values.
     */
    public void testDomainUpdateCustomerWithInvalidValues() throws Exception {

        // Creates an object
    	final int id = _dao.getUniqueId();
        createCustomer(id);

        // Ensures that the object exists
        Customer customer = null;
        try {
            customer = findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Updates the object with empty values
        try {
            customer.setFirstname(new String());
            customer.setLastname(new String());
            _dao.update(customer);
            fail("Updating an object with empty values should break");
        } catch (CheckException e) {
        }

        // Updates the object with null values
        try {
            customer.setFirstname(null);
            customer.setLastname(null);
            _dao.update(customer);
            fail("Updating an object with null values should break");
        } catch (CheckException e) {
        }

        // Ensures that the object still exists
        try {
            customer = findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Cleans the test environment
        removeCustomer(id);

        try {
            findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test make sure that updating an object success
     */
    public void testDomainUpdateCustomer() throws Exception {
    	final int id = _dao.getUniqueId();

        // Creates an object
        createCustomer(id);

        // Ensures that the object exists
        Customer customer = null;
        try {
            customer = findCustomer(id);
        } catch (ObjectNotFoundException e) {
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
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkCustomer(customerUpdated, id + 1);

        // Cleans the test environment
        removeCustomer(id);

        try {
            findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testDomainDeleteUnknownCustomer() throws Exception {
        // Removes an unknown object
        try {
        	Customer customer = new Customer();
            _dao.remove(customer.getId());
            fail("Deleting an unknown object should break");
        } catch (ObjectNotFoundException e) {
        }
    }

    //==================================
    //=         Private Methods        =
    //==================================
    private Customer findCustomer(final int id) throws FinderException, CheckException {
        final Customer customer = (Customer)_dao.select("custo" + id);
        _dao.select("custo" + id);
        return customer;
    }

    private void createCustomer(final int id) throws CreateException, CheckException {
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

    private void updateCustomer(final Customer customer, final int id) throws DuplicateKeyException, ObjectNotFoundException, CheckException {
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

    private void removeCustomer(final int id) throws CheckException, RemoveException, ObjectNotFoundException {
        final Customer customer = new Customer("custo" + id);
        _dao.remove(customer.getId());
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
