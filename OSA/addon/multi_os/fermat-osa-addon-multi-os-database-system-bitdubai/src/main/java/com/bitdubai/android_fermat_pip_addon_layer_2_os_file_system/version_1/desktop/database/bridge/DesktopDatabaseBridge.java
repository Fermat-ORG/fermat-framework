package com.bitdubai.fermat_osa_addon.layer.desktop.database_system.developer.bitdubai.version_1.desktop.database.bridge;


import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Matias
 */


/**
 * The SQLiteDatabase class is a migration of the Android SQLiteDatabase with the purpose of
 * implement all the same features and methods to handle the database system in desktop enviroment
 *
 * Exposes methods to manage a SQLite database.
 */

public class DesktopDatabaseBridge {
    /**
     * Database member variables.
     */
    private Connection c = null;
    private Statement stmt = null;
    private boolean transaccionSatisfactoria = false;
    private String databasePath;


    /**
     * Constructor
     */
    public DesktopDatabaseBridge(String databasePath) {
        this.databasePath = databasePath;
    }


    /**
     * Method who open the database connection
     * if the database not exist the method create a new one
     * @exception SQLException    //if the Driver is not available
     **/
    public void connect() {
        try {
            //SQLite Driver
            Class.forName("org.sqlite.JDBC");
            //c = DriverManager.getConnection("jdbc:sqlite:"+databasePath);
            c = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
            c.setAutoCommit(false);
            //Testing purpose
            //System.out.println("database open");
        } catch (Exception e) {
            e.printStackTrace();
            close();
            //Luis acá iria una Excepción del tipo SQLiteException
        }
    }

    /**
     * Method who close the database connection
     * @exception SQLException    //if the database is not open
     **/
    private void close() {
        try {
            c.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DesktopDatabaseBridge.class.getName()).log(Level.SEVERE, null, ex);
            //Luis acá iria una Excepcion de que no abrío la base de datos
        }

    }

    /**
     *
     * @param sql   //the SQL query
     * @param selectionArgs  //The values will be bound as Strings.
     * @exception SQLException    //if the SQL string is invalid
     * @return  //A Cursor object, which is positioned before the first entry. 
     */

    public ResultSet rawQuery(String sql, String[] selectionArgs) {
        connect();
        ResultSet rs = null;
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery(sql);
            c.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DesktopDatabaseBridge.class.getName()).log(Level.SEVERE, null, ex);
            //Luis acá iria la Excepcion de SQLite que deberia crear
        } finally {
            close();
        }
        return rs;
    }


    /**
     * Execute a single SQL statement that is NOT a SELECT or any other SQL statement that returns data.
     * @param sql  //the SQL query
     * Example String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
    "VALUES (1, 'Paul', 32, 'California', 20000.00 );";
     @exception SQLException    //if the SQL string is invalid
     */
    public void execSQL(String sql) {
        connect();
        ResultSet rs = null;
        try {
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(DesktopDatabaseBridge.class.getName()).log(Level.SEVERE, null, ex);
            //Luis acá iria la Excepcion de SQLite que deberia crear
        } finally {
            close();
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
        transaccionSatisfactoria = true;
    }

    /**
     * End a transaction
     * @exception SQLException if the database connection is not open
     */
    public void endTransaction() {
        if (transaccionSatisfactoria) {
            try {
                c.commit();
            } catch (SQLException ex) {
                Logger.getLogger(DesktopDatabaseBridge.class.getName()).log(Level.SEVERE, null, ex);
                //Luis acá iria un Excepcion si la base no fue creada
            }
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
     * @exception SQLException if the database is close or the driver is not more available
     */
    public void update(String tableName, Map<String, Object> recordUpdateList, String whereClause, String[] whereArg) {

        // create our java preparedstatement using a sql update query
        String setVariables = "";
        for (Map.Entry<String, Object> entry : recordUpdateList.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            setVariables += entry.getKey() + "=" + entry.getValue() + " ";
        }

        PreparedStatement ps;
        try {
            ps = c.prepareStatement("UPDATE " + tableName + " SET " + setVariables + whereClause);
            // call executeUpdate to execute our sql update statement
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(DesktopDatabaseBridge.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}



