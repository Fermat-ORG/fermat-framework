package com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.exceptions.CantCreateCryptoBrokerIdentityException;

import java.util.List;

/**
 * Created by jorgegonzalez on 2015.09.15..
 */
public interface CryptoBrokerIdentityManager {

    List<CryptoBrokerIdentity> getAllCryptoBrokersFromCurrentDeviceUser();

    CryptoBrokerIdentity createCryptoBrokerIdentity(final String alias, final byte[] profileImage) throws CantCreateCryptoBrokerIdentityException;

}
