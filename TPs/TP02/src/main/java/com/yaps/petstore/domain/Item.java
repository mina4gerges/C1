/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yaps.petstore.domain;

import com.yaps.petstore.exception.CheckException;
import java.io.Serializable;

/**
 *
 * @author mina2
 */
public class Item implements Serializable {

    String _id = null;
    String _name = null;
    Double _unitCost = null;
    Product _product = null;

    public Item(String id) {
        _id = id;
    }

    public Item(String id, String name, Double unitCost, Product _product) {
        _id = id;
        _name = name;
        _unitCost = unitCost;
    }

    public void setId(String id) {
        _id = id;
    }

    public void setName(String name) {
        _name = name;
    }

    public void setUnitCost(Double unitCost) {
        _unitCost = unitCost;
    }

    public String getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public Double getUnitCost() {
        return _unitCost;
    }

    public void setProduct(Product product) {
        _product = product;
    }
    
    public Product getProduct(){
        return _product;
    }

    public void checkData() throws CheckException {
        if (_id == null || "".equals(_id)) {
            throw new CheckException("Invalid item id");
        }
        if (_name == null || "".equals(_name)) {
            throw new CheckException("Invalid item name");
        }
        if (_unitCost == null || "".equals(_unitCost)) {
            throw new CheckException("Invalid item price");
        }
    }

    @Override
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("\n\tCategory {");
        buf.append("\n\t\tId=").append(_id);
        buf.append("\n\t\tName=").append(_name);
        buf.append("\n\t\tPrice=").append(_unitCost);
        buf.append("\n\t\tProduct Id=").append(_product.getId());
        buf.append("\n\t\tProduct name=").append(_product.getName());
        buf.append("\n\t}");
        return buf.toString();
    }
}
