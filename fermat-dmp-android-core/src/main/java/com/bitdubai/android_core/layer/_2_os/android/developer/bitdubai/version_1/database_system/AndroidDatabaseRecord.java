package com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.database_system;

import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableRecord;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Natalia on 09/02/2015.
 */
public class AndroidDatabaseRecord implements DatabaseTableRecord {

    /**
     * DatabaseTableRecord Interface member variables.
     */
    private List<DatabaseRecord> values;


    /**
     * DatabaseTableRecord interface implementation.
     */


    @Override
    public String getStringValue(String columnName) {

        for (int i = 0; i < values.size(); i++) {
            if(values.get(i).getName().equals(columnName)){
                return values.get(i).getValue();
            }
        }
        return "";
    }

    @Override
    public UUID getUUIDValue(String columnName) {
        for (int i = 0; i < values.size(); i++) {
            if(values.get(i).getName().equals(columnName)){
                return UUID.fromString(values.get(i).getValue());
            }
        }

        return null;
    }

    @Override
    public long getlongValue(String columnName) {

        for (int i = 0; i < values.size(); i++) {
            if(values.get(i).getName().equals(columnName)){
                return Long.valueOf(values.get(i).getValue());
            }
        }
        return 0;
    }

    /**
     * Set the field as modified to take in method after update
     */
    @Override
    public void setStringValue(String columnName, String value) {

        if(values == null)
            values = new ArrayList<DatabaseRecord>();

        DatabaseRecord record = new AndroidRecord();

        record.setName(columnName);
        record.setValue(value);
        record.setChange(true);
        values.add(record);
    }

    @Override
    public void setUUIDValue(String columnName, UUID value) {

        if(values == null)
            values = new ArrayList<DatabaseRecord>();

        DatabaseRecord record = new AndroidRecord();

        record.setName(columnName);
        record.setValue(value.toString());
        record.setChange(true);
        values.add(record);
    }

    @Override
    public void setlongValue(String columnName, long value) {
        if(values == null)
            values = new ArrayList<DatabaseRecord>();

        DatabaseRecord record = new AndroidRecord();

        record.setName(columnName);
        record.setValue(String.valueOf(value));
        record.setChange(true);
        values.add(record);

    }

    @Override
    public List<DatabaseRecord> getValues(){
        return this.values;
    }

    @Override
    public void setValues( List<DatabaseRecord> values ){
        this.values = values;
    }



}
