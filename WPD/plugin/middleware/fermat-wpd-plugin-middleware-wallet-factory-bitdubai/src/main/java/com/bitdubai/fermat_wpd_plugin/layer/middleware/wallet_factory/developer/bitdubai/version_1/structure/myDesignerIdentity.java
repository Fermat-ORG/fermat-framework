package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_identity.designer.exceptions.CantSingMessageException;
import com.bitdubai.fermat_api.layer.dmp_identity.designer.interfaces.DesignerIdentity;

/**
 * Created by rodrigo on 9/10/15.
 */
public class myDesignerIdentity implements DesignerIdentity {
    String alias;
    String publicKey;

    public myDesignerIdentity() {
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public String createMessageSignature(String mensage) throws CantSingMessageException {
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
