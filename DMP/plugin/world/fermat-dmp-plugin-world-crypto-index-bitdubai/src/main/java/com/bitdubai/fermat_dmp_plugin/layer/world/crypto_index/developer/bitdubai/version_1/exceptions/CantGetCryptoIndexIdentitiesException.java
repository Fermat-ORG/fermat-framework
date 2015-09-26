package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by francisco on 10/09/15.
 */
public class CantGetCryptoIndexIdentitiesException extends FermatException {
    /**
     * This is the constructor that every inherited FermatException must implement
     *
     * @param message        the short description of the why this exception happened, there is a public static constant called DEFAULT_MESSAGE that can be used here
     * @param context        a String that provides the values of the variables that could have affected the exception
     * @param possibleReason an explicative reason of why we believe this exception was most likely thrown
     */
    public CantGetCryptoIndexIdentitiesException(String message, String context, String possibleReason) {
        super(message, null, context, possibleReason);
    }
}
