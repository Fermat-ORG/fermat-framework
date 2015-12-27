/*
* @#DesktopDatabaseBridge.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.desktop.database.bridge;

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
    private Connection c = null;
    private Statement stmt = null;
    private boolean transaccionSatisfactoria=false;
    private String databasePath;


    /**
     * Constructor
     */
    public DesktopDatabaseBridge(String databasePath){
        this.databasePath=databasePath;
    }



    /**
     * Method who open the database connection
     * if the database not exist the method create a new one
     * @exception SQLException    //if the Driver is not available
     **/
    public void connect(){
        try {
            //SQLite Driver
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
            c.setAutoCommit(false);

        }catch(Exception e){
            e.printStackTrace();
            if (c != null)
                close();

        }
    }

    /**
     * Method who close the database connection
     * @exception SQLException	//if the database is not open
     **/
    private void close(){
        try {

            if (c != null){
                c.close();
            }

            if (stmt != null){
                stmt.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(DesktopDatabaseBridge.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    /**
     *
     * @param sql   //the SQL query
     * @param selectionArgs  //The values will be bound as Strings.
     * @exception SQLException	//if the SQL string is invalid
     * @return  //A Cursor object, which is positioned before the first entry.
     */

    public ResultSet rawQuery(String sql,String[] selectionArgs) {
        if (c == null)
            connect();

        ResultSet rs=null;
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery(sql);
            c.commit();

        }catch(SQLException ex){
            Logger.getLogger(DesktopDatabaseBridge.class.getName()).log(Level.SEVERE, null, ex);

        }



        return rs;
    }


    /**
     * Execute a single SQL statement that is NOT a SELECT or any other SQL statement that returns data.
     * @param sql  //the SQL query
     * Example String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
    "VALUES (1, 'Paul', 32, 'California', 20000.00 );";
     @exception SQLException	//if the SQL string is invalid
     */
    public void execSQL(String sql) {

        if (c == null)
            connect();

        ResultSet rs=null;
        try {
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();

        }catch(SQLException ex){
            Logger.getLogger(DesktopDatabaseBridge.class.getName()).log(Level.SEVERE, null, ex);

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
        transaccionSatisfactoria=true;
    }

    /**
     * End a transaction
     * @exception SQLException if the database connection is not open
     */
    public void endTransaction() {
        if (transaccionSatisfactoria){
            try {
                c.commit();
            } catch (SQLException ex) {
                Logger.getLogger(DesktopDatabaseBridge.class.getName()).log(Level.SEVERE, null, ex);

            }
            if (c != null)
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
    public void update(String tableName, Map<String, Object> recordUpdateList,String whereClause, String[] whereArg) {

        // create our java preparedstatement using a sql update query
        String setVariables = "";
        int limited = recordUpdateList.entrySet().size();
        int i = 1;
        String com;
        String pk= (whereArg!=null) ?  whereArg[0] : null;
        for(Map.Entry<String, Object> entry : recordUpdateList.entrySet()) {

            if(i !=limited){
                com=", ";
            }else{
                com="";
            }

            if(pk== null || !pk.equalsIgnoreCase(entry.getKey())) {
                setVariables += entry.getKey() + "= '" + entry.getValue() + "'" + com;
            }

            i++;
        }

       // PreparedStatement ps;
       /* try {
            System.out.println("UPDATE "+tableName+" SET "+setVariables+whereClause);
            ps = c.prepareStatement("UPDATE "+tableName+" SET "+setVariables+whereClause);

            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(DesktopDatabaseBridge.class.getName()).log(Level.SEVERE, null, ex);
        }*/

        if (c == null){
            connect();
        }

        ResultSet rs=null;
        try {
            stmt = c.createStatement();
            stmt.executeUpdate("UPDATE " + tableName + " SET " + setVariables + whereClause);
            stmt.close();
            c.commit();

        }catch(SQLException ex){
            Logger.getLogger(DesktopDatabaseBridge.class.getName()).log(Level.SEVERE, null, ex);

            if (c != null)
                close();
        }

    }

}
