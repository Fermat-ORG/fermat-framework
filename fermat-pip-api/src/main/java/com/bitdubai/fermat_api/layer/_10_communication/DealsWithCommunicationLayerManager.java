/*
 * @#DealsWithCommunicationLayerManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer._10_communication;

/**
 * The Class <code>com.bitdubai.fermat_api.layer._10_communication.DealsWithCommunicationLayerManager</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 24/05/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface DealsWithCommunicationLayerManager {

    /**
     * Configure the communication layer manager in the plugin
     *
     * @param communicationLayerManager
     */
    public void setCommunicationLayerManager(CommunicationLayerManager communicationLayerManager);
}
