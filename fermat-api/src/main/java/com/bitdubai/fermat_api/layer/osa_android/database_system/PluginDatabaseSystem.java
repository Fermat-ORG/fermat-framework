package com.bitdubai.fermat_api.layer.osa_android.database_system;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;

import java.util.UUID;

/**
 * <p>The abstract class <code>PluginDatabaseSystem</code> is a interface
 * that define the methods to manage database system.
 * The Database System knows which databases are already accessible on this device.
 *
 * @author Luis
 * @version 1.0.0
 * @since 18/01/15.
 */

public interface PluginDatabaseSystem extends FermatManager {

    Database openDatabase(UUID ownerId, String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException;

    void deleteDatabase(UUID ownerId, String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException;

    Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException;

}
