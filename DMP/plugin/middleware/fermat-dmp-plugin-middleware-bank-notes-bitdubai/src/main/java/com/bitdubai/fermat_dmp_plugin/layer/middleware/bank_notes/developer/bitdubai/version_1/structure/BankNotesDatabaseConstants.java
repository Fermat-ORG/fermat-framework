/*
 * @(#BankNotesDatabaseConstants.java 05/10/2015
 * Copyright 2015 bitDubai, Inc. All rights reserved.
 * BITDUBAI/CONFIDENTIAL
 * */

package com.bitdubai.fermat_dmp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.structure;


// Packages and classes to import of jdk 1.7
import java.util.UUID;


/**
 *
 *  <p>The class <code>com.bitdubai.fermat_dmp_plugin.layer._14_middleware.bank_notes.developer.bitdubai.version_1.structure.BankNotesDatabaseConstants</code> is a object
 *     that define the database resources.
 *
 *  <p>This class is private for don't extends new classes.
 *
 *
 *  @author  Raul Geomar Pena (raul.pena@mac.com)
 *  @version 1.0.0
 *  @since   jdk 1.7
 *  @since   05/10/2015
 * */
public final class BankNotesDatabaseConstants {


    // Public static fields declarations.
    /*
    *
    *  Database resources.
    * */

    /** Constant for Database name. */
    public static final  String DATABASE_NAME = "";

    /** Constant for Database owner. */
    public static final UUID   DATABASE_OWNER = UUID.fromString ("067e6162-3b6f-4ae2-a171-2470b63dff00");


    /*
     *
     *  REQUESTED Table resources.
     * */

    /** Constant for wallet table name. */
    public static final String WALLET_TABLE_NAME = "wallet";

    /** Constant for wallet table PK id. */
    public static final String WALLET_TABLE_COLUMN_ID   = "_id";

    /** Constant for wallet table column name. */
    public static final String WALLET_TABLE_COLUMN_NAME = "name";

    /** Constant for wallet table column description. */
    public static final String WALLET_TABLE_COLUMN_DESCRIPTION = "description";

    /** Constant for wallet table column creation. */
    public static final String WALLET_TABLE_COLUMN_CREATION    = "creation";

    /** Constant for wallet table column update. */
    public static final String WALLET_TABLE_COLUMN_UPDATE      = "update";



    /*
     *
     *  Item Table resources.
     * */

    /** Constant for item table name. */
    public static final String ITEM_TABLE_NAME = "item";

    /** Constant for item table PK id. */
    public static final String ITEM_TABLE_COLUMN_ID   = "_id";

    /** Constant for item table column name. */
    public static final String ITEM_TABLE_COLUMN_NAME = "name";

    /** Constant for item table column description.. */
    public static final String ITEM_TABLE_COLUMN_DESCRIPTION = "description";

    /** Constant for item table column creation. */
    public static final String ITEM_TABLE_COLUMN_CREATION    = "creation";

    /** Constant for item table column update. */
    public static final String ITEM_TABLE_COLUMN_UPDATE      = "update";



    /*
     *
     *  Depot Table resources.
     * */

    /** Constant for depot table name. */
    public static final String DEPOT_TABLE_NAME = "depot";

    /** Constant for depot table PK id. */
    public static final String DEPOT_TABLE_COLUMN_ID   = "_id";

    /** Constant for depot table column name. */
    public static final String DEPOT_TABLE_COLUMN_NAME = "name";

    /** Constant for depot table column description.. */
    public static final String DEPOT_TABLE_COLUMN_DESCRIPTION = "description";

    /** Constant for depot table column creation. */
    public static final String DEPOT_TABLE_COLUMN_CREATION    = "creation";

    /** Constant for depot table column update. */
    public static final String DEPOT_TABLE_COLUMN_UPDATE      = "update";



    /*
     *
     *  Item By Depot Table resources.
     * */

    /** Constant for item by depot table name. */
    public static final String ITEM_BY_DEPOT_TABLE_NAME = "item_by_depot";

    /** Constant for item by depot table PK id. */
    public static final String ITEM_BY_DEPOT_TABLE_COLUMN_ID   = "_id";

    /** Constant for item by depot table FK id to item table. */
    public static final String ITEM_BY_DEPOT_TABLE_COLUMN_ITEM   = "_id_item";

    /** Constant for item by depot table FK id to depot table. */
    public static final String ITEM_BY_DEPOT_TABLE_COLUMN_DEPOT  = "_id_depot";

    /** Constant for item by depot table column creation. */
    public static final String ITEM_BY_DEPOT_TABLE_COLUMN_CREATION    = "creation";

    /** Constant for item by depot table column update. */
    public static final String ITEM_BY_DEPOT_TABLE_COLUMN_UPDATE      = "update";



    /*
     *
     *  Item By Account Table resources.
     * */

    /** Constant for item by account table name. */
    public static final String ITEM_BY_ACCOUNT_TABLE_NAME = "item_by_account";

    /** Constant for item by account table PK id. */
    public static final String ITEM_BY_ACCOUNT_TABLE_COLUMN_ID     = "_id";

    /** Constant for item by account table FK id to item table. */
    public static final String ITEM_BY_ACCOUNT_TABLE_COLUMN_ITEM   = "_id_item";

    /** Constant for item by depot table FK id to depot table. */
    public static final String ITEM_BY_ACCOUNT_TABLE_COLUMN_DEPOT       = "_id_depot";

    /** Constant for item by account table account id. */
    public static final String ITEM_BY_ACCOUNT_TABLE_COLUMN_ACCOUNT     = "_id_account";

    /** Constant for item by depot table column creation. */
    public static final String ITEM_BY_ACCOUNT_TABLE_COLUMN_CREATION    = "creation";

    /** Constant for item by depot table column update. */
    public static final String ITEM_BY_ACCOUNT_TABLE_COLUMN_UPDATE      = "update";


    // Private constructor declarations.
    /**
     *
     *  <p>Unique constructor declarations without parameters. This constructor is private for don't create instances.
     * */
    private BankNotesDatabaseConstants () {

        // Call to super class.
        super ();
    }
}