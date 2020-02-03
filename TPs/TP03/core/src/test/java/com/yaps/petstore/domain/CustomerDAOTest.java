package com.yaps.petstore.domain;

import com.yaps.petstore.domain.customer.Customer;
import com.yaps.petstore.domain.customer.CustomerDAO;
import com.yaps.petstore.exception.CheckException;
import com.yaps.petstore.exception.CreateException;
import com.yaps.petstore.exception.DuplicateKeyException;
import com.yaps.petstore.exception.FinderException;
import com.yaps.petstore.exception.ObjectNotFoundException;

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
     * This test tries to find an object with an invalid identifier.
     */
    public void testFindCustomerWithInvalidValues() throws Exception {

        // Finds an object with a unknown identifier
        final String id = _dao.getUniqueStringId();
        try {
            findCustomer(id);
            fail("Object with unknonw id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with an empty identifier
        try {
        	findCustomer(new String());
            fail("Object with empty id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with a null identifier
        try {
        	findCustomer(null);
            fail("Object with null id should not be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     */
    public void testCreateCustomer() throws Exception {
        final String id = _dao.getUniqueStringId();
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
     * This test make sure that updating an object success
     */
    public void testUpdateCustomer() throws Exception {
        final String id = _dao.getUniqueStringId();

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
        String  newId = "12345";
        updateCustomer(customer, newId);

        // Ensures that the object still exists
        Customer customerUpdated = null;
        try {
            customerUpdated = findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkCustomer(customerUpdated, newId);

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
    public void testDeleteUnknownCustomer() throws Exception {
        // Removes an unknown object
        try {
            removeCustomer(_dao.getUniqueStringId());
            fail("Deleting an unknown object should break");
        } catch (ObjectNotFoundException e) {
        }
    }

    //==================================
    //=         Private Methods        =
    //==================================
    private Customer findCustomer(final String id) throws FinderException, CheckException {
        final Customer customer = (Customer)_dao.select("custo" + id);
        return customer;
    }

    private void createCustomer(final String id) throws CreateException, CheckException {
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

    private void updateCustomer(final Customer customer, final String id) 
    throws ObjectNotFoundException, DuplicateKeyException, CheckException {
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

    private void removeCustomer(final String id) throws ObjectNotFoundException {
        final String sid = "custo" + id;
        _dao.remove(sid);
    }

    private void checkCustomer(final Customer customer, final String id) {
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
