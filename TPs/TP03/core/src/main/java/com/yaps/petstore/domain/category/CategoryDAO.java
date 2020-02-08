/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yaps.petstore.domain.category;

import com.yaps.petstore.domain.DomainObject;
import com.yaps.petstore.exception.DataAccessException;
import com.yaps.petstore.exception.DuplicateKeyException;
import com.yaps.petstore.exception.ObjectNotFoundException;
import com.yaps.petstore.persistence.AbstractDataAccessObject;
import static com.yaps.petstore.persistence.DataAccessConstants.DATA_ALREADY_EXIST;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author mina2
 */
public final class CategoryDAO extends AbstractDataAccessObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private static final String TABLE = "T_CATEGORY";
    private static final String COLUMNS = "ID, NAME, DESCRIPTION";

    // ======================================
    // =            Constructors            =
    // ======================================
    public CategoryDAO() {
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    public DomainObject select(final String id) throws ObjectNotFoundException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Category category = null;

        try {
            // Gets a database connection
            connection = getConnection();
            statement = connection.createStatement();

            // Select a Row
            final String sql = "SELECT " + COLUMNS + " FROM " + TABLE + " WHERE ID = '" + id + "' ";
            resultSet = statement.executeQuery(sql);
            if (!resultSet.next()) {
                throw new ObjectNotFoundException();
            }

            // Set data to current object
            category = new Category(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));

        } catch (SQLException e) {
            // A Severe SQL Exception is caught
            displaySqlException(e);
            throw new DataAccessException("Cannot get data from the database: " + e.getMessage(), e);
        } finally {
            // Close
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                displaySqlException("Cannot close connection", e);
                throw new DataAccessException("Cannot close the database connection", e);
            }
        }
        return category;
    }

    public Collection selectAll() throws ObjectNotFoundException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        final Collection categories = new ArrayList();

        try {
            // Gets a database connection
            connection = getConnection();
            statement = connection.createStatement();

            // Select a Row
            final String sql = "SELECT " + COLUMNS + " FROM " + TABLE;
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                // Set data to the collection
                final Category category;
                category = new Category(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                categories.add(category);
            }

            if (categories.isEmpty()) {
                throw new ObjectNotFoundException();
            }

        } catch (SQLException e) {
            // A Severe SQL Exception is caught
            displaySqlException(e);
            throw new DataAccessException("Cannot get data from the database: " + e.getMessage(), e);
        } finally {
            // Close
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                displaySqlException("Cannot close connection", e);
                throw new DataAccessException("Cannot close the database connection", e);
            }
        }
        return categories;
    }

    public void insert(final DomainObject object) throws DuplicateKeyException {
        Connection connection = null;
        Statement statement = null;
        final Category category = (Category) object;

        try {
            // Gets a database connection
            connection = getConnection();
            statement = connection.createStatement();

            // Inserts a Row
            final String sql = "INSERT INTO " + TABLE + "(" + COLUMNS + ") VALUES ('" + category.getId() + "', '" + category.getName() + "','" + category.getDescription() + "' )";
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
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                displaySqlException("Cannot close connection", e);
                throw new DataAccessException("Cannot close the database connection", e);
            }
        }
    }

    public void update(final DomainObject object) throws ObjectNotFoundException {
        Connection connection = null;
        Statement statement = null;
        final Category category = (Category) object;

        try {
            // Gets a database connection
            connection = getConnection();
            statement = connection.createStatement();

            // Update a Row
            final String sql = "UPDATE " + TABLE + " SET NAME = '" + category.getName() + "', DESCRIPTION = '" + category.getDescription() + "' ";

            if (statement.executeUpdate(sql) == 0) {
                throw new ObjectNotFoundException();
            }

        } catch (SQLException e) {
            // A Severe SQL Exception is caught
            displaySqlException(e);
            throw new DataAccessException("Cannot update data into the database", e);
        } finally {
            // Close
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
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
            if (statement.executeUpdate(sql) == 0) {
                throw new ObjectNotFoundException();
            }

        } catch (SQLException e) {
            // A Severe SQL Exception is caught
            displaySqlException(e);
            throw new DataAccessException("Cannot remove data into the database", e);

        } finally {
            // Close
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                displaySqlException("Cannot close connection", e);
                throw new DataAccessException("Cannot close the database connection", e);
            }
        }
    }
}
