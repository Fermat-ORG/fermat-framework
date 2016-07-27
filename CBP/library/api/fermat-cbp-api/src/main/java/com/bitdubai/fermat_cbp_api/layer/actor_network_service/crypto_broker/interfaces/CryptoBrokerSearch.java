package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantListCryptoBrokersException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerExposingData;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerCommunitySearch</code>
 * expose all the methods to search a Crypto Broker.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/11/2015.
 */
public abstract class CryptoBrokerSearch {

    protected List<String> aliasList;

    /**
     * Through the method <code>addAlias</code> we can add alias of the broker to search.
     *
     * @param alias of the broker.
     */
    public final void addAlias(final String alias) {

        if (this.aliasList == null)
            this.aliasList = new ArrayList<>();

        this.aliasList.add(alias);
    }

    /**
     * Through the method <code>getResult</code> we can get the results of the search,
     * Like we're not setting max and offset we will return all the crypto brokers that match
     * with the parameters set.
     *
     * @return a list of crypto brokers with their information.
     * @throws CantListCryptoBrokersException if something goes wrong.
     */
    public abstract List<CryptoBrokerExposingData> getResult(Integer max, Integer offset) throws CantListCryptoBrokersException;

    public abstract List<CryptoBrokerExposingData> getResult(String publicKey, DeviceLocation deviceLocation, double distance, String alias, Integer max, Integer offset) throws CantListCryptoBrokersException;

    public abstract List<CryptoBrokerExposingData> getResultLocation(DeviceLocation deviceLocation, Integer max, Integer offset) throws CantListCryptoBrokersException;

    public abstract List<CryptoBrokerExposingData> getResultDistance(double distance, Integer max, Integer offset) throws CantListCryptoBrokersException;

    public abstract List<CryptoBrokerExposingData> getResultAlias(String alias, Integer max, Integer offset) throws CantListCryptoBrokersException;

    public abstract CryptoBrokerExposingData getResult(String publicKey) throws CantListCryptoBrokersException;

    /**
     * Through the method <code>getResult</code> we can get the results of the search,
     * filtered by the parameters set.
     * We'll receive at most the quantity of @max set. If null by default the max will be 100.
     *
     * @param max maximum quantity of results expected.
     * @return a list of crypto brokers with their information.
     * @throws CantListCryptoBrokersException if something goes wrong.
     */
    public abstract List<CryptoBrokerExposingData> getResult(final Integer max) throws CantListCryptoBrokersException;

    /**
     * Through the method <code>resetFilters</code> you can reset the filters set,
     */
    public final void resetFilters() {

        this.aliasList = null;
    }
}
