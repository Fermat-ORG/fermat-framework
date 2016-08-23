package com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;

/**
 * Created by Gabriel Araujo on 16/08/16.
 */
public interface MetadataToSend {

    PlatformComponentType getLocalActorType();

    String getLocalActorPublicKey();

    PlatformComponentType getRemoteActorType();

    String getRemoteActorPublicKey();

    String getDate();

    String toJson();

}
