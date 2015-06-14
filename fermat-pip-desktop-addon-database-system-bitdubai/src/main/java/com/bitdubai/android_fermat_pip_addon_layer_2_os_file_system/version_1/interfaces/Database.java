package com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.interfaces;

import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.DatabaseTransactionFailedException;

/**
 * Created by ciencias on 22.01.15.
 */
public interface Database {

    public void executeQuery();

    public DatabaseTable getTable(String tableName);

    public DatabaseTransaction newTransaction();

    public void executeTransaction(DatabaseTransaction transaction) throws DatabaseTransactionFailedException;
}
