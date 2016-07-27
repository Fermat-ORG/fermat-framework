package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces;

/**
 * Created by natalia on 16/09/15.
 */

import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;

import java.io.Serializable;

/**
 * The interface <code>CryptoCustomerIdentityInformation</code>
 * provides the method to extract information about an crypto Customer identity.
 */
public interface CryptoCustomerIdentityInformation extends Serializable {

    String getAlias();

    byte[] getProfileImage();

    String getPublicKey();

    boolean isPublished();

    long getAccuracy();

    GeoFrequency getFrequency();
}

