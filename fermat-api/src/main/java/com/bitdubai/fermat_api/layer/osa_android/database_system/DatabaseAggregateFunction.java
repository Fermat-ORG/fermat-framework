package com.bitdubai.fermat_api.layer.osa_android.database_system;

/**
 * <p>The enum <code>DatabaseAggregateFunction</code>
 * defined operators that can be applied in a select statement
 *
 * @author Natalia
 * @version 1.0.0
 * @since 08/07/2015
 */


public interface DatabaseAggregateFunction {

    void setColumn(String column);

    void setType(DataBaseAggregateFunctionType type);

    void setAliasColumn(String alias);

    String getAliasColumn();

    String getColumn();

    DataBaseAggregateFunctionType getType();

    String toSQLQuery();
}
