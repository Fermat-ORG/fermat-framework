package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces;

import java.util.UUID;

/**
 * Created by Eleazar (eorono@protonmail.com) on 3/04/16.
 */
public interface LinkedChatActorIdentity {

    UUID getConnectionId();

    /**
     * The method <code>getPublicKey</code> returns the public key of the represented crypto broker
     * @return the public key of the crypto broker
     */
    String getPublicKey();

    /**
     * The method <code>getAlias</code> returns the name of the represented crypto broker
     *
     * @return the name of the crypto broker
     */
    String getAlias();

    /**
     * The method <code>getProfileImage</code> returns the profile image of the represented crypto broker
     *
     * @return the profile image
     */
    byte[] getImage();

    /**
     * The method <code>listCryptoBrokerWallets</code> returns the list of the public crypto customer wallets
     * @return
     */
}
