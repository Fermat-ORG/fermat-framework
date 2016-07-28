package com.bitdubai.fermat_api.layer.osa_android.database_system;


import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * <p>The enum <code>DatabaseFilterOperator</code>
 * define the operator for query filter
 *
 * @author Leon Acosta
 * @version 1.0.0
 * @since 15/05/15.
 */

public enum DatabaseFilterOperator {

    //Modified by Manuel Perez on 05/08/2015
    OR("OR"),
    AND("AND");

    private String code;

    DatabaseFilterOperator(String code) {

        this.code = code;

    }

    public String getCode() {

        return this.code;

    }

    public static DatabaseFilterOperator getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "OR":
                return DatabaseFilterOperator.OR;
            case "AND":
                return DatabaseFilterOperator.AND;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the DataFilterOperator enum");


        }

    }

}
