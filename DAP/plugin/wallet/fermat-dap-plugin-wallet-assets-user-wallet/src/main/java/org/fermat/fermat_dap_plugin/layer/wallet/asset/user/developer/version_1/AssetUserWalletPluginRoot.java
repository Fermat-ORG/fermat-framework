package org.fermat.fermat_dap_plugin.layer.wallet.asset.user.developer.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
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
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWallet;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantCreateWalletException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import org.fermat.fermat_dap_plugin.layer.wallet.asset.user.developer.version_1.structure.database.DeveloperDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.wallet.asset.user.developer.version_1.structure.exceptions.CantDeliveryDatabaseException;
import org.fermat.fermat_dap_plugin.layer.wallet.asset.user.developer.version_1.structure.functional.AssetUserWalletImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by Franklin on 07/09/15.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.MEDIUM,
        maintainerMail = "marsvicam@gmail.com",
        createdBy = "franklin",
        layer = Layers.WALLET,
        platform = Platforms.DIGITAL_ASSET_PLATFORM,
        plugin = Plugins.ASSET_USER)
public class AssetUserWalletPluginRoot extends AbstractPlugin implements
        AssetUserWalletManager,
        DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_USER)
    private ActorAssetUserManager userManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_ISSUER)
    private ActorAssetIssuerManager issuerManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.REDEEM_POINT)
    private ActorAssetRedeemPointManager redeemPointManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    private Broadcaster broadcaster;

    public AssetUserWalletPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    private static final String WALLET_USER_FILE_NAME = "walletsIds";
    private List<UUID> userWallets = new ArrayList<>();
    public static final String PATH_DIRECTORY = "asset-user-swap/";
    private static final String walletPublicKey = WalletUtilities.WALLET_PUBLIC_KEY;
    private boolean existsWallet = false;
    private AssetUserWallet assetUserWallet; //We will use this, I swear.

    @Override
    public void start() throws CantStartPluginException {
        try {
            boolean existWallet = loadWalletUserList();
            if (!existWallet) {
                createAssetUserWallet(walletPublicKey, BlockchainNetworkType.REG_TEST);
                createAssetUserWallet(walletPublicKey, BlockchainNetworkType.TEST_NET);
                createAssetUserWallet(walletPublicKey, BlockchainNetworkType.PRODUCTION);
            }
            assetUserWallet = loadAssetUserWallet(walletPublicKey, BlockchainNetworkType.getDefaultBlockchainNetworkType());
            System.out.println("Star Plugin AssetWalletUser");
            this.serviceStatus = ServiceStatus.STARTED;
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
    }

    @Override
    public AssetUserWallet loadAssetUserWallet(String walletPublicKey, BlockchainNetworkType networkType) throws CantLoadWalletException {
        try {
            AssetUserWalletImpl userWallet = new AssetUserWalletImpl(
                    this,
                    pluginDatabaseSystem,
                    pluginFileSystem,
                    pluginId,
                    userManager,
                    issuerManager,
                    redeemPointManager,
                    broadcaster);

            UUID internalAssetIssuerWalletId = WalletUtilities.constructWalletId(walletPublicKey, networkType);
            userWallet.initialize(internalAssetIssuerWalletId);
            return userWallet;
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantLoadWalletException(CantLoadWalletException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public void createAssetUserWallet(String walletPublicKey, BlockchainNetworkType networkType) throws CantCreateWalletException {
        try {
            AssetUserWalletImpl userWallet = new AssetUserWalletImpl(
                    this,
                    pluginDatabaseSystem,
                    pluginFileSystem,
                    pluginId,
                    userManager,
                    issuerManager,
                    redeemPointManager,
                    broadcaster);

            UUID internalAssetIssuerWalletId = userWallet.create(walletPublicKey, networkType);
            userWallets.add(internalAssetIssuerWalletId);
        } catch (CantCreateWalletException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateWalletException("Wallet Creation Failed", e, "walletId: " + walletPublicKey, "");
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateWalletException("Wallet Creation Failed", FermatException.wrapException(e), "walletId: " + walletPublicKey, "");
        }
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        List<String> databasesNames = new ArrayList<>();
        Collection<UUID> ids = userWallets;
        for (UUID id : ids)
            databasesNames.add(id.toString());
        DeveloperDatabaseFactory dbFactory = new DeveloperDatabaseFactory(this.pluginId.toString(), databasesNames);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return DeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        List<DeveloperDatabaseTableRecord> databaseTableRecords = new ArrayList<>();
        UUID walletId = null;
        try {
            Database database = this.pluginDatabaseSystem.openDatabase(this.pluginId, developerDatabase.getName());
            databaseTableRecords.addAll(DeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable));
        } catch (CantOpenDatabaseException e) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            e.printStackTrace();
        } catch (DatabaseNotFoundException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            e.printStackTrace();
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            e.printStackTrace();
        }
        // If we are here the database could not be opened, so we return an empry list
        return databaseTableRecords;
    }

    private boolean loadWalletUserList() throws CantStartPluginException {
        PluginTextFile walletIssuerFile = getWalletUserFile();
        boolean existWallet = false;
        for (String stringWalletId : walletIssuerFile.getContent().split(";"))
            if (!stringWalletId.equals("")) {
                userWallets.add(UUID.fromString(stringWalletId));
                existWallet = true;
            }
        return existWallet;
    }

    private PluginTextFile getWalletUserFile() throws CantStartPluginException {
        try {
            PluginTextFile walletUserFile = pluginFileSystem.getTextFile(pluginId, "", WALLET_USER_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            walletUserFile.loadFromMedia();
            return walletUserFile;
        } catch (FileNotFoundException | CantCreateFileException e) {
            return createWalletUserFile();
        } catch (CantLoadFileException e) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, null, null);
        }
    }

    private PluginTextFile createWalletUserFile() throws CantStartPluginException {
        try {
            PluginTextFile walletIssuerFile = pluginFileSystem.createTextFile(pluginId, "", WALLET_USER_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            walletIssuerFile.persistToMedia();
            return walletIssuerFile;
        } catch (CantCreateFileException | CantPersistFileException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            e.printStackTrace();
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, null, null);
        }
    }
}
