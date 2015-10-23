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

    BITDUBAI_BITCOIN_WALLET_BASIC_WALLET            ("BBWBW" ),
    BITDUBAI_CRYPTO_ADDRESSES_NETWORK_SERVICE       ("BCANS" ),
    BITDUBAI_CRYPTO_PAYMENT_REQUEST                 ("BCPR"  ),
    BITDUBAI_CRYPTO_PAYMENT_REQUEST_NETWORK_SERVICE ("BCPRNS"),
    BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE    ("BCTNS" ),
    BITDUBAI_CRYPTO_WALLET_MODULE                   ("BCWM"  ),
    BITDUBAI_EXTRA_WALLET_USER_ACTOR                ("BEWUA" ),
    BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION        ("BIEUT" ),
    BITDUBAI_INCOMING_INTRA_USER_TRANSACTION        ("BIIUT" ),
    BITDUBAI_INTRA_USER_NETWORK_SERVICE             ("BIUNS" ),
    BITDUBAI_INTRA_WALLET_USER_ACTOR                ("BIWUA" ),
    BITDUBAI_INTRA_WALLET_USER_IDENTITY             ("BIUI"  ),
    BITDUBAI_INTRA_WALLET_USER_MODULE               ("BIUM"  ),
    BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION        ("BOEUT" ),
    BITDUBAI_OUTGOING_INTRA_ACTOR_TRANSACTION       ("BOIAT" ),
    BITDUBAI_WALLET_CONTACTS_MIDDLEWARE             ("BWCM"  ),

    ;

    private final String code;

    CCPPlugins(final String code) {

        this.code = code;
    }

    public static CCPPlugins getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "BBWBW" : return BITDUBAI_BITCOIN_WALLET_BASIC_WALLET           ;
            case "BCANS" : return BITDUBAI_CRYPTO_ADDRESSES_NETWORK_SERVICE      ;
            case "BCPR"  : return BITDUBAI_CRYPTO_PAYMENT_REQUEST                ;
            case "BCPRNS": return BITDUBAI_CRYPTO_PAYMENT_REQUEST_NETWORK_SERVICE;
            case "BCTNS" : return BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE   ;
            case "BCWM"  : return BITDUBAI_CRYPTO_WALLET_MODULE                  ;
            case "BEWUA" : return BITDUBAI_EXTRA_WALLET_USER_ACTOR               ;
            case "BIEUT" : return BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION       ;
            case "BIIUT" : return BITDUBAI_INCOMING_INTRA_USER_TRANSACTION       ;
            case "BIUNS" : return BITDUBAI_INTRA_USER_NETWORK_SERVICE            ;
            case "BIWUA" : return BITDUBAI_INTRA_WALLET_USER_ACTOR               ;
            case "BIUI"  : return BITDUBAI_INTRA_WALLET_USER_IDENTITY            ;
            case "BIUM"  : return BITDUBAI_INTRA_WALLET_USER_MODULE              ;
            case "BOEUT" : return BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION       ;
            case "BOIAT" : return BITDUBAI_OUTGOING_INTRA_ACTOR_TRANSACTION      ;
            case "BWCM"  : return BITDUBAI_WALLET_CONTACTS_MIDDLEWARE            ;

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
