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
     * The metho <code>getPublicKey</code> gives us the public key of the represented Asset user
     *
     * @return the public key
     */
    String getPublicKey();

    /**
     * The method <code>getName</code> gives us the name of the represented Asset user
     *
     * @return the name of the intra user
     */
    String getName();

    /**
     * The method <code>getAge</code> gives us the Age of the represented Asset user
     *
     * @return the Location of the Asset user
     */
    String getAge();

    /**
     * The method <code>getGender</code> gives us the Gender of the represented Asset user
     *
     * @return the Gender of the Asset user
     */
    Genders getGender();

    /**
     * The method <code>getConnectionState</code> gives us the ConnectionState state of the represented Asset
     * user
     *
     * @return the Connection state
     */
    ConnectionState getConnectionState();

    /**
     * The method <code>getContactRegistrationDate</code> gives us the date when both Asset users
     * exchanged their information and accepted each other as contacts.
     *
     * @return the date
     */
    long getRegistrationDate();

    long getLastConnectionDate();

    Double getLocationLatitude();

    Double getLocationLongitude();

    /**
     * The method <coda>getProfileImage</coda> gives us the profile image of the represented Asset user
     *
     * @return the image
     */
    byte[] getProfileImage();

    /**
     * The method <code>getLocation</code> gives us the Location of the represented Asset user
     *
     * @return the Location of the Asset user
     */
//    Location getLocation();

    /**
     * returns the crypto address to which it belongs
     * @return CryptoAddress instance.
     */
    CryptoAddress getCryptoAddress();
}
