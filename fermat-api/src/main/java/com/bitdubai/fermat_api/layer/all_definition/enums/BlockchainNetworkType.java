package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * Created by rodrigo on 9/21/15.
 */
public enum BlockchainNetworkType implements FermatEnum {

    DEFAULT     ("DEF"),
    PRODUCTION  ("PROD"),
    REG_TEST    ("RTEST"),
    TEST        ("TEST"),
    ;

    private String code;

    BlockchainNetworkType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static BlockchainNetworkType getByCode(String code) {

        switch (code) {
            case "PROD":  return BlockchainNetworkType.PRODUCTION;
            case "RTEST": return BlockchainNetworkType.REG_TEST;
            case "TEST":  return BlockchainNetworkType.TEST;
            default:      return BlockchainNetworkType.DEFAULT;
        }
    }

}
