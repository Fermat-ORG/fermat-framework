package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DataBaseAggregateFunctionType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseAggregateFunction;

/**
 * This class define methods to sets the operators to apply on select field
 * <p/>
 * Created by natalia on 08/07/15.
 */
public class AndroidDatabaseAggregateFunction implements DatabaseAggregateFunction {

    /**
     * DatabaseAggregateFunction Member Variables.
     */
    private String column;
    private DataBaseAggregateFunctionType type;
    private String alias;

    public AndroidDatabaseAggregateFunction(final String column,
                                            final DataBaseAggregateFunctionType type,
                                            final String alias) {
        this.column = column;
        this.type = type;
        this.alias = alias;
    }

    /**
     * DatabaseAggregateFunction interface implementation.
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
     * @param type enum DataBaseAggregateFunctionType , type of operator for the select
     */
    @Override
    public void setType(DataBaseAggregateFunctionType type) {
        this.type = type;
    }

    /**
     * <p>Gets the operator type for the select.
     *
     * @return DataBaseAggregateFunctionType enum
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
    public String toString() {
        return new StringBuilder()
                .append("AndroidDatabaseAggregateFunction{")
                .append("column='").append(column)
                .append('\'')
                .append(", type=").append(type)
                .append(", alias='").append(alias)
                .append('\'')
                .append('}').toString();
    }

    public String toSQLQuery() {

        StringBuilder strFilter = new StringBuilder();

        switch (type) {
            case AVG:
                strFilter.append("AVG(")
                        .append(column).append(") AS '").append(alias).append("'");
                break;

            case MAX:
                strFilter.append("MAX(")
                        .append(column).append(") AS '").append(alias).append("'");
                break;

            case MIN:
                strFilter.append("MIN(")
                        .append(column).append(") AS '").append(alias).append("'");
                break;

            case COUNT:
                strFilter.append("COUNT(")
                        .append(column).append(") AS '").append(alias).append("'");
                break;

            case ROUND:
                strFilter.append("ROUND(")
                        .append(column).append(") AS '").append(alias).append("'");
                break;

            case SUM:
                strFilter.append("SUM(")
                        .append(column).append(") AS '").append(alias).append("'");

            default:
                strFilter.append("");
                break;
        }

        return strFilter.toString();
    }
}


