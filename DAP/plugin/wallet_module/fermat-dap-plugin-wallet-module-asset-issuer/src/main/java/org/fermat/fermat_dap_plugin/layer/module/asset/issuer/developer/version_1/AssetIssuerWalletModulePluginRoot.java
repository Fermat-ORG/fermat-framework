package org.fermat.fermat_dap_plugin.layer.module.asset.issuer.developer.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import org.fermat.fermat_dap_api.layer.all_definition.enums.AssetCurrentStatus;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetIssuerException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserGroupException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.exceptions.CantGetAssetIssuerIdentitiesException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactoryManager;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.AssetIssuerSettings;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.exceptions.CantGetAssetStatisticException;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions.CantDistributeDigitalAssetsException;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_distribution.interfaces.AssetDistributionManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteAppropriationTransactionException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.NotEnoughAcceptsException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.TransactionAlreadyStartedException;
import org.fermat.fermat_dap_api.layer.dap_transaction.issuer_appropriation.interfaces.IssuerAppropriationManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletList;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetStatistic;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantCreateWalletException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * TODO explain here the main functionality of the plug-in.
 * <p/>
 * Created by Franklin on 07/09/15.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.MEDIUM,
        maintainerMail = "nerioindriago@gmail.com",
        createdBy = "franklin",
        layer = Layers.WALLET_MODULE,
        platform = Platforms.DIGITAL_ASSET_PLATFORM,
        plugin = Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE)
public class AssetIssuerWalletModulePluginRoot extends AbstractPlugin implements
        AssetIssuerWalletSupAppModuleManager {

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_USER)
    private ActorAssetUserManager actorAssetUserManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.WALLET, plugin = Plugins.ASSET_ISSUER)
    private AssetIssuerWalletManager assetIssuerWalletManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.DIGITAL_ASSET_TRANSACTION, plugin = Plugins.ASSET_DISTRIBUTION)
    AssetDistributionManager assetDistributionManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.MIDDLEWARE, plugin = Plugins.ASSET_FACTORY)
    AssetFactoryManager assetFactoryManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.ASSET_ISSUER)
    IdentityAssetIssuerManager identityAssetIssuerManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.MIDDLEWARE, plugin = Plugins.WALLET_MANAGER)
    private WalletManagerManager walletMiddlewareManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.DIGITAL_ASSET_TRANSACTION, plugin = Plugins.ISSUER_APPROPRIATION)
    private IssuerAppropriationManager issuerAppropriationManager;

    private AssetIssuerWallet wallet;

    private List<ActorAssetUser> selectedUsersToDeliver;

    {
        selectedUsersToDeliver = new ArrayList<>();
    }

    private BlockchainNetworkType selectedNetwork;

    private SettingsManager<AssetIssuerSettings> settingsManager;
    AssetIssuerSettings settings = null;
    String publicKeyApp;

    private boolean showUsersOutsideGroup;

    // TODO PLEASE MAKE USE OF THE ERROR MANAGER.

    private org.fermat.fermat_dap_plugin.layer.module.asset.issuer.developer.version_1.structure.AssetIssuerWalletModuleManager assetIssuerWalletModuleManager;

    public AssetIssuerWalletModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     * (non-Javadoc)
     *
     * @see Service#start()
     */
    @Override
    public void start() throws CantStartPluginException {
        try {
            assetIssuerWalletModuleManager = new org.fermat.fermat_dap_plugin.layer.module.asset.issuer.developer.version_1.structure.AssetIssuerWalletModuleManager(
                    assetIssuerWalletManager,
                    actorAssetUserManager,
                    assetDistributionManager,
                    issuerAppropriationManager,
                    identityAssetIssuerManager,
                    pluginId,
                    pluginFileSystem,
                    errorManager
            );

            System.out.println("******* Asset Issuer Wallet Module Init ******");

            this.serviceStatus = ServiceStatus.STARTED;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantStartPluginException(exception);
        }
    }

    @Override
    public List<AssetIssuerWalletList> getAssetIssuerWalletBalances(String publicKey) throws CantLoadWalletException {
        try {
            return assetIssuerWalletModuleManager.getAssetIssuerWalletBalances(publicKey, selectedNetwork);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantLoadWalletException(e);
        }
    }

    @Override
    public List<ActorAssetUser> getAllActorUserRegistered() throws CantGetAssetUserActorsException {
        List<ActorAssetUser> allUsers = actorAssetUserManager.getAllAssetUserActorInTableRegistered();

        if (showUsersOutsideGroup) {
            return allUsers;
        } else {
            try {
                for (ActorAssetUserGroup group : actorAssetUserManager.getAssetUserGroupsList()) {
                    allUsers.removeAll(actorAssetUserManager.getListActorAssetUserByGroups(group.getGroupId(), selectedNetwork));
                }
                return allUsers;
            } catch (CantGetAssetUserGroupException exception) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
                //If this happen for some reason it means that we failed to read the groups, then we return the full list.
                return allUsers;
            }
        }
    }

    @Override
    public List<ActorAssetUser> getAllAssetUserActorConnected() throws CantGetAssetUserActorsException {
        try {
            return assetIssuerWalletModuleManager.getAllAssetUserActorConnected(selectedNetwork);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetAssetUserActorsException(e);
        }
    }

    @Override
    public List<ActorAssetUserGroup> getAssetUserGroupsList() throws CantGetAssetUserGroupException {
        try {
            return actorAssetUserManager.getAssetUserGroupsList();
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetAssetUserGroupException(e);
        }
    }

    @Override
    public List<ActorAssetUser> getListActorAssetUserByGroups(String groupId) throws CantGetAssetUserActorsException {
        try {
            return actorAssetUserManager.getListActorAssetUserByGroups(groupId, selectedNetwork);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetAssetUserActorsException(e);
        }
    }

    @Override
    public void toggleShowUsersOutsideTheirGroup() {
        showUsersOutsideGroup = !showUsersOutsideGroup;
    }

    @Override
    public IdentityAssetIssuer getActiveAssetIssuerIdentity() throws CantGetIdentityAssetIssuerException {
        try {
            return identityAssetIssuerManager.getIdentityAssetIssuer();
        } catch (CantGetAssetIssuerIdentitiesException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetIdentityAssetIssuerException(e);
        }
    }

    @Override
    public void addUserToDeliver(ActorAssetUser user) {
        if (!selectedUsersToDeliver.contains(user)) {
            selectedUsersToDeliver.add(user);
        }
    }

    @Override
    public void addGroupToDeliver(ActorAssetUserGroup group) throws
            CantGetAssetUserActorsException {
        for (ActorAssetUser user : getListActorAssetUserByGroups(group.getGroupId())) {
            selectedUsersToDeliver.add(user);
        }
    }

    @Override
    public void removeUserToDeliver(ActorAssetUser user) {
        selectedUsersToDeliver.remove(user);
    }

    @Override
    public void removeGroupToDeliver(ActorAssetUserGroup group) throws
            CantGetAssetUserActorsException {
        for (ActorAssetUser user : getListActorAssetUserByGroups(group.getGroupId())) {
            selectedUsersToDeliver.remove(user);
        }
    }

    @Override
    public void clearDeliverList() {
        selectedUsersToDeliver.clear();
    }

    @Override
    public void addAllRegisteredUsersToDeliver() throws CantGetAssetUserActorsException {
        for (ActorAssetUser user : getAllActorUserRegistered()) {
            addUserToDeliver(user);
        }
    }

    @Override
    public List<ActorAssetUser> getSelectedUsersToDeliver() {
        return selectedUsersToDeliver;
    }

    @Override
    public void distributionAssets(String assetPublicKey, String walletPublicKey, int assetsAmount) throws
            CantDistributeDigitalAssetsException, CantGetTransactionsException, CantCreateFileException, FileNotFoundException, CantLoadWalletException {
        assetIssuerWalletModuleManager.distributionAssets(assetPublicKey, walletPublicKey, selectedUsersToDeliver, assetsAmount, selectedNetwork);
    }

    @Override
    public void appropriateAsset(String digitalAssetPublicKey, String bitcoinWalletPublicKey) throws CantExecuteAppropriationTransactionException, TransactionAlreadyStartedException, NotEnoughAcceptsException {
        try {
            List<InstalledWallet> installedWallets = walletMiddlewareManager.getInstalledWallets();
            if (installedWallets.isEmpty()) {
                System.out.println("NO INSTALLED WALLETS, CANNOT PROCEED.");
                return;
            }
            //TODO REMOVE HARDCODE
            InstalledWallet installedWallet = installedWallets.get(0);
            assetIssuerWalletModuleManager.appropriateAsset(digitalAssetPublicKey, installedWallet.getWalletPublicKey(), selectedNetwork);
        } catch (CantListWalletsException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            exception.printStackTrace();
        }
    }

    @Override
    public AssetIssuerWallet loadAssetIssuerWallet(String walletPublicKey) throws
            CantLoadWalletException {
        wallet = assetIssuerWalletManager.loadAssetIssuerWallet(walletPublicKey, selectedNetwork);
        return wallet;
    }

    @Override
    public void createWalletAssetIssuer(String walletPublicKey) throws CantCreateWalletException {
        assetIssuerWalletManager.createWalletAssetIssuer(walletPublicKey, selectedNetwork);
    }

    @Override
    public AssetFactory getAssetFactory(String publicKey) throws CantGetAssetFactoryException, CantCreateFileException {
        return assetFactoryManager.getAssetFactoryByPublicKey(publicKey);
    }

    @Override
    public PluginBinaryFile getAssetFactoryResource(Resource resource) throws FileNotFoundException, CantCreateFileException {
        return assetFactoryManager.getAssetFactoryResource(resource);
    }

    @Override
    public List<AssetStatistic> getWalletStatisticsByAssetAndStatus(String walletPublicKey, String assetName, AssetCurrentStatus assetCurrentStatus) throws CantLoadWalletException, CantGetAssetStatisticException {
        return loadAssetIssuerWallet(walletPublicKey).getStatisticForGivenAssetByStatus(assetName, assetCurrentStatus);
    }

    @Override
    public List<AssetStatistic> getWalletStatisticsByAsset(String walletPublicKey, String assetName) throws CantLoadWalletException, CantGetAssetStatisticException {
        return loadAssetIssuerWallet(walletPublicKey).getAllStatisticForGivenAsset(assetName);
    }

    @Override
    public void changeNetworkType(BlockchainNetworkType networkType) {
        if (networkType == null) {
            selectedNetwork = BlockchainNetworkType.getDefaultBlockchainNetworkType();
        } else {
            selectedNetwork = networkType;
        }
    }

    @Override
    public SettingsManager<AssetIssuerSettings> getSettingsManager() {
        if (this.settingsManager != null)
            return this.settingsManager;

        this.settingsManager = new SettingsManager<>(
                pluginFileSystem,
                pluginId
        );

        return this.settingsManager;
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        try {
            List<IdentityAssetIssuer> identities = assetIssuerWalletModuleManager.getActiveIdentities();
            return (identities == null || identities.isEmpty()) ? null : assetIssuerWalletModuleManager.getActiveIdentities().get(0);
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {
        identityAssetIssuerManager.createNewIdentityAssetIssuer(name, profile_img);
    }

    @Override
    public BlockchainNetworkType getSelectedNetwork() {
        if (selectedNetwork == null) {
            try {
                if (settings == null) {
                    settingsManager = getSettingsManager();
                }
                settings = settingsManager.loadAndGetSettings(WalletsPublicKeys.DAP_ISSUER_WALLET.getCode());
                selectedNetwork = settings.getBlockchainNetwork().get(settings.getBlockchainNetworkPosition());
            } catch (CantGetSettingsException exception) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
                exception.printStackTrace();
            } catch (SettingsNotFoundException e) {
                //TODO: Only enter while the Active Actor Wallet is not open.
                selectedNetwork = BlockchainNetworkType.getDefaultBlockchainNetworkType();
//                e.printStackTrace();
            }
        }
        return selectedNetwork;
    }

    @Override
    public void setAppPublicKey(String publicKey) {
        this.publicKeyApp = publicKey;

        try {
            settings = settingsManager.loadAndGetSettings(publicKeyApp);
        } catch (Exception e) {
            settings = null;
        }

        if (settings != null && settings.getBlockchainNetwork() != null) {
            settings.setBlockchainNetwork(Arrays.asList(BlockchainNetworkType.values()));
        } else {
            int position = 0;
            List<BlockchainNetworkType> list = Arrays.asList(BlockchainNetworkType.values());

            for (BlockchainNetworkType networkType : list) {
                if (Objects.equals(networkType.getCode(), BlockchainNetworkType.getDefaultBlockchainNetworkType().getCode())) {
                    settings.setBlockchainNetworkPosition(position);
                    break;
                } else {
                    position++;
                }
            }
            settings.setBlockchainNetwork(list);
        }

        try {
            settingsManager.persistSettings(publicKeyApp, settings);
        } catch (CantPersistSettingsException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            exception.printStackTrace();
        }
    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }
}
