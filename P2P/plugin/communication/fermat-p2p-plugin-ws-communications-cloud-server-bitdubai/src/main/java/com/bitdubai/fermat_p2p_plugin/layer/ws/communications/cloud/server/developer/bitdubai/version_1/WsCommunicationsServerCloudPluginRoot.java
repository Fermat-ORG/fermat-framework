/*
 * @#WsCommunicationsServerCloudPluginRoot.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;

import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.WsCommunicationCloudServer;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors.ComponentConnectionRequestPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors.ComponentRegistrationRequestPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors.MessageTransmitPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors.RequestListComponentRegisterPacketProcessor;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import org.java_websocket.WebSocketImpl;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
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
import java.util.regex.Pattern;


/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.WsCommunicationsServerCloudPluginRoot</code> is
 * the responsible to initialize all component to work together, and hold all resources they needed.
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/09/15.
 *
 * @version 1.0
 */
public class WsCommunicationsServerCloudPluginRoot implements Service, DealsWithEvents,DealsWithLogger, LogManagerForDevelopers, DealsWithErrors, DealsWithPluginFileSystem,Plugin {

    /**
     * Represents the value of DISABLE_SERVER
     */
    public static final Boolean DISABLE_SERVER = Boolean.TRUE;

    /**
     * Represents the value of ENABLE_SERVER
     */
    public static final Boolean ENABLE_SERVER = Boolean.FALSE;

    /**
     * Represent the status of this service
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    /*
     * Hold the list of event listeners
     */
    private List<FermatEventListener> listenersAdded = new ArrayList<>();

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

    /*
     * Represent the executorService
     */
    private ExecutorService executorService;

    /**
     * Represent the disableServerFlag
     */
    private Boolean disableServerFlag;


    /**
     * Constructor
     */
    public WsCommunicationsServerCloudPluginRoot(){
        super();
        this.disableServerFlag = WsCommunicationsServerCloudPluginRoot.DISABLE_SERVER;
    }

    /**
     * (non-Javadoc)
     *
     * @see Service#start()
     */
    @Override
    public void start() {

        try {

            if (disableServerFlag) {
                System.out.println("WsCommunicationsServerCloudPluginRoot - Local Server is Disable, no started");
                return;
            }

            System.out.println("WsCommunicationsServerCloudPluginRoot - Starting plugin");

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
                         if (address instanceof Inet6Address) {
                             continue;
                         }

                        WebSocketImpl.DEBUG = false;
                        InetSocketAddress inetSocketAddress = new InetSocketAddress(address, WsCommunicationCloudServer.DEFAULT_PORT);
                        WsCommunicationCloudServer wsCommunicationCloudServer = new WsCommunicationCloudServer(inetSocketAddress);
                        wsCommunicationCloudServer.registerFermatPacketProcessor(new ComponentRegistrationRequestPacketProcessor());
                        wsCommunicationCloudServer.registerFermatPacketProcessor(new ComponentConnectionRequestPacketProcessor());
                        //wsCommunicationCloudServer.registerFermatPacketProcessor(new MessageTransmitPacketProcessor()); DEPRECATE
                        wsCommunicationCloudServer.registerFermatPacketProcessor(new RequestListComponentRegisterPacketProcessor());

                        wsCommunicationCloudServer.start();


                        System.out.println("New CommunicationChannelAddress linked on " + networkInterface.getName());
                        System.out.println("Host = " + inetSocketAddress.getHostString());
                        System.out.println("Port = "     + inetSocketAddress.getPort());
                        System.out.println("Communication Service Manager on " + networkInterface.getName() + " started.");

                    }

                }

            }
        } catch (SocketException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

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
        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
        }

        /*
         * Clear the list
         */
        listenersAdded.clear();

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
        returnedClasses.add("com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.WsCommunicationsServerCloudPluginRoot");
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
            if (WsCommunicationsServerCloudPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                WsCommunicationsServerCloudPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                WsCommunicationsServerCloudPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                WsCommunicationsServerCloudPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
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
            return WsCommunicationsServerCloudPluginRoot.newLoggingLevel.get(correctedClass[0]);
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
