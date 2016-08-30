package org.iop.client.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkChannel;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkClientConnection;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkClientManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.P2PLayerManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;

import org.iop.client.version_1.context.ClientContext;
import org.iop.client.version_1.context.ClientContextItem;
import org.iop.client.version_1.exceptions.CantInitializeNetworkClientP2PDatabaseException;
import org.iop.client.version_1.exceptions.CantSendPackageException;
import org.iop.client.version_1.structure.NetworkClientCommunicationConnection;
import org.iop.client.version_1.structure.NetworkClientCommunicationSupervisorConnectionAgent;
import org.iop.client.version_1.util.HardcodeConstants;

import java.util.List;
import java.util.UUID;
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
public class IoPClientPluginRoot extends AbstractPlugin implements NetworkClientManager,NetworkChannel {


    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem        ;

//    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
//    private PluginDatabaseSystem pluginDatabaseSystem;

//    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.DEVICE_LOCATION)
//    private LocationManager locationManager;

//    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.DEVICE_CONNECTIVITY)
//    private ConnectivityManager connectivityManager;

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
//    private Database dataBase;

    /**
     * Represent the NetworkClientP2PDatabaseFactory of the client
     */
//    private NetworkClientP2PDatabaseFactory networkClientP2PDatabaseFactory;

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
     * Canal de conexión con el home node
     */
    private NetworkClientCommunicationConnection networkClientCommunicationConnection;

    /*
     * Represent The executorService
     */
    private ExecutorService executorService;

    /**
     * Represent the list of nodes
     */
//    private List<NodeProfile> nodesProfileList;


    /**
     * Represent the executor
     */
    private ScheduledExecutorService scheduledExecutorService;


    @Override
    public FermatManager getManager() {
        return this;
    }

    /**
     * Constructor
     */
    public IoPClientPluginRoot() {
        super(new PluginVersionReference(new Version()));
        this.listenersAdded        = new CopyOnWriteArrayList<>();
    }

    @Override
    public void start() throws CantStartPluginException {

        System.out.println("Calling the method - start() in NetworkClientCommunicationPluginRoot");


        try{

            /*
             * Initialize the identity of the node
             */
            initializeIdentity();

             /*
             * Initialize the Data Base of the node
             */
//            initializeDb();


            /*
             * Add references to the node context
             */
            ClientContext.add(ClientContextItem.CLIENT_IDENTITY, identity    );
            ClientContext.add(ClientContextItem.EVENT_MANAGER  , eventManager);

            /*
             * get NodesProfile List From NodesProfileConnectionHistory table
             */

            networkClientCommunicationConnection = new NetworkClientCommunicationConnection(
                    SERVER_IP + ":" + HardcodeConstants.DEFAULT_PORT,
                    eventManager,//eventManager,
                    null,//locationManager,
                    identity,
                    this,
                    -1,
                    Boolean.FALSE,
                    null,
                    null,//connectivityManager,
                    p2PLayerManager//p2PLayerManager
            );


            p2PLayerManager.register(this);

//            FermatEventListener networkClientConnected = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_CONNNECTED_TO_NODE);
//            networkClientConnected.setEventHandler(new NetworkClientConnectedToNodeEventHandler(this));
//            eventManager.addListener(networkClientConnected);
//            listenersAdded.add(networkClientConnected);


        } catch (Exception exception){

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
//            contextBuffer.append("Database Name: " + NetworkClientP2PDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The  Network Client Service triggered an unexpected problem that wasn't able to solve by itself";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

            super.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;

        }


    }

    private static final String IDENTITY_FILE_DIRECTORY = "private";
    private static final String IDENTITY_FILE_NAME      = "clientIdentity";

    /**
     * Initialize the identity of this plugin
     */
    private void initializeIdentity() throws CantInitializeNetworkClientP2PDatabaseException, CantPersistFileException, CantCreateFileException {

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
                throw exception;
            }


        } catch (CantCreateFileException cantCreateFileException) {

            /*
             * The file cannot be load. I can not handle this situation.
             */
            throw new CantInitializeNetworkClientP2PDatabaseException(cantCreateFileException.getLocalizedMessage());

        }

    }

    @Override
    public NetworkClientConnection getConnection() {

        return networkClientCommunicationConnection;
    }

    /**
     *  Metodo para obtener conexión de un nodo en especifico
     *
     * @param uriToNode
     * @return
     */
    @Override
    public NetworkClientConnection getConnection(String uriToNode) {

//        if(networkClientConnectionsManager.getActiveConnectionsToExternalNodes().containsKey(uriToNode))
//            return networkClientConnectionsManager.getActiveConnectionsToExternalNodes().get(uriToNode);
//        else
//            return null;

        return null;
    }

    /**
     * Metodo para obtener conexión del home node
     * @return
     */
    public NetworkClientCommunicationConnection getNetworkClientCommunicationConnection() {
        return networkClientCommunicationConnection;
    }



    @Override
    public synchronized void connect() {

        try {
            if (!networkClientCommunicationConnection.isConnected()) {
                networkClientCommunicationConnection.initializeAndConnect();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        try {
            stopConnectionSuperVisorAgent();
        }catch (Exception e){

        }
        try {
            networkClientCommunicationConnection.close();
        }catch (Exception e){

        }


    }

    public void stopConnectionSuperVisorAgent(){
        if(scheduledExecutorService != null){
            scheduledExecutorService.shutdownNow();
            scheduledExecutorService = null;
        }

    }

    /**
     * Ping pong agent
     */
    public void startConnectionSuperVisorAgent(){
        final NetworkClientCommunicationSupervisorConnectionAgent supervisorConnectionAgent = new NetworkClientCommunicationSupervisorConnectionAgent(this);
        if (scheduledExecutorService == null){
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            scheduledExecutorService.scheduleAtFixedRate(supervisorConnectionAgent, 10, 7, TimeUnit.SECONDS);
        }
    }
    @Override
    public void stop() {
        if(executorService != null)
            try {
                executorService.shutdownNow();
                executorService = null;
            }catch (Exception ignore){
                //nothing
            }

        stopConnectionSuperVisorAgent();

        super.stop();
    }

    @Override
    public boolean isConnected() {
        return getConnection().isConnected();
    }


    @Override
    public UUID sendMessage(PackageContent packageContent, PackageType packageType, NetworkServiceType networkServiceType, String destinationPublicKey) throws CantSendMessageException {
        try {
            return networkClientCommunicationConnection.sendPackageMessage(packageContent,packageType,networkServiceType,destinationPublicKey);
        } catch (CantSendPackageException e) {
            throw new CantSendMessageException(e);
        }
    }

    @Override
    public UUID sendMessage(PackageContent packageContent, PackageType packageType,NetworkServiceType networkServiceType) throws CantSendMessageException {
        try {
            return networkClientCommunicationConnection.sendPackageMessage(packageContent,packageType,networkServiceType);
        } catch (CantSendPackageException e) {
            throw new CantSendMessageException(e);
        }
    }

    @Override
    public UUID sendMessage(PackageContent packageContent, PackageType packageType) throws CantSendMessageException {
        try {
            return networkClientCommunicationConnection.sendPackageMessage(packageContent,packageType);
        } catch (CantSendPackageException e) {
            throw new CantSendMessageException(e);
        }
    }

    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public void setP2PLayerManager(P2PLayerManager p2PLayerManager) {
        this.p2PLayerManager = p2PLayerManager;
    }
}
