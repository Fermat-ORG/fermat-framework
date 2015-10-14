package com.bitdubai.fermat_api.layer.all_definition.enums;

/**
 * Created by rodrigo on 9/21/15.
 */
public enum BlockchainNetworkType {
    PRODUCTION("PROD"),
    TEST("TEST"),
    REG_TEST("RTEST"),
    DEFAULT("DEF");


    private String code;

    BlockchainNetworkType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static BlockchainNetworkType getByCode(String code){

        switch (code) {
            case "PRD":
                return PRODUCTION;
            case "TEST":
                return TEST;
            case "RTEST":
                return REG_TEST;
            default:
                return DEFAULT;
        }
    }
}
