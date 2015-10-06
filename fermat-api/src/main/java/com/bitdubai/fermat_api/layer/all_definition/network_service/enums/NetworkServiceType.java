/*
 * @#NetworkServiceType.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.all_definition.network_service.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType</code> represent
 * all types that a network service cam be.
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 01/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum NetworkServiceType implements FermatEnum {

    /**
     * Please keep the elements sorted alphabetically.
     */

    CRYPTO_PAYMENT_REQUEST("CRY_PAY_REQ"),
    CRYPTO_TRANSMISSION   ("CRY_TRAN"   ),
    INTRA_USER            ("INT_USR"    ),
    TEMPLATE              ("TEMP"       ),
    UNDEFINED             ("UNDEF"      );

    /**
     * Represent the code
     */
    private String code;

    /**
     * Constructor whit parameter
     *
     * @param code
     */
    NetworkServiceType(String code){
        this.code = code;
    }

    /**
     * Return the NetworkServiceType represented by the code pass as parameter
     *
     * @param code
     * @return PlatformComponentType
     */
    public static NetworkServiceType getByCode(final String code){

        switch (code){

            case "CRY_PAY_REQ": return CRYPTO_PAYMENT_REQUEST;
            case "CRY_TRAN"   : return CRYPTO_TRANSMISSION   ;
            case "INT_USR"    : return INTRA_USER            ;
            case "TEMP"       : return TEMPLATE              ;
            case "UNDEF"      : return UNDEFINED             ;

            default: throw new IllegalArgumentException();
        }

    }

    /**
     * Return the code representation
     *
     * @return String
     */
    @Override
    public String getCode() {
        return this.code;
    }

}
