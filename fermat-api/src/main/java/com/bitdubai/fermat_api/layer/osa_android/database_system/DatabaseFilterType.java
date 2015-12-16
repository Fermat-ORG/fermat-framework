package com.bitdubai.fermat_api.layer.osa_android.database_system;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
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
public enum DatabaseFilterType implements FermatEnum {

    ENDS_WITH    ("EW"),
    EQUAL        ("EQ"),
    GREATER_THAN ("GT"),
    LESS_THAN    ("LT"),
    LIKE         ("LK"),
    STARTS_WITH  ("SW"),

    ;

    private final String code;

    DatabaseFilterType(final String code){

        this.code = code;
    }

    public static DatabaseFilterType getByCode(final String code) throws InvalidParameterException {

        switch (code){

            case "EW": return ENDS_WITH   ;
            case "EQ": return EQUAL       ;
            case "GT": return GREATER_THAN;
            case "LT": return LESS_THAN   ;
            case "LK": return LIKE        ;
            case "SW": return STARTS_WITH ;

            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "This Code Is Not Valid for the DatabaseFilterType enum"
                );


        }

    }

    @Override
    public String getCode(){
        return this.code;
    }

}
