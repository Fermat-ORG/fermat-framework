package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum class <code>com.bitdubai.fermat_api.layer.all_definition.enums.Platforms</code>
 * Enums all the platforms to be found on Fermat.
 * Created by lnacosta (laion.cj91@gmail.com) on 02/09/2015.
 * Modified by PatricioGesualdi (pmgesualdi@hotmail.com) on 10/11/2015.
 * Modified by Alejandro Bicelis  on 12/27/2015.
 */
public enum Platforms implements FermatEnum {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    ART_PLATFORM("ART", "Artist"),
    BLOCKCHAINS("BCH", "Blockchain"),
    BANKING_PLATFORM("BNK", "Banking"),
    COMMUNICATION_PLATFORM("CP", "Communication"),
    CRYPTO_BROKER_PLATFORM("CBP", "Brokers"),
    CURRENCY_EXCHANGE_RATE_PLATFORM("CER", "Exchange"),
    CASH_PLATFORM("CSH", "Cash"),
    CHAT_PLATFORM("CHT", "Chat"),
    CRYPTO_COMMODITY_MONEY("CCM", "Crypto Commodity"),
    CRYPTO_CURRENCY_PLATFORM("CCP", "Crypto Currencies"),
    DIGITAL_ASSET_PLATFORM("DAP", "Digital Assets"),
    OPERATIVE_SYSTEM_API("OSA", "OSA"),
    PLUG_INS_PLATFORM("PIP", "Tools"),
    WALLET_PRODUCTION_AND_DISTRIBUTION("WPD", "Wallets"),
    TOKENLY("TKY", "Tokenly");

    private final String code;
    private final String textForm;

    Platforms(final String code,
              final String textForm) {

        this.code = code;
        this.textForm = textForm;
    }

    public static Platforms getByCode(final String code) throws InvalidParameterException {

        for (Platforms platform : Platforms.values()) {
            if (platform.getCode().equals(code))
                return platform;
        }

        throw new InvalidParameterException(
                new StringBuilder().append("Code Received: ").append(code).toString(),
                "The received code is not valid for the Platforms enum"
        );
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public String getTextForm() {
        return textForm;
    }
}