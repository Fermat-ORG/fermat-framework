package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DataBaseSelectOperatorType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseSelectOperator;

/**
 * This class define methods to sets the operators to apply on select field
 *
 *Created by natalia on 08/07/15.
 */
public class AndroidDatabaseSelectOperator implements DatabaseSelectOperator {

    /**
     * DatabaseSelectOperator Member Variables.
     */
    private String column;
    private DataBaseSelectOperatorType type;
    private String alias;

    public AndroidDatabaseSelectOperator(final String                     column,
                                         final DataBaseSelectOperatorType type  ,
                                         final String                     alias ) {
        this.column = column;
        this.type = type;
        this.alias = alias;
    }

    /**
     * DatabaseSelectOperator interface implementation.
     */

    /**
     * <p>Sets the column to apply the operation
     *
     * @param column colum name to apply operation
     */
    @Override
    public void setColumn (String column)
    {
        this.column = column;
    }

    /**
     * <p>Sets the operator type for the select
     *
     * @param type enum DataBaseSelectOperatorType , type of operator for the select
     */
    @Override
    public void setType (DataBaseSelectOperatorType type)
    {
        this.type = type;
    }

    /**
     * <p>Gets the operator type for the select.
     *
     * @return DataBaseSelectOperatorType enum
     */
    @Override
    public DataBaseSelectOperatorType getType()
    {
        return this.type;
    }

    /**
     * <p>Sets the field alias to the result of the select
     * @param alias String alias result
     */
    @Override
    public void setAliasColumn (String alias){
        this.alias = alias;
    }

    /**
     * <p>Gets the field alias to the result of the select
     * @return String alias
     */
    @Override
    public String  getAliasColumn (){
        return this.alias;
    }

    /**
     *<p>Gets column the column to apply the operator.
     *
     * @return String select column name
     */
    @Override
    public String  getColumn ()
    {
        return this.column;
    }
}


