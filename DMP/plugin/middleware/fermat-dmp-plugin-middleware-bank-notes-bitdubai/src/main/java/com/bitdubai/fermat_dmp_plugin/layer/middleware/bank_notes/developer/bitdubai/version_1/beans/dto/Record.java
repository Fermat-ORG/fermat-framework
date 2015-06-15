/*
 * @(#Record.java 05/16/2015
 * Copyright 2015 bitDubai, Inc. All rights reserved.
 * BITDUBAI/CONFIDENTIAL
 * */

package com.bitdubai.fermat_dmp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.beans.dto;


// Packages and classes to import of bitDubai API.
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;


/**
 *
 *  <p>The class <code>com.bitdubai.fermat_dmp_plugin.layer._14_middleware.bank_notes.developer.bitdubai.version_1.beans.dto.Record</code> is a POJOs object for
 *     management the record database objects.
 *
 *
 *  @author  Raul Geomar Pena (raul.pena@mac.com)
 *  @version 1.0.0
 *  @since   jdk 1.7
 *  @since   05/16/2015
 * */
public class Record {


    // Private instance fields.
    /* Table object. */
    private DatabaseTable table = null;

    /* Record object. */
    private DatabaseTableRecord record = null;


    // Public constructor declaration.
    /**
     *
     *  <p>Constructor declaration with arguments.
     *
     * */
    public Record() {

        // Call to super class.
        super ();
    }

    /**
     *
     *  <p>Constructor declaration with arguments.
     *
     *  @param table Table object.
     *  @param record Record object.
     * */
    public Record (DatabaseTable table, DatabaseTableRecord record) {

        // Call to super class.
        super ();

        // Set the internal values.
        this.table = table;
        this.record = record;
    }


    // Public instance method declarations.
    /**
     *
     *  <p>Method that return the table object.
     *
     *  @return The table object.
     * */
    public DatabaseTable getTable () {

        // Return the value.
        return this.table;
    }

    /**
     *
     *  <p>Method that set the table object.
     *
     *  @param table The table object.
     * */
    public void setTable (DatabaseTable table) {

        // Set the value.
        this.table = table;
    }

    /**
     *
     *  <p>Method that return the record object.
     *
     *  @return The record object.
     * */
    public DatabaseTableRecord getRecord () {

        // Return the value.
        return this.record;
    }

    /**
     *
     *  <p>Method that set the record object.
     *
     *  @param record The record object.
     * */
    public void setWallet (DatabaseTableRecord record) {

        // Set the value.
        this.record = record;
    }
}