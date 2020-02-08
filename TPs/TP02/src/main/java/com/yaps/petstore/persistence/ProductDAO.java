/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yaps.petstore.persistence;

import com.yaps.petstore.domain.Product;
import com.yaps.petstore.exception.CheckException;
import com.yaps.petstore.exception.DuplicateKeyException;
import com.yaps.petstore.exception.ObjectNotFoundException;
import java.util.Collection;

/**
 *
 * @author mina2
 */
public final class ProductDAO extends DataAccessObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private static final String HASHTABLE_FILE_NAME = "persistentProduct.ser";

    // ======================================
    // =            Constructors            =
    // ======================================
    public ProductDAO() {
        super(HASHTABLE_FILE_NAME);
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method gets a Product object from the HashMap.
     *
     * @param id Product identifier to be found in the hastable
     * @return Product the product object with all its attributs set
     * @throws CheckException is thrown if invalid data is found
     * @throws ObjectNotFoundException is thrown if the product id not found in
     * the hastable
     */
    public Product find(final String id) throws ObjectNotFoundException, CheckException {
        // Checks data integrity
        checkId(id);
        return (Product) super.select(id);
    }

    /**
     * This method gets All Category object from the HashMap.
     *
     * @return Collection contains All products
     * @throws CheckException is thrown if invalid data is found
     * @throws ObjectNotFoundException is thrown if the category id not found in
     * the hastable
     */
    public Collection findAll() throws ObjectNotFoundException, CheckException {
        return super.selectAll();
    }

    /**
     * This method inserts a Product object into the HashMap and serializes the
     * Hastable on disk.
     *
     * @param product Product Object to be inserted into the hastable
     * @throws CheckException is thrown if invalid data is found
     * @throws DuplicateKeyException is thrown when an identical object is
     * already in the hastable
     */
    public void insert(final Product product) throws DuplicateKeyException, CheckException {
        // Checks data integrity
        product.checkData();
        // Puts the object into the hastable
        super.insert(product, product.getId());
    }

    /**
     * This method updates a Product object of the HashMap and serializes the
     * Hastable on disk.
     *
     * @param product Product to be updated from the hastable
     * @throws ObjectNotFoundException is thrown if the product id not found in
     * the hastable
     * @throws DuplicateKeyException is thrown when an identical object is
     * already in the hastable
     * @throws CheckException is thrown if the product has invalid data
     */
    public void update(final Product product) throws ObjectNotFoundException, DuplicateKeyException, CheckException {
        String id = product.getId();
        checkId(id);
        product.checkData();
        super.update(product, id);
    }

    /**
     * This method deletes a Product object from the HashMap and serializes the
     * Hastable on disk.
     *
     * @param id Product identifier to be deleted from the hastable
     * @throws ObjectNotFoundException is thrown if there's a persistent problem
     */
    public void remove(final String id) throws ObjectNotFoundException {
        super.remove(id);
    }

}
