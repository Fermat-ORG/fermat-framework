package com.bitdubai.fermat_api.layer.osa_android.database_system;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * <p>The enum <code>DatabaseDataType</code>
 * defined data types for fields in a table
 * <p/>
 * Modified by Manuel Perez on 05/08/2015
 * Updated by Leon Acosta (laion.cj91a@gmail.com) on 03/02/2016.
 *
 * @author Luis
 * @version 1.0.0
 * @since 01/02/15.
 */
public enum DatabaseDataType implements FermatEnum {

    BLOB("BLB"),
    INTEGER("INT"),
    LONG_INTEGER("LIN"),
    MONEY("MNY"),
    REAL("REL"),
    STRING("STR"),
    TEXT("TXT"),;

    private final String code;

    DatabaseDataType(final String code) {

        this.code = code;
    }

    public static DatabaseDataType getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "BLB":
                return BLOB;
            case "INT":
                return INTEGER;
            case "LIN":
                return LONG_INTEGER;
            case "MNY":
                return MONEY;
            case "REL":
                return REAL;
            case "STR":
                return STRING;
            case "TXT":
                return TEXT;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "This code is not valid for the DatabaseDataType enum."
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
