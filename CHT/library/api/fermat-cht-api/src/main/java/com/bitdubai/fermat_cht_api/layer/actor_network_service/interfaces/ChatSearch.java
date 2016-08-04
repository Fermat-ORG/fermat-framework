package com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantListChatException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatExposingData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 05/04/16.
 */
public abstract class ChatSearch {

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
     * @throws CantListChatException if something goes wrong.
     */
    public abstract List<ChatExposingData> getResult() throws CantListChatException;

    /**
     * Through the method <code>getResult</code> we can get the results of the search,
     * filtered by the parameters set.
     * We'll receive at most the quantity of @max set. If null by default the max will be 100.
     *
     * @param max maximum quantity of results expected.
     * @return a list of crypto brokers with their information.
     * @throws CantListChatException if something goes wrong.
     */
    public abstract List<ChatExposingData> getResult(final Integer max) throws CantListChatException;

    public abstract ChatExposingData getResult(final String publicKey) throws CantListChatException;

    public abstract List<ChatExposingData> getResult(String publicKey, DeviceLocation deviceLocation, double distance, String alias, Integer offSet, Integer max) throws CantListChatException;

    public abstract List<ChatExposingData> getResultLocation(DeviceLocation deviceLocation) throws CantListChatException;

    public abstract List<ChatExposingData> getResultDistance(double distance) throws CantListChatException;

    public abstract List<ChatExposingData> getResultAlias(String alias) throws CantListChatException;

    /**
     * Through the method <code>resetFilters</code> you can reset the filters set,
     */
    public final void resetFilters() {

        this.aliasList = null;
    }
}
