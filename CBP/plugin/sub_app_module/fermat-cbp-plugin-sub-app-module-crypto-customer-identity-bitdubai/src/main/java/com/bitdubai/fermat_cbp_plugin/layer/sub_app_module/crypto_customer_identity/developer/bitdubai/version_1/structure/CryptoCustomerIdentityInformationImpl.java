package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_customer_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_cbp_api.all_definition.enums.IdentityPublished;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateMessageSignatureException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityInformation;

/**
 * Created by angel on 15/10/15.
 */
public class CryptoCustomerIdentityInformationImpl implements CryptoCustomerIdentityInformation {

    private final String alias;
    private String publicKey;
    private String privateKey;
    private byte[] profileImage;
    private IdentityPublished publicKeyPublished;

    public CryptoCustomerIdentityInformationImpl(final String alias, String publicKey, String privateKey, final byte[] profileImage, IdentityPublished publicKeyPublished) {
        this.alias = alias;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.profileImage = profileImage;
        this.publicKeyPublished = publicKeyPublished;
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
    public IdentityPublished getPublicKeyPublished() {
        return this.publicKeyPublished;
    }

    @Override
    public String createMessageSignature(String message) throws CantCreateMessageSignatureException {
        try {
            return AsymmetricCryptography.createMessageSignature(message, this.privateKey);
        } catch (Exception ex) {
            throw new CantCreateMessageSignatureException(CantCreateMessageSignatureException.DEFAULT_MESSAGE, ex, "Message: " + message, "The message could be invalid");
        }
    }
}
