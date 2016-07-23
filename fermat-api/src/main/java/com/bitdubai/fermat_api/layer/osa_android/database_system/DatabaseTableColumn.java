package com.bitdubai.fermat_api.layer.osa_android.database_system;

/**
 * <p>The abstract class <code>DatabaseTableColumn</code> is a interface
 * that define the methods to get and set the properties of the columns in a table in the database.
 * <p/>
 * Created by Luis Molina on 01/02/15.
 * Modified by Leon Acosta - (laion.cj91@gmail.com) on 03/02/2016.
 *
 * @author Luis
 * @version 1.0.0
 * @since 01/02/15.
 */
public interface DatabaseTableColumn {

    String getName();

    DatabaseDataType getDataType();

    int getDataTypeSize();

    boolean isPrimaryKey();

}
