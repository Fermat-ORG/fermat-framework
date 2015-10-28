/*
 * @(#DatabaseTemplate.java 05/16/2015
 * Copyright 2015 bitDubai, Inc. All rights reserved.
 * BITDUBAI/CONFIDENTIAL
 * */

package com.bitdubai.fermat_dmp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.structure.dao;


// Packages and classes to import of jdk 1.7

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.exceptions.FailCreateObjectException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.exceptions.FailUpdateObjectException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.interfaces.dao.DatabaseCallback;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.UUID;

import static com.bitdubai.fermat_api.layer.all_definition.enums.Plugins.BITDUBAI_BANK_NOTES_MIDDLEWARE;
import static com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN;

// Packages and classes to import of bitDubai API.
// Packages and classes to import of Middleware Bank Notes API.


/**
 *
 *  <p>The class <code>com.bitdubai.fermat_dmp_plugin.layer._14_middleware.bank_notes.developer.bitdubai.version_1.structure.dao.DatabaseTemplate</code> is a template object
 *     for management the database operations.
 *
 *
 *  @author  Raul Geomar Pena (raul.pena@mac.com)
 *  @version 1.0.0
 *  @since   jdk 1.7
 *  @since   05/16/2015
 *  @see     {@link DealsWithErrors}
 *  @see     {@link com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem}
 * */
public class DatabaseTemplate implements DealsWithErrors, DealsWithPluginDatabaseSystem {


    // Private instances fields declarations.
    /* Plugin Database. */
    private PluginDatabaseSystem pluginDatabaseSystem = null;

    /* Database object. */
    private Database database = null;

    /* Error manager. */
    private ErrorManager errorManager = null;


    // Public constructor declarations.
    /**
      *
      *  <p>Constructor without arguments.
      * */
    public DatabaseTemplate () {

        // Call to super class.
        super ();
    }

    /**
     *
     *  <p>Constructor with arguments.
     *
     *  @param pluginDatabaseSystem Plugin database.
     *  @param database Database object.
     *  @param errorManager Error manager.
     * */
    public DatabaseTemplate (PluginDatabaseSystem pluginDatabaseSystem, Database database, ErrorManager errorManager) {

        // Call to super class.
        super ();

        // Set the internal values.
        this.database     = database;
        this.errorManager = errorManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }


    // Protected instance methods declarations.
    /*
     * <p>Method that execute a create operation.
     *
     * <p>This method cannot be inherited.
     *
     * @param target
     */
    protected final void doCreate (DatabaseCallback callBack) {


        // Valid the arguments.
        if (callBack == null) {
        /*
         * Cancel the operation.
         * */
            return;
        }


            /*
             *  Execute the operation.
             * */
        try {

            // 1) Create new transaction.
            DatabaseTransaction tx = this.database.newTransaction ();

            // 2) Get the record.
           // Record record = callBack.doExecute (this.database);

            // 3) Insert the new value.
            //tx.addRecordToInsert (record.getTable (), record.getRecord ());
            this.database.executeTransaction (tx);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {
            /*
             * Catch the failures.
             * */

            // Register the failure.
            errorManager.reportUnexpectedPluginException (BITDUBAI_BANK_NOTES_MIDDLEWARE,
                    DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    databaseTransactionFailedException);

            // Return the failure
            throw new FailCreateObjectException ();
        }
    }

    /*
     * <p>Method that execute a update operation.
     *
     * <p>This method cannot be inherited.
     *
     * @param target
     */
    protected final void doUpdate (DatabaseCallback callBack) {


        // Valid the arguments.
        if (callBack == null) {
        /*
         * Cancel the operation.
         * */
            return;
        }


            /*
             *  Execute the operation.
             * */
        try {

            // 1) Create new transaction.
            DatabaseTransaction tx = this.database.newTransaction ();

            // 2) Get the record.
            //Record record = callBack.doExecute (this.database);

            // 3) Update the value.
            //tx.addRecordToUpdate (record.getTable(), record.getRecord());
            this.database.executeTransaction (tx);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {
            /*
             * Catch the failures.
             * */

            // Register the failure.
            errorManager.reportUnexpectedPluginException (BITDUBAI_BANK_NOTES_MIDDLEWARE,
                    DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    databaseTransactionFailedException);

            // Return the failure
            throw new FailUpdateObjectException ();
        }
    }

    /*
     * <p>Method that initialize the resources.
     *
     * <p>This method cannot be inherited.
     *
     * @param pluginId Plugin id.
     */
    protected final void Initialize(UUID pluginId) {

    }


    // Public instance methods declarations extends of com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem interface.
    /**
     *
     *  <p>Method that set the plugin database.
     *
     *  @param pluginDatabaseSystem Error manager.
     * */
    @Override
    public void setPluginDatabaseSystem (PluginDatabaseSystem pluginDatabaseSystem) {

        // Set the values.
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }


    // Public instance methods declarations extends of com.bitdubai.fermat_api.layer.platform_service.error_manager.DealsWithErrors interface.
    /**
     *
     *  <p>Method that set the error manager.
     *
     *  @param errorManager Error manager.
     * */
    @Override
    public void setErrorManager (ErrorManager errorManager) {

        // Set the value.
        this.errorManager = errorManager;
    }


    // Public instance methods declarations. Get/Set
    /**
     *
     *  <p>Method that set the Database object.
     *
     *  @param database Database object
     * */
    public void setDatabase (Database database) {

        // Set the value.
        this.database = database;
    }


    // Public instance methods declarations extends of java.lang.Object.
	/* (non-Javadoc)
	 * @see java.lang.Object#toString ()
	 */
    @Override
    public String toString () {

        // Custom implementations.
        return "Database Manager";
    }
}