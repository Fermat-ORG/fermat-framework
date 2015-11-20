package com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantGetCryptoBrokerIdentityException;

import java.util.List;

/**
 * Created by jorgegonzalez on 2015.09.15..
 */
public interface CryptoBrokerIdentityManager {

    List<CryptoBrokerIdentity> getAllCryptoBrokersFromCurrentDeviceUser() throws CantGetCryptoBrokerIdentityException;

    CryptoBrokerIdentity createCryptoBrokerIdentity(final String alias, final byte[] profileImage) throws com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantCreateCryptoBrokerIdentityException;

}
