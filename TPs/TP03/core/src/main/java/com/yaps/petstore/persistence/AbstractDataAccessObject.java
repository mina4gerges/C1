package com.yaps.petstore.persistence;

import com.yaps.petstore.domain.DomainObject;
import com.yaps.petstore.exception.DuplicateKeyException;
import com.yaps.petstore.exception.ObjectNotFoundException;
import com.yaps.petstore.logging.Trace;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;

/**
 * This class follows the Data Acces Object (DAO) Design Pattern.
 * It uses JDBC to store object values in a database.
 * Every concrete DAO class should extends this class.
 */
public abstract class AbstractDataAccessObject implements DataAccessConstants {

    // ======================================
    // =             Attributes             =
    // ======================================

    // Used for logging
    protected final transient String _cname = this.getClass().getName();
    private static final String sname = AbstractDataAccessObject.class.getName();
    
    // ======================================
    // =            Static Block            =
    // ======================================
    static {
        // Loads the JDBC driver class
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            Trace.throwing(sname, "static", e);
        }
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method gets all the attributes for the object from the database.
     *
     * @param id Object identifier to be found in the persistent layer
     * @return DomainObject the object with all its attributs set
     * @throws ObjectNotFoundException is thrown if the object id not found in the persistent layer
     * @throws com.yaps.petstore.exception.DataAccessException
     *                                 is thrown if there's a persistent problem
     */
    public abstract DomainObject select(final String id) throws ObjectNotFoundException;

    /**
     * This method return all the objects from the database.
     *
     * @return collection of DomainObject
     * @throws ObjectNotFoundException is thrown if the collection is empty
     * @throws com.yaps.petstore.exception.DataAccessException
     *                                 is thrown if there's a persistent problem
     */
    public abstract Collection selectAll() throws ObjectNotFoundException;

    /**
     * This method inserts an object into the database.
     *
     * @param object Domain object to be inserted
     * @throws DuplicateKeyException is thrown when an identical object is already in the persistent layer
     * @throws com.yaps.petstore.exception.DataAccessException
     *                               is thrown if there's a persistent problem
     */
    public abstract void insert(final DomainObject object) throws DuplicateKeyException;

    /**
     * This method updates an object in the database.
     *
     * @param object Object to be updated in the database
     * @throws ObjectNotFoundException is thrown if the object id not found in the database
     * @throws com.yaps.petstore.exception.DataAccessException
     *                                 is thrown if there's a persistent problem
     */
    public abstract void update(final DomainObject object) throws ObjectNotFoundException;

    /**
     * This method deletes an object from the database.
     *
     * @param id identifier of the object to be deleted
     * @throws ObjectNotFoundException is thrown if the object id not found in the persistent layer
     * @throws com.yaps.petstore.exception.DataAccessException
     *                                 is thrown if there's a persistent problem
     */
    public abstract void remove(final String id) throws ObjectNotFoundException;

    /**
     * This method returns a database connection.
     *
     * @return a JDBC connection to the petstore database
     * @throws SQLException if a SQl exception is found
     */
    protected static final Connection getConnection() throws SQLException {
        final Connection connection;
        connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWD_DB);
        return connection;
    }

    /**
     * This method displays all information of an SQL exception. Its error code, state,
     * sql message and ultimately the stacktrace of the Exception
     *
     * @param e SQLException that you want to display
     */
    protected static void displaySqlException(final SQLException e) {
        final String mname = "displaySqlException";

        Trace.severe(sname, mname, "Error code  : " + e.getErrorCode());
        Trace.severe(sname, mname, "SQL state   : " + e.getSQLState());
        Trace.severe(sname, mname, "SQL message : " + e.getMessage());
        Trace.throwing(sname, mname, e);
    }

    /**
     * This method displays all information of an SQL exception and a custom message.
     * Display the sql error code, state, sql message and ultimately the stacktrace of the Exception
     *
     * @param message custom message to display
     * @param e       SQLException that you want to display
     */
    protected static void displaySqlException(final String message, final SQLException e) {
        final String mname = "displaySqlException";

        Trace.severe(sname, mname, "Message     : " + message);
        Trace.severe(sname, mname, "Error code  : " + e.getErrorCode());
        Trace.severe(sname, mname, "SQL state   : " + e.getSQLState());
        Trace.severe(sname, mname, "SQL message : " + e.getMessage());
        Trace.throwing(sname, mname, e);
    }

    public final String getUniqueStringId() {
        return Integer.toString(getUniqueId());
    }

    public final int getUniqueId() {
    	// TODO to enhance
        return (int) (Math.random() * 100000);
    }

}
