package com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantListChatException;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 05/04/16.
 */
public abstract class ChatSearch {

    /**
     * Through the method <code>getResult</code> we can get the results of the search,
     * Like we're not setting max and offset we will return all the crypto brokers that match
     * with the parameters set.
     *
     * @throws CantListChatException if something goes wrong.
     */
    public abstract void getResult(String publicKey, DeviceLocation deviceLocation, double distance, String alias, Integer offSet, Integer max, String requesterPublicKey) throws CantListChatException;

}
