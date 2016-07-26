package com.bitdubai.fermat_api.layer.osa_android.database_system;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * <p>The enum <code>DatabaseFilterOrder</code>
 * defines the type order of query
 *
 * @author Luis
 * @version 1.0.0
 * @since 27/03/15.
 */
public enum DatabaseFilterOrder {

    //Modified by Manuel Perez 05/08/2015
    ASCENDING("ASC"),
    DESCENDING("DES");

    private String code;

    DatabaseFilterOrder(String code) {

        this.code = code;

    }

    public String getCode() {

        return this.code;

    }

    public static DatabaseFilterOrder getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "ASC":
                return DatabaseFilterOrder.ASCENDING;
            case "DES":
                return DatabaseFilterOrder.DESCENDING;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the DatabaseFilterOrder enum");


        }

    }

}
