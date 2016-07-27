package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum class <code>com.bitdubai.fermat_api.layer.all_definition.enums.Layers</code>
 * enumerates all the layers type of fermat.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public enum Layers implements FermatEnum {
    /**
     * To do make code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    ACTOR("ACT"),
    ACTOR_CONNECTION("ACC"),
    ACTOR_NETWORK_SERVICE("ANS"),
    AGENT("AGN"),
    API("API"),
    BANK_MONEY_TRANSACTION("BMT"),
    BASIC_WALLET("BSW"),
    BUSINESS_TRANSACTION("BTX"),
    CASH_MONEY_TRANSACTION("CMT"),
    COMMUNICATION("COM"),
    CRYPTO_MODULE("CRM"),
    CONTRACT("CON"),
    CRYPTO_NETWORK("CRN"),
    CRYPTO_ROUTER("CRR"),
    CRYPTO_TRANSACTION("CRT"),
    CRYPTO_VAULT("CRV"),
    DEFINITION("DEF"),
    DESKTOP_MODULE("DKM"),
    DIGITAL_ASSET_TRANSACTION("DAT"),
    EXTERNAL_API("EAP"),
    ENGINE("ENG"),
    FUNDS_TRANSACTION("FTX"),
    HARDWARE("HAR"),
    IDENTITY("IDT"),
    METADATA("MET"),
    METADATA_TRANSACTION("MTT"),
    MIDDLEWARE("MID"),
    NEGOTIATION("NEG"),
    NEGOTIATION_TRANSACTION("NTR"),
    NETWORK_SERVICE("NTS"),
    OFFER("OFF"),
    OFFER_TRANSACTION("OFT"),
    PLATFORM_SERVICE("PMS"),
    PROVIDER("PRO"),
    REQUEST("REQ"),
    STOCK_TRANSACTIONS("STR"),
    STATISTIC_AGGREGATOR("STA"),
    STATISTIC_COLLECTOR("STC"),
    SEARCH("SCH"),
    SONG_WALLET("SWL"),
    SUB_APP_MODULE("SAM"),
    SWAP_TRANSACTION("SWT"),
    SYSTEM("SYS"),
    TRANSACTION("TRA"),
    USER("USR"),
    WALLET("WAL"),
    WALLET_MODULE("WAM"),
    WORLD("WRL"),
    USER_LEVEL_BUSINESS_TRANSACTION("ULB"),;

    private final String code;

    Layers(final String code) {
        this.code = code;
    }

    public static Layers getByCode(final String code) throws InvalidParameterException {

        for (Layers layer : Layers.values()) {
            if (layer.getCode().equals(code))
                return layer;
        }

        throw new InvalidParameterException(
                new StringBuilder().append("Code Received: ").append(code).toString(),
                "The received code is not valid for the Layers enum"
        );
    }

    @Override
    public String getCode() {
        return this.code;
    }

}