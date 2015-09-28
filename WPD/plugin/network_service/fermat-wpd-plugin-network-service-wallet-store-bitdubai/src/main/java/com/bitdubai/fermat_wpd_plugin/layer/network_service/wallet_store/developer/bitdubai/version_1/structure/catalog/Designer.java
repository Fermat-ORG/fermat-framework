package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog;

import com.bitdubai.fermat_api.layer.dmp_identity.designer.exceptions.CantSingMessageException;
import com.bitdubai.fermat_api.layer.dmp_identity.designer.interfaces.DesignerIdentity;
import java.util.UUID;

/**
 * Created by rodrigo on 7/22/15.
 */
public class Designer implements DesignerIdentity {
    UUID id;
    String alias;
    String publicKey;

    /**
     * Default constructor
     */
    public Designer() {
    }

    /**
     * Overloaded constructor
     * @param id
     * @param alias
     * @param publicKey
     */
    public Designer(UUID id, String alias, String publicKey) {
        this.id = id;
        this.alias = alias;
        this.publicKey = publicKey;
    }

    @Override
    public String getAlias() { return alias; }

    /**
     * Get the public key of the represented designer
     * @return String publicKey
     */
    @Override
    public String getPublicKey() {
        return this.publicKey;
    }
    /**
     Sign a message with designer private key
     * @param  mensage to sign
     * @return string signed message
     * @throws CantSingMessageException
     */

    @Override
    public String createMessageSignature(String mensage) throws CantSingMessageException
    {
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    public UUID getId() {
        return this.id;
    }

    public void setiD(UUID id) {
        this.id = id;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
