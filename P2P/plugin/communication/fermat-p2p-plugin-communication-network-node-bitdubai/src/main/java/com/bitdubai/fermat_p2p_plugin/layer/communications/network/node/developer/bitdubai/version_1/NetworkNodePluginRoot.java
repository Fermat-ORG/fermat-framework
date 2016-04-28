package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.ip_address.IPAddressHelper;
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
import com.bitdubai.fermat_osa_addon.layer.linux.device_location.developer.bitdubai.version_1.exceptions.CantAcquireLocationException;
import com.bitdubai.fermat_osa_addon.layer.linux.device_location.developer.bitdubai.version_1.utils.LocationProvider;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.RecordNotFoundException;
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
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request.UpdateNodeInCatalogMsgRequest;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseFactory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDeveloperDatabaseFactoryTemp;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.DaoFactory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalogTransaction;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalogTransactionsPendingForPropagation;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantInitializeCommunicationsNetworkNodeP2PDatabaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.ConfigurationManager;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.HexadecimalConverter;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.SeedServerConf;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.NetworkNodePluginRoot</code> is
 * responsible of initialize all the component to work together
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 11/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
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
     * ErrorManager references definition.
     */
    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    /**
     * EventManager references definition.
     */
    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

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
     * Represent the fermatWebSocketClientNodeChannel instance
     */
    private FermatWebSocketClientNodeChannel fermatWebSocketClientNodeChannel;

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

        LOG.info("Calling the method - start() ");
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
             * Get the server ip
             */
            serverIp = IPAddressHelper.getCurrentIPAddress();
            LOG.info("Server ip " + serverIp);

            /*
             * Initialize the configuration file
             */
            initializeConfigurationFile();

            /*
             *  Generate the profile of the node
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
            NodeContext.add(NodeContextItem.ERROR_MANAGER              , errorManager            );
            NodeContext.add(NodeContextItem.EVENT_MANAGER              , eventManager            );
            NodeContext.add(NodeContextItem.FERMAT_EMBEDDED_NODE_SERVER, fermatEmbeddedNodeServer);
            NodeContext.add(NodeContextItem.PLUGIN_DATABASE_SYSTEM     , pluginDatabaseSystem    );
            NodeContext.add(NodeContextItem.PLUGIN_FILE_SYSTEM, pluginFileSystem);
            NodeContext.add(NodeContextItem.PLUGIN_ROOT, this);

            /*
             * Process the node catalog
             */
            initializeNodeCatalog();


            LOG.info("Initialize propagate catalog agents ...");
            /*
             * Initialize propagate catalog agents
             */
            this.propagateNodeCatalogAgent = new PropagateNodeCatalogAgent(this);
            this.propagateActorCatalogAgent =  new PropagateActorCatalogAgent(this);
            propagateNodeCatalogAgent.start();
            propagateActorCatalogAgent.start();

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
            String possibleCause = "The  Network Node Service triggered an unexpected problem that wasn't able to solve by itself";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.NETWORK_NODE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        } catch (Exception exception) {


            exception.printStackTrace();

            String context = "Plugin ID: " + pluginId;
            String possibleCause = "The  Network Node Service triggered an unexpected problem that wasn't able to solve by itself";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.NETWORK_NODE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        }

    }

    /**
     * Generate the node profile of this
     * node
     */
    private void generateNodeProfile() throws CantAcquireLocationException {

        LOG.info("Generating Node Profile");

        nodeProfile = new NodeProfile();
        nodeProfile.setIdentityPublicKey(identity.getPublicKey());

        //TODO: CHANGE
        nodeProfile.setIp(serverIp);
        //nodeProfile.setIp("localhost");

        nodeProfile.setDefaultPort(Integer.valueOf(ConfigurationManager.getValue(ConfigurationManager.PORT)));
        //nodeProfile.setDefaultPort(8080);

        nodeProfile.setName(ConfigurationManager.getValue(ConfigurationManager.NODE_NAME));
        //nodeProfile.setName("Otro Server");

        nodeProfile.setLocation(LocationProvider.acquireLocationThroughIP());

        LOG.info(nodeProfile);

    }

    /**
     * Initialize the configuration file
     */
    private void initializeConfigurationFile() throws ConfigurationException, IOException {

        LOG.info("Starting initializeConfigurationFile()");

        if(ConfigurationManager.isExist()){

            ConfigurationManager.load();

        }else {

            LOG.info("Configuration file don't exit");
            ConfigurationManager.create(identity.getPublicKey());
            ConfigurationManager.load();
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
        if (pluginDatabaseSystem == null         ||
                errorManager == null             ||
                    eventManager == null         ||
                        pluginFileSystem == null ) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginFileSystem: " + pluginFileSystem);

            String context = contextBuffer.toString();
            String possibleCause = "No all required resource are injected";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.NETWORK_NODE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;

        }

    }

    /**
     * Initialize the identity of this plugin
     */
    private void initializeIdentity() throws CantInitializeCommunicationsNetworkNodeP2PDatabaseException {

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
                errorManager.reportUnexpectedPluginException(Plugins.NETWORK_NODE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                throw new CantInitializeCommunicationsNetworkNodeP2PDatabaseException(exception.getLocalizedMessage());
            }


        } catch (CantCreateFileException cantCreateFileException) {

            /*
             * The file cannot be load. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.NETWORK_NODE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantCreateFileException);
            throw new CantInitializeCommunicationsNetworkNodeP2PDatabaseException(cantCreateFileException.getLocalizedMessage());

        }

    }

    /**
     * This method initialize the database
     *
     * @throws CantInitializeCommunicationsNetworkNodeP2PDatabaseException
     */
    private void initializeDb() throws CantInitializeCommunicationsNetworkNodeP2PDatabaseException {

        System.out.println("Calling the method - initializeDb() ");

        try {

            System.out.println("Loading database");
            /*
             * Open new database connection
             */
            this.dataBase = this.pluginDatabaseSystem.openDatabase(pluginId, CommunicationsNetworkNodeP2PDatabaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.NETWORK_NODE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeCommunicationsNetworkNodeP2PDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

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
                this.communicationsNetworkNodeP2PDatabaseFactory = new CommunicationsNetworkNodeP2PDatabaseFactory(pluginDatabaseSystem);
                this.dataBase = communicationsNetworkNodeP2PDatabaseFactory.createDatabase(pluginId, CommunicationsNetworkNodeP2PDatabaseConstants.DATA_BASE_NAME);


            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.NETWORK_NODE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeCommunicationsNetworkNodeP2PDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }

        //Validate if database is ok
        if (dataBase != null) {

            /*
             * Create the daoFactory
             */
            this.daoFactory = new DaoFactory(dataBase);

        }

    }

    /**
     * Validate if the node is the seed server
     *
     * @return boolean
     */
    private boolean iAmSeedServer(){

        if (serverIp.equals(SeedServerConf.DEFAULT_IP)){
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    /**
     * Method that validate if the node profile register change
     * from the registration
     */
    private boolean validateNodeProfileRegisterChange(){

        String jsonString = new String(HexadecimalConverter.convertHexStringToByteArray(ConfigurationManager.getValue(ConfigurationManager.LAST_REGISTER_NODE_PROFILE)));

        LOG.info("Last Profile Register = "+jsonString);

        NodeProfile lastNodeProfileRegister = NodeProfile.fromJson(jsonString);
        if (!nodeProfile.equals(lastNodeProfileRegister)){
            return Boolean.TRUE;
        }

        return Boolean.FALSE;

    }

    /**
     * Method that request to the seed server to register
     * the profile of this node
     */
    private void requestRegisterProfileInTheNodeCatalog(){

        LOG.info("Request register profile in the node catalog");
        AddNodeToCatalogMsgRequest addNodeToCatalogMsgRequest = new AddNodeToCatalogMsgRequest(nodeProfile);
        fermatWebSocketClientNodeChannel.sendMessage(addNodeToCatalogMsgRequest.toJson(), PackageType.ADD_NODE_TO_CATALOG_REQUEST);

    }

    /**
     * Method that request to the seed server to update
     * the profile of this node
     */
    private void requestUpdateProfileInTheNodeCatalog(){

        LOG.info("Request update profile in the node catalog");
        UpdateNodeInCatalogMsgRequest updateNodeInCatalogMsgRequest = new UpdateNodeInCatalogMsgRequest(nodeProfile);
        fermatWebSocketClientNodeChannel.sendMessage(updateNodeInCatalogMsgRequest.toJson(), PackageType.UPDATE_NODE_IN_CATALOG_REQUEST);

    }

    /**
     * Process the node into the node catalog
     */
    private void initializeNodeCatalog() throws Exception {

        LOG.info("Initialize node catalog");
        boolean iAm = iAmSeedServer();
        LOG.info("i Am Seed Server() = " + iAm);

        /*
         * Validate if the node are the seed server
         */
        if (iAm){

            /*
             * Validate if the node are register in the node catalog
             */
            if (Boolean.valueOf(ConfigurationManager.getValue(ConfigurationManager.REGISTER_IN_CATALOG))){

                /*
                 * Validate if the node server profile register has change
                 */
                if (validateNodeProfileRegisterChange()){
                    updateNodeProfileIntoCatalog();
                }

            }else {
                insertNodeProfileIntoCatalog();
            }

        } else {

            //TODO: UNCOMMENT THE CLIENT
            //fermatWebSocketClientNodeChannel = new FermatWebSocketClientNodeChannel(SeedServerConf.DEFAULT_IP, SeedServerConf.DEFAULT_PORT);
            fermatWebSocketClientNodeChannel = new FermatWebSocketClientNodeChannel("localhost", 9090);

            /*
             * Validate if the node are register in the node catalog
             */
            if (Boolean.valueOf(ConfigurationManager.getValue(ConfigurationManager.REGISTER_IN_CATALOG))){

                    /*
                     * Validate if the node server profile register has change
                     */
                if (validateNodeProfileRegisterChange()){
                    requestUpdateProfileInTheNodeCatalog();
                }

            }else {
                requestRegisterProfileInTheNodeCatalog();
            }
        }

    }

    /**
     * Insert the node profile into the catalog
     * @throws CantInsertRecordDataBaseException
     */
    private void insertNodeProfileIntoCatalog() throws Exception {

        LOG.info("Inserting my profile in the node catalog");

        if (!daoFactory.getNodesCatalogDao().exists(nodeProfile.getIdentityPublicKey())){

            /*
             * Create the NodesCatalog
             */
            NodesCatalog nodeCatalog = new NodesCatalog();
            nodeCatalog.setIp(nodeProfile.getIp());
            nodeCatalog.setDefaultPort(nodeProfile.getDefaultPort());
            nodeCatalog.setIdentityPublicKey(nodeProfile.getIdentityPublicKey());
            nodeCatalog.setName(nodeProfile.getName());
            nodeCatalog.setOfflineCounter(0);
            nodeCatalog.setLastConnectionTimestamp(new Timestamp(System.currentTimeMillis()));

            //Validate if location are available
            if (nodeProfile.getLocation() != null){
                nodeCatalog.setLastLatitude(nodeProfile.getLocation().getLatitude());
                nodeCatalog.setLastLongitude(nodeProfile.getLocation().getLongitude());
            }

            /*
             * Save into the data base
             */
            daoFactory.getNodesCatalogDao().create(nodeCatalog);

            /*
             * Create the NodesCatalog
             */
            NodesCatalogTransaction transaction = new NodesCatalogTransaction();
            transaction.setIp(nodeProfile.getIp());
            transaction.setDefaultPort(nodeProfile.getDefaultPort());
            transaction.setIdentityPublicKey(nodeProfile.getIdentityPublicKey());
            transaction.setName(nodeProfile.getName());
            transaction.setTransactionType(NodesCatalogTransaction.ADD_TRANSACTION_TYPE);
            transaction.setHashId(transaction.getHashId());
            transaction.setLastConnectionTimestamp(new Timestamp(System.currentTimeMillis()));

            //Validate if location are available
            if (nodeProfile.getLocation() != null){
                transaction.setLastLatitude(nodeProfile.getLocation().getLatitude());
                transaction.setLastLongitude(nodeProfile.getLocation().getLongitude());
            }

            /*
             * Save into the data base
             */
            daoFactory.getNodesCatalogTransactionDao().create(transaction);

            /*
             * Create the NodesCatalog
             */
            NodesCatalogTransactionsPendingForPropagation pendingForPropagation = new NodesCatalogTransactionsPendingForPropagation();
            pendingForPropagation.setIp(nodeProfile.getIp());
            pendingForPropagation.setDefaultPort(nodeProfile.getDefaultPort());
            pendingForPropagation.setIdentityPublicKey(nodeProfile.getIdentityPublicKey());
            pendingForPropagation.setName(nodeProfile.getName());
            pendingForPropagation.setTransactionType(NodesCatalogTransaction.ADD_TRANSACTION_TYPE);
            pendingForPropagation.setHashId(transaction.getHashId());
            pendingForPropagation.setLastConnectionTimestamp(new Timestamp(System.currentTimeMillis()));

            //Validate if location are available
            if (nodeProfile.getLocation() != null){
                pendingForPropagation.setLastLatitude(nodeProfile.getLocation().getLatitude());
                pendingForPropagation.setLastLongitude(nodeProfile.getLocation().getLongitude());
            }

            /*
             * Save into the data base
             */
            daoFactory.getNodesCatalogTransactionsPendingForPropagationDao().create(pendingForPropagation);

            ConfigurationManager.updateValue(ConfigurationManager.REGISTER_IN_CATALOG, String.valueOf(Boolean.TRUE));
            ConfigurationManager.updateValue(ConfigurationManager.LAST_REGISTER_NODE_PROFILE, HexadecimalConverter.convertHexString(nodeProfile.toJson().getBytes("UTF-8")));

        }else {

            ConfigurationManager.updateValue(ConfigurationManager.REGISTER_IN_CATALOG, String.valueOf(Boolean.TRUE));
            ConfigurationManager.updateValue(ConfigurationManager.LAST_REGISTER_NODE_PROFILE, HexadecimalConverter.convertHexString(nodeProfile.toJson().getBytes("UTF-8")));
        }

    }

    /**
     * Update the node profile into the catalog
     * @throws CantInsertRecordDataBaseException
     */
    private void updateNodeProfileIntoCatalog() throws Exception {

        LOG.info("Updating my profile in the node catalog");

        if (daoFactory.getNodesCatalogDao().exists(nodeProfile.getIdentityPublicKey())) {

            /*
             * Create the NodesCatalog
             */
            NodesCatalog nodeCatalog = new NodesCatalog();
            nodeCatalog.setIp(nodeProfile.getIp());
            nodeCatalog.setDefaultPort(nodeProfile.getDefaultPort());
            nodeCatalog.setIdentityPublicKey(nodeProfile.getIdentityPublicKey());
            nodeCatalog.setName(nodeProfile.getName());
            nodeCatalog.setOfflineCounter(0);
            nodeCatalog.setLastConnectionTimestamp(new Timestamp(System.currentTimeMillis()));

            //Validate if location are available
            if (nodeProfile.getLocation() != null) {
                nodeCatalog.setLastLatitude(nodeProfile.getLocation().getLatitude());
                nodeCatalog.setLastLongitude(nodeProfile.getLocation().getLongitude());
            }

            /*
             * Save into the data base
             */
            daoFactory.getNodesCatalogDao().update(nodeCatalog);

            /*
             * Create the NodesCatalog
             */
            NodesCatalogTransaction transaction = new NodesCatalogTransaction();
            transaction.setIp(nodeProfile.getIp());
            transaction.setDefaultPort(nodeProfile.getDefaultPort());
            transaction.setIdentityPublicKey(nodeProfile.getIdentityPublicKey());
            transaction.setName(nodeProfile.getName());
            transaction.setTransactionType(NodesCatalogTransaction.UPDATE_TRANSACTION_TYPE);
            transaction.setHashId(transaction.getHashId());
            transaction.setLastConnectionTimestamp(new Timestamp(System.currentTimeMillis()));

            //Validate if location are available
            if (nodeProfile.getLocation() != null) {
                transaction.setLastLatitude(nodeProfile.getLocation().getLatitude());
                transaction.setLastLongitude(nodeProfile.getLocation().getLongitude());
            }

            /*
             * Save into the data base
             */
            daoFactory.getNodesCatalogTransactionDao().create(transaction);

            /*
             * Create the NodesCatalog
             */
            NodesCatalogTransactionsPendingForPropagation pendingForPropagation = new NodesCatalogTransactionsPendingForPropagation();
            pendingForPropagation.setIp(nodeProfile.getIp());
            pendingForPropagation.setDefaultPort(nodeProfile.getDefaultPort());
            pendingForPropagation.setIdentityPublicKey(nodeProfile.getIdentityPublicKey());
            pendingForPropagation.setName(nodeProfile.getName());
            pendingForPropagation.setTransactionType(NodesCatalogTransaction.UPDATE_TRANSACTION_TYPE);
            pendingForPropagation.setHashId(transaction.getHashId());
            pendingForPropagation.setLastConnectionTimestamp(new Timestamp(System.currentTimeMillis()));

            //Validate if location are available
            if (nodeProfile.getLocation() != null) {
                pendingForPropagation.setLastLatitude(nodeProfile.getLocation().getLatitude());
                pendingForPropagation.setLastLongitude(nodeProfile.getLocation().getLongitude());
            }

            /*
             * Save into the data base
             */
            daoFactory.getNodesCatalogTransactionsPendingForPropagationDao().create(pendingForPropagation);

            ConfigurationManager.updateValue(ConfigurationManager.REGISTER_IN_CATALOG, String.valueOf(Boolean.TRUE));
            ConfigurationManager.updateValue(ConfigurationManager.LAST_REGISTER_NODE_PROFILE, HexadecimalConverter.convertHexString(nodeProfile.toJson().getBytes("UTF-8")));

        } else {

            insertNodeProfileIntoCatalog();

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
}
