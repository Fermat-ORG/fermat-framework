package com.bitdubai.fermat_api.layer._2_os.database_system;

import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantOpenDatabaseException;

import java.util.UUID;

/**
 * Created by ciencias on 3/18/15.
 */
public interface PlatformDatabaseSystem {

    public Database openDatabase(String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException;

    public Database createDatabase (String databaseName) throws CantCreateDatabaseException;

    void setContext (Object context);
}
