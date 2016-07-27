package com.bitdubai.fermat_api.layer.osa_android.database_system;


import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * <p>The enum <code>DatabaseAggregateFunction</code>
 * defined operators that can be applied in a select statement
 *
 * @author Natalia
 * @version 1.0.0
 * @since 08/07/2015
 * <p/>
 * Modified by pmgesualdi (pmgesualdi@hotmail.com) on 21/12/2015
 */

public enum DataBaseAggregateFunctionType implements FermatEnum {


    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    AVG("AVG"),
    COUNT("COUNT"),
    MAX("MAX"),
    MIN("MIN"),
    ROUND("ROUND"),
    SUM("SUM"),;

    private final String code;

    DataBaseAggregateFunctionType(final String code) {

        this.code = code;
    }

    public static DataBaseAggregateFunctionType getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "AVG":
                return AVG;
            case "COUNT":
                return COUNT;
            case "MAX":
                return MAX;
            case "MIN":
                return MIN;
            case "ROUND":
                return ROUND;
            case "SUM":
                return SUM;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "This Code Is Not Valid for the DataBaseAggregateFunctionType enum"
                );
        }

    }

    @Override
    public String getCode() {
        return this.code;
    }

}
