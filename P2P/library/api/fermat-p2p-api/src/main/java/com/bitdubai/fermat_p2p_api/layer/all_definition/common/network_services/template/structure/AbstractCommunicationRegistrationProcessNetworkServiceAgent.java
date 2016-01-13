package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.structure;

import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkServiceV2;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.structure.AbstractCommunicationRegistrationProcessNetworkServiceAgent</code>
 * contains all the basic functionality of a CommunicationRegistrationProcessNetworkServiceAgent.
 *
 * The method <code>registrationProcess</code> can be override to modify its behavior.
 *
 * Created by lnacosta - (laion.cj91@gmail.com) on 30/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class AbstractCommunicationRegistrationProcessNetworkServiceAgent extends FermatAgent {

    /*
     * Represent the sleep time for the read or send (5000 milliseconds)
     */
    private static final long SLEEP_TIME     =  5000;
    private static final long MAX_SLEEP_TIME = 20000;

    private final Thread agentThread;

    private AbstractNetworkService networkServicePluginRoot;

    private AbstractNetworkServiceV2 abstractNetworkServiceV2;
    private final CommunicationsClientConnection communicationsClientConnection;

    /**
     * Constructor with parameters.
     *
     * @param networkServicePluginRoot         pluginRoot of the network service.
     * @param communicationsClientConnection   communication client connection instance.
     */
    public AbstractCommunicationRegistrationProcessNetworkServiceAgent(final AbstractNetworkService         networkServicePluginRoot      ,
                                                                       final CommunicationsClientConnection communicationsClientConnection) {

        this.networkServicePluginRoot       = networkServicePluginRoot      ;
        this.communicationsClientConnection = communicationsClientConnection;

        this.status = AgentStatus.CREATED;

        this.agentThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning())
                    registrationProcess();
            }
        });
    }

    /**
     * Constructor with parameters.
     *
     * @param networkServicePluginRoot         pluginRoot of the network service.
     * @param communicationsClientConnection   communication client connection instance.
     */
    public AbstractCommunicationRegistrationProcessNetworkServiceAgent(final AbstractNetworkServiceV2 networkServicePluginRoot      ,
                                                                       final CommunicationsClientConnection communicationsClientConnection) {

        this.abstractNetworkServiceV2       = networkServicePluginRoot      ;
        this.communicationsClientConnection = communicationsClientConnection;

        this.status = AgentStatus.CREATED;

        this.agentThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning())
                    registrationProcess();
            }
        });
    }

    @Override
    public final synchronized void start() {

        this.agentThread.start();

        this.status = AgentStatus.STARTED;

    }

    @Override
    public final void stop() {

        this.agentThread.interrupt();

        this.status = AgentStatus.STOPPED;

    }

    protected void registrationProcess() {

        try {

            if(networkServicePluginRoot!=null) {
                if (communicationsClientConnection.isRegister() && !networkServicePluginRoot.isRegister()) {

                    //Construct my profile and register me
                    PlatformComponentProfile platformComponentProfile = communicationsClientConnection.constructPlatformComponentProfileFactory(
                            networkServicePluginRoot.getIdentityPublicKey(),
                            networkServicePluginRoot.getAlias().toLowerCase(),
                            networkServicePluginRoot.getName(),
                            networkServicePluginRoot.getNetworkServiceType(),
                            networkServicePluginRoot.getPlatformComponentType(),
                            networkServicePluginRoot.getExtraData()
                    );

                    // Register me
                    communicationsClientConnection.registerComponentForCommunication(networkServicePluginRoot.getNetworkServiceType(), platformComponentProfile);

                    // Configure my new profile
                    networkServicePluginRoot.setPlatformComponentProfilePluginRoot(platformComponentProfile);

                    //Initialize the connection manager
                    networkServicePluginRoot.initializeCommunicationNetworkServiceConnectionManager();

                    // Stop the agent
                    this.status = AgentStatus.STOPPED;

                } else if (!networkServicePluginRoot.isRegister()) {

                    try {
                        Thread.sleep(AbstractCommunicationRegistrationProcessNetworkServiceAgent.SLEEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        this.status = AgentStatus.STOPPED;
                    }

                }
            }else if(abstractNetworkServiceV2!=null){
                if (communicationsClientConnection.isRegister() && !abstractNetworkServiceV2.isRegister()) {

                    //Construct my profile and register me
                    PlatformComponentProfile platformComponentProfile = communicationsClientConnection.constructPlatformComponentProfileFactory(
                            abstractNetworkServiceV2.getIdentityPublicKey(),
                            abstractNetworkServiceV2.getAlias().toLowerCase(),
                            abstractNetworkServiceV2.getName(),
                            abstractNetworkServiceV2.getNetworkServiceType(),
                            abstractNetworkServiceV2.getPlatformComponentType(),
                            abstractNetworkServiceV2.getExtraData()
                    );

                    // Register me
                    communicationsClientConnection.registerComponentForCommunication(abstractNetworkServiceV2.getNetworkServiceType(), platformComponentProfile);

                    // Configure my new profile
                    abstractNetworkServiceV2.setPlatformComponentProfilePluginRoot(platformComponentProfile);

                    //Initialize the connection manager
                    abstractNetworkServiceV2.initializeCommunicationNetworkServiceConnectionManager();

                    // Stop the agent
                    this.status = AgentStatus.STOPPED;

                } else if (!abstractNetworkServiceV2.isRegister()) {

                    try {
                        Thread.sleep(AbstractCommunicationRegistrationProcessNetworkServiceAgent.SLEEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        this.status = AgentStatus.STOPPED;
                    }

                }
            }

            // TODO add better exception control here.

        } catch (Exception e) {
            try {
                e.printStackTrace();
                Thread.sleep(AbstractCommunicationRegistrationProcessNetworkServiceAgent.MAX_SLEEP_TIME);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                this.status = AgentStatus.STOPPED;
            }
        }
    }
}
