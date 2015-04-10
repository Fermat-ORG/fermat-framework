package com.bitdubai.fermat_api.layer._2_os.database_system;

/**
 * Created by ciencias on 01.02.15.
 */
public interface DatabaseTableColumn {
    
    public String getName ();
    
    public void setName (String name);

    public DatabaseDataType getType ();
    
    public void setType(DatabaseDataType type);

    public void setDataTypeSize (int dataTypeSize);

    public int getDataTypeSize ();

    
}
