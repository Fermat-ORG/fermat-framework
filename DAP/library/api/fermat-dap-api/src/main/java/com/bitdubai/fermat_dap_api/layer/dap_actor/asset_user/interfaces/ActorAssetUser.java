package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;

/**
 * Created by Nerio on 10/09/15.
 */
public interface ActorAssetUser {

    /**
     * The metho <code>getPublicKey</code> gives us the public key of the represented Asset user
     *
     * @return the public key
     */
    public String getPublicKey();

    /**
     * The method <code>getName</code> gives us the name of the represented Asset user
     *
     * @return the name of the intra user
     */
    public String getName();

    /**
     * The method <code>getContactRegistrationDate</code> gives us the date when both Asset users
     * exchanged their information and accepted each other as contacts.
     *
     * @return the date
     */
    public long getContactRegistrationDate();

    /**
     * The method <coda>getProfileImage</coda> gives us the profile image of the represented Asset user
     *
     * @return the image
     */
    public byte[] getProfileImage();

    /**
     * The method <code>getConnectionState</code> gives us the ConnectionState state of the represented Asset
     * user
     *
     * @return the contact state
     */
    public ConnectionState getConnectionState();

}
