package com.bitdubai.fermat_dmp_plugin.layer.identity.translator.developer.bitdubai.version_1.estructure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.dmp_identity.translator.exceptions.CantSingMessageException;
import com.bitdubai.fermat_api.layer.dmp_identity.translator.interfaces.TranslatorIdentity;

/**
 * Created by natalia on 31/07/15.
 */

/**
 * The class <code>com.bitdubai.fermat_pip_plugin.layer.identity_translator.developer.bitdubai.version_1.estructure.IdentityTranslatorTranslator</code>
 * represents a translator identity record.
 *
 * Created by  natalia on 31/07/15
 *
 * @version 1.0
 */
public class IdentityTranslatorTranslator implements TranslatorIdentity {

    private String alias;
    private String publicKey;
    private String privateKey;

    /**
     * Constructor
     * @param alias
     * @param publicKey
     * @param privateKey
     */
    public IdentityTranslatorTranslator (String alias, String publicKey, String privateKey) {

        this.alias = alias;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    /**
     * Translator Interface implementation
     */

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    @Override
    public String createMessageSignature(String mensage) throws CantSingMessageException {
        try{
            return AsymmetricCryptography.createMessageSignature(mensage, privateKey);
        }
        catch(Exception e)
        {
            throw new CantSingMessageException("Fatal Error Signed message",e,"","");
        }
    }
}
