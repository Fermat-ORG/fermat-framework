/*
 * @#CommunicationServerPluginRoot.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.agents.WsCommunicationsCloudClientSupervisorConnectionAgent;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.WsCommunicationsTyrusCloudClientConnection;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.util.ServerConf;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communication.server.developer.bitdubai.version_1.WsCommunicationsCloudClientPluginRoot</code> is
 * the responsible to initialize all component to work together, and hold all resources they needed.
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/09/15.
 *
 * @version 1.0
 */
public class WsCommunicationsCloudClientPluginRoot extends AbstractPlugin implements WsCommunicationsCloudClientManager {

    /**
     * Addons References definition.
     */
    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.DEVICE_LOCATION)
    private LocationManager locationManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem;
//
//    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.HARDWARE)
//    protected HardwareManager hardwareManager;


    private static WsCommunicationsCloudClientPluginRoot instance = new WsCommunicationsCloudClientPluginRoot();

    /**
     * Represent the SERVER_IP
     */
    private String SERVER_IP;
    //public static final String SERVER_IP = ServerConf.SERVER_IP_DEVELOPER_LOCAL;

    /**
     * Represent the PORT
     */
    private Integer PORT;

    /**
     * Represent the executor
     */
    private ScheduledExecutorService scheduledExecutorService;

    /**
     * Represent the uri
     */
    private URI uri;

    /**
     * Represent the clientIdentity
     */
    private ECCKeyPair clientIdentity;

    /*
     * Hold the list of event listeners
     */
    private List<FermatEventListener> listenersAdded = new ArrayList<>();

    /**
     * Represent the disableClientFlagq
     */
    private Boolean disableClientFlag;

    /**
     * Represent the flag
     */
    private AtomicBoolean flag = new AtomicBoolean(false);

    /**
     * Represent the wsCommunicationsTyrusCloudClientConnection
     */
    private  WsCommunicationsTyrusCloudClientConnection wsCommunicationsTyrusCloudClientConnection;

    /**
     * Represent the isTaskCompleted
     */
    private boolean isTaskCompleted;

    /**
     * Represent the networkState
     */
    private boolean networkState;

    /**
     * Constructor
     */
    public WsCommunicationsCloudClientPluginRoot(){
        super(new PluginVersionReference(new Version()));
        this.disableClientFlag = ServerConf.ENABLE_CLIENT;
        isTaskCompleted = Boolean.FALSE;
        scheduledExecutorService = Executors.newScheduledThreadPool(2);
    }

    public static AbstractPlugin getInstance() {
        return instance;
    }

    /**
     * This method validate is all required resource are injected into
     * the plugin root by the platform
     *
     * @throws CantStartPluginException
     */
    private void validateInjectedResources() throws CantStartPluginException {

         /*
         * If all resources are inject
         */
        if (eventManager    == null ||
            locationManager == null ||
            errorManager    == null ||
            pluginFileSystem == null) {

            String context = "Plugin ID: "       + pluginId        + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR +
                             "eventManager: "    + eventManager    + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR +
                             "locationManager: " + locationManager + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR +
                             "errorManager: "    + errorManager    + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR +
                             "pluginFileSystem: " + pluginFileSystem;

            CantStartPluginException pluginStartException = new CantStartPluginException(context, "No all required resource are injected");

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WS_COMMUNICATION_CLIENT_CHANNEL, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;

        }

    }

    /**
     * (non-Javadoc)
     *
     * @see Service#start()
     */
    @Override
    public synchronized void start() {

            if(!flag.getAndSet(true)) {
                if (this.serviceStatus != ServiceStatus.STARTING) {
                    serviceStatus = ServiceStatus.STARTING;

                    try {

                        /*
                         * Validate required resources
                         */
                        validateInjectedResources();

                        if (disableClientFlag) {
                            System.out.println("WsCommunicationsCloudClientPluginRoot - Local Client is Disable, no started");
                            return;
                        }

                        /*
                         * Initialize the Ip And Port to connect to the CLoud Server
                         */
                        initializeConfigurationPropertiesFile();

                        System.out.println("WsCommunicationsCloudClientPluginRoot - Starting plugin");

                        /*
                         * Construct the URI
                         */
                        this.uri = new URI(ServerConf.WS_PROTOCOL + SERVER_IP + ":" + PORT + ServerConf.WEB_SOCKET_CONTEXT_PATH);

                        /*
                         * Initialize the identity for the cloud client
                         */
                        initializeClientIdentity();

                        /*
                         * Try to connect whit the cloud server
                         */
                        System.out.println(" WsCommunicationsCloudClientPluginRoot - ================================================================");
                        System.out.println(" WsCommunicationsCloudClientPluginRoot - Connecting with the cloud server. Server IP ("+SERVER_IP+")");
                        System.out.println(" WsCommunicationsCloudClientPluginRoot - ================================================================");


                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                try {

                                    wsCommunicationsTyrusCloudClientConnection = new WsCommunicationsTyrusCloudClientConnection(uri, eventManager, locationManager, clientIdentity, (WsCommunicationsCloudClientPluginRoot)getInstance());
                                    wsCommunicationsTyrusCloudClientConnection.initializeAndConnect();

                                    /*
                                     * Scheduled the reconnection agent
                                     */
                                    scheduledExecutorService.scheduleAtFixedRate(new WsCommunicationsCloudClientSupervisorConnectionAgent((WsCommunicationsCloudClientPluginRoot) getInstance()), 10, 20, TimeUnit.SECONDS);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();


                    } catch (Exception e) {
                        e.printStackTrace();
                        //wsCommunicationsTyrusCloudClientConnection.getWsCommunicationsCloudClientChannel().close();
                        throw new RuntimeException(e);
                    }
                }

                /*
                 * Set the new status of the service
                 */
                this.serviceStatus = ServiceStatus.STARTED;

            }

    }

    /**
     * Method that connect the client whit the server
     *
     * @throws URISyntaxException
     */
    @Override
    public void reloadConnectClient() throws Exception {

        System.out.println(" WsCommunicationsCloudClientPluginRoot - ****************************************************************");
        System.out.println(" WsCommunicationsCloudClientPluginRoot - ReloadConnecting with the cloud server. Server IP ("+SERVER_IP+")  Port "+PORT);
        System.out.println(" WsCommunicationsCloudClientPluginRoot - ****************************************************************");

        /*
         * reset Url to server
         */
        uri = new URI(ServerConf.WS_PROTOCOL + SERVER_IP + ":" + PORT + ServerConf.WEB_SOCKET_CONTEXT_PATH);

        if (wsCommunicationsTyrusCloudClientConnection != null){
            wsCommunicationsTyrusCloudClientConnection.setNotTryToReconnectToCloud();
            wsCommunicationsTyrusCloudClientConnection.closeMainConnection();
        }

        wsCommunicationsTyrusCloudClientConnection = new WsCommunicationsTyrusCloudClientConnection(uri,eventManager, locationManager, clientIdentity, (WsCommunicationsCloudClientPluginRoot) getInstance());
        wsCommunicationsTyrusCloudClientConnection.initializeAndConnect();

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
         * Change the status
         */
        this.serviceStatus = ServiceStatus.STOPPED;

    }

    /**
     * Get the DisableClientFlag
     * @return Boolean
     */
    public Boolean getDisableClientFlag() {
        return disableClientFlag;
    }

    /**
     * Set Disable Server Flag
     *
     * @param disableClientFlag
     */
    public void setDisableClientFlag(Boolean disableClientFlag) {
        this.disableClientFlag = disableClientFlag;
    }

    /**
     * (non-Javadoc)
     *
     * @see WsCommunicationsCloudClientManager#getCommunicationsCloudClientConnection()
     * //TODO: acá hay que pasarle algo para devolverle el cloud client del server
     */
    @Override
    public CommunicationsClientConnection getCommunicationsCloudClientConnection() {
        return wsCommunicationsTyrusCloudClientConnection;
    }

    /**
     * (non-Javadoc)
     *
     * @see WsCommunicationsCloudClientManager#isDisable()
     */
    @Override
    public Boolean isDisable() {
        return getDisableClientFlag();
    }

    @Override
    public void setNetworkState(boolean state) {
        networkState = state;
    }

    /*
     * set the ipAddress and the port to connect to the Cloud Server
     */
    @Override
    public void changeIpAndPortProperties(String ipAddress, Integer port) {

        this.SERVER_IP = (ipAddress != null) ? ipAddress : ServerConf.SERVER_IP_PRODUCTION;
        this.PORT = (port != null && port > 0) ? port : ServerConf.DEFAULT_PORT;

        /*
         * Set the ipAddress and the port in the File Text
         */
        try {
            editServerIpAndPortProperties(SERVER_IP, PORT);
            reloadConnectClient();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * change default values the ipAddress and the port to connect to the Cloud Server
     */
    @Override
    public void resetIpAndPortDefault() {

        this.SERVER_IP = ServerConf.SERVER_IP_PRODUCTION;
        this.PORT = ServerConf.DEFAULT_PORT;

        /*
         * Set the ipAddress and the port in the File Text
         */
        try {
            editServerIpAndPortProperties(ServerConf.SERVER_IP_PRODUCTION, ServerConf.DEFAULT_PORT);
            reloadConnectClient();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Initialize the clientIdentity of this plugin
     */
    private void initializeClientIdentity() throws CantStartPluginException {

        System.out.println("Calling the method - initializeClientIdentity() ");

        try {

            System.out.println("Loading clientIdentity");

             /*
              * Load the file with the clientIdentity
              */
            PluginTextFile pluginTextFile = pluginFileSystem.getTextFile(pluginId, "private", "clientIdentity", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            String content = pluginTextFile.getContent();

            //System.out.println("content = "+content);

            clientIdentity = new ECCKeyPair(content);

        } catch (FileNotFoundException e) {

            /*
             * The file no exist may be the first time the plugin is running on this device,
             * We need to create the new clientIdentity
             */
            try {

                System.out.println("No previous clientIdentity found - Proceed to create new one");

                /*
                 * Create the new clientIdentity
                 */
                clientIdentity = new ECCKeyPair();

                /*
                 * save into the file
                 */
                PluginTextFile pluginTextFile = pluginFileSystem.createTextFile(pluginId, "private", "clientIdentity", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                pluginTextFile.setContent(clientIdentity.getPrivateKey());
                pluginTextFile.persistToMedia();

            } catch (Exception exception) {
                /*
                 * The file cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WS_COMMUNICATION_CLIENT_CHANNEL, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                throw new CantStartPluginException(exception.getLocalizedMessage());
            }


        } catch (CantCreateFileException cantCreateFileException) {

            /*
             * The file cannot be load. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WS_COMMUNICATION_CLIENT_CHANNEL, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantCreateFileException);
            throw new CantStartPluginException(cantCreateFileException.getLocalizedMessage());

        }

    }

    /**
     * Initialize the Ip And Port to connect to the CLoud Server
     */
    private void initializeConfigurationPropertiesFile() throws CantStartPluginException {

        System.out.println("Calling the method - initializeConfigurationPropertiesFile() ");

        try{

            System.out.println("Loading ServerIpAndPort");

            /*
            * Load the file with the configuration
            */
            PluginTextFile pluginTextFile = pluginFileSystem.getTextFile(pluginId, "private", "server_conf", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            String content = pluginTextFile.getContent();

            /*
             *  get content from json String
             */
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(content).getAsJsonObject();

            /*
             * we set the SERVER_IP and PORT from text file json
             */
            try {
                SERVER_IP = jsonObject.get("ip").getAsString();
                PORT = jsonObject.get("port").getAsInt();
            }catch (Exception e){
                SERVER_IP = ServerConf.SERVER_IP_PRODUCTION;
                PORT =  ServerConf.DEFAULT_PORT;
            }

        }catch (FileNotFoundException e){

             /*
             * The file no exist may be the first time the plugin is running on this device,
             * We need to create the new configuration
             */
            try {

                System.out.println("No previous ServerIpAndPort found - Proceed to create new one");

                /*
                 * we construct the Json to text file Content
                 */
                Gson gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("ip",ServerConf.SERVER_IP_PRODUCTION);
                jsonObject.addProperty("port", ServerConf.DEFAULT_PORT);

                /*
                 * set to default value
                 */
                SERVER_IP = ServerConf.SERVER_IP_PRODUCTION;
                PORT =  ServerConf.DEFAULT_PORT;

                 /*
                 * save into the file
                 */
                PluginTextFile pluginTextFile = pluginFileSystem.createTextFile(pluginId, "private", "server_conf", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                pluginTextFile.setContent(gson.toJson(jsonObject));
                pluginTextFile.persistToMedia();


            }catch (Exception exception){
                /*
                 * The file cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WS_COMMUNICATION_CLIENT_CHANNEL, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                throw new CantStartPluginException(exception.getLocalizedMessage());
            }

        }catch (CantCreateFileException cantCreateFileException){

              /*
             * The file cannot be load. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WS_COMMUNICATION_CLIENT_CHANNEL, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantCreateFileException);
            throw new CantStartPluginException(cantCreateFileException.getLocalizedMessage());

        }


    }

    /*
     * Set the ipAddress and the port in the File Text
     */
    private void editServerIpAndPortProperties(String ipAddress, Integer port) throws Exception{

        try{

             /*
            * Load the file with the configuration
            */
            PluginTextFile pluginTextFile = pluginFileSystem.getTextFile(pluginId, "private", "server_conf", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            pluginTextFile.loadFromMedia();
            String content = pluginTextFile.getContent();

            /*
             * we construct the Json to text file Content
             */
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("ip",ipAddress);
            jsonObject.addProperty("port", port);

            /*
             * edit the content
             */
            pluginTextFile.setContent(gson.toJson(jsonObject));
            pluginTextFile.persistToMedia();

        }catch(Exception exception){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WS_COMMUNICATION_CLIENT_CHANNEL, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new Exception(exception.getLocalizedMessage());
        }

    }

    /*
     * get the Server Ip
     */
    public String getServerIp() {
        return SERVER_IP;
    }

    /*
     * get the Server Port
     */
    public Integer getServerPort(){
        return PORT;
    }

}
