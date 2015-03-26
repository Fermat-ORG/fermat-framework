package com.bitdubai.fermat_api.layer._2_os.database_system;

/**
 * Created by ciencias on 01.02.15.
 */
public interface DatabaseTableFilter {
    
    
    public void setColumn (String column);

    public void setType (DatabaseFilterType type);

    public void setValue (String value);

    public String  getColumn ();

    public String getValue ();

    public DatabaseFilterType getType ();
    
}
