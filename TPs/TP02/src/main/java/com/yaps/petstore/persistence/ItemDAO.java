/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yaps.petstore.persistence;

import com.yaps.petstore.domain.Item;
import com.yaps.petstore.exception.CheckException;
import com.yaps.petstore.exception.DuplicateKeyException;
import com.yaps.petstore.exception.ObjectNotFoundException;
import java.util.Collection;

/**
 *
 * @author mina2
 */
public final class ItemDAO extends DataAccessObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private static final String HASHTABLE_FILE_NAME = "persistentItem.ser";

    // ======================================
    // =            Constructors            =
    // ======================================
    public ItemDAO() {
        super(HASHTABLE_FILE_NAME);
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method gets a Item object from the HashMap.
     *
     * @param id Item identifier to be found in the hastable
     * @return Item the item object with all its attributs set
     * @throws CheckException is thrown if invalid data is found
     * @throws ObjectNotFoundException is thrown if the item id not found in the
     * hastable
     */
    public Item find(final String id) throws ObjectNotFoundException, CheckException {
        // Checks data integrity
        checkId(id);
        return (Item) super.select(id);
    }

    /**
     * This method gets All Category object from the HashMap.
     *
     * @return collection contains All Items
     * @throws CheckException is thrown if invalid data is found
     * @throws ObjectNotFoundException is thrown if the category id not found in
     * the hastable
     */
    public Collection findAll() throws ObjectNotFoundException, CheckException {
        return super.selectAll();
    }

    /**
     * This method inserts a Item object into the HashMap and serializes the
     * Hastable on disk.
     *
     * @param item Item Object to be inserted into the hastable
     * @throws CheckException is thrown if invalid data is found
     * @throws DuplicateKeyException is thrown when an identical object is
     * already in the hastable
     */
    public void insert(final Item item) throws DuplicateKeyException, CheckException {
        // Checks data integrity
        item.checkData();
        // Puts the object into the hastable
        super.insert(item, item.getId());
    }

    /**
     * This method updates a Item object of the HashMap and serializes the
     * Hastable on disk.
     *
     * @param item Item to be updated from the hastable
     * @throws ObjectNotFoundException is thrown if the item id not found in the
     * hastable
     * @throws DuplicateKeyException is thrown when an identical object is
     * already in the hastable
     * @throws CheckException is thrown if the item has invalid data
     */
    public void update(final Item item) throws ObjectNotFoundException, DuplicateKeyException, CheckException {
        String id = item.getId();
        checkId(id);
        item.checkData();
        super.update(item, id);
    }

    /**
     * This method deletes a Item object from the HashMap and serializes the
     * Hastable on disk.
     *
     * @param id Item identifier to be deleted from the hastable
     * @throws ObjectNotFoundException is thrown if there's a persistent problem
     */
    public void remove(final String id) throws ObjectNotFoundException {
        super.remove(id);
    }

}
