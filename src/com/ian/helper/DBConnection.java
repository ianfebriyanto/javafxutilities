/*
 * Helper JAVAFX application
 */

package com.ian.helper;

import java.sql.*;

public class DBConnection {
    private String mConnectionUrl = null;
    private Connection mConnection = null;
    private Statement mStatement = null;
    private String host;
    private String port;
    private String username;
    private String password;
    private String dbName;
    private DB_CONNECTION dbConnection;

    /**
     * sql server only
     */
    private Boolean useInstance;

    /**
     * Callback Result
     */
    public interface CallbackResult {
        void queryResultSet(ResultSet resultSet);
    }

    /**
     * Callback Connection
     */
    private interface CallbackConnection {
        void onConnected(Connection mConnection);

        void onErrorConection(String message);
    }

    /**
     * DB Connection type
     */
    public enum DB_CONNECTION {
        SQL_SERVER,
        ORACLE
    }

    /**
     * Abstrack Connection callback
     */
    public static abstract class AbstractConnection implements CallbackConnection {
        @Override
        public void onConnected(Connection mConnection) {

        }

        @Override
        public void onErrorConection(String message) {

        }
    }

    /**
     * instance connection
     *
     * @param dbConnection db connection type
     * @return instance
     */
    public static DBConnection create(DB_CONNECTION dbConnection) {
        return new DBConnection(dbConnection);
    }

    private DBConnection(DB_CONNECTION db_connection) {
        this.dbConnection = db_connection;
    }

    /**
     * set host
     *
     * @param host database host
     * @return this
     */
    public DBConnection setHost(String host) {
        this.host = host;
        return this;
    }

    /**
     * set port
     *
     * @param port database port
     * @return this
     */
    public DBConnection setPort(String port) {
        this.port = port;
        return this;
    }

    /**
     * set username
     *
     * @param username database username
     * @return
     */
    public DBConnection setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * set password
     *
     * @param password database password
     * @return this
     */
    public DBConnection setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * set database name
     *
     * @param dbName database name
     * @return this
     */
    public DBConnection setDBName(String dbName) {
        this.dbName = dbName;
        return this;
    }

    /**
     * identify using instance for sql server
     *
     * @param useInstance database intstance
     * @return this
     */
    public DBConnection setUseInstance(Boolean useInstance) {
        this.useInstance = useInstance;
        return this;
    }

    /**
     * execute query
     *
     * @param query              user query
     * @param callbackResult     callback result
     * @param abstractConnection callback connection
     */
    public void executeQuery(String query, CallbackResult callbackResult, AbstractConnection abstractConnection) {
        getConnection(new AbstractConnection() {
            @Override
            public void onConnected(Connection mConnection) {
                super.onConnected(mConnection);
                try {
                    mStatement = mConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet resultSet = mStatement.executeQuery(query);
                    callbackResult.queryResultSet(resultSet);

                    //close connection
                    closeConnection();
                } catch (SQLException e) {
                    if (abstractConnection != null) {
                        abstractConnection.onErrorConection(e.getMessage());
                    }
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorConection(String message) {
                if (abstractConnection != null) {
                    abstractConnection.onErrorConection(message);
                }
                super.onErrorConection(message);
            }
        });
    }

    /**
     * get current connection
     *
     * @param abstractConnection connection
     */
    private void getConnection(AbstractConnection abstractConnection) {
        try {
            if (dbConnection == DB_CONNECTION.SQL_SERVER) {
                if (useInstance) {
                    mConnectionUrl = "jdbc:sqlserver://" + host + ";" + "databaseName=" + dbName + ";user=" + username + ";password=" + password;
                } else {
                    mConnectionUrl = "jdbc:sqlserver://" + host + ":" + port + ";" + "databaseName=" + dbName + ";user=" + username + ";password=" + password;
                }

                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                mConnection = DriverManager.getConnection(mConnectionUrl);
            } else if (dbConnection == DB_CONNECTION.ORACLE) {
                mConnectionUrl = "jdbc:oracle:thin:@" + host + ":" + port + ":" + dbName;
                Class.forName("oracle.jdbc.driver.OracleDriver");
                mConnection = DriverManager.getConnection(mConnectionUrl, username, password);
            } else {
                abstractConnection.onErrorConection("Error select database");
            }

            abstractConnection.onConnected(mConnection);
        } catch (ClassNotFoundException | SQLException e) {
            abstractConnection.onErrorConection(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * close connection
     */
    public void closeConnection() {
        try {
            if (mConnection != null) mConnection.close();
            if (mStatement != null) mStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
