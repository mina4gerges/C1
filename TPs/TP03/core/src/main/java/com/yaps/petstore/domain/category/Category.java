/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yaps.petstore.domain.category;

import com.yaps.petstore.domain.DomainObject;
import java.io.Serializable;

/**
 *
 * @author mina2
 */
public final class Category extends DomainObject implements Serializable {

    String _id = null;
    String _name = null;
    String _description = null;

    public Category(String id) {
        _id = id;
    }

    public Category(String id, String name, String description) {
        _id = id;
        _name = name;
        _description = description;
    }

    @Override
    public void setId(String id) {
        _id = id;
    }

    public void seName(String name) {
        _name = name;
    }

    public void setDescription(String description) {
        _description = description;
    }

    @Override
    public String getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public String getDescription() {
        return _description;
    }

    @Override
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("\n\tCategory {");
        buf.append("\n\t\tId=").append(_id);
        buf.append("\n\t\tName=").append(_name);
        buf.append("\n\t\tDescription=").append(_description);
        buf.append("\n\t}");
        return buf.toString();
    }
}
