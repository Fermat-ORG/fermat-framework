package com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.desktop.database.bridge;

import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Class <code>com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.desktop.database.bridge.DesktopDatabaseBridge</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 10/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DesktopDatabaseBridge {

    /**
     * Database member variables.
     */
    private final ConnectionPool connectionPool;
    private Connection connection = null;
    private boolean successfulTransaction = false;
    private String databasePath;

    /**
     * Constructor
     */
    public DesktopDatabaseBridge(String databasePath) {

        try {
            PoolProperties p = new PoolProperties();
            p.setUrl("jdbc:sqlite:" + databasePath);
            p.setDriverClassName("org.sqlite.JDBC");
            p.setMaxActive(10);
            p.setMaxIdle(10);
            p.setInitialSize(2);
            p.setMaxWait(10000);
            p.setRemoveAbandonedTimeout(60);
            p.setMinEvictableIdleTimeMillis(30000);
            p.setMinIdle(10);
            this.connectionPool = new ConnectionPool(p);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.databasePath = databasePath;
    }

    /**
     * Method who open the database connection
     * if the database not exist the method create a new one
     *
     * @throws SQLException //if the Driver is not available
     **/
    public void connect() {
        try {
            //SQLite Driver
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
            connection.setAutoCommit(false);

        } catch (Exception e) {
            e.printStackTrace();
            if (connection != null)
                close();
            throw new RuntimeException(e);
        }
    }

    /**
     * Method who close the database connection
     *
     * @throws SQLException //if the database is not open
     **/
    private void close() {
        try {

            if (connection != null) {
                connection.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(DesktopDatabaseBridge.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }

    }

    public boolean isTableExists(String tableName) throws SQLException {

        String SQL_QUERY = "select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'";

        synchronized (connectionPool) {
            try (Connection connection = connectionPool.getConnection();
                 Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(SQL_QUERY)) {

                ResultSetMetaData rsmd = rs.getMetaData();

                if (rsmd.getColumnCount() > 0)
                    return true;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Execute a single SQL statement that is NOT a SELECT or any other SQL statement that returns data.
     *
     * @param SQL_QUERY //the SQL query
     *                  Example String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
     *                  "VALUES (1, 'Paul', 32, 'California', 20000.00 );";
     * @throws SQLException //if the SQL string is invalid
     */
    public void execSQL(final String SQL_QUERY) throws SQLException {

        synchronized (connectionPool) {
            try (Connection connection = connectionPool.getConnection();
                 Statement stmt = connection.createStatement()) {

                stmt.executeUpdate(SQL_QUERY);
            }
        }
    }

    /**
     * transactions can be nested. When the outer transaction is ended all
     * of the work done in that transaction and all of the nested transactions will be committed or rolled back.
     * The changes will be rolled back if any transaction is ended without being marked
     * as clean (by calling setTransactionSuccessful). Otherwise they will be committed.
     */
    public void beginTransaction() {
        connect();
    }

    /**
     * Marks the current transaction as successful.
     * Do not do any more database work between calling this and calling endTransaction.
     * Do as little non-database work as possible in that situation too.
     * If any errors are encountered between this and endTransaction the transaction will still be committed.
     */
    public void setTransactionSuccessful() {
        successfulTransaction = true;
    }

    /**
     * End a transaction
     *
     * @throws SQLException if the database connection is not open
     */
    public void endTransaction() {
        if (successfulTransaction) {
            try {
                connection.commit();
            } catch (SQLException ex) {
                Logger.getLogger(DesktopDatabaseBridge.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException(ex);
            }
            if (connection != null)
                close();
        }

    }

    /**
     * Create a new SQLiteDatabase class and open de connection
     * Is an implementation of the openDatabase in Android SQLite
     * We only use the databasePath param
     *
     * @param databasePath
     * @param object
     * @param flag
     * @param object0
     * @return SQLiteDatase  //return an open database
     */
    public static DesktopDatabaseBridge openDatabase(String databasePath, Object object, int flag, Object object0) {
        DesktopDatabaseBridge sqliteDB = new DesktopDatabaseBridge(databasePath);
        sqliteDB.connect();
        return sqliteDB;
    }


    /**
     * Deletes a database file
     *
     * @param databaseFile
     */
    public static void deleteDatabase(File databaseFile) {
        databaseFile.delete();
    }

    /**
     * Equivalent to openDatabase
     *
     * @param databaseFile
     * @param object
     * @return SQLiteDatase  //return an open database
     */
    public static DesktopDatabaseBridge openOrCreateDatabase(File databaseFile, Object object) {
        DesktopDatabaseBridge sqliteDB = new DesktopDatabaseBridge(databaseFile.getAbsolutePath());
        sqliteDB.connect();
        return sqliteDB;
    }

    /**
     * Method for updating rows in the database.
     *
     * @param tableName
     * @param recordUpdateList
     * @param whereClause
     * @param whereArg
     * @throws SQLException if the database is close or the driver is not more available
     */
    public void update(String tableName, Map<String, Object> recordUpdateList, String whereClause, String[] whereArg) throws SQLException {

        // create our java preparedstatement using a sql update query
        String setVariables = "";
        int limited = recordUpdateList.entrySet().size();
        int i = 1;
        String com;
        String pk = (whereArg != null) ? whereArg[0] : null;
        for (Map.Entry<String, Object> entry : recordUpdateList.entrySet()) {

            if (i != limited) {
                com = ", ";
            } else {
                com = "";
            }

            if (pk == null || !pk.equalsIgnoreCase(entry.getKey())) {
                setVariables += entry.getKey() + "= '" + entry.getValue() + "'" + com;
            }

            i++;
        }

        String SQL_QUERY = "UPDATE " + tableName + " SET " + setVariables + whereClause;

        synchronized (connectionPool) {
            try (Connection connection = connectionPool.getConnection();
                 Statement stmt = connection.createStatement()) {

                stmt.executeUpdate(SQL_QUERY);

            } catch (SQLException ex) {

                Logger.getLogger(DesktopDatabaseBridge.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException(ex);
            }
        }
    }

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }
}