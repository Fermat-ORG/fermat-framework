package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * // TODO EXPLANATION OF THE ENUM.
 * <p/>
 * Created by angel on 18/9/15.
 */
public enum ReferenceCurrency implements FermatEnum {


    // TODO, WHY A REFERENCE CURRENCY IF WE HAVE A CURRENCY ENUM THAT CAN INDICATES THE SAME? PLEASE DON'T BE REPEAT THINGS, IT MAKES IT DIFFICULT TO UNDERSTAND.
    DOLLAR("DOL"),
    EURO("EUR");

    private final String code;

    ReferenceCurrency(final String code) {
        this.code = code;
    }


    public static ReferenceCurrency getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "DOL":
                return DOLLAR;
            case "EUR":
                return EURO;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "This Code Is Not Valid for the ContactState enum"
                );
        }
    }

    @Override
    public final String getCode() {
        return this.code;
    }

}
