/*
 * @#RegistrationProcessNetworkServiceAgent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.agents;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;

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
public final class CommunicationRegistrationProcessNetworkServiceAgent {

    /**
     * Represent the sleep time for registration process (5000 milliseconds)
     */
    private static final long SLEEP_TIME = 5000;

    /**
     * Represent the sleep time  for registration process (20000 milliseconds)
     */
    private static final long MAX_SLEEP_TIME = 20000;

    /**
     *  Represent the network service plugin root
     */
    private AbstractNetworkServiceBase networkServiceRoot;

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
     * @param networkServiceRoot
     */
    public CommunicationRegistrationProcessNetworkServiceAgent(AbstractNetworkServiceBase networkServiceRoot) {
        super();
        this.networkServiceRoot = networkServiceRoot;
        this.active = Boolean.FALSE;
    }

    /**
     * Process to the registration
     */
    private void processRegistration() {

            try{

                System.out.println(networkServiceRoot.getNetworkServiceProfile().getAlias() + " networkServiceRoot.isRegister() " + networkServiceRoot.isRegister()
                             + " networkServiceRoot.getCommunicationsClientConnection().isRegister() " + networkServiceRoot.getCommunicationsClientConnection().isRegister());

                if (networkServiceRoot.getCommunicationsClientConnection().isRegister() && !networkServiceRoot.isRegister()){

                    /*
                     * Register me
                     */
                    networkServiceRoot.getCommunicationsClientConnection().registerComponentForCommunication(networkServiceRoot.getNetworkServiceProfile().getNetworkServiceType(), networkServiceRoot.getNetworkServiceProfile());

                    /*
                     * Stop the internal threads
                     */
                    stop();

                }else if (!networkServiceRoot.isRegister()){

                    if(Thread.currentThread().isInterrupted() == Boolean.FALSE)
                        Thread.sleep(CommunicationRegistrationProcessNetworkServiceAgent.SLEEP_TIME);

                }else {

                    /*
                     * Stop the internal threads
                     */
                    stop();
                }

            }catch (Exception e){
                e.printStackTrace();
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
