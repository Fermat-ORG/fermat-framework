package com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateMessageSignatureException;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.interfaces.CryptoBrokerIdentity;

/**
 * Created by jorge on 28-09-2015.
 */
public class CryptoBrokerIdentityImpl implements DealsWithPluginFileSystem, CryptoBrokerIdentity {

    private final String alias;
    private final ECCKeyPair keyPair;
    private byte[] profileImage;
    private final PluginFileSystem pluginFileSystem;

    public CryptoBrokerIdentityImpl(final String alias, final ECCKeyPair keyPair, final byte[] profileImage, final PluginFileSystem pluginFileSystem){
        this.alias = alias;
        this.keyPair = keyPair;
        this.profileImage = profileImage;
        this.pluginFileSystem = pluginFileSystem;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getPublicKey() {
        return keyPair.getPublicKey();
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
            return AsymmectricCryptography.createMessageSignature(message, keyPair.getPrivateKey());
        } catch(Exception ex){
            throw new CantCreateMessageSignatureException(CantCreateMessageSignatureException.DEFAULT_MESSAGE, ex, "Message: "+ message, "The message could be invalid");
        }
    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {

    }
}
