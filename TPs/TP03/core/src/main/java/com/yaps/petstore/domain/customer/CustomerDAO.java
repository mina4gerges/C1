package com.yaps.petstore.domain.customer;

import com.yaps.petstore.domain.DomainObject;
import com.yaps.petstore.exception.CheckException;
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
 * This class does all the database access for the class Customer.
 *
 * @see Customer
 */
public final class CustomerDAO extends AbstractDataAccessObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private static final String TABLE = "T_CUSTOMER";
    private static final String COLUMNS = "ID, FIRSTNAME, LASTNAME, TELEPHONE, STREET1, STREET2, CITY, STATE, ZIPCODE, COUNTRY";

    // ======================================
    // =            Constructors            =
    // ======================================
    public CustomerDAO() {
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    public DomainObject select(final String id) throws ObjectNotFoundException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Customer customer = null;

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
            customer = new Customer(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
            customer.setTelephone(resultSet.getString(4));
            customer.setStreet1(resultSet.getString(5));
            customer.setStreet2(resultSet.getString(6));
            customer.setCity(resultSet.getString(7));
            customer.setState(resultSet.getString(8));
            customer.setZipcode(resultSet.getString(9));
            customer.setCountry(resultSet.getString(10));

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
        return customer;
    }

    public Collection selectAll() throws ObjectNotFoundException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        final Collection customers = new ArrayList();

        try {
            // Gets a database connection
            connection = getConnection();
            statement = connection.createStatement();

            // Select a Row
            final String sql = "SELECT " + COLUMNS + " FROM " + TABLE;
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                // Set data to the collection
            	final Customer customer;
            	customer = new Customer(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
            	customer.setTelephone(resultSet.getString(4));
            	customer.setStreet1(resultSet.getString(5));
            	customer.setStreet2(resultSet.getString(6));
            	customer.setCity(resultSet.getString(7));
            	customer.setState(resultSet.getString(8));
            	customer.setZipcode(resultSet.getString(9));
            	customer.setCountry(resultSet.getString(10));
            	customers.add(customer);
            }

            if (customers.isEmpty())
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
        return customers;
    }

    public void insert(final DomainObject object) throws DuplicateKeyException {
        Connection connection = null;
        Statement statement = null;
        final Customer customer = (Customer) object;

        try {
            // Gets a database connection
            connection = getConnection();
            statement = connection.createStatement();

            // Inserts a Row
            final String sql = "INSERT INTO " + TABLE + "(" + COLUMNS + ") VALUES ('" + customer.getId() + "', '" + customer.getFirstname() + "','" + customer.getLastname() + "', '" + customer.getTelephone() + "', '" + customer.getStreet1() + "', '" + customer.getStreet2() + "', '" + customer.getCity() + "', '" + customer.getState() + "', '" + customer.getZipcode() + "', '" + customer.getCountry() + "' )";
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
        final Customer customer = (Customer) object;

        try {
            // Gets a database connection
            connection = getConnection();
            statement = connection.createStatement();

            // Update a Row
            final String sql = "UPDATE " + TABLE + " SET FIRSTNAME = '" + customer.getFirstname() + "', LASTNAME = '" + customer.getLastname() + "', TELEPHONE = '" + customer.getTelephone() + "', STREET1 = '" + customer.getStreet1() + "', STREET2 = '" + customer.getStreet2() + "', CITY = '" + customer.getCity() + "', STATE = '" + customer.getState() + "', ZIPCODE = '" + customer.getZipcode() + "', COUNTRY = '" + customer.getCountry() + "' WHERE ID = '" + customer.getId() + "' ";

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
