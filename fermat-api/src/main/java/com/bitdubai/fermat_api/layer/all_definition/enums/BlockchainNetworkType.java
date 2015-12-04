package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * * The enum <code>com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType</code>
 * Represents the different Network Types available in Fermat.
 * <p/>
 * Created by rodrigo on 9/21/15.
 * Modified by pmgesualdi - (pmgesualdi@hotmail.com) on 30/11/2015.
 */
public enum BlockchainNetworkType implements FermatEnum {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    DEFAULT     ("DEF"),
    PRODUCTION  ("PROD"),
    REG_TEST    ("RTEST"),
    TEST        ("TEST"),

    ;

    private final String code;

    BlockchainNetworkType(final String code) {
        this.code = code;
    }

    public static BlockchainNetworkType getByCode(String code) {

        switch (code) {
            case "PROD":  return BlockchainNetworkType.PRODUCTION;
            case "RTEST": return BlockchainNetworkType.REG_TEST;
            case "TEST":  return BlockchainNetworkType.TEST;
            default:      return BlockchainNetworkType.DEFAULT;
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
