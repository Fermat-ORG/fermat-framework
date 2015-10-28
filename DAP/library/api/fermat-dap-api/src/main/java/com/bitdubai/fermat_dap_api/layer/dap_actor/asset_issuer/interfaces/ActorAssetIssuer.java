package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;

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
     * The method <code>getContactRegistrationDate</code> gives us the date when both Asset Issuers
     * exchanged their information and accepted each other as contacts.
     *
     * @return the date
     */
    long getRegistrationDate();

    /**
     * The method <coda>getProfileImage</coda> gives us the profile image of the represented Asset Issuer
     *
     * @return the image
     */
    byte[] getProfileImage();

    /**
     * The method <code>getContactState</code> gives us the contact state of the represented Asset
     * Issuer
     *
     * @return the contact state
     */
    ConnectionState getConnectionState();

    /**
     * Método {@code getDescription}
     * Este método retorna la descripción o información personal que el Issuer haya configurado
     * acerca de él mismo.
     *
     * @return {@link String} con la descripción del {@link ActorAssetIssuer}
     */
    String getDescription();

    /**
     * Método {@code getLocation}
     * Este método retorna la ubicación geográfica del Actor Issuer.
     * Sólo se utiliza Longitude y Latitude para esto.
     *
     * @return {@link Location} con la ubicación del {@link ActorAssetIssuer}
     */
    Location getLocation();

    Double getLocationLatitude();

    Double getLocationLongitude();

    /**
     * Método {@code getCryptoAddress}
     * Este método retorna la dirección criptográfica del
     * Issuer.
     *
     * @return {@link CryptoAddress} con la dirección criptoráfica del {@link ActorAssetIssuer}
     */
    CryptoAddress getCryptoAddress();

}
