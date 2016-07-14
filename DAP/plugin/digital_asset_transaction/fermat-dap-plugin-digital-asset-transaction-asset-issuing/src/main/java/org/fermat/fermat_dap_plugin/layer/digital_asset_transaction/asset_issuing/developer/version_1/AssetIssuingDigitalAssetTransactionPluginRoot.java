package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;

import com.bitdubai.fermat_bch_api.layer.crypto_network.manager.BlockchainManager;
import com.bitdubai.fermat_bch_api.layer.crypto_router.incoming_crypto.IncomingCryptoManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetLoggedInDeviceUserException;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.enums.IssuingStatus;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantIssueDigitalAssetsException;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeliverDatabaseException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.developer_utils.AssetIssuingTransactionDeveloperDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDAO;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDatabaseConstants;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.events.AssetIssuingMonitorAgent;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.events.AssetIssuingRecorderService;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.functional.AssetIssuingTransactionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 31/08/15.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.HIGH,
        maintainerMail = "marsvicam@gmail.com",
        createdBy = "manuel",
        layer = Layers.DIGITAL_ASSET_TRANSACTION,
        platform = Platforms.DIGITAL_ASSET_PLATFORM,
        plugin = Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION)
public class AssetIssuingDigitalAssetTransactionPluginRoot extends AbstractPlugin implements
        AssetIssuingManager,
        DatabaseManagerForDevelopers {


    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_ROUTER, plugin = Plugins.INCOMING_CRYPTO)
    IncomingCryptoManager incomingCryptoManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.BASIC_WALLET, plugin = Plugins.BITCOIN_WALLET)
    CryptoWalletManager cryptoWalletManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_MODULE, plugin = Plugins.CRYPTO_ADDRESS_BOOK)
    CryptoAddressBookManager cryptoAddressBookManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.TRANSACTION, plugin = Plugins.OUTGOING_INTRA_ACTOR)
    OutgoingIntraActorManager outgoingIntraActorManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_ISSUER)
    ActorAssetIssuerManager actorAssetIssuerManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.INTRA_WALLET_USER)
    IntraWalletUserIdentityManager intraWalletUserIdentityManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.WALLET, plugin = Plugins.ASSET_ISSUER)
    AssetIssuerWalletManager assetIssuerWalletManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_NETWORK, plugin = Plugins.BITCOIN_NETWORK)
    BlockchainManager bitcoinNetworkManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_VAULT, plugin = Plugins.BITCOIN_ASSET_VAULT)
    AssetVaultManager assetVaultManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    EventManager eventManager;


    public AssetIssuingDigitalAssetTransactionPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    private AssetIssuingTransactionManager transactionManager;
    private AssetIssuingMonitorAgent monitorAgent;
    private AssetIssuingDAO dao;

    @Override
    public void start() throws CantStartPluginException {
        try {
            pluginDatabaseSystem.openDatabase(pluginId, AssetIssuingDatabaseConstants.ASSET_ISSUING_DATABASE);
        } catch (DatabaseNotFoundException | CantOpenDatabaseException exception) {
            try {
                createAssetIssuingTransactionDatabase();
            } catch (CantCreateDatabaseException innerException) {
                throw new CantStartPluginException(CantCreateDatabaseException.DEFAULT_MESSAGE, innerException, "Starting Asset Issuing plugin - " + this.pluginId, "Cannot open or create the plugin database");
            }
        }
        try {
            initializeDAO();
            transactionManager = new AssetIssuingTransactionManager(dao, cryptoWalletManager);
            startRecorderService();
            startMonitorAgent();
            dao.reprocessIssuingAssets();
        } catch (CantSetObjectException exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, "Starting Asset Issuing plugin", "Cannot set an object, probably is null");
        } catch (CantExecuteDatabaseOperationException exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, "Starting pluginDatabaseSystem in DigitalAssetCryptoTransactionFactory", "Error in constructor method AssetIssuingTransactionDao");
        } catch (CantStartAgentException exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, "Starting Asset Issuing plugin", "cannot start monitor agent");
        } catch (Exception exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Starting Asset Issuing plugin", "Unexpected exception");
        } finally {
            this.serviceStatus = ServiceStatus.STOPPED;
        }
        this.serviceStatus = ServiceStatus.STARTED;

    }

    /**
     * This method will start the Monitor Agent that watches the asyncronic process registered in the asset issuing plugin
     *
     * @throws CantGetLoggedInDeviceUserException
     * @throws CantSetObjectException
     * @throws CantStartAgentException
     */
    private void startMonitorAgent() throws CantGetLoggedInDeviceUserException, CantSetObjectException, CantStartAgentException {
        if (monitorAgent == null) {
            monitorAgent = new AssetIssuingMonitorAgent(incomingCryptoManager, errorManager, bitcoinNetworkManager, outgoingIntraActorManager, actorAssetIssuerManager, assetVaultManager, cryptoWalletManager, cryptoAddressBookManager, intraWalletUserIdentityManager, assetIssuerWalletManager, dao);
        }
        monitorAgent.start();
    }

    private void startRecorderService() throws CantStartServiceException {
        AssetIssuingRecorderService assetIssuingEventRecorderService = new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.events.AssetIssuingRecorderService(eventManager, dao);
        assetIssuingEventRecorderService.start();
    }

    private void initializeDAO() throws CantSetObjectException, CantExecuteDatabaseOperationException {
        org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.functional.DigitalAssetIssuingVault issuingVault = new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.functional.DigitalAssetIssuingVault(pluginId,
                pluginFileSystem,
                errorManager,
                assetIssuerWalletManager,
                actorAssetIssuerManager);
        dao = new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDAO(pluginId, pluginDatabaseSystem, issuingVault);
    }

    @Override
    public void stop() {
        try {
            this.monitorAgent.stop();
        } catch (CantStopAgentException e) {
            e.printStackTrace();
        }
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public void issueAssets(DigitalAsset digitalAssetToIssue, int assetsAmount, String issuerWalletPk, String btcWalletPublicKey, BlockchainNetworkType blockchainNetworkType) throws CantIssueDigitalAssetsException {
        try {
            this.transactionManager.issueAssets(digitalAssetToIssue, assetsAmount, issuerWalletPk, btcWalletPublicKey, blockchainNetworkType);
        } catch (Exception exception) {
            throw new CantIssueDigitalAssetsException(exception, "Issuing Assets", "Unexpected exception");
        }

    }

    /**
     * This method will create the plugin database
     *
     * @throws CantCreateDatabaseException
     */
    private void createAssetIssuingTransactionDatabase() throws CantCreateDatabaseException {
        AssetIssuingDatabaseFactory databaseFactory = new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDatabaseFactory(this.pluginDatabaseSystem);
        databaseFactory.createDatabase(pluginId, AssetIssuingDatabaseConstants.ASSET_ISSUING_DATABASE);
    }

    @Override
    public int getNumberOfIssuedAssets(String assetPublicKey) throws CantExecuteDatabaseOperationException {
        return transactionManager.getNumberOfIssuedAssets(assetPublicKey);
    }

    @Override
    public IssuingStatus getIssuingStatus(String assetPublicKey) throws CantExecuteDatabaseOperationException {
        return transactionManager.getIssuingStatus(assetPublicKey);
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        AssetIssuingTransactionDeveloperDatabaseFactory assetIssuingTransactionDeveloperDatabaseFactory = new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.developer_utils.AssetIssuingTransactionDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return assetIssuingTransactionDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.developer_utils.AssetIssuingTransactionDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        Database database;
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, AssetIssuingDatabaseConstants.ASSET_ISSUING_DATABASE);
            return AssetIssuingTransactionDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            FermatException e = new CantDeliverDatabaseException("Cannot open the database", cantOpenDatabaseException, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            FermatException e = new CantDeliverDatabaseException("Database does not exists", databaseNotFoundException, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception exception) {
            FermatException e = new CantDeliverDatabaseException("Unexpected Exception", FermatException.wrapException(exception), "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empty list
        return new ArrayList<>();
    }

}
