package com.bitdubai.fermat_api.layer.osa_android.database_system;


import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 *  <p>The enum <code>DatabaseSelectOperator</code>
 *     defined operators that can be applied in a select statement
 *
 *
 *  @author  Natalia
 *  @version 1.0.0
 *  @since   08/07/2015
 * */

public enum DataBaseSelectOperatorType implements FermatEnum {

    COUNT("COUNT"),
    SUM  ("SUM"),

    ;

    private final String code;

    DataBaseSelectOperatorType(final String code){

        this.code = code;
    }

    public static DataBaseSelectOperatorType getByCode(final String code) throws InvalidParameterException {

        switch (code){

            case "COUNT": return COUNT;
            case "SUM":   return SUM;

            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "This Code Is Not Valid for the DataBaseSelectOperatorType enum"
                );
        }

    }

    @Override
    public String getCode() {
        return this.code;
    }

}
