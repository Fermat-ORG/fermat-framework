package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces;

import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoCustomerIdentityInformation</code>
 * provides the method to extract information about an crypto broker identity.
 *
 * Created by natalia on 16/09/15.
 * Updated by lnacosta (laion.cj91@gmail.com) on 25/11/2015.
 */
public interface CryptoBrokerIdentityInformation {

    String getAlias();

    String getPublicKey();

    byte[] getProfileImage();

    boolean isPublished();

}

