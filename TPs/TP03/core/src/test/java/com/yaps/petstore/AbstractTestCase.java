package com.yaps.petstore;

import com.yaps.petstore.persistence.DataAccessConstants;
import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractTestCase extends TestCase {

    protected AbstractTestCase() {
    }

    protected AbstractTestCase(final String id) {
        super(id);
    }

    protected final Connection getConnection() throws SQLException {
        final Connection connection;
        connection = DriverManager.getConnection(DataAccessConstants.URL_DB, DataAccessConstants.USER_DB, DataAccessConstants.PASSWD_DB);
        return connection;
    }
}
