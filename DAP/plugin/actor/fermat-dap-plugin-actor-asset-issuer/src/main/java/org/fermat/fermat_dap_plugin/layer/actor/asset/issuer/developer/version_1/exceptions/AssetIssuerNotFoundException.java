package org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Esta excepción representa que se intentó hacer alguna operación
 * directa (Update o Delete) a un AssetIssuer específico, apuntando
 * a su PublicKey, y ésta no se ha encontrado. Por lo que es necesario
 * alertar al consumidor de que esto ha ocurrido.
 * <p></p>
 * ------------------------------------------------------------------
 * <p></p>
 * This exception represents an attempt to do some operation on a
 * specific AssetIssuer using its PublicKey, if that Key wasn't found
 * in the database then in most of the cases nothing would happen but
 * I need to inform the consumer that no records were found.
 * Created by Víctor A. Mars M. on 20/10/15.
 */
public class AssetIssuerNotFoundException extends FermatException {

    private static String DEFAULT_MESSAGE = "The provided AssetIssuer's public key wasn't found in the database.";

    public AssetIssuerNotFoundException(String context) {
        super(DEFAULT_MESSAGE, null, context, null);
    }

    /**
     * {@inheritDoc}
     */
    public AssetIssuerNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
