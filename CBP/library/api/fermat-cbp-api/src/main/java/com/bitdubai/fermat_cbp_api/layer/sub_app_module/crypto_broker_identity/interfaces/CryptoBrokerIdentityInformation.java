package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces;

/**
 * Created by natalia on 16/09/15.
 */

import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoCustomerIdentityInformation</code>
 * provides the method to extract information about an crypto broker identity.
 */
public interface CryptoBrokerIdentityInformation {
    String getAlias();
    String getPublicKey();
    byte[] getProfileImage();
    boolean isPublished();
}

