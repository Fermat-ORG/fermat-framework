package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableColumn;

/**
 * This class define methods to get and set the properties of the columns in a table in the database.
 * <p/>
 * Created by Natalia on 10/02/2015.
 * Modified by Leon Acosta - (laion.cj91@gmail.com) on 03/02/2016.
 *
 * @author Natalia
 * @version 1.0.0
 * @since 10/02/15.
 */
public class AndroidDatabaseTableColumn implements DatabaseTableColumn {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 4019;
    private static final int HASH_PRIME_NUMBER_ADD = 877;

    private final String name;
    private final DatabaseDataType dataType;
    private final int dataTypeSize;
    private final boolean isPrimaryKey;

    public AndroidDatabaseTableColumn(final String name,
                                      final DatabaseDataType dataType,
                                      final int dataTypeSize,
                                      final boolean isPrimaryKey) {

        this.name = name;
        this.dataType = dataType;
        this.dataTypeSize = dataTypeSize;
        this.isPrimaryKey = isPrimaryKey;
    }

    public final String getName() {
        return name;
    }

    public final DatabaseDataType getDataType() {
        return dataType;
    }

    public final int getDataTypeSize() {
        return dataTypeSize;
    }

    public final boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    @Override
    public String toString() {
        String column = dataType.toString();
        column += dataTypeSize > 0 ? new StringBuilder().append("(").append(dataTypeSize).append(")").toString() : "";
        column += " ";
        column += name;
        return column;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DatabaseTableColumn))
            return false;
        DatabaseTableColumn compare = (DatabaseTableColumn) o;

        return dataType.equals(compare.getDataType()) && name.equals(compare.getName());
    }

    @Override
    public int hashCode() {
        int c = 0;
        if (dataType != null)
            c += dataType.hashCode();
        if (name != null)
            c += name.hashCode();
        return HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }
}
