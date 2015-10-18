package com.bitdubai.fermat_api.layer.osa_android.database_system;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;

import java.io.Serializable;


/**
 * Created by ciencias on 3/18/15.
 */

/**
 *
 *  <p>The abstract class <code>PlatformDatabaseSystem</code> is a interface
 *     that define the methods to manage database system. Used from PlugIn
 *     The Database System knows which databases are already accessible on this device.
 *
 *
 *  @author  Luis
 *  @version 1.0.0
 *  @since   18/01/15.
 * */

 public interface PlatformDatabaseSystem extends Serializable {

    Database openDatabase(String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException;

    Database createDatabase (String databaseName) throws CantCreateDatabaseException;

    void deleteDatabase(String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException;

}
