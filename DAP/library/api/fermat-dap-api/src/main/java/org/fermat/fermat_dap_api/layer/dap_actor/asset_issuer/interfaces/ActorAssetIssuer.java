package org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;

import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;

import java.io.Serializable;

/**
 * Created by Nerio on 10/09/15.
 */
public interface ActorAssetIssuer extends DAPActor, Serializable {

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
     * The method <code>getConnectionState</code> gives us the connection state of the represented
     * Asset Issuer
     *
     * @return the Connection state
     */
    org.fermat.fermat_dap_api.layer.all_definition.enums.DAPConnectionState getDapConnectionState();

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
     * returns the ExtendedPublicKey to which it belongs
     *
     * @return ExtendedPublicKey instance.
     */
    String getExtendedPublicKey();
}
