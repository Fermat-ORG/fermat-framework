package com.bitdubai.fermat_api.layer.osa_android.database_system;


import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

import java.util.List;
import java.util.UUID;

/**
 * <p>The abstract class <code>com.bitdubai.fermat_api.layer.osa_android.database_syste.DatabaseTableRecord</code> is a interface
 * that define the methods to get and set database table record values.
 *
 * @author Luis
 * @version 1.0.0
 * @since 01/02/2015.
 */

public interface DatabaseTableRecord {

    String getStringValue(String columnName);

    UUID getUUIDValue(String columnName);

    long getLongValue(String columnName);

    Integer getIntegerValue(String columnName);

    float getFloatValue(String columnName);

    double getDoubleValue(String columnName);

    void setStringValue(String columnName, String value);

    void setUUIDValue(String columnName, UUID value);

    void setLongValue(String columnName, long value);

    void setIntegerValue(String columnName, Integer value);

    void setFloatValue(String columnName, float value);

    void setDoubleValue(String columnName, double value);

    void setFermatEnum(String columnName, FermatEnum state);

    List<DatabaseRecord> getValues();

}
