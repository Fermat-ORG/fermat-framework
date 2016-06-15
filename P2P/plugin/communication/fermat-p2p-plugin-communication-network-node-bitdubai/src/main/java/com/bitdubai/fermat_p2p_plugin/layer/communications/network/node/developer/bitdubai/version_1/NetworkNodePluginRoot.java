package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.ip_address.IPAddressHelper;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
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
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.NetworkNodeManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NodeProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.FermatEmbeddedNodeServer;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.agents.PropagateActorCatalogAgent;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.agents.PropagateNodeCatalogAgent;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.clients.FermatWebSocketClientNodeChannel;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContextItem;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request.AddNodeToCatalogMsgRequest;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request.GetActorCatalogTransactionsMsjRequest;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request.GetNodeCatalogTransactionsMsjRequest;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request.UpdateNodeInCatalogMsgRequest;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseFactory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDeveloperDatabaseFactoryTemp;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.DaoFactory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.utils.DatabaseTransactionStatementPair;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalogTransaction;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantInitializeCommunicationsNetworkNodeP2PDatabaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantInitializeNetworkNodeIdentityException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.ConfigurationManager;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.HexadecimalConverter;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.SeedServerConf;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.UPNPService;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.NetworkNodePluginRoot</code> is
 * responsible of initialize all the component to work together
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 11/11/15.
 *
 * @author  Rart3001
 * @version 1.0
 * @since   Java JDK 1.7
 */
@PluginInfo(createdBy = "Roberto Requena", maintainerMail = "rart3001@gmail.com", platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION, plugin = Plugins.NETWORK_NODE)
public class NetworkNodePluginRoot extends AbstractPlugin implements NetworkNodeManager {

    /**
     * Represent the LOG
     */
    private static final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(NetworkNodePluginRoot.class));

    /**
     * Represent the IDENTITY_FILE_DIRECTORY
     */
    private static final String IDENTITY_FILE_DIRECTORY = "private";

    /**
     * Represent the IDENTITY_FILE_NAME
     */
    private static final String IDENTITY_FILE_NAME      = "nodeIdentity";

    /**
     * EventManager references definition.
     */
    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    /**
     * EventManager references definition.
     */
    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.DEVICE_LOCATION)
    private LocationManager locationManager;

    /**
     * PluginFileSystem references definition.
     */
    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem;

    /**
     * PluginDatabaseSystem references definition.
     */
    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Represent the daoFactory instance
     */
    private DaoFactory daoFactory;

    /**
     * Represent the node identity
     */
    private ECCKeyPair identity;

    /**
     * Represent the database of the node
     */
    private Database dataBase;

    /**
     * Represent the propagateNodeCatalogAgent
     */
    private PropagateNodeCatalogAgent propagateNodeCatalogAgent;

    /**
     * Represent the propagateActorCatalogAgent
     */
    private PropagateActorCatalogAgent propagateActorCatalogAgent;

    /**
     * Represent the communicationsNetworkNodeP2PDatabaseFactory of the node
     */
    private CommunicationsNetworkNodeP2PDatabaseFactory communicationsNetworkNodeP2PDatabaseFactory;

    /**
     * Represent the fermatEmbeddedNodeServer instance
     */
    private FermatEmbeddedNodeServer fermatEmbeddedNodeServer;

    /**
     * Represent the nodeProfile
     */
    private NodeProfile nodeProfile;

    /**
     * Represent the server ip
     */
    private String serverIp;

    /**
     * Constructor
     */
    public NetworkNodePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     * (non-javadoc)
     *
     * @see AbstractPlugin#start()
     */
    @Override
    public void start() throws CantStartPluginException {

        LOG.info("Calling method - start()...");
        LOG.info("pluginId = " + pluginId);

        /*
         * Validate required resources
         */
        validateInjectedResources();

        try {

            /*
             * Initialize the identity of the node
             */
            initializeIdentity();

             /*
             * Initialize the Data Base of the node
             */
            initializeDb();
            CommunicationsNetworkNodeP2PDeveloperDatabaseFactoryTemp developerDatabaseFactory = new CommunicationsNetworkNodeP2PDeveloperDatabaseFactoryTemp(pluginDatabaseSystem, pluginId);

            /*
             * Delete all checked in components
             * when node starts
             */
            daoFactory.getCheckedInActorDao().deleteAll();
            daoFactory.getCheckedInNetworkServiceDao().deleteAll();
            daoFactory.getCheckedInClientDao().deleteAll();
            daoFactory.getCheckedNetworkServicesHistoryDao().deleteAll();
            daoFactory.getCheckedActorsHistoryDao().deleteAll();

            /*
             * Get the server ip
             */
            serverIp = IPAddressHelper.getCurrentIPAddress();
            LOG.info("Server ip: " + serverIp);

            /*
             * Initialize the configuration file
             */
            initializeConfigurationFile();

            /*
             * Generate the profile of the node
             */
            generateNodeProfile();

            /*
             * Create and start the internal server
             */
            fermatEmbeddedNodeServer = new FermatEmbeddedNodeServer();
            fermatEmbeddedNodeServer.start();

            LOG.info("Add references to the node context...");

            /*
             * Add references to the node context
             */
            NodeContext.add(NodeContextItem.DAO_FACTORY, daoFactory);
            NodeContext.add(NodeContextItem.DEVELOPER_DATABASE_FACTORY, developerDatabaseFactory);
            NodeContext.add(NodeContextItem.EVENT_MANAGER, eventManager);
            NodeContext.add(NodeContextItem.FERMAT_EMBEDDED_NODE_SERVER, fermatEmbeddedNodeServer);
            NodeContext.add(NodeContextItem.PLUGIN_DATABASE_SYSTEM, pluginDatabaseSystem);
            NodeContext.add(NodeContextItem.PLUGIN_FILE_SYSTEM, pluginFileSystem);
            NodeContext.add(NodeContextItem.PLUGIN_ROOT, this);

            /*
             * Process the node catalog
             */
            initializeNodeCatalog();

            /*
             * Initialize propagate catalog agents
             */
            LOG.info("Initializing propagate catalog agents ...");
            this.propagateNodeCatalogAgent = new PropagateNodeCatalogAgent(this);
            this.propagateActorCatalogAgent =  new PropagateActorCatalogAgent(this);
            propagateNodeCatalogAgent.start();
            propagateActorCatalogAgent.start();

            /*
             * Try to forwarding port
             */
            UPNPService.portForwarding(Integer.parseInt(ConfigurationManager.getValue(ConfigurationManager.PORT)), ConfigurationManager.getValue(ConfigurationManager.NODE_NAME));

        } catch (CantInitializeCommunicationsNetworkNodeP2PDatabaseException exception) {


            exception.printStackTrace();

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Error trying to initialize the network node database.");
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("Plugin ID: ");
            contextBuffer.append(pluginId.toString());
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("Database Name: " + CommunicationsNetworkNodeP2PDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Network Node Service triggered an unexpected problem that wasn't able to solve by itself";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

            super.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;

        } catch (Exception exception) {


            exception.printStackTrace();

            String context = "Plugin ID: " + pluginId;
            String possibleCause = "The Network Node Service triggered an unexpected problem that wasn't able to solve by itself";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

            super.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);


            throw pluginStartException;
        }

    }

    @Override
    public void pause() {

        try {

            this.propagateActorCatalogAgent.pause();
            this.propagateNodeCatalogAgent.pause();

        } catch (Exception e) {

            super.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

        }
    }

    @Override
    public void resume() {

        try {

            this.propagateActorCatalogAgent.resume();
            this.propagateNodeCatalogAgent.resume();

        } catch (Exception e) {

            super.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
    }

    @Override
    public void stop() {

        try {

            this.propagateActorCatalogAgent.stop();
            this.propagateNodeCatalogAgent.stop();
            UPNPService.removePortForwarding(Integer.parseInt(ConfigurationManager.getValue(ConfigurationManager.PORT)));

        } catch (Exception e) {

            super.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

        }
    }

    /**
     * Generate the node profile of this node
     */
    private void generateNodeProfile() throws CantGetDeviceLocationException {

        LOG.info("Generating Node Profile...");

        nodeProfile = new NodeProfile();
        nodeProfile.setIdentityPublicKey(identity.getPublicKey());
        nodeProfile.setIp(serverIp);
        nodeProfile.setDefaultPort(Integer.valueOf(ConfigurationManager.getValue(ConfigurationManager.PORT)));
        nodeProfile.setName(ConfigurationManager.getValue(ConfigurationManager.NODE_NAME));
        nodeProfile.setLocation(locationManager.getLocation());

        LOG.info("Node Profile = "+nodeProfile);

    }

    /**
     * Initializes the configuration file
     */
    private void initializeConfigurationFile() throws ConfigurationException, IOException {

        LOG.info("Starting initializeConfigurationFile()...");

        if(ConfigurationManager.isExist()){

            ConfigurationManager.load();

        }else {

            LOG.info("Configuration file doesn't exit");
            ConfigurationManager.create(identity.getPublicKey());
            ConfigurationManager.load();
        }

    }

    /**
     * This method validates if all required resources are injected into
     * the plugin root by the fermat system.
     *
     * @throws CantStartPluginException if something goes wrong.
     */
    private void validateInjectedResources() throws CantStartPluginException {

         /*
         * If all resources are inject
         */
        if (pluginDatabaseSystem == null         ||
                    eventManager == null         ||
                        pluginFileSystem == null ) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginFileSystem: " + pluginFileSystem);

            String context = contextBuffer.toString();
            String possibleCause = "No all required resource are injected";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);

            super.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;

        }

    }

    /**
     * Initializes the identity of this plugin
     */
    private void initializeIdentity() throws CantInitializeNetworkNodeIdentityException {

        System.out.println("Calling method - initializeIdentity()...");

        try {

            System.out.println("Loading identity...");

         /*
          * Load the file with the identity
          */
            PluginTextFile pluginTextFile = pluginFileSystem.getTextFile(pluginId, IDENTITY_FILE_DIRECTORY, IDENTITY_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            String content = pluginTextFile.getContent();

            System.out.println("content = " + content);

            identity = new ECCKeyPair(content);

        } catch (FileNotFoundException e) {

            /*
             * The file does not exist, maybe is the first time that the plugin is running on this device,
             * We need to create a new identity for the network node.
             */
            try {

                System.out.println("No previous identity found - Proceeding to create new one...");

                /*
                 * Create the new identity
                 */
                identity = new ECCKeyPair();

                System.out.println("identity.getPrivateKey() = " + identity.getPrivateKey());
                System.out.println("identity.getPublicKey() = " + identity.getPublicKey());

                /*
                 * save the identity into the identity file
                 */
                PluginTextFile pluginTextFile = pluginFileSystem.createTextFile(pluginId, IDENTITY_FILE_DIRECTORY, IDENTITY_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                pluginTextFile.setContent(identity.getPrivateKey());
                pluginTextFile.persistToMedia();

            } catch (Exception exception) {
                /*
                 * The file cannot be created. I can not handle this situation.
                 */
                super.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);

                throw new CantInitializeNetworkNodeIdentityException(exception, "", "Unhandled Error.");
            }


        } catch (CantCreateFileException cantCreateFileException) {

            /*
             * The file cannot be load. I can not handle this situation.
             */
            super.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateFileException);

            throw new CantInitializeNetworkNodeIdentityException(cantCreateFileException, "", "Error trying to create the file.");

        }

    }

    /**
     * This method initializes the database.
     *
     * @throws CantInitializeCommunicationsNetworkNodeP2PDatabaseException
     */
    private void initializeDb() throws CantInitializeCommunicationsNetworkNodeP2PDatabaseException {

        System.out.println("Calling method - initializeDb()...");

        try {

            System.out.println("Loading database...");
            /*
             * Open new database connection
             */
            this.dataBase = this.pluginDatabaseSystem.openDatabase(pluginId, CommunicationsNetworkNodeP2PDatabaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            super.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);

            throw new CantInitializeCommunicationsNetworkNodeP2PDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            try {

                System.out.println("No previous data base found - Proceeding to create new one...");

                /*
                 * We create the new database
                 */
                this.communicationsNetworkNodeP2PDatabaseFactory = new CommunicationsNetworkNodeP2PDatabaseFactory(pluginDatabaseSystem);
                this.dataBase = communicationsNetworkNodeP2PDatabaseFactory.createDatabase(pluginId, CommunicationsNetworkNodeP2PDatabaseConstants.DATA_BASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. We can not handle this situation.
                 */
                super.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeCommunicationsNetworkNodeP2PDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }

        //Validate if database is correctly instantiated
        if (dataBase != null) {

            /*
             * Instantiate daoFactory
             */
            this.daoFactory = new DaoFactory(dataBase);

        }

    }

    /**
     * Create a new instance of the client to the seed node
     * @return
     */
    private FermatWebSocketClientNodeChannel getFermatWebSocketClientNodeChannelInstanceSeedNode(){

        return new FermatWebSocketClientNodeChannel(SeedServerConf.DEFAULT_IP, SeedServerConf.DEFAULT_PORT);
    }

    /**
     * Validate if the current node belongs to the list of seed servers
     *
     * @return boolean
     */
    private boolean isSeedServer(String serverIp){

        if (serverIp.equals(SeedServerConf.DEFAULT_IP)){
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    /**
     * Method that validate if the node profile registered had changed
     * from the registration
     */
    private boolean validateNodeProfileRegisterChange() {

        String jsonString = new String(HexadecimalConverter.convertHexStringToByteArray(ConfigurationManager.getValue(ConfigurationManager.LAST_REGISTER_NODE_PROFILE)));

        LOG.info("Last Profile Registered = " + jsonString);

        NodeProfile lastNodeProfileRegister = NodeProfile.fromJson(jsonString);
        if (!nodeProfile.equals(lastNodeProfileRegister)){
            return Boolean.TRUE;
        }

        return Boolean.FALSE;

    }

    /**
     * Method that requests to the seed server the registration of
     * the current node profile.
     */
    private void requestRegisterProfileInTheNodeCatalog(){

        LOG.info("Requesting registration of the node profile in the node catalog...");

        FermatWebSocketClientNodeChannel fermatWebSocketClientNodeChannel = getFermatWebSocketClientNodeChannelInstanceSeedNode();
        AddNodeToCatalogMsgRequest addNodeToCatalogMsgRequest = new AddNodeToCatalogMsgRequest(nodeProfile);
        fermatWebSocketClientNodeChannel.sendMessage(addNodeToCatalogMsgRequest.toJson(), PackageType.ADD_NODE_TO_CATALOG_REQUEST);

    }

    /**
     * Method that requests to the seed server to update
     * the profile of this node
     */
    private void requestUpdateProfileInTheNodeCatalog(){

        LOG.info("Requesting update of the profile on the node catalog...");

        FermatWebSocketClientNodeChannel fermatWebSocketClientNodeChannel = getFermatWebSocketClientNodeChannelInstanceSeedNode();
        UpdateNodeInCatalogMsgRequest updateNodeInCatalogMsgRequest = new UpdateNodeInCatalogMsgRequest(nodeProfile);
        fermatWebSocketClientNodeChannel.sendMessage(updateNodeInCatalogMsgRequest.toJson(), PackageType.UPDATE_NODE_IN_CATALOG_REQUEST);

    }

    /**
     * Validate if the catalog is empty
     * if it is, we'll request the data to the seed server
     *
     * @throws CantReadRecordDataBaseException if something goes wrong.
     */
    private void requestNodesCatalogTransactions() throws CantReadRecordDataBaseException {

        if (daoFactory.getNodesCatalogDao().getAllCount() <= 0){
            LOG.info("Request the list of transactions in the node catalog");

            FermatWebSocketClientNodeChannel fermatWebSocketClientNodeChannel = getFermatWebSocketClientNodeChannelInstanceSeedNode();
            GetNodeCatalogTransactionsMsjRequest getNodeCatalogTransactionsMsjRequest = new GetNodeCatalogTransactionsMsjRequest(0, 250);
            fermatWebSocketClientNodeChannel.sendMessage(getNodeCatalogTransactionsMsjRequest.toJson(), PackageType.GET_NODE_CATALOG_TRANSACTIONS_REQUEST);
        }

    }

    /**
     * Validate if the catalog if empty request the data to the seed server
     *
     * @throws CantReadRecordDataBaseException
     */
    private void requestActorsCatalogTransactions() throws CantReadRecordDataBaseException {

        if (daoFactory.getActorsCatalogDao().getAllCount() <= 0) {
            LOG.info("Request the list of transactions in the actors catalog");

            FermatWebSocketClientNodeChannel fermatWebSocketClientNodeChannel = getFermatWebSocketClientNodeChannelInstanceSeedNode();
            GetActorCatalogTransactionsMsjRequest getActorCatalogTransactionsMsjRequest = new GetActorCatalogTransactionsMsjRequest(0, 250);
            fermatWebSocketClientNodeChannel.sendMessage(getActorCatalogTransactionsMsjRequest.toJson(), PackageType.GET_ACTOR_CATALOG_TRANSACTIONS_REQUEST);
        }

    }


    /**
     * Process the node into the node catalog
     */
    private void initializeNodeCatalog() throws Exception {

        LOG.info("Initialize node catalog");
        boolean isSeedServer = isSeedServer(this.serverIp);
        Boolean isRegister = isRegisterInNodeCatalog();

        LOG.info("Is Register? = " + isRegister);
        LOG.info("Am i a Seed Node? = " + isSeedServer);

        /*
         * Validate if the node are the seed server
         */
        if (isSeedServer){

            /*
             * Validate if the node is registered in the node catalog
             */
            if (isRegister){

                /*
                 * Validate if the node server profile register had changed
                 */
                if (validateNodeProfileRegisterChange()){
                    updateNodeProfileOnCatalog();
                }

            } else {
                insertNodeProfileIntoCatalog();
            }

        } else {

            /*
             * Validate if the node is registered in the node catalog
             */
            if (isRegister){

                    /*
                     * Validate if the node server profile register had changed
                     */
                if (validateNodeProfileRegisterChange()){
                    requestUpdateProfileInTheNodeCatalog();
                }

            }else {
                requestRegisterProfileInTheNodeCatalog();
            }

            requestNodesCatalogTransactions();
            requestActorsCatalogTransactions();

        }

    }

    /**
     * Insert the node profile into the catalog
     * @throws CantInsertRecordDataBaseException
     */
    private void insertNodeProfileIntoCatalog() throws Exception {

        LOG.info("Inserting my profile in the node catalog...");

        if (!daoFactory.getNodesCatalogDao().exists(nodeProfile.getIdentityPublicKey())){

            // create transaction for
            DatabaseTransaction databaseTransaction = daoFactory.getNodesCatalogDao().getNewTransaction();
            DatabaseTransactionStatementPair pair;

            /*
             * Create the NodesCatalog entity
             */
            NodesCatalog nodeCatalog = new NodesCatalog();
            nodeCatalog.setIp(nodeProfile.getIp());
            nodeCatalog.setDefaultPort(nodeProfile.getDefaultPort());
            nodeCatalog.setIdentityPublicKey(nodeProfile.getIdentityPublicKey());
            nodeCatalog.setName(nodeProfile.getName());
            nodeCatalog.setOfflineCounter(0);
            nodeCatalog.setLastConnectionTimestamp(new Timestamp(System.currentTimeMillis()));
            nodeCatalog.setLastLocation(nodeProfile.getLocation().getLatitude(), nodeProfile.getLocation().getLongitude());

            /*
             * Insert NodesCatalog into data base
             */
            pair = daoFactory.getNodesCatalogDao().createInsertTransactionStatementPair(nodeCatalog);
            databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());

            // create the node catalog transaction
            NodesCatalogTransaction transaction = new NodesCatalogTransaction();
            transaction.setIp(nodeProfile.getIp());
            transaction.setDefaultPort(nodeProfile.getDefaultPort());
            transaction.setIdentityPublicKey(nodeProfile.getIdentityPublicKey());
            transaction.setName(nodeProfile.getName());
            transaction.setTransactionType(NodesCatalogTransaction.ADD_TRANSACTION_TYPE);
            transaction.setHashId(transaction.getHashId());
            transaction.setLastConnectionTimestamp(new Timestamp(System.currentTimeMillis()));
            transaction.setLastLocation(nodeProfile.getLocation().getLatitude(), nodeProfile.getLocation().getLongitude());

            /*
             * Insert NodesCatalogTransaction into data base
             */
            pair = daoFactory.getNodesCatalogTransactionDao().createInsertTransactionStatementPair(transaction);
            databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());

            /*
             * Insert NodesCatalogTransactionsPendingForPropagation into data base
             */
            pair = daoFactory.getNodesCatalogTransactionsPendingForPropagationDao().createInsertTransactionStatementPair(transaction);
            databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());

            databaseTransaction.execute();

            ConfigurationManager.updateValue(ConfigurationManager.REGISTERED_IN_CATALOG, String.valueOf(Boolean.TRUE));
            ConfigurationManager.updateValue(ConfigurationManager.LAST_REGISTER_NODE_PROFILE, HexadecimalConverter.convertHexString(nodeProfile.toJson().getBytes("UTF-8")));

        } else {

            ConfigurationManager.updateValue(ConfigurationManager.REGISTERED_IN_CATALOG, String.valueOf(Boolean.TRUE));
            ConfigurationManager.updateValue(ConfigurationManager.LAST_REGISTER_NODE_PROFILE, HexadecimalConverter.convertHexString(nodeProfile.toJson().getBytes("UTF-8")));
        }

    }

    /**
     * Update the node profile into the catalog
     * @throws CantInsertRecordDataBaseException
     */
    private void updateNodeProfileOnCatalog() throws Exception {

        LOG.info("Updating my profile in the node catalog");

        if (daoFactory.getNodesCatalogDao().exists(nodeProfile.getIdentityPublicKey())) {

            // create transaction for
            DatabaseTransaction databaseTransaction = daoFactory.getNodesCatalogDao().getNewTransaction();
            DatabaseTransactionStatementPair pair;

            /*
             * Create the NodesCatalog entity
             */
            NodesCatalog nodeCatalog = new NodesCatalog();
            nodeCatalog.setIp(nodeProfile.getIp());
            nodeCatalog.setDefaultPort(nodeProfile.getDefaultPort());
            nodeCatalog.setIdentityPublicKey(nodeProfile.getIdentityPublicKey());
            nodeCatalog.setName(nodeProfile.getName());
            nodeCatalog.setOfflineCounter(0);
            nodeCatalog.setLastConnectionTimestamp(new Timestamp(System.currentTimeMillis()));
            nodeCatalog.setLastLocation(nodeProfile.getLocation().getLatitude(), nodeProfile.getLocation().getLongitude());

            /*
             * Insert NodesCatalog into data base
             */
            pair = daoFactory.getNodesCatalogDao().createUpdateTransactionStatementPair(nodeCatalog);
            databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());

            // create the node catalog transaction
            NodesCatalogTransaction transaction = new NodesCatalogTransaction();
            transaction.setIp(nodeProfile.getIp());
            transaction.setDefaultPort(nodeProfile.getDefaultPort());
            transaction.setIdentityPublicKey(nodeProfile.getIdentityPublicKey());
            transaction.setName(nodeProfile.getName());
            transaction.setTransactionType(NodesCatalogTransaction.UPDATE_TRANSACTION_TYPE);
            transaction.setHashId(transaction.getHashId());
            transaction.setLastConnectionTimestamp(new Timestamp(System.currentTimeMillis()));
            transaction.setLastLocation(nodeProfile.getLocation().getLatitude(), nodeProfile.getLocation().getLongitude());

            /*
             * Insert NodesCatalogTransaction into data base
             */
            pair = daoFactory.getNodesCatalogTransactionDao().createInsertTransactionStatementPair(transaction);
            databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());

            /*
             * Insert NodesCatalogTransactionsPendingForPropagation into data base
             */
            pair = daoFactory.getNodesCatalogTransactionsPendingForPropagationDao().createInsertTransactionStatementPair(transaction);
            databaseTransaction.addRecordToInsert(pair.getTable(), pair.getRecord());

            databaseTransaction.execute();

            ConfigurationManager.updateValue(ConfigurationManager.REGISTERED_IN_CATALOG, String.valueOf(Boolean.TRUE));
            ConfigurationManager.updateValue(ConfigurationManager.LAST_REGISTER_NODE_PROFILE, HexadecimalConverter.convertHexString(nodeProfile.toJson().getBytes("UTF-8")));

        } else {

            insertNodeProfileIntoCatalog();

        }

    }

    /**
     * Validate is register in the catalog
     * @return boolean
     */
    private boolean isRegisterInNodeCatalog(){

        HttpURLConnection httpURLConnection = null;

        try {

            /*
             * Get from configuration file
             */
            Boolean isRegister = Boolean.valueOf(ConfigurationManager.getValue(ConfigurationManager.REGISTERED_IN_CATALOG));

            /*
             * If the configuration file says that is registered, validate against seed node
             */
            if (isRegister){

                URL url = new URL("http://" + SeedServerConf.DEFAULT_IP + ":" + SeedServerConf.DEFAULT_PORT + "/fermat/rest/api/v1/nodes/registered/"+getIdentity().getPublicKey());
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setRequestProperty("Accept", "application/json");

                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String respond = reader.readLine();

                if (httpURLConnection.getResponseCode() == 200 && respond != null && respond.contains("success")) {

                   /*
                    * Decode into a json Object
                    */
                    JsonParser parser = new JsonParser();
                    JsonObject respondJsonObject = (JsonObject) parser.parse(respond.trim());

                    LOG.info(respondJsonObject);

                    if (respondJsonObject.get("success").getAsBoolean()){
                        return respondJsonObject.get("isRegistered").getAsBoolean();
                    }else {
                        return Boolean.FALSE;
                    }

                }else{
                    return Boolean.FALSE;
                }


            } else {
              return isRegister;
            }

        }catch (Exception e){
            return Boolean.FALSE;
        }finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
        }

    }

    /**
     * Get the identity
     *
     * @return ECCKeyPair
     */
    public ECCKeyPair getIdentity() {
        return identity;
    }


    /**
     * Get the Propagate Actor Catalog Agent
     * @return PropagateActorCatalogAgent
     */
    public PropagateActorCatalogAgent getPropagateActorCatalogAgent() {
        return propagateActorCatalogAgent;
    }

    /**
     * Get Propagate Node Catalog Agent
     * @return PropagateNodeCatalogAgent
     */
    public PropagateNodeCatalogAgent getPropagateNodeCatalogAgent() {
        return propagateNodeCatalogAgent;
    }
}
