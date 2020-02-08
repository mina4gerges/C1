package com.yaps.petstore.domain;

import com.yaps.petstore.exception.*;

import java.io.Serializable;
import java.util.Collection;

/**
 * This class represents a Product in the catalog of the YAPS company.
 * The catalog is divided into categories. Each one divided into products
 * and each product in items.
 */
public final class Product implements Serializable {

    // ======================================
    // =             Attributes             =
    // ======================================
    private String _id;
    private String _name;
    private String _description;
    private Category _category;
    private Collection _items;

    // ======================================
    // =            Constructors            =
    // ======================================
    public Product() {
    }

    public Product(final String id) {
        _id = id;
    }

    public Product(final String id, final String name, final String description, final Category category) {
        _id = id;
        _name = name;
        _description = description;
        _category = category;
    }

    // ======================================
    // =            Private methods         =
    // ======================================

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    public String getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public void setName(final String name) {
        _name = name;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(final String description) {
        _description = description;
    }

    public Category getCategory() {
        return _category;
    }

    public void setCategory(final Category category) {
        _category = category;
    }

    public Collection getItems() {
        return _items;
    }

    /**
     * This method checks the integrity of the object data.
     *
     * @throws CheckException if data is invalid
     */
    public void checkData() throws CheckException {
        if (_name == null || "".equals(_name))
            throw new CheckException("Invalid name");
        if (_description == null || "".equals(_description))
            throw new CheckException("Invalid description");
        if (_category == null || _category.getId() == null || "".equals(_category.getId()))
            throw new CheckException("Invalid category");
    }

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("\n\tProduct {");
        buf.append("\n\t\tId=").append(_id);
        buf.append("\n\t\tName=").append(_name);
        buf.append("\n\t\tDescription=").append(_description);
        buf.append("\n\t\tCategory Id=").append(_category.getId());
        buf.append("\n\t\tCategory Name=").append(_category.getName());
        buf.append("\n\t}");
        return buf.toString();
    }
}
