package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.communication.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.CryptoPaymentRequestNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;

/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.communication.structure.RegistrationProcessNetworkServiceAgent</code>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 06/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class RegistrationProcessNetworkServiceAgent extends Thread {

    /*
     * Represent the sleep time for the read or send (5000 milliseconds)
     */
    private static final long SLEEP_TIME = 5000;

    /**
     * Represent the CryptoPaymentRequestNetworkServicePluginRoot
     */
    private final CryptoPaymentRequestNetworkServicePluginRoot cryptoPaymentRequestNetworkServicePluginRoot;

    /**
     * Represent the communicationsClientConnection
     */
    private final CommunicationsClientConnection communicationsClientConnection;

    /**
     * Represent the active
     */
    private boolean active;

    /**
     * Constructor with parameters
     * @param cryptoPaymentRequestNetworkServicePluginRoot
     * @param communicationsClientConnection
     */
    public RegistrationProcessNetworkServiceAgent(final CryptoPaymentRequestNetworkServicePluginRoot cryptoPaymentRequestNetworkServicePluginRoot,
                                                  final CommunicationsClientConnection               communicationsClientConnection              ) {

        this.cryptoPaymentRequestNetworkServicePluginRoot = cryptoPaymentRequestNetworkServicePluginRoot;
        this.communicationsClientConnection               = communicationsClientConnection              ;
        this.active                                       = Boolean.FALSE                               ;
    }

    /**
     * (non-javadoc)
     * @see Thread#run()
     */
    @Override
    public void run() {

        while (active){

            if (communicationsClientConnection.isRegister() && !cryptoPaymentRequestNetworkServicePluginRoot.isRegister()){

                /*
                 * Construct my profile and register me
                 */
                PlatformComponentProfile platformComponentProfile =  communicationsClientConnection.constructPlatformComponentProfileFactory(
                        cryptoPaymentRequestNetworkServicePluginRoot.getIdentityPublicKey(),
                        "CryptoPaymentRequestNetworkService",
                        "Crypto Payment Request Network Service ("+cryptoPaymentRequestNetworkServicePluginRoot.getId()+")",
                        NetworkServiceType.CRYPTO_PAYMENT_REQUEST,
                        PlatformComponentType.NETWORK_SERVICE_COMPONENT,
                        null
                );

                communicationsClientConnection.registerComponentForCommunication(platformComponentProfile);
                cryptoPaymentRequestNetworkServicePluginRoot.setPlatformComponentProfile(platformComponentProfile);
                cryptoPaymentRequestNetworkServicePluginRoot.initializeCryptoPaymentRequestNetworkServiceConnectionManager();
                active = Boolean.FALSE;

            } else if (!cryptoPaymentRequestNetworkServicePluginRoot.isRegister()) {
                try {

                    sleep(RegistrationProcessNetworkServiceAgent.SLEEP_TIME);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (!cryptoPaymentRequestNetworkServicePluginRoot.isRegister()) {
                active = Boolean.FALSE;
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
