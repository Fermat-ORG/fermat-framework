package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_local_community.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;

import java.util.UUID;

/**
 * Created by eorono on 31/03/16.
 */
public interface ChtActorLocalCommunityInformation {

    String getPublickey();

    String getAlias();

    byte[] getImage();

    ConnectionState getConnectionState();

    UUID getConnectionId();



}
