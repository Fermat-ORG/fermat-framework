/*
 * @#IntraUserNetworkServiceRemoteAgent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.Message;

import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer._11_network_service.intra_user.developer.bitdubai.version_1.structure.IntraUserNetworkServiceRemoteAgent</code>
 *
 * This class extend of the <code>java.util.Observer</code> class,  its used on the software design pattern called: The observer pattern,
 * for more info see @link https://en.wikipedia.org/wiki/Observer_pattern
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 13/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class IntraUserNetworkServiceLocal implements Observer{

    /**
     * Represent the public key of the remote network service
     */
    private String remoteNetworkServicePublicKey;

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * Represent the outgoingMessageDataAccessObject
     */
    private OutgoingMessageDataAccessObject outgoingMessageDataAccessObject;

    /**
     * Constructor with parameters
     *
     * @param remoteNetworkServicePublicKey
     * @param errorManager instance
     * @param outgoingMessageDataAccessObject instance
     */
    public IntraUserNetworkServiceLocal(String remoteNetworkServicePublicKey, ErrorManager errorManager, OutgoingMessageDataAccessObject outgoingMessageDataAccessObject) {
        this.remoteNetworkServicePublicKey = remoteNetworkServicePublicKey;
        this.errorManager = errorManager;
        this.outgoingMessageDataAccessObject = outgoingMessageDataAccessObject;
    }


    /**
     * This method encrypt the content of the message to send and save on the
     * data base in the table <code>outbox_messages</code>
     *
     * @param message the message to send
     */
    public void sendMessage(Message message){


        //Cast the message to IntraUserNetworkServiceMessage
        IntraUserNetworkServiceMessage intraUserNetworkServiceMessage = (IntraUserNetworkServiceMessage) message;


        //Save to the data base table

    }



    /**
     * Notify the client when a incoming message is receive by the IntraUserNetworkServiceRemoteAgent
     * ant fire a new event
     */
    private void onMessageReceived(IntraUserNetworkServiceMessage intraUserNetworkServiceMessage){


        /**
         * Put the message on and event and fire new event
         */

    }

    /**
     * This method is called automatically when IntraUserNetworkServiceRemoteAgent (Observable object) update the database
     * when new message is received
     *
     * @param observable the observable object
     * @param data the data update
     */
    @Override
    public void update(Observable observable, Object data) {

        if (data instanceof IntraUserNetworkServiceMessage)
            onMessageReceived((IntraUserNetworkServiceMessage) data);
    }

    /**
     * Return the public key of the remote network service
     * @return
     */
    public String getRemoteNetworkServicePublicKey() {
        return remoteNetworkServicePublicKey;
    }
}
