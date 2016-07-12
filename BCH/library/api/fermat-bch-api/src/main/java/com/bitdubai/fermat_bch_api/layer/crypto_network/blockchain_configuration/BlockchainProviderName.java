package com.bitdubai.fermat_bch_api.layer.crypto_network.blockchain_configuration;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by rodrigo on 6/22/16.
 * Enum that list all blockchain provider supported at the platform.
 * Currently Bitcoin and Fermat.
 */
public enum BlockchainProviderName {
    BITCOIN ("BTC"),
    FERMAT ("FER");

    private String code;

    /**
     * sets the code of the provider
     * @param code
     */
    BlockchainProviderName(String code) {
        this.code = code;
    }

    /**
     * gets the code of the enum
     * @return
     */
    public String getCode(){return this.code;}


    public static BlockchainProviderName getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "BTC":
                return BITCOIN;
            case "FER":
                return FERMAT;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This code is not valid for the Blockchain Provider enum.");
        }
    }
}
