package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.exceptions.CantListCryptoCustomersException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.utils.CryptoCustomerExposingData;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.interfaces.CryptoCustomerCommunitySearch</code>
 * expose all the methods to search a Crypto Customer.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/11/2015.
 */
public abstract class CryptoCustomerSearch {

    protected List<String> aliasList;

    /**
     * Through the method <code>addAlias</code> we can add alias of the customer to search.
     *
     * @param alias of the customer.
     */
    public final void addAlias(final String alias) {

        if (this.aliasList == null)
            this.aliasList = new ArrayList<>();

        this.aliasList.add(alias);
    }

    /**
     * Through the method <code>getResult</code> we can get the results of the search,
     * Like we're not setting max and offset we will return all the crypto customers that match
     * with the parameters set.
     *
     * @return a list of crypto customers with their information.
     * @throws CantListCryptoCustomersException if something goes wrong.
     */
    public abstract List<CryptoCustomerExposingData> getResult(Integer max, Integer offSet) throws CantListCryptoCustomersException;

    public abstract List<CryptoCustomerExposingData> getResult(String publicKey, DeviceLocation deviceLocation, double distance, String alias, Integer max, Integer offSet) throws CantListCryptoCustomersException;

    public abstract List<CryptoCustomerExposingData> getResultLocation(DeviceLocation deviceLocation, Integer max, Integer offSet) throws CantListCryptoCustomersException;

    public abstract List<CryptoCustomerExposingData> getResultDistance(double distance, Integer max, Integer offSet) throws CantListCryptoCustomersException;

    public abstract List<CryptoCustomerExposingData> getResultAlias(String alias, Integer max, Integer offSet) throws CantListCryptoCustomersException;

    public abstract CryptoCustomerExposingData getResult(String publicKey) throws CantListCryptoCustomersException;

    /**
     * Through the method <code>getResult</code> we can get the results of the search,
     * filtered by the parameters set.
     * We'll receive at most the quantity of @max set. If null by default the max will be 100.
     *
     * @param max maximum quantity of results expected.
     * @return a list of crypto customers with their information.
     * @throws CantListCryptoCustomersException if something goes wrong.
     */
    public abstract List<CryptoCustomerExposingData> getResult(final Integer max) throws CantListCryptoCustomersException;

    /**
     * Through the method <code>resetFilters</code> you can reset the filters set,
     */
    public final void resetFilters() {

        this.aliasList = null;
    }
}
