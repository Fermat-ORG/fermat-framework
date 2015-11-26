package com.bitdubai.fermat_api.layer.all_definition.network_service.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType</code> represent
 * all the types that a network service can be.
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 01/09/15.
 * Modified by Leon Acosta - (laion.cj91@gmail.com) on 07/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum NetworkServiceType implements FermatEnum {

    /*
     * For doing the code more readable, please keep the elements in the Enum sorted alphabetically.
     * Network Service prefix or Type sufix are not necessary having in count that the name of the plugin is NETWORK SERVICE TYPE.,
     */

    ASSET_USER_ACTOR                 ("AUANS"),
    ASSET_ISSUER_ACTOR               ("AIANS"),
    ASSET_REDEEM_POINT_ACTOR         ("ARPANS"),
    ASSET_TRANSMISSION               ("ASS_TRANS"),
    CRYPTO_ADDRESSES                 ("CADD"),
    CRYPTO_BROKER                    ("CRBR"),
    CRYPTO_PAYMENT_REQUEST           ("CPR"),
    CRYPTO_TRANSMISSION              ("CRY_TRANS"),
    INTRA_USER                       ("INT_USR"),
    TEMPLATE                         ("TEMP"),
    TRANSACTION_TRANSMISSION         ("TRTX"),
    UNDEFINED                        ("UNDEF"),

    ;

    private String code;

    NetworkServiceType(String code){
        this.code = code;
    }

    public static NetworkServiceType getByCode(final String code) throws InvalidParameterException {

        switch (code){

            case "AUANS"     : return ASSET_USER_ACTOR;
            case "AIANS"     : return ASSET_ISSUER_ACTOR;
            case "ARPANS"    : return ASSET_REDEEM_POINT_ACTOR;
            case "ASS_TRANS" : return ASSET_TRANSMISSION;
            case "CADD"      : return CRYPTO_ADDRESSES;
            case "CRBR"      : return CRYPTO_BROKER;
            case "CPR"       : return CRYPTO_PAYMENT_REQUEST;
            case "CRY_TRANS" : return CRYPTO_TRANSMISSION;
            case "INT_USR"   : return INTRA_USER;
            case "TEMP"      : return TEMPLATE;
            case "TRTX"      : return TRANSACTION_TRANSMISSION;
            case "UNDEF"     : return UNDEFINED;

            default: throw new InvalidParameterException(
                    "Code received: "+code,
                    "The code received is not valid for NetworkServiceType enum."
            );
        }

    }

    @Override
    public String getCode() {
        return this.code;
    }

}
