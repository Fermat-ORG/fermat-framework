package com.bitdubai.fermat_api.layer.osa_android.database_system;

/**
 *  <p>The enum <code>DatabaseAggregateFunction</code>
 *     defined operators that can be applied in a select statement
 *
 *
 *  @author  Natalia
 *  @version 1.0.0
 *  @since   08/07/2015
 * */


public interface DatabaseAggregateFunction {

    public void setColumn (String column);

    public void setType (DataBaseAggregateFunctionType type);

    public void setAliasColumn (String alias);

    public String  getAliasColumn ();

    public String  getColumn ();

    public DataBaseAggregateFunctionType getType ();

    public String toSQLQuery();
}
