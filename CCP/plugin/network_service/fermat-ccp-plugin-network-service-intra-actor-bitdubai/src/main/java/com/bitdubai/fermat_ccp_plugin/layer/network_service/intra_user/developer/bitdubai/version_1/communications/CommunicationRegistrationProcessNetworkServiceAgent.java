/*
 * @#RegistrationProcessNetworkServiceAgent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.communications;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.pluginOld.IntraActorNetworkServicePluginRoot;
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
    private IntraActorNetworkServicePluginRoot networkService;

    /**
     * Represent the communicationsClientConnection
     */
    private WsCommunicationsCloudClientManager communicationsClientConnection;

    /**
     * Represent the active
     */
    private boolean active;

    /**
     * Represent the executorService
     */
    private ExecutorService executorService;

    /**
     * Represent the main task
     */
    private Runnable toRegistration = new Runnable() {
        @Override
        public void run() {
            while (active)
                processRegistration();
        }
    };

    /**
     * Constructor with parameters
     * @param networkService
     * @param communicationsClientConnection
     */
    public CommunicationRegistrationProcessNetworkServiceAgent(IntraActorNetworkServicePluginRoot networkService, WsCommunicationsCloudClientManager communicationsClientConnection) {
        this.networkService = networkService;
        this.communicationsClientConnection = communicationsClientConnection;
        this.active = Boolean.FALSE;
    }

    private void processRegistration() {

            try{

                System.out.println(networkService.getName()+" isRegister "+networkService.isRegister()+" communicationsClientConnection.isRegister() "+communicationsClientConnection.getCommunicationsCloudClientConnection().isRegister());

                if (communicationsClientConnection.getCommunicationsCloudClientConnection().isRegister() && !networkService.isRegister()){

                    /*
                     * Register me
                     */
                    communicationsClientConnection.getCommunicationsCloudClientConnection().registerComponentForCommunication(networkService.getNetworkServiceType(), networkService.getPlatformComponentProfilePluginRoot());

                    /*
                     * Stop the internal threads
                     */
                    stop();

                }else if (!networkService.isRegister()){
                   try {

                        if(Thread.currentThread().isInterrupted() == Boolean.FALSE)
                            Thread.sleep(CommunicationRegistrationProcessNetworkServiceAgent.SLEEP_TIME);

                    } catch (InterruptedException e) {
                        active = Boolean.FALSE;
                    }

                }else {
                    /*
                     * Stop the internal threads
                     */
                    stop();
                }

            }catch (Exception e){
                try {
                    if(Thread.currentThread().isInterrupted() == Boolean.FALSE)
                        Thread.sleep(CommunicationRegistrationProcessNetworkServiceAgent.MAX_SLEEP_TIME);
                } catch (InterruptedException e1) {
                    active = Boolean.FALSE;
                }
            }

    }

    public void start() {
        this.active = Boolean.TRUE;
        executorService = Executors.newSingleThreadExecutor();
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
