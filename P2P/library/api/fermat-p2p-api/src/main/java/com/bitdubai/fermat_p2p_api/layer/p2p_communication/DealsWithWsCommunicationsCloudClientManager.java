/*
 * @#DealsWithCommunicationLayerManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.p2p_communication;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.DealsWithWsCommunicationsCloudClientManager</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 24/05/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface DealsWithWsCommunicationsCloudClientManager {

    /**
     * Configure the WsCommunicationsCloudClientManager in the plugin
     *
     * @param wsCommunicationsCloudClientManager
     */
    public void setWsCommunicationsCloudClientConnectionManager(WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager);
}
