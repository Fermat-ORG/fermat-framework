package com.bitdubai.fermat_api.layer._2_os.database_system;


import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by ciencias on 01.02.15.
 */
public interface DatabaseTableRecord {
    
    public String getStringValue(String columnName);
    
    public UUID getUUIDValue(String columnName);
    
    public long getlongValue(String columnName);
    
    public void setStringValue (String columnName, String value);

    public void setUUIDValue (String columnName, UUID value);

    public void setlongValue (String columnName, long value);

    public List<DatabaseRecord> getValues();

    public void setValues( List<DatabaseRecord> values );
}
