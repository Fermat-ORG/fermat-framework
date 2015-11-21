package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.enums.MessageTypes</code>
 * enumerates the different types of messages transmitted by the crypto payment request network service ccp plugin.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/10/2015.
 */
public enum MessageTypes implements FermatEnum {

    INFORMATION("INF"),
    REQUEST    ("REQ")

    ;

    private String code;

    MessageTypes(String code) {
        this.code = code;
    }

    public static MessageTypes getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "INF": return INFORMATION;
            case "REQ": return REQUEST    ;

            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "This code is not valid for the MessageTypes enum."
                );
        }

    }

    @Override
    public String getCode() {
        return this.code;
    }

}