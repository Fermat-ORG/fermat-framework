package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerQuote;

import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 19/02/16.
 */

public interface CryptoBrokerExtraDataInfo {

    UUID getRequestId();

    String getRequesterPublicKey();

    Actors getRequesterActorType();

    String getCryptoBrokerPublicKey();

    long getUpdateTime();

    List<CryptoBrokerQuote> listInformation();

}
