package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.endpoints.NetworkClientCommunicationChannel;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.context.ClientContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.context.ClientContextItem;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import javax.websocket.Session;

/**
 * The Class <code>PackageProcessor</code>
 * is the base class for all message processor class in the network client<p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 07/04/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class PackageProcessor {

    /**
     * Represent the webSocketChannelServerEndpoint instance with the processor are register
     */
    private NetworkClientCommunicationChannel channel;

    /**
     * Represent the packageType
     */
    private PackageType packageType;

    private EventManager eventManager;

    /**
     * Constructor with parameter
     *
     * @param channel
     * @param packageType
     */
    public PackageProcessor(final NetworkClientCommunicationChannel channel    ,
                            final PackageType                        packageType) {

        this.channel     = channel;
        this.packageType = packageType;
    }

    /**
     * Gets the value of communicationsNetworkClientChannel and returns
     *
     * @return communicationsNetworkClientChannel
     */
    public NetworkClientCommunicationChannel getChannel() {
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

    public EventManager getEventManager() {

        if (eventManager == null)
            eventManager = (EventManager) ClientContext.get(ClientContextItem.EVENT_MANAGER);

        return eventManager;
    }

    /**
     * Method that call to process the message
     *
     * @param session that send the package
     * @param packageReceived to process
     */
    public abstract void processingPackage(final Session session, final Package packageReceived);
}
