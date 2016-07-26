package com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;

import java.io.Serializable;


/**
 * Created by jorgegonzalez on 2015.09.15..
 */
public interface CryptoBrokerIdentity extends ActorIdentity, Serializable {

    /**
     * This method returns the CryptoBrokerIdentityExtraData from this identity.
     *
     * @return
     */
    CryptoBrokerIdentityExtraData getCryptoBrokerIdentityExtraData();

}
