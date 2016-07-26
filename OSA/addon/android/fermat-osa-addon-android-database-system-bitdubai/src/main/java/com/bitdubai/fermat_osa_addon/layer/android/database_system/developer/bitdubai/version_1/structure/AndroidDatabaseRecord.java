package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This class define methods to get and set database table record values.
 * <p/>
 * Created by Natalia on 09/02/2015.
 * Modified by Leon Acosta (laion.cj91@gmail.com) on 03/02/2016.
 *
 * @author Natalia
 * @version 1.0.0
 * @since 09/02/2015.
 */
public class AndroidDatabaseRecord implements DatabaseTableRecord {

    /**
     * DatabaseTableRecord Interface member variables.
     */
    private Map<String, DatabaseRecord> values;

    public AndroidDatabaseRecord() {

        values = new LinkedHashMap<>();
    }

    /**
     * DatabaseTableRecord interface implementation.
     */
    @Override
    public String getStringValue(String columnName) {

        if (values.get(columnName) != null)
            return values.get(columnName).getValue();
        else
            return null;
    }

    @Override
    public UUID getUUIDValue(String columnName) {

        try {
            if (values.get(columnName) != null)
                return UUID.fromString(values.get(columnName).getValue());
            else
                return null;
        } catch (NullPointerException nullPointerException) {
            return null;
        }
    }

    @Override
    public long getLongValue(String columnName) {
        try {
            if (values.get(columnName).getValue() != null)
                return Long.parseLong(values.get(columnName).getValue());
            else
                return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Integer getIntegerValue(String columnName) {

        if (values.get(columnName) != null)
            return Integer.valueOf(values.get(columnName).getValue());
        else
            return 0;
    }

    /**
     * <p>Get field record value in float object
     *
     * @param columnName column name to get back
     * @return float object
     */
    @Override
    public float getFloatValue(String columnName) {

        if (values.get(columnName) != null)
            return Float.parseFloat(values.get(columnName).getValue());
        else
            return 0;
    }

    /**
     * <p>Get field record value in Double object
     *
     * @param columnName column name to get back
     * @return Double object
     */
    @Override
    public double getDoubleValue(String columnName) {
        try {
            if (values.get(columnName).getValue() != null)
                return Double.parseDouble(values.get(columnName).getValue());
            else
                return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * <p>Set String field record value
     *
     * @param columnName name of the column to which is assigned the value
     * @param value      column value in string
     */
    @Override
    public void setStringValue(String columnName, String value) {

        DatabaseRecord record = new AndroidRecord(columnName, value, true);

        values.put(columnName, record);
    }

    /**
     * <p>Set UUID  field record value
     *
     * @param columnName name of the column to which is assigned the value
     * @param value      column value in UUID
     */
    @Override
    public void setUUIDValue(String columnName, UUID value) {

        DatabaseRecord record = new AndroidRecord(columnName, value.toString(), true);

        values.put(columnName, record);
    }

    /**
     * <p>Set Long field record value
     *
     * @param columnName name of the column to which is assigned the value
     * @param value      column value in long
     */

    @Override
    public void setLongValue(String columnName, long value) {

        DatabaseRecord record = new AndroidRecord(columnName, String.valueOf(value), true);

        values.put(columnName, record);

    }


    /**
     * <p>Set Integer field record value
     *
     * @param columnName name of the column to which is assigned the value
     * @param value      column value in integer
     */
    @Override
    public void setIntegerValue(String columnName, Integer value) {

        DatabaseRecord record = new AndroidRecord(columnName, String.valueOf(value), true);

        values.put(columnName, record);

    }

    /**
     * <p>Set Float field record value
     *
     * @param columnName name of the column to which is assigned the value
     * @param value
     */
    @Override
    public void setFloatValue(String columnName, float value) {

        DatabaseRecord record = new AndroidRecord(columnName, Float.toString(value), true);

        values.put(columnName, record);
    }

    /**
     * <p>Set Double  field record value
     *
     * @param columnName name of the column to which is assigned the value
     * @param value      column value in double
     */
    @Override
    public void setDoubleValue(String columnName, double value) {

        DatabaseRecord record = new AndroidRecord(columnName, Double.toString(value), true);

        values.put(columnName, record);
    }

    @Override
    public void setFermatEnum(final String columnName,
                              final FermatEnum fermatEnum) {

        DatabaseRecord record = new AndroidRecord(columnName, fermatEnum.getCode(), true);

        values.put(columnName, record);
    }

    public void addValue(DatabaseRecord databaseRecord) {

        values.put(databaseRecord.getName(), databaseRecord);
    }

    @Override
    public List<DatabaseRecord> getValues() {

        return new ArrayList<>(this.values.values());
    }

    @Override
    public String toString() {

        StringBuilder buffer = new StringBuilder();

        buffer.append("[");
        for (Map.Entry<String, DatabaseRecord> record : values.entrySet()) {
            buffer.append(" ")
                    .append(record.getValue().toString())
                    .append(",");
        }
        buffer.append("]");
        return buffer.toString();
    }

}
