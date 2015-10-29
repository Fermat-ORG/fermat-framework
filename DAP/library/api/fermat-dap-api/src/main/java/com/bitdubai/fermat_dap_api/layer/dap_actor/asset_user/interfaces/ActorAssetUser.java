package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;

/**
 * Created by Nerio on 10/09/15.
 */
public interface ActorAssetUser {

    /**
     * The method <code>getPubliclinkedIdentity</code> gives us the public Linked Identity of the represented Asset User
     *
     * @return the Public Linked Identity
     */
    String getPublicLinkedIdentity();

    /**
     * The method <code>getPublicKey</code> gives us the public key of the represented Asset User
     *
     * @return the public key
     */
    String getPublicKey();

    /**
     * The method <code>getName</code> gives us the name of the represented Asset User
     *
     * @return the name of the intra user
     */
    String getName();

    /**
     * The method <code>getAge</code> gives us the Age of the represented Asset user
     *
     * @return the Age of the Asset user
     */
    String getAge();

    /**
     * The method <code>getGenders</code> gives us the Gender of the represented Asset user
     *
     * @return the Gender of the Asset user
     */
    Genders getGenders();

    /**
     * The method <code>getRegistrationDate</code> gives us the date when both Asset Users
     * exchanged their information and accepted each other as contacts.
     *
     * @return the date
     */
    long getRegistrationDate();

    /**
     * The method <code>getLastConnectionDate</code> gives us the Las Connection Date of the represented Asset User
     *
     * @return the Connection Date
     */
    long getLastConnectionDate();

    /**
     * The method <code>getConnectionState</code> gives us the connection state of the represented Asset User
     *
     * @return the Connection state
     */
    ConnectionState getConnectionState();

    /**
     * The method <code>getLocation</code> gives us the Location of the represented Asset user
     *
     * @return the Location of the Asset user
     */
    Location getLocation();

    /**
     * The method <code>getLocationLatitude</code> gives us the Location of the represented Asset user
     *
     * @return the Location Latitude of the Asset user
     */
    Double getLocationLatitude();

    /**
     * The method <code>getLocationLongitude</code> gives us the Location of the represented Asset user
     *
     * @return the Location Longitude of the Asset user
     */
    Double getLocationLongitude();

    /**
     * The method <coda>getProfileImage</coda> gives us the profile image of the represented Asset User
     *
     * @return the image
     */
    byte[] getProfileImage();

    /**
     * returns the crypto address to which it belongs
     *
     * @return CryptoAddress instance.
     */
    CryptoAddress getCryptoAddress();
}
