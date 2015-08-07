/*
 * @(#BankNotesDatabaseFactory.java 05/10/2015
 * Copyright 2015 bitDubai, Inc. All rights reserved.
 * BITDUBAI/CONFIDENTIAL
 * */

package com.bitdubai.fermat_dmp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.structure;


// Packages and classes to import of jdk 1.7

// Packages and classes to import of Middleware Bank Notes API.

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;


/**
 * <p>The class <code>com.bitdubai.fermat_dmp_plugin.layer._14_middleware.bank_notes.developer.bitdubai.version_1.structure.BankNotesDatabaseFactory</code> is a object
 * for management the database resources.
 * <p/>
 * <p>This class is private for don't extends new classes.
 *
 * @author Raul Geomar Pena (raul.pena@mac.com)
 * @version 1.0.0
 * @see {@link com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem}
 * @since 05/10/2015
 */
public final class BankNotesDatabaseFactory implements DealsWithPluginDatabaseSystem {


    // Private instance fields declarations.
    /* Database system. */
    private PluginDatabaseSystem pluginDatabaseSystem = null;


    // Public constructor declarations.

    /**
     * <p>Unique constructor without parameters.
     */
    private BankNotesDatabaseFactory() {

        // Call to super class.
        super();
    }


    // Private instances methods declarations.
    /*
     *  <p>Method that create a new Database.
     *
     *  @return New Database.
     *  @throw CantCreateDatabaseException
     * */
    private Database doCreateDatabase() throws CantCreateDatabaseException {


        // Create a new Database.

        // Return the new Database.
        return this.pluginDatabaseSystem.createDatabase(BankNotesDatabaseConstants.DATABASE_OWNER,
                BankNotesDatabaseConstants.DATABASE_NAME);
    }

    /*
     *
     *   <p>Method that create the wallet table.
     *
     *   @param database Database object.
     *   @throw InvalidOwnerIdException
     *   @throw CantCreateTableException
     * */
    private void doCreateWalletTable(Database database) throws InvalidOwnerIdException, CantCreateTableException {


        // Method declarations.
        //DatabaseTableFactory table = null;


        // 1) Create the table factory.
        DatabaseTableFactory table;
        DatabaseFactory databaseFactory = database.getDatabaseFactory();

        table = databaseFactory.newTableFactory(BankNotesDatabaseConstants.DATABASE_OWNER, BankNotesDatabaseConstants.WALLET_TABLE_NAME);
//        table = ((DatabaseFactory) database).newTableFactory(BankNotesDatabaseConstants.DATABASE_OWNER, BankNotesDatabaseConstants.WALLET_TABLE_NAME);


        // 2) Add columns to wallet table
        table.addColumn(BankNotesDatabaseConstants.WALLET_TABLE_COLUMN_ID, DatabaseDataType.LONG_INTEGER, 0, true);
        table.addColumn(BankNotesDatabaseConstants.WALLET_TABLE_COLUMN_NAME, DatabaseDataType.STRING, 64, false);
        table.addColumn(BankNotesDatabaseConstants.WALLET_TABLE_COLUMN_DESCRIPTION, DatabaseDataType.STRING, 128, false);
        table.addColumn(BankNotesDatabaseConstants.WALLET_TABLE_COLUMN_CREATION, DatabaseDataType.STRING, 10, false);
        table.addColumn(BankNotesDatabaseConstants.WALLET_TABLE_COLUMN_UPDATE, DatabaseDataType.STRING, 10, false);


        // 3) Create the table.
        databaseFactory.createTable(BankNotesDatabaseConstants.DATABASE_OWNER, table);
//        ((DatabaseFactory) database).createTable(BankNotesDatabaseConstants.DATABASE_OWNER,table);
    }

    /*
     *
     *   <p>Method that create the item table.
     *
     *   @param database Database object.
     *   @throw InvalidOwnerIdException
     *   @throw CantCreateTableException
     * */
    private void doCreateItemTable(Database database) throws InvalidOwnerIdException, CantCreateTableException {


        // Method declarations.
        //DatabaseTableFactory table = null;


        // 1) Create the table factory.
        DatabaseTableFactory table;
        DatabaseFactory databaseFactory = database.getDatabaseFactory();

        table = databaseFactory.newTableFactory(BankNotesDatabaseConstants.DATABASE_OWNER, BankNotesDatabaseConstants.ITEM_TABLE_NAME);
//        table = ((DatabaseFactory) database).newTableFactory(BankNotesDatabaseConstants.DATABASE_OWNER, BankNotesDatabaseConstants.ITEM_TABLE_NAME);


        // 2) Add columns to item table
        table.addColumn(BankNotesDatabaseConstants.ITEM_TABLE_COLUMN_ID, DatabaseDataType.LONG_INTEGER, 0, true);
        table.addColumn(BankNotesDatabaseConstants.ITEM_TABLE_COLUMN_NAME, DatabaseDataType.STRING, 64, false);
        table.addColumn(BankNotesDatabaseConstants.ITEM_TABLE_COLUMN_DESCRIPTION, DatabaseDataType.STRING, 128, false);
        table.addColumn(BankNotesDatabaseConstants.ITEM_TABLE_COLUMN_CREATION, DatabaseDataType.STRING, 10, false);
        table.addColumn(BankNotesDatabaseConstants.ITEM_TABLE_COLUMN_UPDATE, DatabaseDataType.STRING, 10, false);


        // 3) Create the table.
        databaseFactory.createTable(BankNotesDatabaseConstants.DATABASE_OWNER,table);
//        ((DatabaseFactory) database).createTable(BankNotesDatabaseConstants.DATABASE_OWNER,table);
    }

    /*
     *
     *   <p>Method that create the depot table.
     *
     *   @param database Database object.
     *   @throw InvalidOwnerIdException
     *   @throw CantCreateTableException
     * */
    private void doCreateDepotTable(Database database) throws InvalidOwnerIdException, CantCreateTableException {


        // Method declarations.
        //DatabaseTableFactory table = null;


        // 1) Create the table factory.
        DatabaseTableFactory table;
        DatabaseFactory databaseFactory = database.getDatabaseFactory();

        table = databaseFactory.newTableFactory(BankNotesDatabaseConstants.DATABASE_OWNER, BankNotesDatabaseConstants.DEPOT_TABLE_NAME);
//        table = ((DatabaseFactory) database).newTableFactory (BankNotesDatabaseConstants.DATABASE_OWNER, BankNotesDatabaseConstants.DEPOT_TABLE_NAME);


        // 2) Add columns to depot table
        table.addColumn(BankNotesDatabaseConstants.DEPOT_TABLE_COLUMN_ID, DatabaseDataType.LONG_INTEGER, 0, true);
        table.addColumn(BankNotesDatabaseConstants.DEPOT_TABLE_COLUMN_NAME, DatabaseDataType.STRING, 64, false);
        table.addColumn(BankNotesDatabaseConstants.DEPOT_TABLE_COLUMN_DESCRIPTION, DatabaseDataType.STRING, 128, false);
        table.addColumn(BankNotesDatabaseConstants.DEPOT_TABLE_COLUMN_CREATION, DatabaseDataType.STRING, 10, false);
        table.addColumn(BankNotesDatabaseConstants.DEPOT_TABLE_COLUMN_UPDATE, DatabaseDataType.STRING, 10, false);


        // 3) Create the table.
        databaseFactory.createTable(BankNotesDatabaseConstants.DATABASE_OWNER, table);
//        ((DatabaseFactory) database).createTable (BankNotesDatabaseConstants.DATABASE_OWNER,table);
    }

    /*
     *
     *   <p>Method that create the item by depot table.
     *
     *   @param database Database object.
     *   @throw InvalidOwnerIdException
     *   @throw CantCreateTableException
     * */
    private void doCreateItemByDepotTable(Database database) throws InvalidOwnerIdException, CantCreateTableException {


        // Method declarations.
        //DatabaseTableFactory table = null;


        // 1) Create the table factory.
        DatabaseTableFactory table;
        DatabaseFactory databaseFactory = database.getDatabaseFactory();

        table = databaseFactory.newTableFactory(BankNotesDatabaseConstants.DATABASE_OWNER, BankNotesDatabaseConstants.ITEM_BY_DEPOT_TABLE_NAME);
//        table = ((DatabaseFactory) database).newTableFactory (BankNotesDatabaseConstants.DATABASE_OWNER,BankNotesDatabaseConstants.ITEM_BY_DEPOT_TABLE_NAME);


        // 2) Add columns to item by depot table
        table.addColumn(BankNotesDatabaseConstants.ITEM_BY_DEPOT_TABLE_COLUMN_ID, DatabaseDataType.LONG_INTEGER, 0, true);
        table.addColumn(BankNotesDatabaseConstants.ITEM_BY_DEPOT_TABLE_COLUMN_ITEM, DatabaseDataType.LONG_INTEGER, 0, false);
        table.addColumn(BankNotesDatabaseConstants.ITEM_BY_DEPOT_TABLE_COLUMN_DEPOT, DatabaseDataType.LONG_INTEGER, 0, false);
        table.addColumn(BankNotesDatabaseConstants.ITEM_BY_DEPOT_TABLE_COLUMN_CREATION, DatabaseDataType.STRING, 10, false);
        table.addColumn(BankNotesDatabaseConstants.ITEM_BY_DEPOT_TABLE_COLUMN_UPDATE, DatabaseDataType.STRING, 10, false);


        // 3) Create the table.
        databaseFactory.createTable(BankNotesDatabaseConstants.DATABASE_OWNER, table);
//        ((DatabaseFactory) database).createTable (BankNotesDatabaseConstants.DATABASE_OWNER,table);
    }


    // Public instance method declarations extends of com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem

    /**
     * <p>Method that set the database plugin.
     *
     * @param pluginDatabaseSystem Database plugin.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {

        // Set the internal values.
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }


    // Public instance methods declarations.

    /**
     * <p>Method that create a new database and her tables.
     *
     * @return Object database.
     * @throw CantCreateDatabaseException
     * @throw InvalidOwnerIdException
     * @throw CantCreateTableException
     */
    public Database createDatabase() throws CantCreateDatabaseException, InvalidOwnerIdException, CantCreateTableException {


        // Method resources.
        Database database = null;


        // 1) Create the new Database.
        database = this.doCreateDatabase();


        // 2) Create the wallet table.
        this.doCreateWalletTable(database);


        // 3) Create the item table.
        this.doCreateItemTable(database);


        // 4) Create the depot table.
        this.doCreateDepotTable(database);


        // 5) Create the depot table.
        this.doCreateItemByDepotTable(database);


        // Return the database.
        return database;
    }
}