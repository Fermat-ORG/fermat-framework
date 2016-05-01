package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
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
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NodeProfile;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.context.ClientContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.context.ClientContextItem;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.database.NetworkClientP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.database.NetworkClientP2PDatabaseFactory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.exceptions.CantInitializeNetworkClientP2PDatabaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.NetworkClientCommunicationConnection;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.util.HardcodeConstants;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.NetworkClientCommunicationPluginRoot</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 12/11/15.
 * Updated by Leon Acosta - (laion.cj91@gmail.com) on 07/04/2016.
 *
 * @author xxxxxxxxxx
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NetworkClientCommunicationPluginRoot extends AbstractPlugin {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem        ;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.DEVICE_LOCATION)
    private LocationManager locationManager;

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


    @Override
    public FermatManager getManager() {
        return null;
    }

    public NetworkClientCommunicationPluginRoot() {
        super(new PluginVersionReference(new Version()));
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
             * Add references to the node context
             */
            ClientContext.add(ClientContextItem.CLIENT_IDENTITY, identity    );
            ClientContext.add(ClientContextItem.ERROR_MANAGER  , errorManager);
            ClientContext.add(ClientContextItem.EVENT_MANAGER, eventManager);

            //nodesProfileList = getNodesProfileList();

            if(nodesProfileList != null && nodesProfileList.size() > 0){

                URI uri = new URI(HardcodeConstants.WS_PROTOCOL + nodesProfileList.get(0).getIp() + ":" + nodesProfileList.get(0).getDefaultPort() + "/fermat/ws/client-channel");

                networkClientCommunicationConnection = new NetworkClientCommunicationConnection(
                        uri,
                        errorManager,
                        eventManager,
                        locationManager,
                        identity,
                        getPluginVersionReference(),
                        this,
                        0
                );

            }else {

                URI uri = new URI(HardcodeConstants.WS_PROTOCOL + NetworkClientCommunicationPluginRoot.SERVER_IP + ":" + 8080 + "/fermat/ws/client-channel");

                networkClientCommunicationConnection = new NetworkClientCommunicationConnection(
                        uri,
                        errorManager,
                        eventManager,
                        locationManager,
                        identity,
                        getPluginVersionReference(),
                        this,
                        -1
                );

            }

            Thread thread = new Thread(){
                @Override
                public void run(){
                    networkClientCommunicationConnection.initializeAndConnect();
                }
            };

            executorService = Executors.newSingleThreadExecutor();
            executorService.submit(thread);


        } catch (Exception exception){

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("Database Name: " + NetworkClientP2PDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The  Network Client Service triggered an unexpected problem that wasn't able to solve by itself";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

            errorManager.reportUnexpectedPluginException(getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;

        }


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
                errorManager      == null ||
                eventManager  == null) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "No all required resource are injected";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);

            errorManager.reportUnexpectedPluginException(getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
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
            errorManager.reportUnexpectedPluginException(getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
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
                errorManager.reportUnexpectedPluginException(getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeNetworkClientP2PDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }

    }

    /*
     * Receive the Actual index of the Nodes list
     */
    public void intentToConnectToOtherNode(Integer i){

        if(executorService != null)
            executorService.shutdownNow();

        /*
         * if is the last index then connect to networkNode Harcoded
         * else intent connect to other networkNode
         */
        if(nodesProfileList != null  && nodesProfileList.size() > 0 && i < (nodesProfileList.size() - 1)){

            URI uri = null;
            try {
                uri = new URI(HardcodeConstants.WS_PROTOCOL + nodesProfileList.get(i+1).getIp() + ":" + nodesProfileList.get(i+1).getDefaultPort() + "/fermat/ws/client-channel");
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            networkClientCommunicationConnection = new NetworkClientCommunicationConnection(
                    uri,
                    errorManager,
                    eventManager,
                    locationManager,
                    identity,
                    getPluginVersionReference(),
                    this,
                    i+1
            );

        }else{


            URI uri = null;
            try {
                uri = new URI(HardcodeConstants.WS_PROTOCOL + NetworkClientCommunicationPluginRoot.SERVER_IP + ":" + 8080 + "/fermat/ws/client-channel");
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            networkClientCommunicationConnection = new NetworkClientCommunicationConnection(
                    uri,
                    errorManager,
                    eventManager,
                    locationManager,
                    identity,
                    getPluginVersionReference(),
                    this,
                    -1
            );

        }

        Thread thread = new Thread(){
            @Override
            public void run(){
                networkClientCommunicationConnection.initializeAndConnect();
            }
        };

        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(thread);

    }

    /*
     * get the NodesProfile List in the webService of the NetworkNode Harcoded
     */
    private List<NodeProfile> getNodesProfileList(){

        HttpURLConnection conn = null;

        try {

            URL url = new URL("http://" + HardcodeConstants.SERVER_IP_DEFAULT + ":" + 8080 + "/fermat/rest/api/v1/availablenodes/listavailablenodesprofile");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String respond = reader.readLine();

            if (conn.getResponseCode() == 200 && respond != null && respond.contains("data")) {

               /*
                * Decode into a json Object
                */
                JsonParser parser = new JsonParser();
                JsonObject respondJsonobject = (JsonObject) parser.parse(respond.trim());

                Gson gson = new Gson();
                List<NodeProfile> listServer = gson.fromJson(respondJsonobject.get("data").getAsString(), new TypeToken<List<NodeProfile>>() {
                }.getType());

                System.out.println(respondJsonobject);

                return listServer;

            }else{
                return null;
            }

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            if (conn != null)
                conn.disconnect();
        }

    }

}
