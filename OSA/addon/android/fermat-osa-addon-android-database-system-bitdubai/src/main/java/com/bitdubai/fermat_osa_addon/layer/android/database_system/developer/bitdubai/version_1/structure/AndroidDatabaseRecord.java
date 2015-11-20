package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.enums.WalletFactoryProjectState;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Natalia on 09/02/2015.
 */

/**
 * This class define methods to get and set database table record values.
 *
 * *
 */

public class AndroidDatabaseRecord implements DatabaseTableRecord {

    /**
     * DatabaseTableRecord Interface member variables.
     */
     private List<DatabaseRecord> values;

    private List<DatabaseVariable> variables;

    public AndroidDatabaseRecord (){

    }

    /**
     * DatabaseTableRecord interface implementation.
     */


    /**
     * <p>Get field record value in string object
     *
     * @param columnName name of the column to which is assigned the valuee
     * @return String value object
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

    /**
     * <p>Get field record value in UUID object
     * @param columnName column name to get back
     * @return UUID value object
     */
    @Override
    public UUID getUUIDValue(String columnName) {
        try {
            for (int i = 0; i < values.size(); i++) {
                if (values.get(i).getName().equals(columnName)) {
                    return UUID.fromString(values.get(i).getValue());
                }
            }
        }catch (NullPointerException nullPointerException){
            return null;
        }
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    /**
     * <p>Get field record value in long object
     * @param columnName column name to get back
     * @return long object
     */
    @Override
    public long getLongValue(String columnName) {

        for (int i = 0; i < values.size(); i++) {
            if(values.get(i).getName().equals(columnName)){
                return Long.parseLong(values.get(i).getValue());
            }
        }
        return 0;
    }

    /**
     * <p>Get field record value in Integer object
     * @param columnName column name to get back
     * @return Integer object
     */
    @Override
    public Integer getIntegerValue(String columnName){
        for (int i = 0; i < values.size(); i++) {
            if(values.get(i).getName().equals(columnName)){
                return Integer.valueOf(values.get(i).getValue());
            }
        }
        return 0;
    }

    /**
     * <p>Get field record value in float object
     * @param columnName column name to get back
     * @return float object
     */
    @Override
    public float getFloatValue(String columnName){
        for (int i = 0; i < values.size(); i++) {
            if(values.get(i).getName().equals(columnName)){
                return Float.parseFloat(values.get(i).getValue());
            }
        }
        return 0;
    }

    /**
     * <p>Get field record value in Double object
     * @param columnName column name to get back
     * @return Double object
     */
    @Override
    public double getDoubleValue(String columnName){
        for (int i = 0; i < values.size(); i++) {
            if(values.get(i).getName().equals(columnName)){
                return Double.parseDouble(values.get(i).getValue());
            }
        }
        return 0;
    }

    @Override
    public String getVariableName(String columnName){
        for (int i = 0; i < variables.size(); i++) {
            if(variables.get(i).getName().equals(columnName)){
                return variables.get(i).getValue();
            }
        }
        return "";
    }

    /**
     * <p>Set String field record value
     * @param columnName name of the column to which is assigned the value
     * @param value column value in string
     */
    @Override
    public void setStringValue(String columnName, String value) {

        if(values == null)
            values = new ArrayList<DatabaseRecord>();

        DatabaseRecord record = new AndroidRecord();

        record.setName(columnName);
        record.setValue(value);

         // Set the field as modified to take in method after update
        record.setChange(true);
        values.add(record);
    }

    /**
     * <p>Set UUID  field record value
     * @param columnName name of the column to which is assigned the value
     * @param value column value in UUID
     */
    @Override
    public void setUUIDValue(String columnName, UUID value) {

        if(values == null)
            values = new ArrayList<DatabaseRecord>();

        DatabaseRecord record = new AndroidRecord();

        record.setName(columnName);
        record.setValue(value.toString());
        // Set the field as modified to take in method after update
        record.setChange(true);
        values.add(record);
    }

    /**
     * <p>Set Long field record value
     * @param columnName name of the column to which is assigned the value
     * @param value column value in long
     */

    @Override
    public void setLongValue(String columnName, long value) {
        if(values == null)
            values = new ArrayList<DatabaseRecord>();

        DatabaseRecord record = new AndroidRecord();

        record.setName(columnName);
        record.setValue(String.valueOf(value));
        // Set the field as modified to take in method after update
        record.setChange(true);
        values.add(record);

    }


    /**
     * <p>Set Integer field record value
     * @param columnName name of the column to which is assigned the value
     * @param value column value in integer
     */
    @Override
    public void setIntegerValue(String columnName, Integer value) {
        if(values == null)
            values = new ArrayList<DatabaseRecord>();

        DatabaseRecord record = new AndroidRecord();

        record.setName(columnName);
        record.setValue(String.valueOf(value));
        // Set the field as modified to take in method after update
        record.setChange(true);
        values.add(record);

    }

    /**
     * <p>Set Float field record value
     * @param columnName name of the column to which is assigned the value
     * @param value
     */
    @Override
     public void setFloatValue(String columnName, float value){

        if(values == null)
            values = new ArrayList<DatabaseRecord>();

        DatabaseRecord record = new AndroidRecord();

        record.setName(columnName);
        record.setValue(Float.toString(value));
        record.setChange(true);
        values.add(record);
    }

    /**
     * <p>Set Double  field record value
     * @param columnName name of the column to which is assigned the value
     * @param value column value in double
     */
    @Override
    public void setDoubleValue(String columnName, double value){

        if(values == null)
            values = new ArrayList<DatabaseRecord>();

        DatabaseRecord record = new AndroidRecord();

        record.setName(columnName);
        record.setValue(Double.toString(value));
        record.setChange(true);
        values.add(record);
    }

    @Override
    public void setVariableValue (String columnName, String variableName){
        if(values == null)
            values = new ArrayList<DatabaseRecord>();

        DatabaseRecord record = new AndroidRecord();

        record.setName(columnName);
        record.setValue(variableName);
        record.setChange(true);
        record.setUseValueofVariable(true);
        values.add(record);
    }

    @Override
    public void setSelectField (String columnName){
        if(values == null)
            values = new ArrayList<DatabaseRecord>();

        DatabaseRecord record = new AndroidRecord();

        record.setName(columnName);
        values.add(record);
    }

    @Override
    public void setStateValue(String columnName, WalletFactoryProjectState state) {
        if(values == null)
            values = new ArrayList<DatabaseRecord>();

        DatabaseRecord record = new AndroidRecord();

        record.setName(columnName);
        record.setValue(state.toString());
        // Set the field as modified to take in method after update
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

    @Override
    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        for(DatabaseRecord record : values)
            buffer.append(" " + record.toString() + ",");
        buffer.append("]");
        return buffer.toString();
    }

}
