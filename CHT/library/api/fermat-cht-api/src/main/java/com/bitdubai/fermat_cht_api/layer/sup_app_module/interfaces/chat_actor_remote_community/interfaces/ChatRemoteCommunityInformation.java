package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_remote_community.interfaces;


/**
 * Created by Eleazar (eorono@protonmail.com) on 3/04/16.
 */

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;

import java.util.List;
import java.util.UUID;


public interface ChatRemoteCommunityInformation {

    String getPublicKey();


    String getAlias();


    byte[] getImage();


    List listCryptoCustomerWallets();


    ConnectionState getConnectionState();


    UUID getConnectionId();
}

