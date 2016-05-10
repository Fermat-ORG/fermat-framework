package org.fermat.fermat_dap_plugin.layer.wallet.asset.issuer.developer.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
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
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantInitializeAssetIssuerWalletException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantCreateWalletException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by Franklin on 07/09/15.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.MEDIUM,
        maintainerMail = "nerioindriago@gmail.com",
        createdBy = "franklin",
        layer = Layers.WALLET,
        platform = Platforms.DIGITAL_ASSET_PLATFORM,
        plugin = Plugins.BITDUBAI_ASSET_WALLET_ISSUER)
public class AssetIssuerWalletPluginRoot extends AbstractPlugin implements
        AssetIssuerWalletManager,
        DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.REDEEM_POINT)
    ActorAssetRedeemPointManager actorAssetRedeemPointManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_USER)
    ActorAssetUserManager actorAssetUserManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_ISSUER)
    ActorAssetIssuerManager assetIssuerManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    private Broadcaster broadcaster;

    private static final String WALLET_ISSUER_FILE_NAME = "walletsIds";
    private List<UUID> issuerWallets = new ArrayList<>();

    public AssetIssuerWalletPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    String walletPublicKey = WalletUtilities.WALLET_PUBLIC_KEY;
    private AssetIssuerWallet assetIssuerWallet;

    @Override
    public void start() throws CantStartPluginException {
        try {
            boolean existWallet = loadWalletIssuerList();

            try {
                if (!existWallet) {
                    createWalletAssetIssuer(walletPublicKey, BlockchainNetworkType.REG_TEST);
                    createWalletAssetIssuer(walletPublicKey, BlockchainNetworkType.TEST_NET);
                    createWalletAssetIssuer(walletPublicKey, BlockchainNetworkType.PRODUCTION);
                }

                assetIssuerWallet = loadAssetIssuerWallet(walletPublicKey, BlockchainNetworkType.getDefaultBlockchainNetworkType());

            } catch (CantLoadWalletException e) {
                e.printStackTrace();
            }

            //testWallet();
            System.out.println("Start Plugin AssetWalletIssuer");
            this.serviceStatus = ServiceStatus.STARTED;
        } catch (CantStartPluginException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);

        }
    }

    private boolean loadWalletIssuerList() throws CantStartPluginException {
        PluginTextFile walletIssuerFile = getWalletIssuerFile();
        boolean existWallet = false;
        for (String stringWalletId : walletIssuerFile.getContent().split(";"))
            if (!stringWalletId.equals("")) {
                issuerWallets.add(UUID.fromString(stringWalletId));
                existWallet = true;
            }
        return existWallet;
    }

    private PluginTextFile getWalletIssuerFile() throws CantStartPluginException {
        try {
            PluginTextFile walletIssuerFile = pluginFileSystem.getTextFile(pluginId, "", WALLET_ISSUER_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            walletIssuerFile.loadFromMedia();
            return walletIssuerFile;
        } catch (FileNotFoundException | CantCreateFileException exception) {
            return createWalletIssuerFile();
        } catch (CantLoadFileException exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, null, null);
        }
    }

    private PluginTextFile createWalletIssuerFile() throws CantStartPluginException {
        try {
            PluginTextFile walletIssuerFile = pluginFileSystem.createTextFile(pluginId, "", WALLET_ISSUER_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            walletIssuerFile.persistToMedia();
            return walletIssuerFile;
        } catch (CantCreateFileException | CantPersistFileException exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, null, null);
        }
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        List<String> databasesNames = new ArrayList<>();
        Collection<UUID> ids = this.issuerWallets;
        for (UUID id : ids)
            databasesNames.add(id.toString());
        org.fermat.fermat_dap_plugin.layer.wallet.asset.issuer.developer.version_1.structure.database.DeveloperDatabaseFactory dbFactory = new org.fermat.fermat_dap_plugin.layer.wallet.asset.issuer.developer.version_1.structure.database.DeveloperDatabaseFactory(this.pluginId.toString(), databasesNames);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return org.fermat.fermat_dap_plugin.layer.wallet.asset.issuer.developer.version_1.structure.database.DeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        List<DeveloperDatabaseTableRecord> databaseTableRecords = new ArrayList<>();
        try {
            Database database = this.pluginDatabaseSystem.openDatabase(this.pluginId, developerDatabase.getName());
            databaseTableRecords.addAll(org.fermat.fermat_dap_plugin.layer.wallet.asset.issuer.developer.version_1.structure.database.DeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable));
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            FermatException e = new org.fermat.fermat_dap_plugin.layer.wallet.asset.issuer.developer.version_1.structure.exceptions.CantDeliverDatabaseException("I can't open database", cantOpenDatabaseException, "WalletId: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            FermatException e = new org.fermat.fermat_dap_plugin.layer.wallet.asset.issuer.developer.version_1.structure.exceptions.CantDeliverDatabaseException("Database does not exists", databaseNotFoundException, "WalletId: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception exception) {
            FermatException e = new org.fermat.fermat_dap_plugin.layer.wallet.asset.issuer.developer.version_1.structure.exceptions.CantDeliverDatabaseException(org.fermat.fermat_dap_plugin.layer.wallet.asset.issuer.developer.version_1.structure.exceptions.CantDeliverDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "WalletId: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empry list
        return databaseTableRecords;
    }

    @Override
    public AssetIssuerWallet loadAssetIssuerWallet(String walletPublicKey, BlockchainNetworkType networkType) throws CantLoadWalletException {

        try {
            org.fermat.fermat_dap_plugin.layer.wallet.asset.issuer.developer.version_1.structure.functional.AssetIssuerWalletImpl assetIssuerWallet = new org.fermat.fermat_dap_plugin.layer.wallet.asset.issuer.developer.version_1.structure.functional.AssetIssuerWalletImpl(
                    errorManager,
                    pluginDatabaseSystem,
                    pluginFileSystem,
                    pluginId,
                    actorAssetUserManager,
                    actorAssetRedeemPointManager,
                    assetIssuerManager,
                    broadcaster);

            UUID internalAssetIssuerWalletId = WalletUtilities.constructWalletId(walletPublicKey, networkType);
            assetIssuerWallet.initialize(internalAssetIssuerWalletId);
            return assetIssuerWallet;
        } catch (CantInitializeAssetIssuerWalletException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantLoadWalletException("I can't initialize wallet", exception, "", "");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantLoadWalletException(CantLoadWalletException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "", "");
        }
    }

    @Override
    public void createWalletAssetIssuer(String walletPublicKey, BlockchainNetworkType networkType) throws CantCreateWalletException {
        try {
            org.fermat.fermat_dap_plugin.layer.wallet.asset.issuer.developer.version_1.structure.functional.AssetIssuerWalletImpl assetIssuerWallet = new org.fermat.fermat_dap_plugin.layer.wallet.asset.issuer.developer.version_1.structure.functional.AssetIssuerWalletImpl(
                    errorManager,
                    pluginDatabaseSystem,
                    pluginFileSystem,
                    pluginId,
                    actorAssetUserManager,
                    actorAssetRedeemPointManager,
                    assetIssuerManager,
                    broadcaster);

            UUID internalAssetIssuerWalletId = assetIssuerWallet.create(walletPublicKey, networkType);
            issuerWallets.add(internalAssetIssuerWalletId);
        } catch (CantCreateWalletException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantCreateWalletException("Wallet Creation Failed", exception, "walletId: " + walletPublicKey, "");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantCreateWalletException("Wallet Creation Failed", FermatException.wrapException(exception), "walletId: " + walletPublicKey, "");
        }
    }
}
