/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yaps.petstore.persistence;

import com.yaps.petstore.domain.Category;
import com.yaps.petstore.exception.CheckException;
import com.yaps.petstore.exception.DuplicateKeyException;
import com.yaps.petstore.exception.ObjectNotFoundException;
import java.util.Collection;

/**
 *
 * @author mina2
 */
public class CategoryDAO extends DataAccessObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private static final String HASHTABLE_FILE_NAME = "persistentCategory.ser";

    // ======================================
    // =            Constructors            =
    // ======================================
    public CategoryDAO() {
        super(HASHTABLE_FILE_NAME);
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method gets a Category object from the HashMap.
     *
     * @param id Category identifier to be found in the hastable
     * @return Category the category object with all its attributs set
     * @throws CheckException is thrown if invalid data is found
     * @throws ObjectNotFoundException is thrown if the category id not found in
     * the hastable
     */
    public Category find(final String id) throws ObjectNotFoundException, CheckException {
        // Checks data integrity
        checkId(id);
        return (Category) super.select(id);
    }

    /**
     * This method gets All Category object from the HashMap.
     *
     * @return collection contains all Categories
     * @throws CheckException is thrown if invalid data is found
     * @throws ObjectNotFoundException is thrown if the category id not found in
     * the hastable
     */
    public Collection findAll() throws ObjectNotFoundException, CheckException {
        return super.selectAll();
    }

    /**
     * This method inserts a Category object into the HashMap and serializes the
     * Hastable on disk.
     *
     * @param category Category Object to be inserted into the hastable
     * @throws CheckException is thrown if invalid data is found
     * @throws DuplicateKeyException is thrown when an identical object is
     * already in the hastable
     */
    public void insert(final Category category) throws DuplicateKeyException, CheckException {
        // Checks data integrity
        category.checkData();
        // Puts the object into the hastable
        super.insert(category, category.getId());
    }

    /**
     * This method updates a Category object of the HashMap and serializes the
     * Hastable on disk.
     *
     * @param category Category to be updated from the hastable
     * @throws ObjectNotFoundException is thrown if the category id not found in
     * the hastable
     * @throws DuplicateKeyException is thrown when an identical object is
     * already in the hastable
     * @throws CheckException is thrown if the category has invalid data
     */
    public void update(final Category category) throws ObjectNotFoundException, DuplicateKeyException, CheckException {
        String id = category.getId();
        checkId(id);
        category.checkData();
        super.update(category, id);
    }

    /**
     * This method deletes a Category object from the HashMap and serializes the
     * Hastable on disk.
     *
     * @param id Category identifier to be deleted from the hastable
     * @throws ObjectNotFoundException is thrown if there's a persistent problem
     */
    public void remove(final String id) throws ObjectNotFoundException {
        super.remove(id);
    }
}
