package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.endpoints;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.PackageDecoder;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.PackageEncoder;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.exception.PackageTypeNotSupportedException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors.ActorCallRequestProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors.ActorCallRespondProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors.ActorTraceDiscoveryQueryRespondProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors.CheckInActorRespondProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors.CheckInClientRespondProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors.CheckInNetworkServiceRespondProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors.CheckInProfileDiscoveryQueryRespondProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors.NearNodeListRespondProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors.NetworkServiceCallRequestProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors.NetworkServiceCallRespondProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.processors.ServerHandshakeRespondProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.context.ClientContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.context.ClientContextItem;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.NetworkClientCommunicationConnection;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

/**
 * The Class <code>CommunicationsNetworkClientChannel</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 27/11/15.
 * Updated by Leon Acosta - (laion.cj91@gmail.com) on 07/04/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */

@ClientEndpoint(
        configurator = com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.conf.ClientChannelConfigurator.class,
        encoders = {PackageEncoder.class},
        decoders = {PackageDecoder.class}
)
public class CommunicationsNetworkClientChannel {

    /**
     * Represent the list of package processors
     */
    private Map<PackageType, List<PackageProcessor>> packageProcessors;

    private NetworkClientCommunicationConnection networkClientCommunicationConnection;

    /**
     * Represent if the client is register with the server
     */
    private boolean isRegister;

    private ECCKeyPair   clientIdentity;
    private ErrorManager errorManager  ;
    private EventManager eventManager  ;

    public CommunicationsNetworkClientChannel(NetworkClientCommunicationConnection networkClientCommunicationConnection) {

        this.clientIdentity            = (ECCKeyPair)   ClientContext.get(ClientContextItem.CLIENT_IDENTITY);
        this.errorManager              = (ErrorManager) ClientContext.get(ClientContextItem.ERROR_MANAGER  );
        this.eventManager              = (EventManager) ClientContext.get(ClientContextItem.EVENT_MANAGER  );

        this.networkClientCommunicationConnection = networkClientCommunicationConnection;

        this.packageProcessors         = new HashMap<>();
        this.isRegister                = Boolean.FALSE;

        initPackageProcessorsRegistration();
    }

    private void initPackageProcessorsRegistration(){

        /*
         * Register all messages processor for this channel
         */
        registerMessageProcessor(new ActorTraceDiscoveryQueryRespondProcessor(this));
        registerMessageProcessor(new ActorCallRequestProcessor(this));
        registerMessageProcessor(new ActorCallRespondProcessor(this));
        registerMessageProcessor(new CheckInActorRespondProcessor(this));
        registerMessageProcessor(new CheckInClientRespondProcessor(this));
        registerMessageProcessor(new CheckInNetworkServiceRespondProcessor(this));
        registerMessageProcessor(new CheckInProfileDiscoveryQueryRespondProcessor(this));
        registerMessageProcessor(new NearNodeListRespondProcessor(this));
        registerMessageProcessor(new NetworkServiceCallRequestProcessor(this));
        registerMessageProcessor(new NetworkServiceCallRespondProcessor(this));
        registerMessageProcessor(new ServerHandshakeRespondProcessor(this));

    }

    @OnOpen
    public void onOpen(Session session){

        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println(" CommunicationsNetworkClientChannel - Starting method onOpen");


        /*
         * set ServerIdentity
         */
        networkClientCommunicationConnection.setServerIdentity((String) session.getUserProperties().get(""));

    }

    @OnMessage
    public void onMessage(Package packageReceived, Session session){
        System.out.println("New package Received");
        System.out.println("session: " + session.getId() + " package = " + packageReceived + "");

        try {

            /*
             * Process the new package received
             */
            processMessage(packageReceived, session);

        }catch (PackageTypeNotSupportedException p){

            p.printStackTrace();

            System.err.println(p.getMessage());
        }

    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason){

        System.out.println("Closed session : " + session.getId() + " Code: (" + closeReason.getCloseCode() + ") - reason: "+ closeReason.getReasonPhrase());

    }

    /**
     * Get the isActive value
     * @return boolean
     */
    public boolean isRegister() {
        return isRegister;
    }

    /**
     * Set the isActive
     * @param isRegister
     */
    public void setIsRegister(boolean isRegister) {
        this.isRegister = isRegister;
    }

    /**
     * Get the ErrorManager
     *
     * @return ErrorManager
     */
    public ErrorManager getErrorManager() {
        return errorManager;
    }

    /**
     * Get the EventManager
     *
     * @return EventManager
     */
    public EventManager getEventManager() {
        return eventManager;
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

    public NetworkClientCommunicationConnection getNetworkClientCommunicationConnection() {
        return networkClientCommunicationConnection;
    }

    /**
     * Method that process a new message received
     *
     * @param packageReceived   package received!
     * @param session           session involved.
     *
     * @throws PackageTypeNotSupportedException if we cannot recognize the package type.
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

        } else {

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

}
