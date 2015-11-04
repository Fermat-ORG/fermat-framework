package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_identity.translator.exceptions.CantSingMessageException;
import com.bitdubai.fermat_api.layer.dmp_identity.translator.interfaces.TranslatorIdentity;

/**
 * Created by rodrigo on 9/10/15.
 */
public class myTranslatorIdentity implements TranslatorIdentity {
    String alias;
    String publicKey;

    public myTranslatorIdentity() {
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
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
