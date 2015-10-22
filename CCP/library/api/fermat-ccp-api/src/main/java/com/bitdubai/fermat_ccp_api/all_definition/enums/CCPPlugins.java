package com.bitdubai.fermat_ccp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatPluginsEnum;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
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

    BITDUBAI_BITCOIN_WALLET_BASIC_WALLET            ("BBWBW" , Developers.BITDUBAI, Layers.BASIC_WALLET   ),
    BITDUBAI_CRYPTO_ADDRESSES_NETWORK_SERVICE       ("BCANS" , Developers.BITDUBAI, Layers.NETWORK_SERVICE),
    BITDUBAI_CRYPTO_PAYMENT_REQUEST                 ("BCPR"  , Developers.BITDUBAI, Layers.REQUEST        ),
    BITDUBAI_CRYPTO_PAYMENT_REQUEST_NETWORK_SERVICE ("BCPRNS", Developers.BITDUBAI, Layers.NETWORK_SERVICE),
    BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE    ("BCTNS" , Developers.BITDUBAI, Layers.NETWORK_SERVICE),
    BITDUBAI_CRYPTO_WALLET_MODULE                   ("BCWM"  , Developers.BITDUBAI, Layers.WALLET_MODULE  ),
    BITDUBAI_EXTRA_WALLET_USER_ACTOR                ("BEWUA" , Developers.BITDUBAI, Layers.ACTOR          ),
    BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION        ("BIEUT" , Developers.BITDUBAI, Layers.TRANSACTION    ),
    BITDUBAI_INCOMING_INTRA_USER_TRANSACTION        ("BIIUT" , Developers.BITDUBAI, Layers.TRANSACTION    ),
    BITDUBAI_INTRA_USER_NETWORK_SERVICE             ("BIUNS" , Developers.BITDUBAI, Layers.NETWORK_SERVICE),
    BITDUBAI_INTRA_WALLET_USER_ACTOR                ("BIWUA" , Developers.BITDUBAI, Layers.ACTOR          ),
    BITDUBAI_INTRA_WALLET_USER_IDENTITY             ("BIUI"  , Developers.BITDUBAI, Layers.IDENTITY       ),
    BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION        ("BOEUT" , Developers.BITDUBAI, Layers.TRANSACTION    ),
    BITDUBAI_OUTGOING_INTRA_ACTOR_TRANSACTION       ("BOIAT" , Developers.BITDUBAI, Layers.TRANSACTION    ),
    BITDUBAI_WALLET_CONTACTS_MIDDLEWARE             ("BWCM"  , Developers.BITDUBAI, Layers.MIDDLEWARE     ),

    ;

    private final String     code     ;
    private final Developers developer;
    private final Layers     layer    ;

    CCPPlugins(final String     code     ,
               final Developers developer,
               final Layers     layer    ) {

        this.code      = code     ;
        this.developer = developer;
        this.layer = layer;
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
    public Layers getLayer() {
        return this.layer;
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
