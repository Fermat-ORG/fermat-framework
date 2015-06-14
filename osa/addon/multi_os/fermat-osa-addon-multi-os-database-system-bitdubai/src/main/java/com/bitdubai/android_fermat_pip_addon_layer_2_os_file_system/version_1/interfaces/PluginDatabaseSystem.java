package com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.interfaces;

/**
 * Created by ciencias on 20.01.15.
 */

import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantOpenDatabaseException;

import java.util.UUID;

/**
 * The Database System knows which databases are already accessible on this device.
 */
public interface PluginDatabaseSystem {

    public Database openDatabase(UUID ownerId, String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException;

    public Database deleteDatabase(UUID ownerId, String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException;

    public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException;

    void setContext(Object context);

}
