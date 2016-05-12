package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

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
    private com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.endpoints.CommunicationsNetworkClientChannel channel;

    /**
     * Represent the packageType
     */
    private PackageType packageType;

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
    public PackageProcessor(com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.endpoints.CommunicationsNetworkClientChannel channel, PackageType packageType) {
        this.channel     = channel;
        this.packageType = packageType;
        this.gson        = new Gson();
        this.jsonParser  = new JsonParser();
    }

    /**
     * Gets the value of communicationsNetworkClientChannel and returns
     *
     * @return communicationsNetworkClientChannel
     */
    public com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.endpoints.CommunicationsNetworkClientChannel getChannel() {
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
     * Method that call to process the message
     *
     * @param session that send the package
     * @param packageReceived to process
     */
    public abstract void processingPackage(final Session session, final Package packageReceived);
}
