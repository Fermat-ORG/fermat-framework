package com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantListArtistsException;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerCommunitySearch</code>
 * expose all the methods to search a Crypto Broker.
 * <p>
 * Created by Gabriel Araujo (1/04/2016.)
 */
public abstract class ActorSearch<V extends ExposingData> {

    protected List<String> aliasList;

    /**
     * Through the method <code>addAlias</code> we can add alias of the broker to search.
     *
     * @param alias of the broker.
     */
    public final void addAlias(final String alias) {

        if(this.aliasList == null)
            this.aliasList = new ArrayList<>();

        this.aliasList.add(alias);
    }

    public abstract List<V> getResult(
            Integer max,
            Integer offset)
            throws CantListArtistsException;

    public abstract List<V> getResult(
            String publicKey,
            DeviceLocation deviceLocation,
            double distance,
            String alias,
            Integer max,
            Integer offset)
            throws CantListArtistsException;

    public abstract List<V> getResultLocation(
            DeviceLocation deviceLocation,
            Integer max,
            Integer offset)
            throws CantListArtistsException;

    public abstract List<V> getResultDistance(
            double distance,
            Integer max,
            Integer offset)
            throws CantListArtistsException;

    public abstract List<V> getResultAlias(
            String alias,
            Integer max,
            Integer offset)
            throws CantListArtistsException;
    /**
     * Through the method <code>getResult</code> we can get the results of the search,
     * filtered by the parameters set.
     * We'll receive at most the quantity of @max set. If null by default the max will be 100.
     *
     * @param max   maximum quantity of results expected.
     *
     * @return a list of crypto brokers with their information.
     *
     * @throws CantListArtistsException  if something goes wrong.
     */
    public abstract List<V> getResult(final Integer max) throws CantListArtistsException;

    /**
     * Through the method <code>resetFilters</code> you can reset the filters set,
     */
    public final void resetFilters() {

        this.aliasList = null;
    }
}
