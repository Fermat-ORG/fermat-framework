package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Enums the platforms on Fermat.
 * Created by Leon Acosta (laion.cj91@gmail.com) on 02/09/2015.
 */
public enum Platforms implements FermatEnum {

    CRYPTO_BROKER_PLATFORM("CBP"),
    CRYPTO_COMMODITY_MONEY("CCM"),
    CRYPTO_CURRENCY_PLATFORM("CCP"),
    DIGITAL_ASSET_PLATFORM("DAP"),
    BLOCKCHAINS("BCH"),
    COMMUNICATION_PLATFORM("CP"),
    OPERATIVE_SYSTEM_API("OSA"),
    PLUG_INS_PLATFORM("PIP"),

    ;

    private String code;

    Platforms(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static Platforms getByCode(String code) throws InvalidParameterException {

        for (Platforms platform : Platforms.values()) {
            if (platform.getCode().equals(code))
                return platform;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This code is not valid for the Platforms enum.");
    }
}
