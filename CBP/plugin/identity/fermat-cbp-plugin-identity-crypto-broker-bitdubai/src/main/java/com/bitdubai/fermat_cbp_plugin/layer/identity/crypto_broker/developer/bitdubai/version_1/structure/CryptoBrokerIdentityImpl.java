package com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateMessageSignatureException;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.enums.IdentityPublished;

/**
 * Created by jorge on 28-09-2015.
 * Modified by Yordin Alayn 10.09.15
 */
public class CryptoBrokerIdentityImpl implements CryptoBrokerIdentity {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 7681;
    private static final int HASH_PRIME_NUMBER_ADD = 3581;

    private final String alias;
    private final KeyPair keyPair;
    private byte[] profileImage;
    private boolean published;

    public CryptoBrokerIdentityImpl(final String alias, final KeyPair keyPair, final byte[] profileImage, final boolean published){
        this.alias = alias;
        this.keyPair = keyPair;
        this.profileImage = profileImage;
        this.published = published;
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public String getPublicKey() {
        return this.keyPair.getPublicKey();
    }

    @Override
    public byte[] getProfileImage() {
        return this.profileImage;
    }

    @Override
    public void setNewProfileImage(byte[] imageBytes) {
        this.profileImage = imageBytes;
    }

    @Override
    public boolean isPublished(){ return this.published; }

    @Override
    public String createMessageSignature(String message) throws CantCreateMessageSignatureException{
        try{
            return AsymmetricCryptography.createMessageSignature(message, this.keyPair.getPrivateKey());
        } catch(Exception ex){
            throw new CantCreateMessageSignatureException(CantCreateMessageSignatureException.DEFAULT_MESSAGE, ex, "Message: "+ message, "The message could be invalid");
        }
    }

    public boolean equals(Object o){
        if(!(o instanceof CryptoBrokerIdentity))
            return false;
        CryptoBrokerIdentity compare = (CryptoBrokerIdentity) o;
        return alias.equals(compare.getAlias()) && keyPair.getPublicKey().equals(compare.getPublicKey());
    }

    @Override
    public int hashCode(){
        int c = 0;
        c += alias.hashCode();
        c += keyPair.hashCode();
        return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }

}
