package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.agents;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes.AbstractNetworkService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.agents.NetworkServiceRegistrationProcessAgent</code>
 * it is responsible for handling the registration process of a network service.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 03/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public final class NetworkServiceRegistrationProcessAgent {

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
    private AbstractNetworkService networkServiceRoot;

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
     *
     * @param networkServiceRoot plugin root of the network service which we want to register.
     */
    public NetworkServiceRegistrationProcessAgent(AbstractNetworkService networkServiceRoot) {

        this.networkServiceRoot = networkServiceRoot;
        this.active             = Boolean.FALSE     ;
    }

    /**
     * Process to the registration
     */
    private void processRegistration() {

            try{

//                System.out.println(networkServiceRoot.getProfile().getNetworkServiceType() + " networkServiceRoot.isRegistered() " + networkServiceRoot.isRegistered()
//                             + " networkServiceRoot.getConnection().isRegistered() " + networkServiceRoot.getConnection().isRegistered());

                if (networkServiceRoot.getConnection().isRegistered() && !networkServiceRoot.isRegistered()){

                    /*
                     * Register me
                     */
                    networkServiceRoot.getConnection().registerProfile(networkServiceRoot.getProfile());

                    /*
                     * Stop the internal threads
                     */
                    stop();

                }else if (!networkServiceRoot.isRegistered()){

                    if(Thread.currentThread().isInterrupted() == Boolean.FALSE)
                        Thread.sleep(NetworkServiceRegistrationProcessAgent.SLEEP_TIME);

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
                        Thread.sleep(NetworkServiceRegistrationProcessAgent.MAX_SLEEP_TIME);
                } catch (InterruptedException e1) {
                    active = Boolean.FALSE;
                }
            }

    }

    public void start() {
        if (!getActive()) {
            this.active = Boolean.TRUE;
            executorService = Executors.newSingleThreadExecutor();
            executorService.execute(toRegistration);
        }
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
