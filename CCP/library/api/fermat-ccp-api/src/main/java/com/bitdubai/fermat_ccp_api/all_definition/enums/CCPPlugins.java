package com.bitdubai.fermat_ccp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatPluginsEnum;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The class <code>com.bitdubai.fermat_ccp_core.test_classes.CCPPLugins</code>
 * contains all the plugins of the CCP Platform and its developer.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public enum CCPPlugins implements FermatPluginsEnum {

    /**
     * For doing the code more readable, please keep the elements in the Enum sorted alphabetically.
     */

    BITCOIN_WALLET_BASIC_WALLET           ("BWBW" ),
    CRYPTO_ADDRESSES_NETWORK_SERVICE      ("CANS" ),
    CRYPTO_PAYMENT_REQUEST                ("CPR"  ),
    CRYPTO_PAYMENT_REQUEST_NETWORK_SERVICE("CPRNS"),
    CRYPTO_TRANSMISSION_NETWORK_SERVICE   ("CTNS" ),
    CRYPTO_WALLET_MODULE                  ("CWM"  ),
    EXTRA_WALLET_USER_ACTOR               ("EWUA" ),
    INCOMING_EXTRA_USER_TRANSACTION       ("IEUT" ),
    INCOMING_INTRA_USER_TRANSACTION       ("IIUT" ),
    INTRA_USER_NETWORK_SERVICE            ("IUNS" ),
    INTRA_WALLET_USER_ACTOR               ("IWUA" ),
    INTRA_WALLET_USER_IDENTITY            ("IUI"  ),
    INTRA_WALLET_USER_MODULE              ("IUM"  ),
    OUTGOING_EXTRA_USER_TRANSACTION       ("OEUT" ),
    OUTGOING_INTRA_ACTOR_TRANSACTION      ("OIAT" ),
    WALLET_CONTACTS_MIDDLEWARE            ("WCM"  ),

    ;

    private final String code;

    CCPPlugins(final String code) {

        this.code = code;
    }

    public static CCPPlugins getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "BWBW" : return BITCOIN_WALLET_BASIC_WALLET           ;
            case "CANS" : return CRYPTO_ADDRESSES_NETWORK_SERVICE      ;
            case "CPR"  : return CRYPTO_PAYMENT_REQUEST                ;
            case "CPRNS": return CRYPTO_PAYMENT_REQUEST_NETWORK_SERVICE;
            case "CTNS" : return CRYPTO_TRANSMISSION_NETWORK_SERVICE   ;
            case "CWM"  : return CRYPTO_WALLET_MODULE                  ;
            case "EWUA" : return EXTRA_WALLET_USER_ACTOR               ;
            case "IEUT" : return INCOMING_EXTRA_USER_TRANSACTION       ;
            case "IIUT" : return INCOMING_INTRA_USER_TRANSACTION       ;
            case "IUNS" : return INTRA_USER_NETWORK_SERVICE            ;
            case "IWUA" : return INTRA_WALLET_USER_ACTOR               ;
            case "IUI"  : return INTRA_WALLET_USER_IDENTITY            ;
            case "IUM"  : return INTRA_WALLET_USER_MODULE              ;
            case "OEUT" : return OUTGOING_EXTRA_USER_TRANSACTION       ;
            case "OIAT" : return OUTGOING_INTRA_ACTOR_TRANSACTION      ;
            case "WCM"  : return WALLET_CONTACTS_MIDDLEWARE            ;

            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "The received code is not valid for the CCPPlugins enum"
                );
        }
    }

    @Override
    public Platforms getPlatform() {
        return Platforms.CRYPTO_CURRENCY_PLATFORM;
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
