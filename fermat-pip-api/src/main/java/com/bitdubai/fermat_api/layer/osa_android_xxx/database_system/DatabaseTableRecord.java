package com.bitdubai.fermat_api.layer.osa_android_xxx.database_system;


import java.util.List;
import java.util.UUID;

/**
 *
 *  <p>The abstract class <code>com.bitdubai.fermat_api.layer._2_os.database_syste.DatabaseTableRecord</code> is a interface
 *     that define the methods to get and set database table record values.
 *
 *
 *  @author  Luis
 *  @version 1.0.0
 *  @since   01/02/2015.
 * */

 public interface DatabaseTableRecord {
    
    public String getStringValue(String columnName);
    
    public UUID getUUIDValue(String columnName);
    
    public long getLongValue(String columnName);

    public Integer getIntegerValue(String columnName);

    public float getFloatValue(String columnName);

    public double getDoubleValue(String columnName);
    
    public void setStringValue (String columnName, String value);

    public void setUUIDValue (String columnName, UUID value);

    public void setLongValue(String columnName, long value);

    public void setIntegerValue(String columnName, Integer value);

    public void setFloatValue(String columnName, float value);

    public void setDoubleValue(String columnName, double value);

    public List<DatabaseRecord> getValues();

    public void setValues( List<DatabaseRecord> values );
}
