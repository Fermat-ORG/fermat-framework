package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.communication.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.CryptoPaymentRequestNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;


/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.communication.structure.CommunicationRegistrationProcessNetworkServiceAgent</code>
 * <p/>
 * Created by Leon Acosta - (laion.cj911@gmail.com) on 07/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CommunicationRegistrationProcessNetworkServiceAgent extends Thread {

    /*
     * Represent the sleep time for the read or send (5000 milliseconds)
     */
    private static final long SLEEP_TIME = 5000;
    private static final long MAX_SLEEP_TIME = 20000;

    private final CryptoPaymentRequestNetworkServicePluginRoot networkServicePluginRoot      ;
    private final CommunicationsClientConnection               communicationsClientConnection;

    /**
     * Represent the active
     */
    private boolean active;

    /**
     * Constructor with parameters.
     */
    public CommunicationRegistrationProcessNetworkServiceAgent(final CryptoPaymentRequestNetworkServicePluginRoot networkServicePluginRoot      ,
                                                               final CommunicationsClientConnection               communicationsClientConnection) {

        this.networkServicePluginRoot       = networkServicePluginRoot      ;
        this.communicationsClientConnection = communicationsClientConnection;
        this.active                         = Boolean.FALSE                 ;
    }

    /**
     * (non-javadoc)
     * @see Thread#run()
     */
    @Override
    public void run() {

        while (active){

            try {

                if (communicationsClientConnection.isRegister() && !networkServicePluginRoot.isRegister()){

                    /*
                     * Construct my profile and register me
                     */
                    PlatformComponentProfile platformComponentProfile =  communicationsClientConnection.constructPlatformComponentProfileFactory(
                            networkServicePluginRoot.getIdentityPublicKey(),
                            networkServicePluginRoot.getAlias().toLowerCase(),
                            networkServicePluginRoot.getName(),
                            networkServicePluginRoot.getNetworkServiceType(),
                            networkServicePluginRoot.getPlatformComponentType(),
                            networkServicePluginRoot.getExtraData());

                    /*
                     * Register me
                     */
                    communicationsClientConnection.registerComponentForCommunication(NetworkServiceType.UNDEFINED,platformComponentProfile);

                    /*
                     * Configure my new profile
                     */
                    networkServicePluginRoot.setPlatformComponentProfile(platformComponentProfile);

                    /*
                     * Initialize the connection manager
                     */
                    networkServicePluginRoot.initializeCommunicationNetworkServiceConnectionManager();

                    /*
                     * Stop the agent
                     */
                    active = Boolean.FALSE;

                }else if (!networkServicePluginRoot.isRegister()){

                    try {
                        sleep(CommunicationRegistrationProcessNetworkServiceAgent.SLEEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        active = Boolean.FALSE;
                    }

                }else if (!networkServicePluginRoot.isRegister()){
                    active = Boolean.FALSE;
                }

            }catch (Exception e){
                try {
                    e.printStackTrace();
                    sleep(CommunicationRegistrationProcessNetworkServiceAgent.MAX_SLEEP_TIME);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                    active = Boolean.FALSE;
                }

            }
        }
    }

    /**
     * (non-javadoc)
     * @see Thread#start()
     */
    @Override
    public synchronized void start() {
        this.active = Boolean.TRUE;
        super.start();
    }

    /**
     * Get the IsRunning
     * @return boolean
     */
    public boolean getActive() {
        return active;
    }

}
