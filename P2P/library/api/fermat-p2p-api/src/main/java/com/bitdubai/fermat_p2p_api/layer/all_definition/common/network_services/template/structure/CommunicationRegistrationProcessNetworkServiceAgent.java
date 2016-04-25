/*
 * @#RegistrationProcessNetworkServiceAgent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.structure;

import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.communications.CommunicationRegistrationProcessNetworkServiceAgent</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 22/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CommunicationRegistrationProcessNetworkServiceAgent {

    /*
     * Represent the sleep time for the read or send (5000 milliseconds)
     */
    private static final long SLEEP_TIME = 5000;
    private static final long MAX_SLEEP_TIME = 20000;

    /**
     * Represent the networkService
     */
    private NetworkService networkService;

    /**
     * Represent the communicationsClientConnection
     */
    private WsCommunicationsCloudClientManager communicationsClientConnection;

    /*
     * Represent the active
     */
    private boolean active;

    /*
     * Represent the Thread
     */
    private Runnable toRegistration = new Runnable() {
        @Override
        public void run() {
            while (active)
                processRegistration();
        }
    };

    private ExecutorService executorService;

    /**
     * Constructor with parameters
     * @param networkService
     * @param communicationsClientConnection
     */
    public CommunicationRegistrationProcessNetworkServiceAgent(NetworkService networkService, WsCommunicationsCloudClientManager communicationsClientConnection) {
        this.networkService = networkService;
        this.communicationsClientConnection = communicationsClientConnection;
        this.active = Boolean.FALSE;
        executorService = Executors.newSingleThreadExecutor();
    }

    private void processRegistration() {

            try{

               System.out.println(networkService.getAlias()+" isRegister "+networkService.isRegister()+" communicationsClientConnection.isRegister() "+communicationsClientConnection.getCommunicationsCloudClientConnection(networkService.getNetworkServiceType()).isRegister());

                if (communicationsClientConnection.getCommunicationsCloudClientConnection(networkService.getNetworkServiceType()).isRegister() && !networkService.isRegister()){

                    /*
                     * Register me
                     */
                    communicationsClientConnection.getCommunicationsCloudClientConnection(networkService.getNetworkServiceType()).registerComponentForCommunication(networkService.getNetworkServiceType(), networkService.getPlatformComponentProfilePluginRoot());

                    /*
                     * Stop the agent
                     */
                    stop();

                }else if (!networkService.isRegister()){

                    try {

                        if(Thread.currentThread().isInterrupted() == Boolean.FALSE)
                            Thread.sleep(CommunicationRegistrationProcessNetworkServiceAgent.SLEEP_TIME);

                    } catch (InterruptedException e) {
                        active = Boolean.FALSE;
                    }

                }else if(networkService.isRegister()){
                    /*
                     * Stop the internal threads
                     */
                    stop();
                }

            }catch (Exception e){
                try {
                    if(Thread.currentThread().isInterrupted() == Boolean.FALSE)
                        Thread.sleep(CommunicationRegistrationProcessNetworkServiceAgent.SLEEP_TIME);
                } catch (InterruptedException e1) {
                    active = Boolean.FALSE;
                }
            }

    }

    public  void start() {
        this.active = Boolean.TRUE;
        executorService.execute(toRegistration);
    }

    /*
     * Stop the internal threads
     */
    public void stop(){
        this.active = Boolean.FALSE;
        executorService.shutdown();
    }

    /**
     * Get the IsRunning
     * @return boolean
     */
    public boolean getActive() {
        return active;
    }
}
