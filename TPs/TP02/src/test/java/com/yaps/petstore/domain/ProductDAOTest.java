package com.yaps.petstore.domain;

import com.yaps.petstore.exception.*;
import com.yaps.petstore.persistence.CategoryDAO;
import com.yaps.petstore.persistence.ProductDAO;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This class tests the Product class
 */
public final class ProductDAOTest extends TestCase {

    private final ProductDAO _dao = new ProductDAO();
    private final CategoryDAO _categoryDAO = new CategoryDAO();

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
     * This test tries to find an object with a invalid identifier.
     */
    public void testDomainFindProductWithInvalidValues() throws Exception {

        // Finds an object with a unknown identifier
        final int id = getUniqueId();
        try {
            findProduct(id);
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
    public void testDomainFindAllProducts() throws Exception {
        final int id = getUniqueId();

        // First findAll
        final int firstSize = getNumberOflProducts();

        // Create an object
        createProduct(id);

        // Ensures that the object exists
        try {
            findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // second findAll
        final int secondSize = getNumberOflProducts();

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
    public void testDomainCreateProduct() throws Exception {
        final int id = getUniqueId();
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
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testDomainCreateProductWithInvalidValues() throws Exception {

        // Creates an object with an empty values
        try {
            final Product product = new Product(new String(), new String(), new String(), null);
            _dao.insert(product);
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an null values
        try {
            final Product product = new Product(null, null, null, null);
            _dao.insert(product);
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid linked object.
     */
    public void testDomainCreateProductWithInvalidCategory() throws Exception {
        final int id = getUniqueId();

        // Creates an object with a null linked object
        try {
            final Product product = new Product("prod" + id, "name" + id, "description" + id, null);
            _dao.insert(product);
            fail("Object with null object linked should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an empty linked object
        try {
            final Product product = new Product("prod" + id, "name" + id, "description" + id, new Category());
            _dao.insert(product);
            fail("Object with an empty object linked should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to update an unknown object.
     */
    public void testDomainUpdateUnknownProduct() throws Exception {
        // Updates an unknown object
        try {
            _dao.update(new Product());
            fail("Updating a none existing object should break");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to update an object with a invalid values.
     */
    public void testDomainUpdateProductWithInvalidValues() throws Exception {

        // Creates an object
        final int id = getUniqueId();
        createProduct(id);

        // Ensures that the object exists
        Product product = null;
        try {
            product = findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Updates the object with empty values
        try {
            product.setName(new String());
            product.setDescription(new String());
            _dao.update(product);
            fail("Updating an object with empty values should break");
        } catch (CheckException e) {
        }

        // Updates the object with null values
        try {
            product.setName(null);
            product.setDescription(null);
            _dao.update(product);
            fail("Updating an object with null values should break");
        } catch (CheckException e) {
        }

        // Ensures that the object still exists
        try {
            product = findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Cleans the test environment
        removeProduct(id);

        try {
            findProduct(id);
            fail();
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test make sure that updating an object success
     */
    public void testDomainUpdateProduct() throws Exception {
        final int id = getUniqueId();

        // Creates an object
        createProduct(id);

        // Ensures that the object exists
        Product product = null;
        try {
            product = findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
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
    }

    /**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testDomainDeleteUnknownProduct() throws Exception {
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
    private Product findProduct(final int id) throws FinderException, CheckException {
        final Product product = _dao.find("prod" + id);
        return product;
    }

    private int getNumberOflProducts() throws FinderException {
        try {
            return _dao.findAll().size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    // Creates a category first and then a product linked to this category
    private void createProduct(final int id) throws CreateException, CheckException {
        // Create Category
        final Category category = new Category("cat" + id, "name" + id, "description" + id);
        _categoryDAO.insert(category);
        // Create Product
        final Product product = new Product("prod" + id, "name" + id, "description" + id, category);
        _dao.insert(product);
    }

    // Creates a category and updates the product with this new category
    private void updateProduct(final Product product, final int id) throws UpdateException, CreateException, CheckException, ObjectNotFoundException {
        // Create Category
        final Category category = new Category("cat" + id, "name" + id, "description" + id);
        _categoryDAO.insert(category);
        // Update Product with new category
        product.setName("name" + id);
        product.setDescription("description" + id);
        product.setCategory(category);
        _dao.update(product);
    }

    private void removeProduct(final int id) throws RemoveException, CheckException, ObjectNotFoundException {
        _dao.remove("prod" + id);
        _categoryDAO.remove("cat" + id);
    }

    private void checkProduct(final Product product, final int id) {
        assertEquals("name", "name" + id, product.getName());
        assertEquals("description", "description" + id, product.getDescription());
        assertNotNull("category", product.getCategory());
    }

    private int getUniqueId() {
        return _dao.getUniqueId();
    }
}
