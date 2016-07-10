/*
 * @#WebSocketChannelEndpoint.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.exception.PackageTypeNotSupportedException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.NetworkNodePluginRoot;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContextItem;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.DaoFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 06/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class FermatWebSocketChannelEndpoint {

    /**
     * Represent the MAX_MESSAGE_SIZE
     */
    protected static final int MAX_MESSAGE_SIZE = 3000000;

    /**
     * Represent the MAX_IDLE_TIMEOUT
     */
    protected static final int MAX_IDLE_TIMEOUT = 60000;

    /**
     * Represent the list of package processors
     */
    private Map<PackageType, List<PackageProcessor>> packageProcessors;

    /**
     * Represent the channelIdentity
     */
    private ECCKeyPair channelIdentity;

    /**
     * Represent the daoFactory instance
     */
    private DaoFactory daoFactory;

    /**
     * Constructor
     */
    public FermatWebSocketChannelEndpoint(){
        super();
        this.packageProcessors = new HashMap<>();
        this.daoFactory  = (DaoFactory) NodeContext.get(NodeContextItem.DAO_FACTORY);
        this.channelIdentity = ((NetworkNodePluginRoot) NodeContext.get(NodeContextItem.PLUGIN_ROOT)).getIdentity(); //new ECCKeyPair(); //
        initPackageProcessorsRegistration();
    }

    /**
     * This method register a PackageProcessor object with this
     * channel
     */
    public void registerMessageProcessor(PackageProcessor packageProcessor) {

        /*
         * Set server reference
         */

        //Validate if a previous list created
        if (packageProcessors.containsKey(packageProcessor.getPackageType())){

            /*
             * Add to the existing list
             */
            packageProcessors.get(packageProcessor.getPackageType()).add(packageProcessor);

        }else{

            /*
             * Create a new list
             */
            List<PackageProcessor> packageProcessorList = new ArrayList<>();
            packageProcessorList.add(packageProcessor);

            /*
             * Add to the packageProcessor
             */
            packageProcessors.put(packageProcessor.getPackageType(), packageProcessorList);
        }

    }

    /**
     * Gets the value of channelIdentity and returns
     *
     * @return channelIdentity
     */
    public ECCKeyPair getChannelIdentity() {
        return channelIdentity;
    }

    /**
     * Sets the channelIdentity
     *
     * @param channelIdentity to set
     */
    protected void setChannelIdentity(ECCKeyPair channelIdentity) {
        this.channelIdentity = channelIdentity;
    }

    /**
     * Gets the value of packageProcessors and returns
     *
     * @return packageProcessors
     */
    protected Map<PackageType, List<PackageProcessor>> getPackageProcessors() {
        return packageProcessors;
    }

    /**
     * Validate if can process the package type
     *
     * @param packageType to validate
     * @return true or false
     */
    protected boolean canProcessMessage(PackageType packageType){
        return packageProcessors.containsKey(packageType);
    }

    /**
     * Method that process a new message received
     *
     * @param packageReceived
     * @param session
     */
    protected void processMessage(Package packageReceived, Session session) throws PackageTypeNotSupportedException {

        /*
         * Validate if can process the message
         */
        if (canProcessMessage(packageReceived.getPackageType())){

            /*
             * Get list of the processor
             */
            for (PackageProcessor packageProcessor : packageProcessors.get(packageReceived.getPackageType())) {

                /*
                 * Process the message
                 */
                packageProcessor.processingPackage(session, packageReceived);
            }

        }else {

            throw new PackageTypeNotSupportedException("The package type: "+packageReceived.getPackageType()+" is not supported");
        }
    }

    public final DaoFactory getDaoFactory() {

        return daoFactory;
    }

    /**
     * Initialize the all package processors for this
     * channel
     */
    protected abstract void initPackageProcessorsRegistration();

}
