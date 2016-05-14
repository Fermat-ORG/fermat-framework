package org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;

import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;

/**
 * Created by Nerio on 10/09/15.
 */
public interface ActorAssetUser extends DAPActor {

    /**
     * The method <code>getPubliclinkedIdentity</code> gives us the public Linked Identity of the represented Asset User
     *
     * @return the Public Linked Identity
     */
    String getPublicLinkedIdentity();

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
     * The method <code>getLastConnectionDate</code> gives us the Las Connection Date of the represented
     * Asset User
     *
     * @return the Connection Date
     */
    long getLastConnectionDate();

    /**
     * The method <code>getConnectionState</code> gives us the connection state of the represented
     * Asset User
     *
     * @return the Connection state
     */
    DAPConnectionState getDapConnectionState();

    /**
     * The method <code>getLocation</code> gives us the Location of the represented
     * Asset user
     *
     * @return the Location of the Asset user
     */
    Location getLocation();

    /**
     * The method <code>getLocationLatitude</code> gives us the Location of the represented
     * Asset user
     *
     * @return the Location Latitude of the Asset user
     */
    Double getLocationLatitude();

    /**
     * The method <code>getLocationLongitude</code> gives us the Location of the represented
     * Asset user
     *
     * @return the Location Longitude of the Asset user
     */
    Double getLocationLongitude();

    /**
     * returns the crypto address to which it belongs
     *
     * @return CryptoAddress instance.
     */
    CryptoAddress getCryptoAddress();

    /**
     * The method <code>getNetworkType</code> returns the network type which it belongs
     *
     * @return BlockchainNetworkType instance with the network type.
     */
    BlockchainNetworkType getBlockchainNetworkType();
}
