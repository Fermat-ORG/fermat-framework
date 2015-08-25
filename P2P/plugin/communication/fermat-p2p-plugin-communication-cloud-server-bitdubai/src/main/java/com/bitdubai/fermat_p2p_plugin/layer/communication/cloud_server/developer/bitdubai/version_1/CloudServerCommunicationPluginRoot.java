/*
 * @#CloudServerCommunicationPluginRoot.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.CommunicationChannelAddressFactory;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannelAddress;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.exceptions.CloudCommunicationException;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudServiceManager;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
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
public class CloudServerCommunicationPluginRoot implements Service, DealsWithEvents,DealsWithLogger, LogManagerForDevelopers, DealsWithErrors, DealsWithPluginFileSystem,Plugin {

    /**
     * Represents the value of DISABLE_SERVER
     */
    public static final Boolean DISABLE_SERVER = Boolean.TRUE;

    /**
     * Represents the value of ENABLE_SERVER
     */
    public static final Boolean ENABLE_SERVER = Boolean.FALSE;

    /**
     * Represents the numbers of port PADDING between network services managers
     */
    private static final int PORTS_PADDING = 1000;

    /**
     * Represents the numbers of port to open to be available to a network services managers
     */
    private static final int PORTS_TO_OPEN_TO_BE_AVAILABLE = 20;

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
    private LogManager logManager;
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

    /**
     * Represent the disableServerFlag
     */
    private Boolean disableServerFlag;

    /**
     * Represent the lastPortAssigned
     */
    private int lastPortAssigned;


    /**
     * Constructor
     */
    public CloudServerCommunicationPluginRoot(){
        super();
        this.cloudServiceManagersCache = new HashMap<>();
        this.disableServerFlag = Boolean.TRUE;
        this.lastPortAssigned = CloudServerCommunicationPluginRoot.LISTENING_PORT;
    }


    /**
     * This method initialize the network services manager supported
     *
     * @param cloudServiceManager
     * @param communicationChannelAddressServer
     * @param networkService
     * @throws CloudCommunicationException
     */
    private void initializeNetworkServicesSupported(CloudServiceManager cloudServiceManager, CommunicationChannelAddress communicationChannelAddressServer, NetworkServices networkService) throws CloudCommunicationException {


        System.out.println("CloudServerCommunicationPluginRoot - initialize Network Services Supported "+networkService);

        /*
         * Create a new communication Channel Address
         */
        CommunicationChannelAddress communicationChannelAddressNS = CommunicationChannelAddressFactory.constructCloudAddress(communicationChannelAddressServer.getHost(), (lastPortAssigned+=CloudServerCommunicationPluginRoot.PORTS_PADDING));

        /*
         *  Generate the ports available
         */
        List<Integer> availableVPNPorts = new ArrayList<>();
        for (int i = 0; i <= CloudServerCommunicationPluginRoot.PORTS_TO_OPEN_TO_BE_AVAILABLE; i++){

            /*
             * increase last assigned ports
             */
            lastPortAssigned = lastPortAssigned + i;

            /*
             * Add to the list
             */
            availableVPNPorts.add(lastPortAssigned);
        }

        /*
         * Create a new identity
         */
        ECCKeyPair netWorkServiceIdentity = new ECCKeyPair();

        /*
         *  register the manager
         */
        cloudServiceManager.registerNetworkServiceManager(new CloudNetworkServiceManager(communicationChannelAddressNS, executorService, netWorkServiceIdentity, networkService, availableVPNPorts));

    }


    /**
     * (non-Javadoc)
     *
     * @see Service#start()
     */
    @Override
    public void start() {

        try {

            if (disableServerFlag) {//skip Start the server
                System.out.println("CloudServerCommunicationPluginRoot - Local Server is Disable, no started");
                return;
            }

            System.out.println("CloudServerCommunicationPluginRoot - Starting plugin");

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
                    for (InetAddress address : Collections.list(addresses)) {

                        /**
                         * look only for ipv4 addresses
                         */
                         if (address instanceof Inet6Address)
                            continue;

                        /*
                         * Create a new key pair for his identity
                         */
                        ECCKeyPair identity = new ECCKeyPair("9723c5ab03c0b73efa1a033fc481d8617a787af9aba240c955611240e8e8d343",
                                                             "04195304BEE8FA81246F23C119D8A294E481F1916B91112FFD402C72B157B934759B287C5654D510653136169495B2CFA0A72958C011D924A5AD651AAB23E0391A");

                        /*
                         * Create the communication chanel communicationChannelAddress
                         */
                        CommunicationChannelAddress communicationChannelAddress = CommunicationChannelAddressFactory.constructCloudAddress(address.getHostAddress(), CloudServerCommunicationPluginRoot.LISTENING_PORT);

                        /*
                         * Create the new cloud service manager for this address and start
                         */
                        CloudServiceManager cloudServiceManager = new CloudServiceManager(communicationChannelAddress, executorService, identity);
                        cloudServiceManager.start();

                        /*
                         * Initialize network services manager supported
                         */
                        initializeNetworkServicesSupported(cloudServiceManager, communicationChannelAddress, NetworkServices.INTRA_USER);
                        initializeNetworkServicesSupported(cloudServiceManager, communicationChannelAddress, NetworkServices.TEMPLATE);

                        /*
                         * Put into the cache
                         */
                        cloudServiceManagersCache.put(networkInterface.getName(), cloudServiceManager);

                        System.out.println("New CommunicationChannelAddress linked on " + networkInterface.getName());
                        System.out.println("Host = " + communicationChannelAddress.getHost());
                        System.out.println("Port = "     + communicationChannelAddress.getPort());
                        System.out.println("Identity Public Key = " + identity.getPublicKey());
                        System.out.println("Last Port Assigned = " + lastPortAssigned);
                        System.out.println("Cloud Service Manager on " + networkInterface.getName() + " started.");

                    }

                }

            }
        } catch (SocketException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }catch (CloudCommunicationException e) {
            e.printStackTrace();
        }

        System.out.println("Cloud Services Managers Cache Size = " + cloudServiceManagersCache.size());

        /*
         * Set the new status of the service
         */
        this.serviceStatus = ServiceStatus.STARTED;

    }

    /**
     * (non-Javadoc)
     *
     * @see Service#pause()
     */
    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    /**
     * (non-Javadoc)
     *
     * @see Service#resume()
     */
    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    /**
     * (non-Javadoc)
     *
     * @see Service#stop()
     */
    @Override
    public void stop() {

        /*
         * Remove all the event listeners registered with the event manager.
         */
        for (EventListener eventListener : listenersAdded) {
            eventManager.removeListener(eventListener);
        }

        /*
         * Clear the list
         */
        listenersAdded.clear();

        /*
         * Stop all managers
         */
         for (String key : cloudServiceManagersCache.keySet())  {

             try {

                 cloudServiceManagersCache.get(key).stop();

             } catch (CloudCommunicationException e) {
                 e.printStackTrace();
             }
         }

        /*
         * Change the estatus
         */
        this.serviceStatus = ServiceStatus.STOPPED;

    }

    /**
     * (non-Javadoc)
     *
     * @see Service#getStatus()
     */
    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }


    /**
     * (non-Javadoc)
     *
     * @see DealsWithLogger#setLogManager(LogManager)
     */
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /**
     * (non-Javadoc)
     *
     * @see LogManagerForDevelopers#getClassesFullPath()
     */
    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.CloudServerCommunicationPluginRoot");
        returnedClasses.add("com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceManager");
        returnedClasses.add("com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudNetworkServiceVPN");
        returnedClasses.add("com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.structure.CloudServiceManager");
        returnedClasses.add("com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair");
        /**
         * I return the values.
         */
        return returnedClasses;
    }

    /**
     * (non-Javadoc)
     *
     * @see LogManagerForDevelopers#setLoggingLevelPerClass(Map<String, LogLevel>)
     */
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
     * (non-Javadoc)
     *
     * @see DealsWithPluginFileSystem#setPluginFileSystem(PluginFileSystem)
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
    	//this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * (non-Javadoc)
     *
     * @see DealsWithEvents#setEventManager(EventManager)
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * (non-Javadoc)
     *
     * @see DealsWithErrors#setErrorManager(ErrorManager)
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
    }

    /**
     * (non-Javadoc)
     *
     * @see Plugin#setId(UUID)
     */
    @Override
    public void setId(UUID pluginId) {
       this.pluginId = pluginId;
    }

    /**
     * Get the disable server flag
     *
     * @return Boolean
     */
    public Boolean getDisableServerFlag() {
        return disableServerFlag;
    }

    /**
     * Set Disable Server Flag
     *
     * @param disableServerFlag
     */
    public void setDisableServerFlag(Boolean disableServerFlag) {
        this.disableServerFlag = disableServerFlag;
    }
}
