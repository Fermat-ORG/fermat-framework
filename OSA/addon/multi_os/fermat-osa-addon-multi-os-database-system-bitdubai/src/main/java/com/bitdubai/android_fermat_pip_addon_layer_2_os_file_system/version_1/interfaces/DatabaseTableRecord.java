package com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.interfaces;


import java.util.UUID;

/**
 * Created by ciencias on 01.02.15.
 */
public interface DatabaseTableRecord {

    public String getStringValue(String columnName);

    public UUID getUUIDValue(String columnName);

    public long getLongValue(String columnName);

    public Integer getIntegerValue(String columnName);

    public float getFloatValue(String columnName);

    public double getDoubleValue(String columnName);

    public void setStringValue(String columnName, String value);

    public void setUUIDValue(String columnName, UUID value);

    public void setLongValue(String columnName, long value);

    public void setIntegerValue(String columnName, Integer value);

    public void setFloatValue(String columnName, float value);

    public void setDoubleValue(String columnName, double value);


    //public List<DatabaseRecord> getValues();

    //public void setValues( List<DatabaseRecord> values );
}
