package com.bitdubai.fermat_bch_api.layer.crypto_network.enums;

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
                return BlockchainNetworkType.PRODUCTION;
            case "TEST":
                return BlockchainNetworkType.TEST;
            case "RTEST":
                return BlockchainNetworkType.REG_TEST;
            default:
                return BlockchainNetworkType.DEFAULT;
        }
    }
}
