package com.bitdubai.fermat_ccp_core.test_classes;

import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
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

    BITDUBAI_CRYPTO_ADDRESSES_NETWORK_SERVICE       ("BCANS" , Developers.BITDUBAI),
    BITDUBAI_CRYPTO_PAYMENT_REQUEST                 ("BCPR"  , Developers.BITDUBAI),
    BITDUBAI_CRYPTO_PAYMENT_REQUEST_NETWORK_SERVICE ("BCPRNS", Developers.BITDUBAI),
    BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE    ("BCTNS" , Developers.BITDUBAI),
    BITDUBAI_INTRA_WALLET_USER_ACTOR                ("BIWUA" , Developers.BITDUBAI),
    BITDUBAI_INTRA_WALLET_USER_IDENTITY             ("BIUI"  , Developers.BITDUBAI),
    BITDUBAI_OUTGOING_INTRA_ACTOR_TRANSACTION       ("BOIAT" , Developers.BITDUBAI),
    BITDUBAI_WALLET_CONTACTS_MIDDLEWARE             ("BWCM"  , Developers.BITDUBAI),

    ;

    private String     code     ;
    private Developers developer;

    CCPPlugins(final String     code     ,
               final Developers developer) {

        this.code      = code     ;
        this.developer = developer;
    }

    public static CCPPlugins getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "BCANS" : return BITDUBAI_CRYPTO_ADDRESSES_NETWORK_SERVICE      ;
            case "BCPR"  : return BITDUBAI_CRYPTO_PAYMENT_REQUEST                ;
            case "BCPRNS": return BITDUBAI_CRYPTO_PAYMENT_REQUEST_NETWORK_SERVICE;
            case "BCTNS" : return BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE   ;
            case "BIWUA" : return BITDUBAI_INTRA_WALLET_USER_ACTOR               ;
            case "BIUI"  : return BITDUBAI_INTRA_WALLET_USER_IDENTITY            ;
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
    public Developers getDeveloper() {
        return this.developer;
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
