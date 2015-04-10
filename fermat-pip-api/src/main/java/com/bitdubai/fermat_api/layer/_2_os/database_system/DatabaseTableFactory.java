package com.bitdubai.fermat_api.layer._2_os.database_system;

import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseDataType;

import java.util.ArrayList;

/**
 * Created by ciencias on 3/23/15.
 */
public interface DatabaseTableFactory {

    public void addIndex(String index);

    public String getIndex();

    public  String getTableName ();

    public void addColumn (String columnName, DatabaseDataType dataType, int dataTypeSize);

    public ArrayList<DatabaseTableColumn> getColumns ();
}
