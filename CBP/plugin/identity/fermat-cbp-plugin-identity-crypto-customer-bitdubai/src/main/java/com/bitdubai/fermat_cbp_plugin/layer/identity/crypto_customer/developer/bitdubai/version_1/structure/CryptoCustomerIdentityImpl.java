package com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateMessageSignatureException;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_customer.interfaces.CryptoCustomerIdentity;

/**
 * Created by jorge on 28-09-2015.
 */
public class CryptoCustomerIdentityImpl implements CryptoCustomerIdentity, DealsWithPluginFileSystem {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 4259;
    private static final int HASH_PRIME_NUMBER_ADD = 3089;

    private final String alias;
//    private final KeyPair keyPair;
    private String publicKey;
    private String privateKey;
    private byte[] profileImage;
    private PluginFileSystem pluginFileSystem;

//    public CryptoCustomerIdentityImpl(final String alias, final KeyPair keyPair, final byte[] profileImage, final PluginFileSystem pluginFileSystem){
    public CryptoCustomerIdentityImpl(final String alias, String publicKey, String privateKey, final byte[] profileImage, PluginFileSystem pluginFileSystem){
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
        this.profileImage = imageBytes;
    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFile) { this.pluginFileSystem = pluginFile; }

    @Override
    public String createMessageSignature(String message) throws CantCreateMessageSignatureException{
        try{
//            return AsymmetricCryptography.createMessageSignature(message, keyPair.getPrivateKey());
            return AsymmetricCryptography.createMessageSignature(message, this.privateKey);
        } catch(Exception ex){
            throw new CantCreateMessageSignatureException(CantCreateMessageSignatureException.DEFAULT_MESSAGE, ex, "Message: "+ message, "The message could be invalid");
        }
    }

    /*@Override
    public boolean equals(Object o){
        if(!(o instanceof CryptoCustomerIdentity))
            return false;
        CryptoCustomerIdentity compare = (CryptoCustomerIdentity) o;
        return alias.equals(compare.getAlias()) && keyPair.getPublicKey().equals(compare.getPublicKey());
    }

    @Override
    public int hashCode(){
        int c = 0;
        c += alias.hashCode();
        c += keyPair.hashCode();
        return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }*/
}
