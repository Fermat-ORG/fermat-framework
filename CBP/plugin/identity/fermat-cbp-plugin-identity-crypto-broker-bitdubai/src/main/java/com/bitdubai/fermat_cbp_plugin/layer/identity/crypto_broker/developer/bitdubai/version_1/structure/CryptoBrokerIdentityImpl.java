package com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
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
    private String publicKey;
    private String privateKey;
    private byte[] profileImage;
    private PluginFileSystem pluginFileSystem;

    public CryptoBrokerIdentityImpl(final String alias, String publicKey, String privateKey, final byte[] profileImage, PluginFileSystem pluginFileSystem){
        this.alias = alias;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.profileImage = profileImage;
        this.pluginFileSystem = pluginFileSystem;
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public String getPublicKey() {
        return this.publicKey;
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
    public void setPluginFileSystem(PluginFileSystem pluginFile) { this.pluginFileSystem = pluginFile; }

    @Override
    public String createMessageSignature(String message) throws CantCreateMessageSignatureException{
        try{
            return AsymmetricCryptography.createMessageSignature(message, this.privateKey);
        } catch(Exception ex){
            throw new CantCreateMessageSignatureException(CantCreateMessageSignatureException.DEFAULT_MESSAGE, ex, "Message: "+ message, "The message could be invalid");
        }
    }

}
