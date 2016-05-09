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
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentRegistrationNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.agents.WsCommunicationsCloudClientSupervisorConnectionAgent;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.WsCommunicationsTyrusCloudClientConnection;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.util.ServerConf;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.websocket.DeploymentException;

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
     * Represent the wsCommunicationsTyrusCloudClientConnection Backup for the connection to AWS
     */
    private  WsCommunicationsTyrusCloudClientConnection wsCommunicationsTyrusCloudClientConnectionBackup;

    /**
     * Represent the list of wsCommunicationsTyrusCloudClientConnection
     */
    private Map<NetworkServiceType,WsCommunicationsTyrusCloudClientConnection> listOfWSCommunicationsTyrusCloudClientConnection;

    /*
     * Represent the list of Platform Server Actives
     */
    private Map<NetworkServiceType,String> listServerConfByPlatform;

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
        listOfWSCommunicationsTyrusCloudClientConnection = new HashMap<>();
        listServerConfByPlatform = new HashMap<>();
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
                        //initializeConfigurationPropertiesFile();

                        System.out.println("WsCommunicationsCloudClientPluginRoot - Starting plugin");

                        /*
                         * Construct the URI
                         */
                        this.uri = new URI(ServerConf.WS_PROTOCOL + ServerConf.SERVER_IP_PRODUCTION + ":" + ServerConf.DEFAULT_PORT + ServerConf.WEB_SOCKET_CONTEXT_PATH);

                        /*
                         * Initialize the identity for the cloud client
                         */
                        initializeClientIdentity();

                        /*
                         * Initialize Credentials to Connection to backup Connection AWS
                         */
                        wsCommunicationsTyrusCloudClientConnectionBackup = new WsCommunicationsTyrusCloudClientConnection(uri, eventManager, locationManager, clientIdentity, (WsCommunicationsCloudClientPluginRoot)getInstance(),ServerConf.SERVER_IP_PRODUCTION,ServerConf.DEFAULT_PORT, NetworkServiceType.UNDEFINED);
                        listServerConfByPlatform = listserverconfbyplatform();

                        /*
                         * Try to connect whit the cloud server
                         */
                        System.out.println(" WsCommunicationsCloudClientPluginRoot - ================================================================");
                        System.out.println(" WsCommunicationsCloudClientPluginRoot - Connecting with All the Multiples cloud server");
                        System.out.println(" WsCommunicationsCloudClientPluginRoot - ================================================================");


                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                try {

                                    loadAllMultiplesServerInTheList();
                                    connectingAllMultiplesServer();

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

    /*
     * load All MultiplesServer in the list of listOfWSCommunicationsTyrusCloudClientConnection
     */
    private void loadAllMultiplesServerInTheList() throws Exception {



        if(listServerConfByPlatform != null) {

            URI uriNewUrl;
            WsCommunicationsTyrusCloudClientConnection wsCommunicationsTyrusCloudClientConnectionNewUrl;

        /* CCP */
            if(listServerConfByPlatform.containsKey(NetworkServiceType.INTRA_USER)) {
                uriNewUrl = new URI(ServerConf.WS_PROTOCOL + listServerConfByPlatform.get(NetworkServiceType.INTRA_USER)  + ":" + 9090 + ServerConf.WEB_SOCKET_CONTEXT_PATH);
                wsCommunicationsTyrusCloudClientConnectionNewUrl = new
                        WsCommunicationsTyrusCloudClientConnection(uriNewUrl, eventManager, locationManager, clientIdentity, (WsCommunicationsCloudClientPluginRoot) getInstance(), "192.168.1.4", 9090, NetworkServiceType.INTRA_USER);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.CRYPTO_ADDRESSES, wsCommunicationsTyrusCloudClientConnectionNewUrl);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.CRYPTO_PAYMENT_REQUEST, wsCommunicationsTyrusCloudClientConnectionNewUrl);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.CRYPTO_TRANSMISSION, wsCommunicationsTyrusCloudClientConnectionNewUrl);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.INTRA_USER, wsCommunicationsTyrusCloudClientConnectionNewUrl);
            }else{
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.CRYPTO_ADDRESSES, wsCommunicationsTyrusCloudClientConnectionBackup);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.CRYPTO_PAYMENT_REQUEST, wsCommunicationsTyrusCloudClientConnectionBackup);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.CRYPTO_TRANSMISSION, wsCommunicationsTyrusCloudClientConnectionBackup);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.INTRA_USER, wsCommunicationsTyrusCloudClientConnectionBackup);

            }
        /* CCP */


        /* CBP */
            if(listServerConfByPlatform.containsKey(NetworkServiceType.CRYPTO_BROKER)) {
                uriNewUrl = new URI(ServerConf.WS_PROTOCOL + listServerConfByPlatform.get(NetworkServiceType.CRYPTO_BROKER) + ":" + 9090 + ServerConf.WEB_SOCKET_CONTEXT_PATH);
                wsCommunicationsTyrusCloudClientConnectionNewUrl = new
                        WsCommunicationsTyrusCloudClientConnection(uriNewUrl, eventManager, locationManager, clientIdentity, (WsCommunicationsCloudClientPluginRoot) getInstance(), "192.168.1.5", 9090, NetworkServiceType.CRYPTO_BROKER);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.CRYPTO_BROKER, wsCommunicationsTyrusCloudClientConnectionNewUrl);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.CRYPTO_CUSTOMER, wsCommunicationsTyrusCloudClientConnectionNewUrl);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.TRANSACTION_TRANSMISSION, wsCommunicationsTyrusCloudClientConnectionNewUrl);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.NEGOTIATION_TRANSMISSION, wsCommunicationsTyrusCloudClientConnectionNewUrl);
            }else{
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.CRYPTO_BROKER, wsCommunicationsTyrusCloudClientConnectionBackup);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.CRYPTO_CUSTOMER, wsCommunicationsTyrusCloudClientConnectionBackup);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.TRANSACTION_TRANSMISSION, wsCommunicationsTyrusCloudClientConnectionBackup);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.NEGOTIATION_TRANSMISSION, wsCommunicationsTyrusCloudClientConnectionBackup);
            }
        /* CBP */


        /* DAP */
            if(listServerConfByPlatform.containsKey(NetworkServiceType.ASSET_USER_ACTOR)) {
                uriNewUrl = new URI(ServerConf.WS_PROTOCOL + listServerConfByPlatform.get(NetworkServiceType.ASSET_USER_ACTOR) + ":" + 9090 + ServerConf.WEB_SOCKET_CONTEXT_PATH);
                wsCommunicationsTyrusCloudClientConnectionNewUrl = new
                        WsCommunicationsTyrusCloudClientConnection(uriNewUrl, eventManager, locationManager, clientIdentity, (WsCommunicationsCloudClientPluginRoot) getInstance(), "192.168.1.6", 9090, NetworkServiceType.ASSET_USER_ACTOR);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.ASSET_USER_ACTOR, wsCommunicationsTyrusCloudClientConnectionNewUrl);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.ASSET_ISSUER_ACTOR, wsCommunicationsTyrusCloudClientConnectionNewUrl);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.ASSET_REDEEM_POINT_ACTOR, wsCommunicationsTyrusCloudClientConnectionNewUrl);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.ASSET_TRANSMISSION, wsCommunicationsTyrusCloudClientConnectionNewUrl);
            }else{
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.ASSET_USER_ACTOR, wsCommunicationsTyrusCloudClientConnectionBackup);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.ASSET_ISSUER_ACTOR, wsCommunicationsTyrusCloudClientConnectionBackup);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.ASSET_REDEEM_POINT_ACTOR, wsCommunicationsTyrusCloudClientConnectionBackup);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.ASSET_TRANSMISSION, wsCommunicationsTyrusCloudClientConnectionBackup);
            }
        /* DAP */

        /* Artist */
            if(listServerConfByPlatform.containsKey(NetworkServiceType.ARTIST_ACTOR)) {
                uriNewUrl = new URI(ServerConf.WS_PROTOCOL + listServerConfByPlatform.get(NetworkServiceType.ARTIST_ACTOR) + ":" + 9090 + ServerConf.WEB_SOCKET_CONTEXT_PATH);
                wsCommunicationsTyrusCloudClientConnectionNewUrl = new
                        WsCommunicationsTyrusCloudClientConnection(uriNewUrl, eventManager, locationManager, clientIdentity, (WsCommunicationsCloudClientPluginRoot) getInstance(), "192.168.1.7", 9090, NetworkServiceType.ARTIST_ACTOR);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.ARTIST_ACTOR, wsCommunicationsTyrusCloudClientConnectionNewUrl);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.FAN_ACTOR, wsCommunicationsTyrusCloudClientConnectionNewUrl);
            }else{
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.ARTIST_ACTOR, wsCommunicationsTyrusCloudClientConnectionBackup);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.FAN_ACTOR, wsCommunicationsTyrusCloudClientConnectionBackup);
            }
        /* Artist */


        /* CHAT */
            if(listServerConfByPlatform.containsKey(NetworkServiceType.CHAT)) {
                uriNewUrl = new URI(ServerConf.WS_PROTOCOL + listServerConfByPlatform.get(NetworkServiceType.CHAT) + ":" + 9090 + ServerConf.WEB_SOCKET_CONTEXT_PATH);
                wsCommunicationsTyrusCloudClientConnectionNewUrl = new
                        WsCommunicationsTyrusCloudClientConnection(uriNewUrl, eventManager, locationManager, clientIdentity, (WsCommunicationsCloudClientPluginRoot) getInstance(), "192.168.1.8", 9090, NetworkServiceType.CHAT);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.CHAT, wsCommunicationsTyrusCloudClientConnectionNewUrl);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.ACTOR_CHAT, wsCommunicationsTyrusCloudClientConnectionNewUrl);

            }else{
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.CHAT, wsCommunicationsTyrusCloudClientConnectionBackup);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.ACTOR_CHAT, wsCommunicationsTyrusCloudClientConnectionBackup);

            }
        /* CHAT */

        /* Fermat Monitor */
            if(listServerConfByPlatform.containsKey(NetworkServiceType.FERMAT_MONITOR)) {
                uriNewUrl = new URI(ServerConf.WS_PROTOCOL + listServerConfByPlatform.get(NetworkServiceType.FERMAT_MONITOR) + ":" + 9090 + ServerConf.WEB_SOCKET_CONTEXT_PATH);
                wsCommunicationsTyrusCloudClientConnectionNewUrl = new
                        WsCommunicationsTyrusCloudClientConnection(uriNewUrl, eventManager, locationManager, clientIdentity, (WsCommunicationsCloudClientPluginRoot) getInstance(), "192.168.1.9", 9090, NetworkServiceType.FERMAT_MONITOR);
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.FERMAT_MONITOR, wsCommunicationsTyrusCloudClientConnectionNewUrl);
            }else{
                listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.FERMAT_MONITOR, wsCommunicationsTyrusCloudClientConnectionBackup);
            }
        /* Fermat Monitor */


        }else{

            /* CCP */
            listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.CRYPTO_ADDRESSES, wsCommunicationsTyrusCloudClientConnectionBackup);
            listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.CRYPTO_PAYMENT_REQUEST, wsCommunicationsTyrusCloudClientConnectionBackup);
            listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.CRYPTO_TRANSMISSION, wsCommunicationsTyrusCloudClientConnectionBackup);
            listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.INTRA_USER, wsCommunicationsTyrusCloudClientConnectionBackup);
            /* CCP */

            /* CBP */
            listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.CRYPTO_BROKER, wsCommunicationsTyrusCloudClientConnectionBackup);
            listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.CRYPTO_CUSTOMER, wsCommunicationsTyrusCloudClientConnectionBackup);
            listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.TRANSACTION_TRANSMISSION, wsCommunicationsTyrusCloudClientConnectionBackup);
            listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.NEGOTIATION_TRANSMISSION, wsCommunicationsTyrusCloudClientConnectionBackup);
            /* CBP */

            /* DAP */
            listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.ASSET_USER_ACTOR, wsCommunicationsTyrusCloudClientConnectionBackup);
            listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.ASSET_ISSUER_ACTOR, wsCommunicationsTyrusCloudClientConnectionBackup);
            listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.ASSET_REDEEM_POINT_ACTOR, wsCommunicationsTyrusCloudClientConnectionBackup);
            listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.ASSET_TRANSMISSION, wsCommunicationsTyrusCloudClientConnectionBackup);
            /* DAP */

            /* Artist */
            listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.ARTIST_ACTOR, wsCommunicationsTyrusCloudClientConnectionBackup);
            listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.FAN_ACTOR, wsCommunicationsTyrusCloudClientConnectionBackup);
            /* Artist */

            /* CHAT */
            listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.CHAT, wsCommunicationsTyrusCloudClientConnectionBackup);
            listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.ACTOR_CHAT, wsCommunicationsTyrusCloudClientConnectionBackup);
            /* CHAT */

            /* Fermat Monitor */
            listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.FERMAT_MONITOR, wsCommunicationsTyrusCloudClientConnectionBackup);
            /* Fermat Monitor */

        }



    }

    /*
     * Connect Concurrently All MultiplesServer in the list of listOfWSCommunicationsTyrusCloudClientConnection
     * the method initializeAndConnect
     */
    private void connectingAllMultiplesServer() throws Exception{

        int n = 1;
        Runnable threadCCP = null, threadCBP = null, threadDAP = null, threadArtist = null, threadCHT = null, threadFM = null;

        Runnable threadBackupMain = new Runnable() {
            @Override
            public void run() {
                try {
                    wsCommunicationsTyrusCloudClientConnectionBackup.initializeAndConnect();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DeploymentException e) {
                    e.printStackTrace();
                }
            }
        };

        if(listServerConfByPlatform != null) {

            if(listServerConfByPlatform.containsKey(NetworkServiceType.INTRA_USER)) {
                threadCCP = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            listOfWSCommunicationsTyrusCloudClientConnection.get(NetworkServiceType.INTRA_USER).initializeAndConnect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (DeploymentException e) {
                            e.printStackTrace();
                        }
                    }
                };
                n++;
            }


            if(listServerConfByPlatform.containsKey(NetworkServiceType.CRYPTO_BROKER)) {
                threadCBP = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            listOfWSCommunicationsTyrusCloudClientConnection.get(NetworkServiceType.CRYPTO_BROKER).initializeAndConnect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (DeploymentException e) {
                            e.printStackTrace();
                        }
                    }
                };
                n++;
            }

            if(listServerConfByPlatform.containsKey(NetworkServiceType.ASSET_USER_ACTOR)) {
                threadDAP = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            listOfWSCommunicationsTyrusCloudClientConnection.get(NetworkServiceType.ASSET_USER_ACTOR).initializeAndConnect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (DeploymentException e) {
                            e.printStackTrace();
                        }
                    }
                };
                n++;
            }

            if(listServerConfByPlatform.containsKey(NetworkServiceType.ARTIST_ACTOR)) {
                threadArtist = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            listOfWSCommunicationsTyrusCloudClientConnection.get(NetworkServiceType.ARTIST_ACTOR).initializeAndConnect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (DeploymentException e) {
                            e.printStackTrace();
                        }
                    }
                };
                n++;
            }

            if(listServerConfByPlatform.containsKey(NetworkServiceType.CHAT)) {
                threadCHT = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            listOfWSCommunicationsTyrusCloudClientConnection.get(NetworkServiceType.CHAT).initializeAndConnect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (DeploymentException e) {
                            e.printStackTrace();
                        }
                    }
                };
                n++;
            }

            if(listServerConfByPlatform.containsKey(NetworkServiceType.FERMAT_MONITOR)) {
                threadFM = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            listOfWSCommunicationsTyrusCloudClientConnection.get(NetworkServiceType.FERMAT_MONITOR).initializeAndConnect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (DeploymentException e) {
                            e.printStackTrace();
                        }
                    }
                };
                n++;
            }

        }

        ExecutorService executorService = Executors.newFixedThreadPool(n);
        executorService.submit(threadBackupMain);

        if(threadCCP != null)
          executorService.submit(threadCCP);

        if(threadCBP != null)
          executorService.submit(threadCBP);

        if(threadDAP != null)
          executorService.submit(threadDAP);

        if(threadArtist != null)
          executorService.submit(threadArtist);

        if(threadCHT != null)
          executorService.submit(threadCHT);

        if(threadFM != null)
          executorService.submit(threadFM);


    }

    /**
     * Method that connect the client whit the server
     *
     * @throws URISyntaxException
     */
    @Override
    public void reloadConnectClient() throws Exception {

        System.out.println(" WsCommunicationsCloudClientPluginRoot - ****************************************************************");
        System.out.println(" WsCommunicationsCloudClientPluginRoot - ReloadConnecting with the cloud server. Server IP (" + SERVER_IP + ")  Port " + PORT);
        System.out.println(" WsCommunicationsCloudClientPluginRoot - ****************************************************************");

        /*
         * reset Url to server
         */
        uri = new URI(ServerConf.WS_PROTOCOL + SERVER_IP + ":" + PORT + ServerConf.WEB_SOCKET_CONTEXT_PATH);

        if (wsCommunicationsTyrusCloudClientConnectionBackup != null){
            wsCommunicationsTyrusCloudClientConnectionBackup.setNotTryToReconnectToCloud();
            wsCommunicationsTyrusCloudClientConnectionBackup.closeMainConnection();
        }

        wsCommunicationsTyrusCloudClientConnectionBackup = new WsCommunicationsTyrusCloudClientConnection(uri,eventManager, locationManager, clientIdentity, (WsCommunicationsCloudClientPluginRoot) getInstance(),SERVER_IP,PORT,NetworkServiceType.UNDEFINED);
        wsCommunicationsTyrusCloudClientConnectionBackup.initializeAndConnect();

    }

    @Override
    public boolean isConnected() {
        for (WsCommunicationsTyrusCloudClientConnection wsCommunicationsTyrusCloudClientConnection : listOfWSCommunicationsTyrusCloudClientConnection.values()) {
            if(wsCommunicationsTyrusCloudClientConnection.isConnected()){
                return true;
            }
        }
        return false;
    }

    public void connectToNewPlatformCloudServer(List<NetworkServiceType> listOfnetworkService, NetworkServiceType networkServiceType, String ipServerNew) throws Exception {

        URI urinewServer = new URI(ServerConf.WS_PROTOCOL + ipServerNew + ":" + 9090 + ServerConf.WEB_SOCKET_CONTEXT_PATH);
        WsCommunicationsTyrusCloudClientConnection communicationsTyrusCloudClientConnectionNewServer = new WsCommunicationsTyrusCloudClientConnection(urinewServer,eventManager, locationManager, clientIdentity, (WsCommunicationsCloudClientPluginRoot) getInstance(), ipServerNew, 9090, networkServiceType);

        for(NetworkServiceType NS : listOfnetworkService){
            listOfWSCommunicationsTyrusCloudClientConnection.put(NS, communicationsTyrusCloudClientConnectionNewServer);
        }

        communicationsTyrusCloudClientConnectionNewServer.initializeAndConnect();

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
     * @see WsCommunicationsCloudClientManager#()
     * //TODO: acá hay que pasarle algo para devolverle el cloud client del server
     * @see WsCommunicationsCloudClientManager#getCommunicationsCloudClientConnection(NetworkServiceType networkServiceType)
     */
    @Override
    public CommunicationsClientConnection getCommunicationsCloudClientConnection(NetworkServiceType networkServiceType) {
        if(listOfWSCommunicationsTyrusCloudClientConnection.containsKey(networkServiceType)){
            return  listOfWSCommunicationsTyrusCloudClientConnection.get(networkServiceType);
        }else{
            return wsCommunicationsTyrusCloudClientConnectionBackup;
        }

    }

    public void connectToBackupConnection(NetworkServiceType networkServiceType) throws Exception {

        System.out.println("************************************* Connect To Backup Connection AWS ********************************************");


          switch (networkServiceType){
              case INTRA_USER :
                      listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.INTRA_USER,wsCommunicationsTyrusCloudClientConnectionBackup);
                      listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.CRYPTO_ADDRESSES,wsCommunicationsTyrusCloudClientConnectionBackup);
                      listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.CRYPTO_PAYMENT_REQUEST,wsCommunicationsTyrusCloudClientConnectionBackup);
                      listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.CRYPTO_TRANSMISSION,wsCommunicationsTyrusCloudClientConnectionBackup);
                  break;
              case CRYPTO_BROKER :
                      listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.CRYPTO_BROKER,wsCommunicationsTyrusCloudClientConnectionBackup);
                      listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.CRYPTO_CUSTOMER,wsCommunicationsTyrusCloudClientConnectionBackup);
                      listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.TRANSACTION_TRANSMISSION,wsCommunicationsTyrusCloudClientConnectionBackup);
                      listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.NEGOTIATION_TRANSMISSION,wsCommunicationsTyrusCloudClientConnectionBackup);
                  break;
              case ASSET_USER_ACTOR:
                      listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.ASSET_USER_ACTOR,wsCommunicationsTyrusCloudClientConnectionBackup);
                      listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.ASSET_ISSUER_ACTOR,wsCommunicationsTyrusCloudClientConnectionBackup);
                      listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.ASSET_REDEEM_POINT_ACTOR,wsCommunicationsTyrusCloudClientConnectionBackup);
                      listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.ASSET_TRANSMISSION,wsCommunicationsTyrusCloudClientConnectionBackup);
                  break;
              case ARTIST_ACTOR:
                      listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.ARTIST_ACTOR,wsCommunicationsTyrusCloudClientConnectionBackup);
                      listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.FAN_ACTOR,wsCommunicationsTyrusCloudClientConnectionBackup);
                  break;
              case CHAT:
                      listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.CHAT,wsCommunicationsTyrusCloudClientConnectionBackup);
                      listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.ACTOR_CHAT,wsCommunicationsTyrusCloudClientConnectionBackup);
                  break;
              case FERMAT_MONITOR:
                      listOfWSCommunicationsTyrusCloudClientConnection.put(NetworkServiceType.FERMAT_MONITOR,wsCommunicationsTyrusCloudClientConnectionBackup);
                  break;
              default:
                  System.out.println("-----------ERROR, defaut connection backupt ns type---------------------------------------");
                  break;
          }

//        if(!wsCommunicationsTyrusCloudClientConnectionBackup.isRegister() && !wsCommunicationsTyrusCloudClientConnectionBackup.isConnected()) {
//
//            wsCommunicationsTyrusCloudClientConnectionBackup.initializeAndConnect();
//
//        }else{

             /*
             * Create a raise a new event whit the platformComponentProfile registered
             */
            FermatEvent event = P2pEventType.COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION.getNewEvent();
            event.setSource(EventSource.WS_COMMUNICATION_CLOUD_CLIENT_PLUGIN);

            /*
             * Set the component already register
             */
            ((CompleteComponentRegistrationNotificationEvent)event).setPlatformComponentProfileRegistered(wsCommunicationsTyrusCloudClientConnectionBackup.getWsCommunicationsTyrusCloudClientChannel().getPlatformComponentProfile());
            ((CompleteComponentRegistrationNotificationEvent)event).setNetworkServiceTypeApplicant(NetworkServiceType.UNDEFINED);

            /*
             * Raise the event
             */
            System.out.println("CompleteRegistrationComponentTyrusPacketProcessor - Raised a event = P2pEventType.COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION");
            eventManager.raiseEvent(event);

//        }
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

        //System.out.println("WsCommunicationsCloudClientPluginRoot - clientIdentity.getPublicKey() = "+clientIdentity.getPublicKey());
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

    /*
     * get the List Of WSCommunicationsTyrusCloudClientConnection
     */
    public Map<NetworkServiceType, WsCommunicationsTyrusCloudClientConnection> getListOfWSCommunicationsTyrusCloudClientConnection() {
        return listOfWSCommunicationsTyrusCloudClientConnection;
    }

    private Map<NetworkServiceType,String> listserverconfbyplatform() throws Exception{

        Map<NetworkServiceType,String> listserverplatform = null;

        // Create a new RestTemplate instance
        String respond;
        RestTemplate restTemplate = new RestTemplate(true);
        try {
            respond = restTemplate.getForObject("http://" + wsCommunicationsTyrusCloudClientConnectionBackup.getServerIp() + ":" + wsCommunicationsTyrusCloudClientConnectionBackup.getServerPort() + "/fermat/api/serverplatform/listserverconfbyplatform", String.class);

        }catch (Exception e){
            e.printStackTrace();
            respond = null;
        }

        /*
         * if respond have the result list
         */
        if(respond!=null) {
            if (respond.contains("data")) {

            /*
            * Decode into a json object
            */
                JsonParser parser = new JsonParser();
                JsonObject respondJsonObject = (JsonObject) parser.parse(respond.toString());

                Gson gson = new Gson();
                listserverplatform = gson.fromJson(respondJsonObject.get("data").getAsString(), new TypeToken<Map<NetworkServiceType, String>>() {
                }.getType());

            }
        }

        return listserverplatform;
    }

}
