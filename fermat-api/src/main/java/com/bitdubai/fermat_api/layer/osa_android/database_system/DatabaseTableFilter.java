package com.bitdubai.fermat_api.layer.osa_android.database_system;

/**
 * <p>The abstract class <code>DatabaseTableFilter</code> is a interface
 * that define methods to sets the columns that were used to make the filter on a table.
 *
 * @author Luis
 * @version 1.0.0
 * @since 01/02/15.
 */
public interface DatabaseTableFilter {


    void setColumn(String column);

    void setType(DatabaseFilterType type);

    void setValue(String value);

    String getColumn();

    String getValue();

    DatabaseFilterType getType();

}
