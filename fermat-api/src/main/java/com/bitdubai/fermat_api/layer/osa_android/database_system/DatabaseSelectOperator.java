package com.bitdubai.fermat_api.layer.osa_android.database_system;

/**
 *  <p>The enum <code>DatabaseSelectOperator</code>
 *     defined operators that can be applied in a select statement
 *
 *
 *  @author  Natalia
 *  @version 1.0.0
 *  @since   08/07/2015
 * */


public interface DatabaseSelectOperator {

    public void setColumn (String column);

    public void setType (DataBaseSelectOperatorType type);

    public void setAliasColumn (String alias);

    public String  getAliasColumn ();

    public String  getColumn ();

    public DataBaseSelectOperatorType getType ();
}
