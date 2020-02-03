package com.yaps.petstore.domain;

import com.yaps.petstore.AbstractTestCase;
import com.yaps.petstore.domain.category.Category;
import com.yaps.petstore.domain.category.CategoryDAO;
import com.yaps.petstore.domain.product.Product;
import com.yaps.petstore.domain.product.ProductDAO;
import com.yaps.petstore.exception.*;
import junit.framework.TestSuite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class tests the ProductDAO class
 */
public final class ProductDAOTest extends AbstractTestCase {

	private ProductDAO _dao = new ProductDAO();
	
    public ProductDAOTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(ProductDAOTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to findan object with a invalid identifier.
     */
    public void testFindProductWithInvalidValues() throws Exception {

        // Finds an object with a unknown identifier
        final int id = _dao.getUniqueId();
        try {
            findProduct(id);
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
    public void testFindAllProducts() throws Exception {
        final int id = _dao.getUniqueId();

        // First findAll
        final int firstSize = findAllProducts();

        // Create an object
        createProduct(id);

        // Ensures that the object exists
        try {
            findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // second findAll
        final int secondSize = findAllProducts();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        removeProduct(id);

        try {
            findProduct(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     */
    public void testCreateProduct() throws Exception {
        final int id = _dao.getUniqueId();
        Product product = null;

        // Ensures that the object doesn't exist
        try {
            product = findProduct(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Creates an object
        createProduct(id);

        // Ensures that the object exists
        try {
            product = findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Ensures that the object exists in the database by executing a sql statement
        try {
            findProductSql(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found in the database");
        }

        // Checks that it's the right object
        checkProduct(product, id);

        // Creates an object with the same identifier. An exception has to be thrown
        try {
            createProduct(id);
            fail("An object with the same id has already been created");
        } catch (DuplicateKeyException e) {
        }

        // Cleans the test environment
        removeProduct(id);

        try {
            findProduct(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        try {
            findProductSql(id);
            fail("Object has been deleted it shouldn't be found in the database");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testCreateProductWithInvalidValues() throws Exception {

        // Creates an object with an empty values
        try {
            final Product product = new Product(new String(), new String(), new String(), null);
            product.checkData();
            _dao.insert(product);
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an null values
        try {
            final Product product = new Product(null, null, null, null);
            product.checkData();
            _dao.insert(product);
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid linked object.
     */
    public void testCreateProductWithInvalidCategory() throws Exception {
        final int id = _dao.getUniqueId();

        // Creates an object with no object linked
        try {
            final Product product = new Product("prod" + id, "name" + id, "description" + id, null);
            product.checkData();
            _dao.insert(product);
            fail("Object with no object linked should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an empty linked object
        try {
            final Product product = new Product("prod" + id, "name" + id, "description" + id, new Category());
            product.checkData();
            _dao.insert(product);
            fail("Object with an empty object linked should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test make sure that updating an object success
     */
    public void testUpdateProduct() throws Exception {
        final int id = _dao.getUniqueId();

        // Creates an object
        createProduct(id);

        // Ensures that the object exists
        Product product = null;
        try {
            product = findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Ensures that the object exists in the database by executing a sql statement
        try {
            findProductSql(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found in the database");
        }

        // Checks that it's the right object
        checkProduct(product, id);

        // Updates the object with new values
        updateProduct(product, id + 1);

        // Ensures that the object still exists
        Product productUpdated = null;
        try {
            productUpdated = findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkProduct(productUpdated, id + 1);

        // Cleans the test environment
        removeProduct(id);

        try {
            findProduct(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        try {
            findProductSql(id);
            fail("Object has been deleted it shouldn't be found in the database");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testDeleteUnknownProduct() throws Exception {
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
    private Product findProduct(final int id) throws FinderException, CheckException {
        final Product product = (Product)_dao.select("prod" + id);
        return product;
    }

    private void findProductSql(final int id) throws ObjectNotFoundException, SQLException {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        final String sql;

        try {
            connection = getConnection();
            statement = connection.createStatement();

            // Select a Row
            sql = "SELECT * FROM T_PRODUCT WHERE ID = 'prod" + id + "' ";
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

    private int findAllProducts() throws FinderException {
        try {
            return _dao.selectAll().size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    // Creates a category first and then a product linked to this category
    private void createProduct(final int id) throws CreateException, CheckException, ObjectNotFoundException  {
        // Create Category
    	final String newCategoryId = "cat" + id;
        final Category category = new Category("cat" + id, "name" + id, "description" + id);
        new CategoryDAO().insert(category);
        // Create Product
        final Product product = new Product("prod" + id, "name" + id, "description" + id, category);
        try {
        	_dao.insert(product);
        } catch ( DuplicateKeyException e ) {
        	// remove the added category object
        	new CategoryDAO().remove(newCategoryId);
        	// retthrow the exception
        	throw e;
        }
    }

    // Creates a category and updates the product with this new category
    private void updateProduct(final Product product, final int id) throws UpdateException, CreateException, ObjectNotFoundException {
        // Create Category
        final Category category = new Category("cat" + id, "name" + id, "description" + id);
        new CategoryDAO().insert(category);
        // Update Product with new category
        product.setName("name" + id);
        product.setDescription("description" + id);
        product.setCategory(category);
        _dao.update(product);
    }

    private void removeProduct(final int id) throws RemoveException, ObjectNotFoundException {
        _dao.remove("prod" + id);
        new CategoryDAO().remove("cat" + id);
    }

    private void checkProduct(final Product product, final int id) {
        assertEquals("name", "name" + id, product.getName());
        assertEquals("description", "description" + id, product.getDescription());
        assertNotNull("category", product.getCategory());
    }
}
