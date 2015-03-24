package com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.database_system;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableRecord;

import java.util.List;
import java.util.UUID;

/**
 * Created by toshiba on 09/02/2015.
 */
public class AndroidDatabaseRecord implements DatabaseTableRecord {

// TODO NATALIA: 24 MAR 2015
  
    @Override
    public String getStringValue(String columnName) {
        return null;
    }

    @Override
    public UUID getUUIDValue(String columnName) {
       // UUID.fromString(record.getValue(FIAT_ACCOUNTS_TABLE_ID_COLUMN_NAME));
        return null;
    }

    @Override
    public long getlongValue(String columnName) {
        //(Long.valueOf(record.getValue(FIAT_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME)).longValue());
        return 0;
    }

    @Override
    public void setStringValue(String columnName, String value) {

        // MUY IMPORTANTE: se deben marcar como modificados los campos a los que se les haga un set value ya que solo
        // esos campos deben ser actualizados luego en la base de datos cuando el objeto table reciba este objeto
        // para un update record.

    }

    @Override
    public void setUUIDValue(String columnName, UUID value) {

    }

    @Override
    public void setlongValue(String columnName, long value) {

    }


}
