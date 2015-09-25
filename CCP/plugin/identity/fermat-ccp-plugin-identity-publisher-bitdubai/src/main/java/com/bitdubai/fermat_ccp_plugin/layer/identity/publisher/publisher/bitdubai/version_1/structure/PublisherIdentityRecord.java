package com.bitdubai.fermat_ccp_plugin.layer.identity.publisher.publisher.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.ccp_identity.publisher.exceptions.CantSingMessageException;
import com.bitdubai.fermat_api.layer.ccp_identity.publisher.interfaces.PublisherIdentity;

/**
 * The class <code>com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.structure.PublisherIdentityRecord</code>
 * represents a developer identity record.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/07/15.
 * Updated by Raul Pena   - (raul.pena@gmail.com)  on 16/07/15.
 *
 * @version 1.0
 */
public class PublisherIdentityRecord implements PublisherIdentity {

    private String alias;
    private String publicKey;
    private String privateKey;
    private String websiteurl;

    public PublisherIdentityRecord() {
        super ();
    }

    public PublisherIdentityRecord(String alias, String publicKey, String privateKey, String websiteurl) {

        super ();

        this.alias = alias;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.websiteurl = websiteurl;
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

    @Override
    public String getWebsiteurl() {
        return websiteurl;
    }

    public void setPublicKey (String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public String createMessageSignature(String mensage) throws CantSingMessageException {

        try{

            /*
             * Get the message hash
             */
            String messageHash = AsymmectricCryptography.encryptMessagePublicKey(mensage, publicKey);

            /*
             * Create the message signature
             */
            return AsymmectricCryptography.createMessageSignature(messageHash, privateKey);
        }
        catch(Exception e)
        {
            throw new CantSingMessageException("Fatal Error Signed message",e,"","");
        }


    }
}
