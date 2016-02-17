package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.structure;

import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
@Deprecated
public abstract class AbstractCommunicationRegistrationProcessNetworkServiceAgent extends FermatAgent {

    /*
     * Represent the sleep time for the read or send (5000 milliseconds)
     */
    private static final long SLEEP_TIME     =  5000;
    private static final long MAX_SLEEP_TIME = 20000;

    private final AbstractNetworkService networkServicePluginRoot      ;
    private final WsCommunicationsCloudClientManager communicationsClientConnection;

    private ExecutorService executorService;

    private final Thread agentThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (isRunning())
                registrationProcess();
        }
    });

    /**
     * Constructor with parameters.
     *
     * @param networkServicePluginRoot         pluginRoot of the network service.
     * @param communicationsClientConnection   communication client connection instance.
     */
    public AbstractCommunicationRegistrationProcessNetworkServiceAgent(final AbstractNetworkService         networkServicePluginRoot      ,
                                                                       final WsCommunicationsCloudClientManager communicationsClientConnection) {

        this.networkServicePluginRoot       = networkServicePluginRoot      ;
        this.communicationsClientConnection = communicationsClientConnection;
        this.status = AgentStatus.CREATED;
        executorService = Executors.newSingleThreadExecutor();

    }

    @Override
    public void start() {
        this.status = AgentStatus.STARTED;
        executorService.execute(agentThread);
    }

    @Override
    public void stop() {
        this.status = AgentStatus.STOPPED;
        executorService.shutdown();
    }

    protected void registrationProcess() {

        try {

            if (communicationsClientConnection.getCommunicationsCloudClientConnection().isRegister() && !networkServicePluginRoot.isRegister()){

                //Construct my profile and register me
                PlatformComponentProfile platformComponentProfile =  communicationsClientConnection.getCommunicationsCloudClientConnection().constructPlatformComponentProfileFactory(
                        networkServicePluginRoot.getIdentityPublicKey(),
                         networkServicePluginRoot.getAlias().toLowerCase(),
                         networkServicePluginRoot.getName(),
                         networkServicePluginRoot.getNetworkServiceType(),
                         networkServicePluginRoot.getPlatformComponentType(),
                         networkServicePluginRoot.getExtraData()
                );

                // Register me
                communicationsClientConnection.getCommunicationsCloudClientConnection().registerComponentForCommunication(networkServicePluginRoot.getNetworkServiceType(), platformComponentProfile);

                // Configure my new profile
                networkServicePluginRoot.setPlatformComponentProfilePluginRoot(platformComponentProfile);

                //Initialize the connection manager
                networkServicePluginRoot.initializeCommunicationNetworkServiceConnectionManager();

                // Stop the agent
                stop();

            } else if (!networkServicePluginRoot.isRegister()){

                try {
                    if(Thread.currentThread().isInterrupted() == Boolean.FALSE)
                        Thread.sleep(AbstractCommunicationRegistrationProcessNetworkServiceAgent.SLEEP_TIME);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                    this.status = AgentStatus.STOPPED;
                }

            }else if(networkServicePluginRoot.isRegister()){
                /*
                 * Stop the internal threads
                 */
                 stop();
            }

        } catch (Exception e) {
            try {
                if(Thread.currentThread().isInterrupted() == Boolean.FALSE)
                    Thread.sleep(AbstractCommunicationRegistrationProcessNetworkServiceAgent.MAX_SLEEP_TIME);
            } catch (InterruptedException e1) {
                System.out.println(e1.getMessage());
                this.status = AgentStatus.STOPPED;
            }
        }

    }
}
