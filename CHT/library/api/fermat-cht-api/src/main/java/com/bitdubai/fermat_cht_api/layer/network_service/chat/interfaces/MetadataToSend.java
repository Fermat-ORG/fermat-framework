package com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces;

/**
 * Created by Gabriel Araujo on 16/08/16.
 */
public interface MetadataToSend {

    String getLocalActorPublicKey();

    String getRemoteActorPublicKey();

    String getDate();

    String toJson();

}
