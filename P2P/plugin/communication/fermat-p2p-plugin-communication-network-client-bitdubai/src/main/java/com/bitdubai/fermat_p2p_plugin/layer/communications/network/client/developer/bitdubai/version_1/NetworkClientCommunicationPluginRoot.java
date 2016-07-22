package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.ConnectivityManager;
import com.bitdubai.fermat_api.layer.osa_android.DeviceNetwork;
import com.bitdubai.fermat_api.layer.osa_android.NetworkStateReceiver;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkChannel;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkClientConnection;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkClientManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.P2PLayerManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NodeProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.context.ClientContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.context.ClientContextItem;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.database.NetworkClientP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.database.NetworkClientP2PDatabaseFactory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.database.daos.NodeConnectionHistoryDao;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.event_handler.NetworkClientConnectedToNodeEventHandler;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.exceptions.CantInitializeNetworkClientP2PDatabaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.NetworkClientCommunicationConnection;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.NetworkClientCommunicationSupervisorConnectionAgent;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.NetworkClientConnectionsManager;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.util.HardcodeConstants;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.NetworkClientCommunicationPluginRoot</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 12/11/15.
 * Updated by Leon Acosta - (laion.cj91@gmail.com) on 07/04/2016.
 *
 * @author Hendry Rodriguez
 * @version 1.0
 * @since Java JDK 1.7
 */
@PluginInfo(createdBy = "Hendry Rodriguez", maintainerMail = "laion.cj91@gmail.com", platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION, plugin = Plugins.NETWORK_CLIENT)
public class NetworkClientCommunicationPluginRoot extends AbstractPlugin implements NetworkClientManager,NetworkChannel {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem        ;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.DEVICE_LOCATION)
    private LocationManager locationManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.DEVICE_CONNECTIVITY)
    private ConnectivityManager connectivityManager;

    //todo: esto va por ahora, más adelante se saca si o si
    @NeededPluginReference(platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION, plugin = Plugins.P2P_LAYER)
    private P2PLayerManager p2PLayerManager;

    /**
     * Represent the node identity
     */
    private ECCKeyPair identity;

    /**
     * Represent the database of the node
     */
    private Database dataBase;

    /**
     * Represent the NetworkClientP2PDatabaseFactory of the client
     */
    private NetworkClientP2PDatabaseFactory networkClientP2PDatabaseFactory;

    /**
     * Represent the SERVER_IP by default conexion to request nodes list
     */
    public static final String SERVER_IP = HardcodeConstants.SERVER_IP_DEFAULT;

    /**
     * Holds the listeners references
     */
    protected List<FermatEventListener> listenersAdded;

    /*
     * Represent the networkClientCommunicationConnection
     */
    private NetworkClientCommunicationConnection networkClientCommunicationConnection;

    /*
     * Represent The executorService
     */
    private ExecutorService executorService;

    /**
     * Represent the list of nodes
     */
    private List<NodeProfile> nodesProfileList;


    /**
     * Represent the executor
     */
    private ScheduledExecutorService scheduledExecutorService;

    /*
     * Represent the networkClientConnectionsManager
     */
    private NetworkClientConnectionsManager networkClientConnectionsManager;


    @Override
    public FermatManager getManager() {
        return this;
    }

    /**
     * Constructor
     */
    public NetworkClientCommunicationPluginRoot() {
        super(new PluginVersionReference(new Version()));
        this.scheduledExecutorService = Executors.newScheduledThreadPool(2);
        this.listenersAdded        = new CopyOnWriteArrayList<>();
    }

    @Override
    public void start() throws CantStartPluginException {

        System.out.println("Calling the method - start() in NetworkClientCommunicationPluginRoot");

        /*
         * Validate required resources
         */
        validateInjectedResources();

        try{

            /*
             * Initialize the identity of the node
             */
            initializeIdentity();

             /*
             * Initialize the Data Base of the node
             */
            initializeDb();

            /*
             * Initialize the networkClientConnectionsManager to the Connections
             */
            networkClientConnectionsManager = new NetworkClientConnectionsManager(identity, eventManager, locationManager, this);

            /*
             * Add references to the node context
             */
            ClientContext.add(ClientContextItem.CLIENT_IDENTITY, identity    );
            ClientContext.add(ClientContextItem.DATABASE, dataBase);
            ClientContext.add(ClientContextItem.LOCATION_MANAGER, locationManager);
            ClientContext.add(ClientContextItem.EVENT_MANAGER, eventManager);
            ClientContext.add(ClientContextItem.CLIENTS_CONNECTIONS_MANAGER, networkClientConnectionsManager);

            /*
             * get NodesProfile List From NodesProfileConnectionHistory table
             */
            nodesProfileList = getNodesProfileFromConnectionHistory();

            if(nodesProfileList != null && nodesProfileList.size() >= 1){

                networkClientCommunicationConnection = new NetworkClientCommunicationConnection(
                        nodesProfileList.get(0).getIp() + ":" + nodesProfileList.get(0).getDefaultPort(),
                        eventManager,
                        locationManager,
                        identity,
                        this,
                        0,
                        Boolean.FALSE,
                        nodesProfileList.get(0)
                );


            }else {

                 /*
                * get NodesProfile List From Restful in Seed Node
                */
                nodesProfileList = getNodesProfileList();

                if (nodesProfileList != null && nodesProfileList.size() > 0) {

                    networkClientCommunicationConnection = new NetworkClientCommunicationConnection(
                            nodesProfileList.get(0).getIp() + ":" + nodesProfileList.get(0).getDefaultPort(),
                            eventManager,
                            locationManager,
                            identity,
                            this,
                            0,
                            Boolean.FALSE,
                            nodesProfileList.get(0)
                    );

                } else {

                    networkClientCommunicationConnection = new NetworkClientCommunicationConnection(
                            NetworkClientCommunicationPluginRoot.SERVER_IP + ":" + HardcodeConstants.DEFAULT_PORT,
                            eventManager,
                            locationManager,
                            identity,
                            this,
                            -1,
                            Boolean.FALSE,
                            null
                    );

                }

            }

            p2PLayerManager.register(this);
//            FermatEventListener networkClientConnected = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_CONNNECTED_TO_NODE);
//            networkClientConnected.setEventHandler(new NetworkClientConnectedToNodeEventHandler(this));
//            eventManager.addListener(networkClientConnected);
//            listenersAdded.add(networkClientConnected);

            connectivityManager.registerListener(new NetworkStateReceiver() {
                @Override
                public void networkAvailable(DeviceNetwork deviceNetwork) {
                    System.out.println("########################################\n");
                    System.out.println("Netowork available!!!!\n+" + "NetworkType: " + deviceNetwork);
                    System.out.println("########################################\n");
                }

                @Override
                public void networkUnavailable() {
                    System.out.println("########################################\n");
                    System.out.println("Netowork UNAVAILABLE!!!!\n");
                    System.out.println("########################################\n");
                }

            });

//            connectivityManager.isConnectedToAnyProvider()


        } catch (Exception exception){

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("Database Name: " + NetworkClientP2PDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The  Network Client Service triggered an unexpected problem that wasn't able to solve by itself";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

            super.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;

        }


    }

    public void register(){
        p2PLayerManager.registerReconnect(this);
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
        if (pluginDatabaseSystem  == null ||
                eventManager  == null) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "No all required resource are injected";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);

            super.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;

        }

    }

    private static final String IDENTITY_FILE_DIRECTORY = "private";
    private static final String IDENTITY_FILE_NAME      = "clientIdentity";

    /**
     * Initialize the identity of this plugin
     */
    private void initializeIdentity() throws CantInitializeNetworkClientP2PDatabaseException {

        System.out.println("Calling the method - initializeIdentity() ");

        try {

            System.out.println("Loading identity");

         /*
          * Load the file with the identity
          */
            PluginTextFile pluginTextFile = pluginFileSystem.getTextFile(pluginId, IDENTITY_FILE_DIRECTORY, IDENTITY_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            String content = pluginTextFile.getContent();

            System.out.println("content = " + content);

            identity = new ECCKeyPair(content);

        } catch (FileNotFoundException e) {

            /*
             * The file no exist may be the first time the plugin is running on this device,
             * We need to create the new identity
             */
            try {

                System.out.println("No previous identity found - Proceed to create new one");

                /*
                 * Create the new identity
                 */
                identity = new ECCKeyPair();

                System.out.println("identity.getPrivateKey() = " + identity.getPrivateKey());
                System.out.println("identity.getPublicKey() = " + identity.getPublicKey());

                /*
                 * save into the file
                 */
                PluginTextFile pluginTextFile = pluginFileSystem.createTextFile(pluginId, IDENTITY_FILE_DIRECTORY, IDENTITY_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                pluginTextFile.setContent(identity.getPrivateKey());
                pluginTextFile.persistToMedia();

            } catch (Exception exception) {
                /*
                 * The file cannot be created. I can not handle this situation.
                 */
                throw new CantInitializeNetworkClientP2PDatabaseException(exception.getLocalizedMessage());
            }


        } catch (CantCreateFileException cantCreateFileException) {

            /*
             * The file cannot be load. I can not handle this situation.
             */
            throw new CantInitializeNetworkClientP2PDatabaseException(cantCreateFileException.getLocalizedMessage());

        }

    }


    /**
     * This method initialize the database
     *
     * @throws CantInitializeNetworkClientP2PDatabaseException
     */
    private void initializeDb() throws CantInitializeNetworkClientP2PDatabaseException {

        System.out.println("Calling the method - initializeDb() ");

        try {

            System.out.println("Loading database");
            /*
             * Open new database connection
             */
            this.dataBase = this.pluginDatabaseSystem.openDatabase(pluginId, NetworkClientP2PDatabaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            super.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeNetworkClientP2PDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            try {

                System.out.println("No previous data base found - Proceed to create new one");

                /*
                 * We create the new database
                 */
                this.networkClientP2PDatabaseFactory = new NetworkClientP2PDatabaseFactory(pluginDatabaseSystem);
                this.dataBase = networkClientP2PDatabaseFactory.createDatabase(pluginId, NetworkClientP2PDatabaseConstants.DATA_BASE_NAME);


            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                super.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeNetworkClientP2PDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }

    }

    /*
     * Receive the Actual index of the Nodes list
     */
    public void intentToConnectToOtherNode(Integer i){

//        if(executorService != null)
//            executorService.shutdownNow();

        /*
         * if is the last index then connect to networkNode Harcoded
         * else intent connect to other networkNode
         */
        if(nodesProfileList != null  && nodesProfileList.size() > 0 && i < (nodesProfileList.size() - 1)){

            networkClientCommunicationConnection = new NetworkClientCommunicationConnection(
                    nodesProfileList.get(i+1).getIp() + ":" + nodesProfileList.get(i+1).getDefaultPort(),
                    eventManager,
                    locationManager,
                    identity,
                    this,
                    i+1,
                    Boolean.FALSE,
                    nodesProfileList.get(i+1)
            );

        }else{

            networkClientCommunicationConnection = new NetworkClientCommunicationConnection(
                    NetworkClientCommunicationPluginRoot.SERVER_IP + ":" + 8080,
                    eventManager,
                    locationManager,
                    identity,
                    this,
                    -1,
                    Boolean.FALSE,
                    null
            );

        }

        Runnable runnable = new Runnable(){
            @Override
            public void run(){
                networkClientCommunicationConnection.initializeAndConnect();
            }
        };

        if(executorService==null) executorService = Executors.newSingleThreadExecutor();
        executorService.submit(runnable);

    }

    @Override
    public NetworkClientConnection getConnection() {

        return networkClientCommunicationConnection;
    }

    @Override
    public NetworkClientConnection getConnection(String uriToNode) {

        if(networkClientConnectionsManager.getActiveConnectionsToExternalNodes().containsKey(uriToNode))
            return networkClientConnectionsManager.getActiveConnectionsToExternalNodes().get(uriToNode);
        else
            return null;

    }

    public NetworkClientCommunicationConnection getNetworkClientCommunicationConnection() {
        return networkClientCommunicationConnection;
    }

    /*
     * get the NodesProfile List  from ConnectionHistory Table
     */
    private List<NodeProfile> getNodesProfileFromConnectionHistory() throws CantGetDeviceLocationException {

        System.out.println("CALLING getNodesProfileFromConnectionHistory");

        Location location;

        try {

            location = (locationManager != null && locationManager.getLastKnownLocation()  != null)  ?  locationManager.getLastKnownLocation()  : null ;

            if (location == null)
                return null;

        } catch (Exception exception) {
            //exception.printStackTrace();
            return null;
        }

        NodeConnectionHistoryDao nodeConnectionHistoryDao = new NodeConnectionHistoryDao(dataBase);
        List<NodeProfile> nodeProfiles;

        try {
            nodeProfiles = nodeConnectionHistoryDao.findAllNodeProfilesNearestTo(10, 0, location);
        } catch (CantReadRecordDataBaseException e) {
           // e.printStackTrace();
            return null;
        }

        return nodeProfiles;
    }

    /*
     * get the NodesProfile List in the webService of the NetworkNode Harcoded
     */
    private List<NodeProfile> getNodesProfileList(){

        HttpURLConnection conn = null;
        List<NodeProfile> listServer = new ArrayList<>();

        System.out.println("CALLING getNodesProfileList");

        try {

            Double latitudeSource = ((locationManager.getLocation() != null && locationManager.getLocation().getLatitude() != null )? locationManager.getLocation().getLatitude() : 0.0);
            Double longitudeSource = ((locationManager.getLocation() != null && locationManager.getLocation().getLongitude() != null )? locationManager.getLocation().getLongitude() : 0.0);

            String formParameters = "latitude=" + latitudeSource + "&longitude=" + longitudeSource;


            URL url = new URL("http://" + HardcodeConstants.SERVER_IP_DEFAULT + ":" + HardcodeConstants.DEFAULT_PORT + "/fermat/rest/api/v1/available/nodes");
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", Integer.toString(formParameters.length()));
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Encoding", "gzip");

            OutputStream os = conn.getOutputStream();
            os.write(formParameters.getBytes());
            os.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String respond = reader.readLine();

            if (respond.contains("data")) {

               /*
                * Decode into a json Object
                */
                JsonParser parser = new JsonParser();
                JsonObject respondJsonObject = (JsonObject) parser.parse(respond.trim());

                listServer = GsonProvider.getGson().fromJson(respondJsonObject.get("data").getAsString(), new TypeToken<List<NodeProfile>>() {
                }.getType());

                System.out.println("NetworkClientCommunicationPluginRoot - resultList.size() = " + listServer.size());
                System.out.println(respondJsonObject);

            }else{
                System.out.println("NetworkClientCommunicationConnection - Requested list is not available, resultList.size() = " + listServer.size());
            }

        }catch (Exception e){
            //e.printStackTrace();
            return null;
        }finally {
            if (conn != null)
                conn.disconnect();
        }

        return listServer;

    }

    @Override
    public void connect() {

        try {

            networkClientCommunicationConnection.initializeAndConnect();



             /*
            * Create and Scheduled the supervisorConnectionAgent
            */
            final NetworkClientCommunicationSupervisorConnectionAgent supervisorConnectionAgent = new NetworkClientCommunicationSupervisorConnectionAgent(this);
            scheduledExecutorService.scheduleAtFixedRate(supervisorConnectionAgent, 10, 7, TimeUnit.SECONDS);

//            executorService = Executors.newSingleThreadExecutor();
//            executorService.submit(thread);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        try {
            scheduledExecutorService.shutdownNow();
        }catch (Exception e){

        }
        try {
            networkClientCommunicationConnection.close();
        }catch (Exception e){

        }


    }

    @Override
    public void stop() {
        if(executorService != null)
            try {
                executorService.shutdownNow();
                executorService = null;
            }catch (Exception ignore){

            }

        if(scheduledExecutorService != null){
            scheduledExecutorService.shutdownNow();
            scheduledExecutorService = null;
        }

        super.stop();
    }

    @Override
    public boolean isConnected() {
        return getConnection().isConnected();
    }
}
