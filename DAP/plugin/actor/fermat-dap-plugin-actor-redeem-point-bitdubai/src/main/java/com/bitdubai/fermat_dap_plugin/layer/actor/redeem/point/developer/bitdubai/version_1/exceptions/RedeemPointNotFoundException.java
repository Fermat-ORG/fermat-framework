package com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Esta excepción representa que se intentó hacer alguna operación
 * directa (Update o Delete) a un RedeemPoint específico, apuntando
 * a su PublicKey, y ésta no se ha encontrado. Por lo que es necesario
 * alertar al consumidor de que esto ha ocurrido.
 * <p></p>
 * ------------------------------------------------------------------
 * <p></p>
 * This exception represents an attempt to do some operation on a
 * specific RedeemPoint using its PublicKey, if that Key wasn't found
 * in the database then in most of the cases nothing would happen but
 * I need to inform the consumer that no records were found.
 * Created by Víctor A. Mars M. on 20/10/15.
 */
public class RedeemPointNotFoundException extends FermatException {

    private static String DEFAULT_MESSAGE = "The provided RedeemPoint's public key wasn't found in the database.";

    public RedeemPointNotFoundException(String context) {
        super(DEFAULT_MESSAGE, null, context, null);
    }

    public RedeemPointNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
