package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

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
        return null;
    }

    @Override
    public String getPublicKey() {
        return null;
    }

    @Override
    public String createMessageSignature(String mensage) throws CantSingMessageException {
        return null;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
