package com.yaps.petstore.domain;

import com.yaps.petstore.exception.*;
import com.yaps.petstore.persistence.CategoryDAO;
import com.yaps.petstore.persistence.ItemDAO;
import com.yaps.petstore.persistence.ProductDAO;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This class tests the Product class
 */
public final class ItemDAOTest extends TestCase {

    private final ItemDAO _dao = new ItemDAO();
    private final ProductDAO _productDAO = new ProductDAO();
    private final CategoryDAO _categoryDAO = new CategoryDAO();

    public ItemDAOTest(final String s) {
        super(s);
    }
    
    public void tearDown() {
    	_categoryDAO.finalize();
    	_productDAO.finalize();
    	_dao.finalize();
    }

    public static TestSuite suite() {
        return new TestSuite(ItemDAOTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with an invalid identifier.
     */
    public void testDomainFindItemWithInvalidValues() throws Exception {

        // Finds an object with an unknown identifier
        final int id = getUniqueId();
        try {
            findItem(id);
            fail("Object with unknown id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with an empty identifier
        try {
            _dao.find(new String());
            fail("Object with empty id should not be found");
        } catch (CheckException e) {
        }

        // Finds an object with a null identifier
        try {
        	_dao.find(null);
            fail("Object with null id should not be found");
        } catch (CheckException e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     */
    public void testDomainFindAllItems() throws Exception {
        final int id = getUniqueId();

        // First findAll
        final int firstSize = getNumberOfItems();

        // Create an object
        createItem(id);

        // Ensures that the object exists
        try {
            findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // second findAll
        final int secondSize = getNumberOfItems();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        removeItem(id);

        try {
            findItem(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     */
    public void testDomainCreateItem() throws Exception {
        final int id = getUniqueId();
        Item item = null;

        // Ensures that the object doesn't exist
        try {
            item = findItem(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Creates an object
        createItem(id);

        // Ensures that the object exists
        try {
            item = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkItem(item, id);

        // Creates an object with the same identifier. An exception has to be thrown
        try {
            createItem(id);
            fail("An object with the same id has already been created");
        } catch (DuplicateKeyException e) {
        }

        // Cleans the test environment
        removeItem(id);

        try {
            findItem(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testDomainCreateItemWithInvalidValues() throws Exception {

        // Creates an object with an empty values
        try {
            final Item item = new Item(new String(), new String(), 0, null);
            _dao.insert(item);
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an null values
        try {
            final Item item = new Item(null, null, 0, null);
            _dao.insert(item);
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid linked object.
     */
    public void testDomainCreateItemWithInvalidProduct() throws Exception {
        final int id = getUniqueId();

        // Creates an object with a null linked object
        try {
            final Item item = new Item("item" + id, "name" + id, id, null);
            _dao.insert(item);;
            fail("Object with null object linked should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an empty linked object
        try {
            final Item item = new Item("item" + id, "name" + id, id, new Product());
            _dao.insert(item);
            fail("Object with an empty object linked should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to update an unknown object.
     */
    public void testDomainUpdateUnknownItem() throws Exception {
        // Updates an unknown object
        try {
            _dao.update(new Item());
            fail("Updating a none existing object should break");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to update an object with a invalid values.
     */
    public void testDomainUpdateItemWithInvalidValues() throws Exception {

        // Creates an object
        final int id = getUniqueId();
        createItem(id);

        // Ensures that the object exists
        Item item = null;
        try {
            item = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Updates the object with empty values
        try {
            item.setName(new String());
            item.setUnitCost(0);
            _dao.update(item);
            fail("Updating an object with empty values should break");
        } catch (CheckException e) {
        }

        // Updates the object with null values
        try {
            item.setName(null);
            item.setUnitCost(0);
            _dao.update(item);
            fail("Updating an object with null values should break");
        } catch (CheckException e) {
        }

        // Ensures that the object still exists
        try {
            item = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Cleans the test environment
        removeItem(id);

        try {
            findItem(id);
            fail();
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test make sure that updating an object success
     */
    public void testDomainUpdateItem() throws Exception {
        final int id = getUniqueId();

        // Creates an object
        createItem(id);

        // Ensures that the object exists
        Item item = null;
        try {
            item = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkItem(item, id);

        // Updates the object with new values
        updateItem(item, id + 1);

        // Ensures that the object still exists
        Item itemUpdated = null;
        try {
            itemUpdated = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkItem(itemUpdated, id + 1);

        // Cleans the test environment
        removeItem(id);

        try {
            findItem(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testDomainDeleteUnknownItem() throws Exception {
        // Removes an unknown object
        try {
        	_dao.remove("");
            fail("Deleting an unknown object should break");
        } catch (ObjectNotFoundException e) {
        }
    }

    //==================================
    //=         Private Methods        =
    //==================================
    private Item findItem(final int id) throws FinderException, CheckException {
        final Item item = _dao.find("item" + id);
        return item;
    }

    private int getNumberOfItems() throws FinderException {
        try {
            return _dao.findAll().size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    // Creates a category first, then a product and then an item linked to this product
    private void createItem(final int id) throws CreateException, CheckException {
        // Create Category
        final Category category = new Category("cat" + id, "name" + id, "description" + id);
        _categoryDAO.insert(category);
        // Create Product
        final Product product = new Product("prod" + id, "name" + id, "description" + id, category);
        _productDAO.insert(product);
        // Create Item
        final Item item = new Item("item" + id, "name" + id, id, product);
        _dao.insert(item);
    }

    // Creates a category, a product and updates the item with this new product
    private void updateItem(final Item item, final int id) throws UpdateException, CreateException, CheckException, ObjectNotFoundException {
        // Create Category
        final Category category = new Category("cat" + id, "name" + id, "description" + id);
        _categoryDAO.insert(category);
        // Create Product
        final Product product = new Product("prod" + id, "name" + id, "description" + id, category);
        _productDAO.insert(product);
        // Updates the item
        item.setName("name" + id);
        item.setUnitCost(id);
        item.setProduct(product);
        _dao.update(item);
    }

    private void checkItem(final Item item, final int id) {
        assertEquals("name", "name" + id, item.getName());
        assertEquals("unitCost", new Double(id), new Double(item.getUnitCost()));
        assertNotNull("product", item.getProduct());
    }

    private void removeItem(final int id) throws RemoveException, CheckException, ObjectNotFoundException {
        _dao.remove("item" + id);
        _productDAO.remove("prod" + id);
        _categoryDAO.remove("cat" + id);
    }

    private int getUniqueId() {
        return _dao.getUniqueId();
    }
}
