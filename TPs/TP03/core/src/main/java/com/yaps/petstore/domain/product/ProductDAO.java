package com.yaps.petstore.domain.product;

import com.yaps.petstore.domain.DomainObject;
import com.yaps.petstore.domain.category.Category;
import com.yaps.petstore.exception.DataAccessException;
import com.yaps.petstore.exception.DuplicateKeyException;
import com.yaps.petstore.exception.ObjectNotFoundException;
import com.yaps.petstore.persistence.AbstractDataAccessObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class does all the database access for the class Product.
 *
 * @see Product
 */
final public class ProductDAO extends AbstractDataAccessObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private static final String TABLE = "T_PRODUCT";
    private static final String COLUMNS = "ID, NAME, DESCRIPTION, CATEGORY_FK";

    // ======================================
    // =            Constructors            =
    // ======================================
    public ProductDAO() {
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    public DomainObject select(final String id) throws ObjectNotFoundException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Product product;

        try {
            // Gets a database connection
            connection = getConnection();
            statement = connection.createStatement();

            // Select a Row
            final String sql = "SELECT " + COLUMNS + " FROM " + TABLE + " WHERE ID = '" + id + "' ";
            resultSet = statement.executeQuery(sql);
            if (!resultSet.next())
                throw new ObjectNotFoundException();

            // Set data to current object
            product = new Product(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), new Category(resultSet.getString(4)));

        } catch (SQLException e) {
            // A Severe SQL Exception is caught
            displaySqlException(e);
            throw new DataAccessException("Cannot get data from the database: " + e.getMessage(), e);
        } finally {
            // Close
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                displaySqlException("Cannot close connection", e);
                throw new DataAccessException("Cannot close the database connection", e);
            }
        }
        return product;
    }

    public Collection selectAll() throws ObjectNotFoundException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        final Collection products = new ArrayList();
        Product product;

        try {
            // Gets a database connection
            connection = getConnection();
            statement = connection.createStatement();

            // Select a Row
            final String sql = "SELECT " + COLUMNS + " FROM " + TABLE;
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                product = new Product(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), new Category(resultSet.getString(4)));
                // Set data to the collection
                products.add(product);
            }

            if (products.isEmpty())
                throw new ObjectNotFoundException();

        } catch (SQLException e) {
            // A Severe SQL Exception is caught
            displaySqlException(e);
            throw new DataAccessException("Cannot get data from the database: " + e.getMessage(), e);
        } finally {
            // Close
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                displaySqlException("Cannot close connection", e);
                throw new DataAccessException("Cannot close the database connection", e);
            }
        }
        return products;
    }

    public void insert(final DomainObject object) throws DuplicateKeyException {
        Connection connection = null;
        Statement statement = null;
        final Product product = (Product) object;

        try {
            // Gets a database connection
            connection = getConnection();
            statement = connection.createStatement();

            // Inserts a Row
            final String sql = "INSERT INTO " + TABLE + "(" + COLUMNS + ") VALUES ('" + product.getId() + "', '" + product.getName() + "', '" + product.getDescription() + "', '" + product.getCategory().getId() + "' )";
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            // The data already exists in the database
            if (e.getErrorCode() == DATA_ALREADY_EXIST) {
                throw new DuplicateKeyException();
            } else {
                // A Severe SQL Exception is caught
                displaySqlException(e);
                throw new DataAccessException("Cannot insert data into the database", e);
            }
        } finally {
            // Close
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                displaySqlException("Cannot close connection", e);
                throw new DataAccessException("Cannot close the database connection", e);
            }
        }
    }

    public void update(final DomainObject object) throws ObjectNotFoundException {
        Connection connection = null;
        Statement statement = null;
        final Product product = (Product) object;

        try {
            // Gets a database connection
            connection = getConnection();
            statement = connection.createStatement();

            // Update a Row
            final String sql = "UPDATE " + TABLE + " SET NAME = '" + product.getName() + "', DESCRIPTION = '" + product.getDescription() + "', CATEGORY_FK = '" + product.getCategory().getId() + "' WHERE ID = '" + product.getId() + "' ";
            if (statement.executeUpdate(sql) == 0)
                throw new ObjectNotFoundException();

        } catch (SQLException e) {
            // A Severe SQL Exception is caught
            displaySqlException(e);
            throw new DataAccessException("Cannot update data into the database", e);
        } finally {
            // Close
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                displaySqlException("Cannot close connection", e);
                throw new DataAccessException("Cannot close the database connection", e);
            }
        }
    }

    public void remove(final String id) throws ObjectNotFoundException {
        Connection connection = null;
        Statement statement = null;

        try {
            // Gets a database connection
            connection = getConnection();
            statement = connection.createStatement();

            // Delete a Row
            final String sql = "DELETE FROM " + TABLE + " WHERE ID = '" + id + "'";
            if (statement.executeUpdate(sql) == 0)
                throw new ObjectNotFoundException();

        } catch (SQLException e) {
            // A Severe SQL Exception is caught
            displaySqlException(e);
            throw new DataAccessException("Cannot remove data into the database", e);

        } finally {
            // Close
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                displaySqlException("Cannot close connection", e);
                throw new DataAccessException("Cannot close the database connection", e);
            }
        }
    }

}
