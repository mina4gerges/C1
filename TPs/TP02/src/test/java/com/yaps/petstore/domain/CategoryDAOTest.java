package com.yaps.petstore.domain;

import com.yaps.petstore.exception.*;
import com.yaps.petstore.persistence.CategoryDAO;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This class tests the Category class
 */
public final class CategoryDAOTest extends TestCase {

	private CategoryDAO _dao = new CategoryDAO();
	
    public CategoryDAOTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(CategoryDAOTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with an invalid identifier.
     */
    public void testDomainFindCategoryWithInvalidValues() throws Exception {

        // Finds an object with a unknown identifier
        final int id = _dao.getUniqueId();
        try {
            findCategory(id);
            fail("Object with unknonw id should not be found");
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
    public void testDomainFindAllCategories() throws Exception {
        final int id = getUniqueId();

        // First findAll
        final int firstSize = getNumberOfCategories();

        // Creates an object
        createCategory(id);

        // Ensures that the object exists
        try {
            findCategory(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Second findAll
        final int secondSize = getNumberOfCategories();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        removeCategory(id);

        try {
            findCategory(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     */
    public void testDomainCreateCategory() throws Exception {
        final int id = getUniqueId();
        Category category = null;

        // Ensures that the object doesn't exist
        try {
            category = findCategory(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Creates an object
        createCategory(id);

        // Ensures that the object exists
        try {
            category = findCategory(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCategory(category, id);

        // Creates an object with the same identifier. An exception has to be thrown
        try {
            createCategory(id);
            fail("An object with the same id has already been created");
        } catch (DuplicateKeyException e) {
        }

        // Cleans the test environment
        removeCategory(id);

        try {
            findCategory(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testDomainCreateCategoryWithInvalidValues() throws Exception {

        // Creates an object with empty values
        try {
            final Category category = new Category(new String(), new String(), new String());
            _dao.insert(category);
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with null values
        try {
            final Category category = new Category(null, null, null);
            _dao.insert(category);
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to update an unknown object.
     */
    public void testDomainUpdateUnknownCategory() throws Exception {
        // Updates an unknown object
        try {
        	Category category = new Category();
        	_dao.update(category);
            fail("Updating a none existing object should break");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to update an object with a invalid values.
     */
    public void testDomainUpdateCategoryWithInvalidValues() throws Exception {

        // Creates an object
        final int id = getUniqueId();
        createCategory(id);

        // Ensures that the object exists
        Category category = null;
        try {
            category = findCategory(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Updates the object with empty values
        try {
            category.setName(new String());
            category.setDescription(new String());
            _dao.update(category);
            fail("Updating an object with empty values should break");
        } catch (CheckException e) {
        }

        // Updates the object with null values
        try {
            category.setName(null);
            category.setDescription(null);
            _dao.update(category);
            fail("Updating an object with null values should break");
        } catch (CheckException e) {
        }

        // Ensures that the object still exists
        try {
            category = findCategory(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Cleans the test environment
        removeCategory(id);

        try {
            findCategory(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test make sure that updating an object success
     */
    public void testDomainUpdateCategory() throws Exception {
        final int id = getUniqueId();

        // Creates an object
        createCategory(id);

        // Ensures that the object exists
        Category category = null;
        try {
            category = findCategory(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCategory(category, id);

        // Updates the object with new values
        updateCategory(category, id + 1);

        // Ensures that the object still exists
        Category categoryUpdated = null;
        try {
            categoryUpdated = findCategory(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkCategory(categoryUpdated, id + 1);

        // Cleans the test environment
        removeCategory(id);

        try {
            findCategory(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testDomainDeleteUnknownCategory() throws Exception {
        // Removes an unknown object
        try {
        	final Category category = new Category();
        	_dao.remove(category.getId());
            fail("Deleting an unknown object should break");
        } catch (ObjectNotFoundException e) {
        }
    }

    //==================================
    //=         Private Methods        =
    //==================================
    private Category findCategory(final int id) throws FinderException, CheckException {
        final Category category = _dao.find("cat" + id);
        return category;
    }

    private int getNumberOfCategories() throws FinderException {
        try {
            return _dao.findAll().size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    private void createCategory(final int id) throws CreateException, CheckException {
        final Category category = new Category("cat" + id, "name" + id, "description" + id);
        _dao.insert(category);
    }

    private void updateCategory(final Category category, final int id) throws UpdateException, CheckException, ObjectNotFoundException, DuplicateKeyException {
        category.setName("name" + id);
        category.setDescription("description" + id);
        _dao.update(category);
    }

    private void removeCategory(final int id) throws CheckException, RemoveException, ObjectNotFoundException {
        final Category category = new Category("cat" + id);
        _dao.remove(category.getId());
    }

    private void checkCategory(final Category category, final int id) {
        assertEquals("name", "name" + id, category.getName());
        assertEquals("description", "description" + id, category.getDescription());
    }

    private int getUniqueId() {
        return _dao.getUniqueId();
    }
}
