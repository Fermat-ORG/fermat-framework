package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerExtraData</code>
 * contains all the extra information about a crypto broker available.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 06/02/2016.
 */
public interface CryptoBrokerExtraData<T extends CryptoBrokerInfo> {

    UUID getRequestId();

    String getRequesterPublicKey();

    Actors getRequesterActorType();

    String getCryptoBrokerPublicKey();

    long getUpdateTime();

    List<T> listInformation();

}
