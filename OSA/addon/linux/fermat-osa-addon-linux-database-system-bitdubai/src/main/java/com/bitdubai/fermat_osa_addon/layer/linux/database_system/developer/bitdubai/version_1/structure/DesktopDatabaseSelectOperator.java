/*
* @#DesktopDatabaseSelectOperator.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DataBaseAggregateFunctionType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseAggregateFunction;

/**
 * The Class <code>com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure.DesktopDatabaseSelectOperator</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 10/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DesktopDatabaseSelectOperator implements DatabaseAggregateFunction {

    /**
     * DatabaseSelectOperator Member Variables.
     */
    private String column;
    private DataBaseAggregateFunctionType type;
    private String alias;


    public DesktopDatabaseSelectOperator() {
    }


    public DesktopDatabaseSelectOperator(String column, DataBaseAggregateFunctionType type, String alias) {
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
    public void setColumn(String column) {
        this.column = column;
    }

    /**
     * <p>Sets the operator type for the select
     *
     * @param type enum DataBaseSelectOperatorType , type of operator for the select
     */
    @Override
    public void setType(DataBaseAggregateFunctionType type) {
        this.type = type;
    }

    /**
     * <p>Gets the operator type for the select.
     *
     * @return DataBaseSelectOperatorType enum
     */
    @Override
    public DataBaseAggregateFunctionType getType() {
        return this.type;
    }

    /**
     * <p>Sets the field alias to the result of the select
     *
     * @param alias String alias result
     */
    @Override
    public void setAliasColumn(String alias) {
        this.alias = alias;
    }

    /**
     * <p>Gets the field alias to the result of the select
     *
     * @return String alias
     */
    @Override
    public String getAliasColumn() {
        return this.alias;
    }

    /**
     * <p>Gets column the column to apply the operator.
     *
     * @return String select column name
     */
    @Override
    public String getColumn() {
        return this.column;
    }

    @Override
    public String toSQLQuery() {
        return null;
    }
}
