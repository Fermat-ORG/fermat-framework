package com.bitdubai.fermat_api.layer.osa_android.database_system;

/**
 *
 *  <p>The abstract class <code>DatabaseTableColumn</code> is a interface
 *     that define the methods to get and set the properties of the columns in a table in the database.
 *
 *
 *  @author  Luis
 *  @version 1.0.0
 *  @since   01/02/15.
 * */
public interface DatabaseTableColumn {
    
    public String getName ();
    
    public void setName (String name);

    public DatabaseDataType getType ();
    
    public void setType(DatabaseDataType type);

    public void setDataTypeSize (int dataTypeSize);

    public int getDataTypeSize ();

    public void setPrimaryKey (boolean primaryKey);

    public boolean getPrimaryKey ();
    
}
