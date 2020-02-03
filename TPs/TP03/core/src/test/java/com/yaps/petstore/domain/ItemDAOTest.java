package com.yaps.petstore.domain;

import com.yaps.petstore.AbstractTestCase;
import com.yaps.petstore.domain.category.Category;
import com.yaps.petstore.domain.category.CategoryDAO;
import com.yaps.petstore.domain.item.ItemDAO;
import com.yaps.petstore.domain.item.Item;
import com.yaps.petstore.domain.product.Product;
import com.yaps.petstore.domain.product.ProductDAO;
import com.yaps.petstore.exception.*;

import junit.framework.TestSuite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class tests the ItemDAO class
 */
public final class ItemDAOTest extends AbstractTestCase {

	private ItemDAO _dao = new ItemDAO();
	
    public ItemDAOTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(ItemDAOTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     */
    public void testFindItemWithInvalidValues() throws Exception {

        // Finds an object with a unknown identifier
        final int id = _dao.getUniqueId();
        try {
            findItem(id);
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
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     */
    public void testFindAllItems() throws Exception {
        final int id = _dao.getUniqueId();

        // First findAll
        final int firstSize = findAllItems();

        // Create an object
        createItem(id);

        // Ensures that the object exists
        try {
            findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // second findAll
        final int secondSize = findAllItems();

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
    public void testCreateItem() throws Exception {
        final int id = _dao.getUniqueId();
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

        // Ensures that the object exists in the database by executing a sql statement
        try {
            findItemSql(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found in the database");
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

        try {
            findItemSql(id);
            fail("Object has been deleted it shouldn't be found in the database");
        } catch (ObjectNotFoundException e) {
        }
    }


    /**
     * This test tries to update an object with a invalid values.
     */
    public void testUpdateItemWithInvalidValues() throws Exception {

        // Creates an object
        final int id = _dao.getUniqueId();
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
            item.checkData();
            _dao.update(item);
            fail("Updating an object with empty values should break");
        } catch (CheckException e) {
        }

        // Updates the object with null values
        try {
            item.setName(null);
            item.setUnitCost(0);
            item.checkData();
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
    public void testUpdateItem() throws Exception {
        final int id = _dao.getUniqueId();

        // Creates an object
        createItem(id);

        // Ensures that the object exists
        Item item = null;
        try {
            item = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Ensures that the object exists in the database by executing a sql statement
        try {
            findItemSql(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found in the database");
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

        try {
            findItemSql(id);
            fail("Object has been deleted it shouldn't be found in the database");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testDeleteUnknownItem() throws Exception {
        // Removes an unknown object
        try {
            _dao.remove(null);
            fail("Deleting an unknown object should break");
        } catch (ObjectNotFoundException e) {
        }
    }

    //==================================
    //=         Private Methods        =
    //==================================
    private Item findItem(final int id) throws FinderException, CheckException {
        final Item item = (Item)_dao.select("item" + id);
        return item;
    }

    private void findItemSql(final int id) throws ObjectNotFoundException, SQLException {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        final String sql;

        try {
            connection = getConnection();
            statement = connection.createStatement();

            // Select a Row
            sql = "SELECT * FROM T_ITEM WHERE ID = 'item" + id + "' ";
            resultSet = statement.executeQuery(sql);
            if (resultSet.next() == false)
                throw new ObjectNotFoundException();

        } finally {
            // Close
            resultSet.close();
            statement.close();
            connection.close();
        }
    }

    private int findAllItems() throws FinderException {
        try {
            return _dao.selectAll().size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    // Creates a category first, then a product and then an item linked to this product
    private void createItem(final int id) throws CreateException, CheckException {
        // Create Category
        final Category category = new Category("cat" + id, "name" + id, "description" + id);
        new CategoryDAO().insert(category);
        // Create Product
        final Product product = new Product("prod" + id, "name" + id, "description" + id, category);
        new ProductDAO().insert(product);
        // Create Item
        final Item item = new Item("item" + id, "name" + id, id, product);
        _dao.insert(item);
    }

    // Creates a category, a product and updates the item with this new product
    private void updateItem(final Item item, final int id) throws UpdateException, CreateException, CheckException, ObjectNotFoundException {
        // Create Category
        final Category category = new Category("cat" + id, "name" + id, "description" + id);
        new CategoryDAO().insert(category);
        // Create Product
        final Product product = new Product("prod" + id, "name" + id, "description" + id, category);
        new ProductDAO().insert(product);
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
        new ProductDAO().remove("prod" + id);
        new CategoryDAO().remove("cat" + id);
    }
}
