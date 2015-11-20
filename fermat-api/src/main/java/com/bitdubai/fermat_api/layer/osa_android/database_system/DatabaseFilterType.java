package com.bitdubai.fermat_api.layer.osa_android.database_system;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 *  <p>The enum <code>DatabaseFilterType</code>
 *     defines the type of operator for the filter
 *
 *
 *  @author  Luis
 *  @version 1.0.0
 *  @since   01/02/15.
 * */
public enum DatabaseFilterType {
    EQUAL("EQ"),
    GREATER_THAN("GT"),
    LESS_THAN("LT"),
    LIKE("LIKE");

    private String code;

    DatabaseFilterType(String code){

        this.code=code;

    }

    public String getCode(){

        return this.code;

    }

    public static DatabaseFilterType getByCode(String code)throws InvalidParameterException{

        switch (code){

            case "EQ":
                return DatabaseFilterType.EQUAL;
            case "GT":
                return DatabaseFilterType.GREATER_THAN;
            case "LT":
                return DatabaseFilterType.LESS_THAN;
            case "LIKE":
                return DatabaseFilterType.LIKE;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the DatabaseFilterType enum");


        }

    }

}
