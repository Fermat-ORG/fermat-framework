/*
 * @(#DepotDaoSupport.java 05/14/2015
 * Copyright 2015 bitDubai, Inc. All rights reserved.
 * BITDUBAI/CONFIDENTIAL
 * */

package com.bitdubai.fermat_dmp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.structure.dao;


// Packages and classes to import of jdk 1.7

import java.util.List;

// Packages and classes to import of bitDubai API.
import com.bitdubai.fermat_api.layer.osa_android.database_system.*;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

// Packages and classes to import of Middleware Bank Notes API.
import com.bitdubai.fermat_dmp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.beans.dto.Depot;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.beans.dto.Record;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.interfaces.dao.DepotDao;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.interfaces.dao.DatabaseCallback;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.structure.BankNotesDatabaseConstants;


/**
 * <p>The class <code>com.bitdubai.fermat_dmp_plugin.layer._14_middleware.bank_notes.developer.bitdubai.version_1.structure.dao.DepotDaoSupport</code> is a object
 * that implements the methods for management the CRUD operations for the Depot DTO.
 *
 * @author Raul Geomar Pena (raul.pena@mac.com)
 * @version 1.0.0
 * @see {@link com.bitdubai.fermat_dmp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.structure.dao.DatabaseTemplate}
 * @see {@link com.bitdubai.fermat_dmp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.interfaces.dao.DepotDao}
 * @see {@link com.bitdubai.fermat_dmp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.beans.dto.Depot}
 * @since 05/14/2015
 */
public class DepotDaoSupport extends DatabaseTemplate implements DepotDao<Long, Depot> {


    // Public constructor declarations.

    /**
     * <p>Constructor without arguments.
     */
    public DepotDaoSupport() {

        // Call to super class.
        super();
    }

    /**
     * <p>Constructor with arguments.
     *
     * @param pluginDatabaseSystem Plugin database.
     * @param database             Database object.
     * @param errorManager         Error manager.
     */
    public DepotDaoSupport(PluginDatabaseSystem pluginDatabaseSystem, Database database, ErrorManager errorManager) {

        // Call to super class.
        super(pluginDatabaseSystem, database, errorManager);
    }


    // Public instance methods declarations extends of com.bitdubai.fermat_dmp_plugin.layer._14_middleware.bank_notes.developer.bitdubai.version_1.interfaces.dao.DepotDao

    /**
     * <p>Method that find an object by id.
     *
     * @param id Object id.
     * @return Object found.
     */
    @Override
    public Depot get(Long id) {
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    /**
     * <p>Method that list the all objects.
     *
     * @return All objects.
     */
    @Override
    public List<Depot> findAll() {
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    /**
     * <p>Method that create a new object.
     *
     * @param target
     */
    @Override
    public void create(final Depot target) {


        // Valid the arguments.
        if (target == null) {
        /*
         * Cancel the operation.
         * */
            return;
        }


        // 1) Create the callback for save the entity.
        DatabaseCallback call = new DatabaseCallback() {

            public Record doExecute(Database database) {

                // 1) Get the wallet table.
                DatabaseTable table = database.getTable(BankNotesDatabaseConstants.DEPOT_TABLE_NAME);

                // 2) Create a new entity object.
                DatabaseTableRecord record = table.getEmptyRecord();

                // 3) Set entity values.
                record.setLongValue(BankNotesDatabaseConstants.DEPOT_TABLE_COLUMN_ID, target.getId());
                record.setStringValue(BankNotesDatabaseConstants.DEPOT_TABLE_COLUMN_NAME, target.getName());
                record.setStringValue(BankNotesDatabaseConstants.DEPOT_TABLE_COLUMN_DESCRIPTION, target.getDescription());
                record.setLongValue(BankNotesDatabaseConstants.DEPOT_TABLE_COLUMN_CREATION, target.getCreation());
                record.setLongValue(BankNotesDatabaseConstants.DEPOT_TABLE_COLUMN_UPDATE, target.getUpdate());

                // Return the new record entity.
                return new Record(table, record);
            }
        };


        // 2) Create the new entity.
        this.doCreate(call);
    }

    /**
     * <p>Method that update an object.
     *
     * @param target
     */
    @Override
    public void update(final Depot target) {


        // Valid the arguments.
        if (target == null) {
        /*
         * Cancel the operation.
         * */
            return;
        }


        // 1) Create the callback for save the entity.
        DatabaseCallback call = new DatabaseCallback() {

            public Record doExecute(Database database) {

                // 1) Get the wallet table.
                DatabaseTable table = database.getTable(BankNotesDatabaseConstants.DEPOT_TABLE_NAME);

                // 2) Create a new entity object.
                DatabaseTableRecord record = table.getEmptyRecord();

                // 3) Set entity values.
                record.setLongValue(BankNotesDatabaseConstants.DEPOT_TABLE_COLUMN_ID, target.getId());
                record.setStringValue(BankNotesDatabaseConstants.DEPOT_TABLE_COLUMN_NAME, target.getName());
                record.setStringValue(BankNotesDatabaseConstants.DEPOT_TABLE_COLUMN_DESCRIPTION, target.getDescription());
                record.setLongValue(BankNotesDatabaseConstants.DEPOT_TABLE_COLUMN_CREATION, target.getCreation());
                record.setLongValue(BankNotesDatabaseConstants.DEPOT_TABLE_COLUMN_UPDATE, target.getUpdate());

                // Return the new record entity.
                return new Record(table, record);
            }
        };


        // 2) Update entity.
        this.doUpdate(call);
    }

    /**
     * <p>Method that delete an object.
     *
     * @param id Object id.
     */
    @Override
    public void delete(Long id) {

    }
}