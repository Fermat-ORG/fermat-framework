package com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateMessageSignatureException;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.interfaces.CryptoBrokerIdentity;

/**
 * Created by jorge on 28-09-2015.
 */
public class CryptoBrokerIdentityImpl implements DealsWithPluginFileSystem, CryptoBrokerIdentity {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 7681;
    private static final int HASH_PRIME_NUMBER_ADD = 3581;

    private final String alias;
//    private final KeyPair keyPair;
    private String publicKey;
    private String privateKey;
    private byte[] profileImage;
    private final PluginFileSystem pluginFileSystem;

//    public CryptoBrokerIdentityImpl(final String alias, final KeyPair keyPair, final byte[] profileImage, final PluginFileSystem pluginFileSystem){
    public CryptoBrokerIdentityImpl(final String alias, String publicKey, String privateKey, final byte[] profileImage, final PluginFileSystem pluginFileSystem){
        this.alias = alias;
//        this.keyPair = keyPair;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.profileImage = profileImage;
        this.pluginFileSystem = pluginFileSystem;
    }

    @Override
    public String getAlias() {
        return alias;
    }

//    @Override
//    public String getPublicKey() {
//        return keyPair.getPublicKey();
//    }

    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    @Override
    public byte[] getProfileImage() {
        return profileImage;
    }

    @Override
    public void setNewProfileImage(byte[] imageBytes) {

    }

    @Override
    public String createMessageSignature(String message) throws CantCreateMessageSignatureException{
        try{
//            return AsymmectricCryptography.createMessageSignature(message, keyPair.getPrivateKey());
            return AsymmectricCryptography.createMessageSignature(message, this.privateKey);
        } catch(Exception ex){
            throw new CantCreateMessageSignatureException(CantCreateMessageSignatureException.DEFAULT_MESSAGE, ex, "Message: "+ message, "The message could be invalid");
        }
    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {

    }
/*
    @Override
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
    */
}
