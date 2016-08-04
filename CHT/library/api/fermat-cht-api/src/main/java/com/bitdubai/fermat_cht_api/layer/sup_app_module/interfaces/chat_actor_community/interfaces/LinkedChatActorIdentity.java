package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces;

import java.util.UUID;

/**
 * Created by Eleazar (eorono@protonmail.com) on 3/04/16.
 */
public interface LinkedChatActorIdentity {

    UUID getConnectionId();


    String getPublicKey();


    String getAlias();


    byte[] getImage();


}
