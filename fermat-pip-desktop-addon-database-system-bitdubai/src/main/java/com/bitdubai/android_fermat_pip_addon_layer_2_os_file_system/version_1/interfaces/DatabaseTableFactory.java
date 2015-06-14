package com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.interfaces;

import java.util.ArrayList;

/**
 * Created by ciencias on 3/23/15.
 */
public interface DatabaseTableFactory {

    public void addIndex(String index);

    public String getIndex();

    public  String getTableName();

    public void addColumn (String columnName, DatabaseDataType dataType, int dataTypeSize, boolean primaryKey);

    public ArrayList<DatabaseTableColumn> getColumns();
}
