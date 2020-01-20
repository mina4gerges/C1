package com.yaps.petstore;

import java.util.HashMap;
import java.util.Map;

/**
 * This class uses a HashTable to store customer objects in it and serializes the hashmap.
 */

// TODO one day : use persistent storage
public final class CustomerDAO {

    // ======================================
    // =             Attributes             =
    // ======================================
    private static Map _hashmap = new HashMap();

    // ======================================
    // =            Constructors            =
    // ======================================
    public CustomerDAO() {
        // TODO one day : loads from persistent storage
    }
    
    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method gets a Customer object from the HashMap.
     *
     * @param id Customer identifier to be found in the hastable
     * @return Customer the customer object with all its attributs set
     * @throws CustomerNotFoundException is thrown if the customer id not found in the hastable
     */
    public Customer find(final String id) throws CustomerNotFoundException {
        // If the given id doesn't exist we throw a CustomerNotFoundException
        if (!_hashmap.containsKey(id)) {
            throw new CustomerNotFoundException();
        }

        return (Customer) _hashmap.get(id);
    }

    /**
     * This method inserts a Customer object into the HashMap and serializes the Hastable on disk.
     *
     * @param customer Customer Object to be inserted into the hastable
     * @throws CustomerDuplicateKeyException is thrown when an identical object is already in the hastable
     * @throws CustomerCheckException 
     */
    public void insert(final Customer customer) throws CustomerDuplicateKeyException, CustomerCheckException {
        // If the given id already exists we cannot insert the new Customer
        if (_hashmap.containsKey(customer.getId())) {
            throw new CustomerDuplicateKeyException();
        }
        customer.checkData();
        // Puts the object into the hastable
        _hashmap.put(customer.getId(), customer);
    }

    /**
     * This method updates a Customer object of the HashMap and serializes the Hastable on disk.
     *
     * @param customer Customer to be updated from the hastable
     * @throws CustomerNotFoundException     is thrown if the customer id not found in the hastable
     * @throws CustomerDuplicateKeyException is thrown when an identical object is already in the hastable
     * @throws CustomerCheckException 
     */
	public void update(final Customer customer) throws CustomerNotFoundException, CustomerDuplicateKeyException, CustomerCheckException {
		customer.checkData();
		if (!_hashmap.containsKey(customer.getId()))
			throw new CustomerNotFoundException();
		_hashmap.put(customer.getId(), customer);
	}

    /**
     * This method deletes a Customer object from the HashMap and serializes the Hastable on disk.
     *
     * @param id Customer identifier to be deleted from the hastable
     * @throws CustomerNotFoundException is thrown if there's a persistent problem
     */
    public void remove(final String id) throws CustomerNotFoundException {
        // If the given id does'nt exist we cannot remove the Customer from the hashmap
        if (!_hashmap.containsKey(id)) {
            throw new CustomerNotFoundException();
        }

        // The object is removed from the hastable
        _hashmap.remove(id);
    }
    
    public int getUniqueId() {
    	String s = null;
    	int result = 0;
    	do {
    		result = (int) (Math.random() * 100000);
    		s = Integer.toString(result);
    		try {
				find(s);
			} catch (CustomerNotFoundException e) {
				return result;
			}
    	} while ( true );
    }


    // ======================================
    // =            Private methods         =
    // ======================================
    /**
     * This method checks that the identifier is valid.
     *
     * @param id identifier to check
     * @throws CustomerCheckException if the id is invalid
     */
    private void checkId(final String id) throws CustomerCheckException {
        if (id == null || "".equals(id))
            throw new CustomerCheckException("Invalid customer id");
    }
}
