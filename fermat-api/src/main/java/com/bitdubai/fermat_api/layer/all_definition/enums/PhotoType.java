package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Joaquin C. on 23/12/15.
 */
public enum PhotoType implements FermatEnum {

    /**
     * Please keep the elements of the enum ordered alphabetically.
     */

    DEFAULT_MALE             ("DM"),
    DEFAULT_FEMALE           ("DF"),
    DEFAULT_COLOR_MALE       ("DCM"),
    DEFAULT_COLOR_FEMALE     ("DCF"),
    CUSTOM                   ("CTM");

    ;

    private final String code;

    PhotoType(final String code) {
        this.code = code;
    }

    public static PhotoType getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "DM":return DEFAULT_MALE;
            case "DF":return DEFAULT_FEMALE   ;
            case "DCM":return DEFAULT_COLOR_MALE     ;
            case "DCF":return DEFAULT_COLOR_FEMALE    ;
            case "CTM":return CUSTOM    ;
            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "This is an invalid CryptoTransactionStatus code"
                );
        }

    }

    @Override
    public final String getCode() {
        return this.code;
    }

}
