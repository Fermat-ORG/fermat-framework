package com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_customer.interfaces;

/**
 * Created by jorgegonzalez on 2015.09.15..
 */
public interface CryptoCustomerIdentity {

    String getAlias();

    String getPublicKey();

    byte[] getProfileImage();

    void setNewProfileImage(final byte[] imageBytes);

    String createMessageSignature(String message);
}
