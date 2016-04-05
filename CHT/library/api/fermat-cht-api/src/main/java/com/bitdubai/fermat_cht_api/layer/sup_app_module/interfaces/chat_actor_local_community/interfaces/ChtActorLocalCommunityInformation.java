package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_local_community.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;

import java.util.List;
import java.util.UUID;

/**
* Created by Eleazar Oroño (eorono@protonmail.com) on 31/03/16.
 */
public interface ChtActorLocalCommunityInformation {

    String getActorPublickey();

    String getLocalActorAlias();

    byte[] getActorImage();

    List listActorLocalAlias();

    ConnectionState getActorConnectionState();

    UUID getLocalActorConnectionId();



}
