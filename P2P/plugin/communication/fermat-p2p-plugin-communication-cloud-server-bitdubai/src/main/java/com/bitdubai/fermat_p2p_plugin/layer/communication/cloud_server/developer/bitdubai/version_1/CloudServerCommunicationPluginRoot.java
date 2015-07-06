/*
 * @#CloudServerCommunicationPluginRoot.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.CommunicationChannelAddressFactory;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudServiceManager;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;


/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.CloudServerCommunicationPluginRoot</code> is
 * the responsible to initialize all component to work together, and hold all resources they needed.
 * <p/>
 *
 * Created by loui on 26/04/15.
 * Update by Jorge Gonzales
 * Update by Roberto Requena - (rart3001@gmail.com) on 03/06/15.
 *
 * @version 1.0
 */
public class CloudServerCommunicationPluginRoot implements Service, DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem,Plugin {

    /**
     * Represents the numbers of Thread that have the pool
     */
    private static final int NUMBER_OF_THREADS = 30;

    /**
     * Represents the numbers of the port that the services is listening
     */
    private static final int LISTENING_PORT = 9090;

    /**
     * Represent the status of this service
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    /*
     * Hold the list of event listeners
     */
    private List<EventListener> listenersAdded = new ArrayList<>();

    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;


    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;
    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();


    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    private UUID pluginId;

    /**
     * Represent the cloudServiceManager
     */
    private Map<String, CloudServiceManager> cloudServiceManagersCache;

    /*
     * Represent the executorService
     */
    private ExecutorService executorService;



    @Override
    public void start() {

        try {


            cloudServiceManagersCache = new HashMap<>();

            /*
             * Create the pool of thread
             */
            executorService = Executors.newFixedThreadPool(CloudServerCommunicationPluginRoot.NUMBER_OF_THREADS);

            /*
             * Get all network interfaces of the device
             */
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();


            while (interfaces.hasMoreElements()) {

                NetworkInterface networkInterface = interfaces.nextElement();

                /**
                 * If not a loopback interfaces (127.0.0.1) and is active
                 */
                if (!networkInterface.isLoopback() && networkInterface.isUp()){

                    /*
                     * Get his inet addresses
                     */
                    Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();

                    /*
                     * Create a cloud service for each ip
                     */
                    while(networkInterface.getInetAddresses().hasMoreElements()) {

                        /*
                         * Create a new key pair
                         */
                        ECCKeyPair keyPair = new ECCKeyPair();

                        /*
                         * Create the communication chanel address
                         */
                        CommunicationChannelAddress communicationChannelAddress = CommunicationChannelAddressFactory.constructCloudAddress(addresses.nextElement().getHostAddress(), CloudServerCommunicationPluginRoot.LISTENING_PORT);


                        String name = networkInterface.getName();

                        /*
                         * Create the new cloud service manager and Put into the cache
                         */
                        cloudServiceManagersCache.put(name, new CloudServiceManager(communicationChannelAddress, executorService, keyPair));

                    }

                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }


        /*
         * Set the new status of the service
         */
        this.serviceStatus = ServiceStatus.STARTED;

    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {

        /**
         * I will remove all the event listeners registered with the event manager.
         */

        for (EventListener eventListener : listenersAdded) {
            eventManager.removeListener(eventListener);
        }

        listenersAdded.clear();
        this.serviceStatus = ServiceStatus.STOPPED;

    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }


    /**
     * Communication channels interface implementation.
     */

    /**
     * DealsWithLogger interface implmentations
     */
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.CloudServerCommunicationPluginRoot");
        returnedClasses.add("com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceManager");
        returnedClasses.add("com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceVPN");
        returnedClasses.add("com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.ECCKeyPair");
        returnedClasses.add("com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudServiceManager");
        /**
         * I return the values.
         */
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */

        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            /**
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (CloudServerCommunicationPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                CloudServerCommunicationPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                CloudServerCommunicationPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                CloudServerCommunicationPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    /**
     * Static method to get the logging level from any class under root.
     * @param className
     * @return
     */
    public static LogLevel getLogLevelByClass(String className){
        try{
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return CloudServerCommunicationPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e){
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    /**
     * UsesFileSystem Interface implementation.
     */

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
    	//this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     *DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
    }

    /**
     * DealsWithPluginIdentity methods implementation.
     */
    @Override
    public void setId(UUID pluginId) {
       this.pluginId = pluginId;
    }
}
