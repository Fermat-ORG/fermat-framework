package com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.enums.MessageTypes</code>
 * enumerates the different types of messages transmitted by the crypto broker actor network service cbp plugin.
 * <p>
 * Created by Gabriel Araujo.
 */
public enum MessageTypes implements FermatEnum {

    CONNECTION_INFORMATION ("INF"),
    CONNECTION_REQUEST     ("REQ"),
    INFORMATION_REQUEST    ("IRQ"),
    ;

    private String code;

    MessageTypes(String code) {
        this.code = code;
    }

    public static MessageTypes getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "INF": return CONNECTION_INFORMATION;
            case "REQ": return CONNECTION_REQUEST    ;
            case "IRQ": return INFORMATION_REQUEST   ;

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