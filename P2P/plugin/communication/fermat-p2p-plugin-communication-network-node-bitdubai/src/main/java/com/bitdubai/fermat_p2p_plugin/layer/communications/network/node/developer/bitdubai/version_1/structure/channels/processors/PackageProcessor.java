/*
 * @#MessageProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors;


import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.NodeContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.WebSocketChannelServerEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.DaoFactory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.MethodCallsHistory;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import javax.websocket.Session;


/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor</code>
 * is the base class for all message processor class <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 06/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class PackageProcessor {

    /**
     * Represent the webSocketChannelServerEndpoint instance with the processor are register
     */
    private WebSocketChannelServerEndpoint channel;

    /**
     * Represent the packageType
     */
    private PackageType packageType;

    /**
     * Represent the daoFactory instance
     */
    private DaoFactory daoFactory;

    /**
     * Represent the gson instance
     */
    private Gson gson;

    /**
     * Represent the jsonParser instance
     */
    private JsonParser jsonParser;

    /**
     * Constructor with parameter
     *
     * @param channel
     * @param packageType
     */
    public PackageProcessor(WebSocketChannelServerEndpoint channel, PackageType packageType) {
        this.channel     = channel;
        this.packageType = packageType;
        this.daoFactory  = (DaoFactory) NodeContext.get(DaoFactory.class.getName());
        this.gson        = new Gson();
        this.jsonParser  = new JsonParser();
    }

    /**
     * Gets the value of daoFactory and returns
     *
     * @return daoFactory
     */
    public DaoFactory getDaoFactory() {
        return daoFactory;
    }

    /**
     * Gets the value of webSocketChannelServerEndpoint and returns
     *
     * @return webSocketChannelServerEndpoint
     */
    public WebSocketChannelServerEndpoint getChannel() {
        return channel;
    }

    /**
     * Gets the value of packageType and returns
     *
     * @return packageType
     */
    public PackageType getPackageType() {
        return packageType;
    }

    /**
     * Gets the value of gson and returns
     *
     * @return gson
     */
    public Gson getGson() {
        return gson;
    }

    /**
     * Gets the value of jsonParser and returns
     *
     * @return jsonParser
     */
    public JsonParser getJsonParser() {
        return jsonParser;
    }


    /**
     * Save the method call history
     *
     * @param parameters
     * @param profileIdentityPublicKey
     */
    protected void methodCallsHistory(String parameters, String profileIdentityPublicKey) throws CantInsertRecordDataBaseException {

        MethodCallsHistory methodCallsHistory = new MethodCallsHistory();
        methodCallsHistory.setMethodName(getPackageType().toString());
        methodCallsHistory.setParameters(parameters);
        methodCallsHistory.setProfileIdentityPublicKey(profileIdentityPublicKey);

        getDaoFactory().getMethodCallsHistoryDao().create(methodCallsHistory);
    }


    /**
     * Method that call to process the message
     *
     * @param session that send the package
     * @param packageReceived to process
     */
    public abstract void processingPackage(final Session session, final Package packageReceived);
}
