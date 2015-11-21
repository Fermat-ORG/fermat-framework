package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;

/**
 * Created by Nerio on 10/09/15.
 */
public interface ActorAssetIssuer {

    /**
     * The metho <code>getPublicKey</code> gives us the public key of the represented Asset Issuer
     *
     * @return the public key
     */
    String getPublicKey();

    /**
     * The method <code>getName</code> gives us the name of the represented Asset Issuer
     *
     * @return the name of the intra user
     */
    String getName();

    /**
     * The method <code>getRegistrationDate</code> gives us the date when both Asset Issuers
     * exchanged their information and accepted each other as contacts.
     *
     * @return the date
     */
    long getRegistrationDate();

    /**
     * The method <code>getLastConnectionDate</code> gives us the Las Connection Date of the represented
     * Asset Issuer
     *
     * @return the Connection Date
     */
    long getLastConnectionDate();

    /**
     * The method <coda>getProfileImage</coda> gives us the profile image of the represented Asset Issuer
     *
     * @return the image
     */
    byte[] getProfileImage();

    /**
     * The method <code>getConnectionState</code> gives us the connection state of the represented
     * Asset Issuer
     *
     * @return the Connection state
     */
    DAPConnectionState getDapConnectionState();

    /**
     * Método {@code getDescription}
     * The Method return a description about Issuer
     * acerca de él mismo.
     *
     * @return {@link String} con la descripción del {@link ActorAssetIssuer}
     */
    String getDescription();

    /**
     * The method <code>getLocation</code> gives us the Location of the represented
     * Asset Issuer
     *
     * @return the Location
     */
    Location getLocation();

    /**
     * The method <code>getLocationLatitude</code> gives us the Location of the represented
     * Asset Issuer
     *
     * @return the Location Latitude
     */
    Double getLocationLatitude();

    /**
     * The method <code>getLocationLongitude</code> gives us the Location of the represented
     * Asset Issuer
     *
     * @return the Location Longitude
     */
    Double getLocationLongitude();

    /**
     * returns the crypto address to which it belongs
     *
     * @return CryptoAddress instance.
     */
//    CryptoAddress getCryptoAddress();
}
