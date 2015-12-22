package com.bitdubai.fermat_dap_plugin.layer.module.asset.issuer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.exceptions.CantGetAssetIssuerIdentitiesException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactoryManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.exceptions.CantGetIdentityAssetIssuerException;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions.CantDistributeDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.interfaces.AssetDistributionManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_dap_plugin.layer.module.asset.issuer.developer.bitdubai.version_1.structure.AssetIssuerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO explain here the main functionality of the plug-in.
 * <p/>
 * Created by Franklin on 07/09/15.
 */
public class AssetIssuerWalletModulePluginRoot extends AbstractPlugin implements
        AssetIssuerWalletSupAppModuleManager {

    @Override
    public SettingsManager getSettingsManager() {
        return null;
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException {
        return null;
    }

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

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.IDENTITY       , plugin = Plugins.ASSET_ISSUER  )
    private IdentityAssetIssuerManager identityAssetIssuerManager;

    private AssetIssuerWallet wallet;

    private List<ActorAssetUser> selectedUsersToDeliver;

    {
        selectedUsersToDeliver = new ArrayList<>();
    }

    private boolean showUsersOutsideGroup;

    // TODO PLEASE MAKE USE OF THE ERROR MANAGER.

    private AssetIssuerWalletModuleManager assetIssuerWalletModuleManager;

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
            assetIssuerWalletModuleManager = new AssetIssuerWalletModuleManager(
                    assetIssuerWalletManager,
                    actorAssetUserManager,
                    assetDistributionManager,
                    pluginId,
                    pluginFileSystem
            );
            //getTransactionsAssetAll("", "");
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
            return assetIssuerWalletModuleManager.getAssetIssuerWalletBalances(publicKey);
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
                    allUsers.removeAll(actorAssetUserManager.getListActorAssetUserByGroups(group.getGroupName()));
                }
                return allUsers;
            } catch (CantGetAssetUserGroupException cantGetAssetUserGroupException) {
                //If this happen for some reason it means that we failed to read the groups, then we return the full list.
                return allUsers;
            }
        }
    }

    @Override
    public List<ActorAssetUser> getAllAssetUserActorConnected() throws CantGetAssetUserActorsException {
        try {
            return assetIssuerWalletModuleManager.getAllAssetUserActorConnected();
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
    public List<ActorAssetUser> getListActorAssetUserByGroups(String groupName) throws CantGetAssetUserActorsException {
        try {
            return actorAssetUserManager.getListActorAssetUserByGroups(groupName);
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
        for (ActorAssetUser user : getListActorAssetUserByGroups(group.getGroupName())) {
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
        for (ActorAssetUser user : getListActorAssetUserByGroups(group.getGroupName())) {
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
    public void distributionAssets(String assetPublicKey, String walletPublicKey) throws
            CantDistributeDigitalAssetsException, CantGetTransactionsException, CantCreateFileException, FileNotFoundException, CantLoadWalletException {
        assetIssuerWalletModuleManager.distributionAssets(assetPublicKey, walletPublicKey, selectedUsersToDeliver);
    }

    @Override
    public AssetIssuerWallet loadAssetIssuerWallet(String walletPublicKey) throws
            CantLoadWalletException {
        wallet = assetIssuerWalletManager.loadAssetIssuerWallet(walletPublicKey);
        return wallet;
    }

    @Override
    public void createWalletAssetIssuer(String walletPublicKey) throws CantCreateWalletException {
        assetIssuerWalletManager.createWalletAssetIssuer(walletPublicKey);
    }

    @Override
    public AssetFactory getAssetFactory(String publicKey) throws CantGetAssetFactoryException, CantCreateFileException {
        return assetFactoryManager.getAssetFactoryByPublicKey(publicKey);
    }

    @Override
    public PluginBinaryFile getAssetFactoryResource(Resource resource) throws FileNotFoundException, CantCreateFileException {
        return assetFactoryManager.getAssetFactoryResource(resource);
    }
}
