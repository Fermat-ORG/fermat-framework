package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * * The enum <code>com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType</code>
 * Represents the different Network Types available in Fermat.
 * <p/>
 * Created by rodrigo on 9/21/15.
 */
public enum BlockchainNetworkType implements FermatEnum {
    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    PRODUCTION  ("PROD"),
    REG_TEST    ("RTEST"),
    TEST_NET    ("TESTN");

    /**
     * sets the default network that will be used at start up.
     */
    private static final BlockchainNetworkType DEFAULT_BLOCKCHAIN_NETWORK_TYPE = BlockchainNetworkType.TEST_NET;

    private final String code;

    BlockchainNetworkType(final String code) {
        this.code = code;
    }

    public static BlockchainNetworkType getByCode(String code) {

        switch (code) {
            case "PROD":  return BlockchainNetworkType.PRODUCTION;
            case "RTEST": return BlockchainNetworkType.REG_TEST;
            case "TESTN": return BlockchainNetworkType.TEST_NET;
            default:      return DEFAULT_BLOCKCHAIN_NETWORK_TYPE;
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * Gets the default network type selected for this platform.
     * @return the default BlockchainNetworkType
     */
    public static BlockchainNetworkType getDefaultBlockchainNetworkType(){
        return DEFAULT_BLOCKCHAIN_NETWORK_TYPE;
    }
}
