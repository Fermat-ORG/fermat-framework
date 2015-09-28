package com.bitdubai.fermat_api.layer.osa_android.database_system;

import java.util.ArrayList;

/**
 *
 *  <p>The abstract class <code>DatabaseTableFactory</code> is a interface
 *     that define the methods to manage the structure of the table.
 *
 *
 *  @author  Luis
 *  @version 1.0.0
 *  @since  23/03/15.
 * */
public interface DatabaseTableFactory {

    public void addIndex(String index);

    public String getIndex();

    public  String getTableName ();

    public void addColumn (String columnName, DatabaseDataType dataType, int dataTypeSize, boolean primaryKey);

    public ArrayList<DatabaseTableColumn> getColumns ();
}
