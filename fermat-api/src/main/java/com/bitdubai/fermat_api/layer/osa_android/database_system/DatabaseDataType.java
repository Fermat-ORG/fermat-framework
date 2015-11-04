package com.bitdubai.fermat_api.layer.osa_android.database_system;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * <p>The enum <code>DatabaseDataType</code>
 * defined data types for fields in a table
 *
 * @author Luis
 * @version 1.0.0
 * @since 01/02/15.
 */
public enum DatabaseDataType {

    //Modified by Manuel Perez on 05/08/2015
    STRING("STR"),
    INTEGER("INT"),
    LONG_INTEGER("LINT"),
    MONEY("MONEY"),
    BLOB("BLOB"),
    REAL("REAL");

    private String code;

    DatabaseDataType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static DatabaseDataType getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "STR":
                return STRING;
            case "INT":
                return INTEGER;
            case "LINT":
                return LONG_INTEGER;
            case "MONEY":
                return MONEY;
            case "BLOB":
                return BLOB;
            case "REAL":
                return REAL;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the DatabaseDataType enum");
        }
    }

}
