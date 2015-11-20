package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Each element of the enum <code>com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.CryptoAddressDealers</code>
 * represents a crypto address dealer, which is a wallet that needs to exchange address between its actors.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/10/2015.
 */
public enum CryptoAddressDealers implements FermatEnum {

    CRYPTO_WALLET   ("CRW"),
    DAP_ASSET       ("DAPA"),

    ;

    private final String code;

    CryptoAddressDealers(final String code){
        this.code = code;
    }

    public static CryptoAddressDealers getByCode(final String code) throws InvalidParameterException {

        switch (code){

            case "CRW": return CRYPTO_WALLET;
            case "DAPA": return DAP_ASSET;

            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "This code is not valid for the CryptoAddressDealers enum"
                );
        }
    }

    @Override
    public String getCode(){
        return this.code;
    }

}

