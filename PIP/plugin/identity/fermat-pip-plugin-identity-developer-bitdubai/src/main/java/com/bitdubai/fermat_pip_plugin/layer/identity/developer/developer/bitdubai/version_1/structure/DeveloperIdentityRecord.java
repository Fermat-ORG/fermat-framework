package com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantSingMessageException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.interfaces.DeveloperIdentity;

/**
 * The class <code>com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.structure.DeveloperIdentityRecord</code>
 * represents a developer identity record.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/07/15.
 * Updated by Raul Pena   - (raul.pena@gmail.com)  on 16/07/15.
 *
 * @version 1.0
 */
public class DeveloperIdentityRecord implements DeveloperIdentity {

    private String alias;
    private String publicKey;
    private String privateKey;

    public DeveloperIdentityRecord () {

        super ();
    }

    public DeveloperIdentityRecord (String alias, String publicKey, String privateKey) {

        super ();

        this.alias = alias;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    @Override
    public String getAlias () {
        return alias;
    }

    public void setAlias (String alias) {
        this.alias = alias;
    }

    @Override
    public String getPublicKey () {
        return publicKey;
    }


    public void setPublicKey (String publicKey) {
        this.publicKey = publicKey;
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
