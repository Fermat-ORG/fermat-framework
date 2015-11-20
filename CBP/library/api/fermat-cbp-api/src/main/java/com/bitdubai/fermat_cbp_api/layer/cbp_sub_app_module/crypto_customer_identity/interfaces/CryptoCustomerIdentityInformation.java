package com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_customer_identity.interfaces;

/**
 * Created by natalia on 16/09/15.
 */

import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityInformation</code>
 * provides the method to extract information about an crypto Customer identity.
 */
public interface CryptoCustomerIdentityInformation {
    String getAlias();
    byte[] getProfileImage();
    String getPublicKey();
    boolean isPublished();

}

