package com.bitdubai.fermat_api.layer.osa_android.database_system;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * <p>The enum <code>DatabaseFilterType</code>
 * defines the type of operator for the filter
 *
 * @author Luis
 * @version 1.0.0
 * @since 01/02/15.
 */
public enum DatabaseFilterType implements FermatEnum {

    ENDS_WITH("EW"),
    EQUAL("EQ"),
    GREATER_OR_EQUAL_THAN("GE"),
    GREATER_THAN("GT"),
    LESS_OR_EQUAL_THAN("LE"),
    LESS_THAN("LT"),
    LIKE("LK"),
    NOT_EQUALS("NE"),
    STARTS_WITH("SW"),;

    private final String code;

    DatabaseFilterType(final String code) {

        this.code = code;
    }

    public static DatabaseFilterType getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "EW":
                return ENDS_WITH;
            case "EQ":
                return EQUAL;
            case "GE":
                return GREATER_OR_EQUAL_THAN;
            case "GT":
                return GREATER_THAN;
            case "LE":
                return LESS_OR_EQUAL_THAN;
            case "LT":
                return LESS_THAN;
            case "LK":
                return LIKE;
            case "NE":
                return NOT_EQUALS;
            case "SW":
                return STARTS_WITH;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "This Code Is Not Valid for the DatabaseFilterType enum"
                );


        }

    }

    @Override
    public String getCode() {
        return this.code;
    }

}
