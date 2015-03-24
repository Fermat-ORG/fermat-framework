package com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.database_system;
import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.Wallet;
import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.enums.Wallets;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_core.layer._12_middleware.app_runtime.developer.bitdubai.version_1.structure.RuntimeWallet;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Natalia on 09/02/2015.
 */
public class AndroidDatabaseRecord implements DatabaseTableRecord {

// TODO NATALIA: 24 MAR 2015

    private Map<String,String> values;
    @Override
    public String getStringValue(String columnName) {

        Iterator<Map.Entry<String, String>> evalue = this.values.entrySet().iterator();

        while (evalue.hasNext()) {
            Map.Entry<String, String> valueEntry = evalue.next();
            String recordValue = (String) valueEntry.getValue();
            if(recordValue.equals(columnName)){
                return recordValue;
            }
        }

        return "";
    }

    @Override
    public UUID getUUIDValue(String columnName) {
        Iterator<Map.Entry<String, String>> evalue = this.values.entrySet().iterator();

        while (evalue.hasNext()) {
            Map.Entry<String, String> valueEntry = evalue.next();
            String recordValue = (String) valueEntry.getValue();
            if(recordValue.equals(columnName)){
                return UUID.fromString(recordValue);
            }
        }

        return null;
    }

    @Override
    public long getlongValue(String columnName) {

        Iterator<Map.Entry<String, String>> evalue = this.values.entrySet().iterator();

        while (evalue.hasNext()) {
            Map.Entry<String, String> valueEntry = evalue.next();
            String recordValue = (String) valueEntry.getValue();
            if(recordValue.equals(columnName)){
                return Long.valueOf(recordValue);
            }
        }

        return 0;
    }

    @Override
    public void setStringValue(String columnName, String value) {

        // MUY IMPORTANTE: se deben marcar como modificados los campos a los que se les haga un set value ya que solo
        // esos campos deben ser actualizados luego en la base de datos cuando el objeto table reciba este objeto
        // para un update record.
        if(values == null)
            values = new HashMap<String, String>();
        values.put(columnName,value);
    }

    @Override
    public void setUUIDValue(String columnName, UUID value) {

        if(values == null)
            values = new HashMap<String, String>();
        values.put(columnName,value.toString());
    }

    @Override
    public void setlongValue(String columnName, long value) {
        if(values == null)
            values = new HashMap<String, String>();
        values.put(columnName,String.valueOf(value));
    }

    @Override
    public Map<String,String> getValues(){
        return this.values;
    }

    @Override
    public void setValues(Map<String,String> values ){
        this.values = values;
    }

}
