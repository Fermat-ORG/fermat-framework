package com.bitdubai.fermat_dap_plugin.layer.module.asset.user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
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
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantGetAssetNegotiationsException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantGetAssetRedeemPointActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_user.exceptions.CantGetAssetUserIdentitiesException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactoryManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.AssetUserSettings;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.interfaces.AssetAppropriationManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_seller.exceptions.CantStartAssetSellTransactionException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_seller.interfaces.AssetSellerManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_transfer.exceptions.CantTransferDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_transfer.interfaces.AssetTransferManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteAppropriationTransactionException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.NotEnoughAcceptsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.TransactionAlreadyStartedException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.user_redemption.exceptions.CantRedeemDigitalAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.user_redemption.interfaces.UserRedemptionManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_dap_plugin.layer.module.asset.user.developer.bitdubai.version_1.structure.AssetUserWalletModule;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * TODO ADD A LITTLE EXPLANATION ABOUT THE MAIN FUNCTIONALITY OF THE PLUG-IN
 * <p/>
 * Created by Franklin on 07/09/15.
 */
public class AssetUserWalletModulePluginRoot extends AbstractPlugin implements
        AssetUserWalletSubAppModuleManager {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.WALLET, plugin = Plugins.ASSET_USER)
    private AssetUserWalletManager assetUserWalletManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.DIGITAL_ASSET_TRANSACTION, plugin = Plugins.ASSET_APPROPRIATION)
    private AssetAppropriationManager assetAppropriationManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.DIGITAL_ASSET_TRANSACTION, plugin = Plugins.USER_REDEMPTION)
    private UserRedemptionManager userRedemptionManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_USER)
    private ActorAssetUserManager actorAssetUserManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.DIGITAL_ASSET_TRANSACTION, plugin = Plugins.ASSET_TRANSFER)
    private AssetTransferManager assetTransferManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.REDEEM_POINT)
    private ActorAssetRedeemPointManager actorAssetRedeemPointManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.MIDDLEWARE, plugin = Plugins.WALLET_MANAGER)
    private WalletManagerManager walletMiddlewareManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.ASSET_USER)
    IdentityAssetUserManager identityAssetUserManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.MIDDLEWARE, plugin = Plugins.ASSET_FACTORY)
    AssetFactoryManager assetFactoryManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.DIGITAL_ASSET_TRANSACTION, plugin = Plugins.ASSET_SELLER)
    AssetSellerManager assetSellerManager;

    private AssetUserWallet wallet;

    private BlockchainNetworkType selectedNetwork;

    private List<ActorAssetUser> selectedUsersToDeliver;

    {
        selectedUsersToDeliver = new ArrayList<>();
    }

    //TODO MAKE USE OF THE ERROR MANAGER ... Someday, over the rainbow

    private AssetUserWalletModule assetUserWalletModule;

    private SettingsManager<AssetUserSettings> settingsManager;
    AssetUserSettings settings = null;
    String publicKeyApp;

    public AssetUserWalletModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public void start() throws CantStartPluginException {
        try {
            assetUserWalletModule = new AssetUserWalletModule(
                    assetUserWalletManager,
                    assetAppropriationManager,
                    userRedemptionManager,
                    identityAssetUserManager,
                    assetTransferManager
            );
            selectedNetwork = BlockchainNetworkType.getDefaultBlockchainNetworkType();
            System.out.println("******* Asset User Wallet Module Init ******");
            this.serviceStatus = ServiceStatus.STARTED;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        }
    }

    @Override
    public List<AssetUserWalletList> getAssetUserWalletBalances(String publicKey) throws CantLoadWalletException {
        //TODO MAKE USE OF THE ERROR MANAGER
        return assetUserWalletModule.getAssetUserWalletBalances(publicKey, selectedNetwork);
    }

    @Override
    public List<ActorAssetRedeemPoint> getAllActorAssetRedeemPointConnected() throws CantGetAssetRedeemPointActorsException {
        return actorAssetRedeemPointManager.getAllRedeemPointActorConnected();
    }

    @Override
    public List<ActorAssetRedeemPoint> getRedeemPointsConnectedForAsset(String assetPublicKey) throws CantGetAssetRedeemPointActorsException {
        String context = "Asset PK: " + assetPublicKey;
        try {
            loadAssetUserWallet(WalletUtilities.WALLET_PUBLIC_KEY);
            DigitalAsset digitalAsset = wallet.getDigitalAsset(assetPublicKey);
            return actorAssetRedeemPointManager.getAllRedeemPointActorConnectedForIssuer(digitalAsset.getIdentityAssetIssuer().getPublicKey());
        } catch (CantGetDigitalAssetFromLocalStorageException | CantLoadWalletException e) {
            throw new CantGetAssetRedeemPointActorsException(CantGetAssetRedeemPointActorsException.DEFAULT_MESSAGE, e, context, null);
        }
    }

    @Override
    public AssetUserWallet loadAssetUserWallet(String walletPublicKey) throws CantLoadWalletException {
        wallet = assetUserWalletManager.loadAssetUserWallet(walletPublicKey, selectedNetwork);
        return wallet;
    }

    @Override
    public void createAssetUserWallet(String walletPublicKey) throws CantCreateWalletException {
        assetUserWalletManager.createAssetUserWallet(walletPublicKey, selectedNetwork);
    }

    public IdentityAssetUser getActiveAssetUserIdentity() throws CantGetIdentityAssetUserException {
        try {
            return identityAssetUserManager.getIdentityAssetUser();
        } catch (CantGetAssetUserIdentitiesException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetIdentityAssetUserException(e);
        }
    }

    @Override
    public void redeemAssetToRedeemPoint(String digitalAssetPublicKey, String walletPublicKey, List<ActorAssetRedeemPoint> actorAssetRedeemPoints, int assetAmount) throws CantRedeemDigitalAssetException, NotEnoughAcceptsException {
        assetUserWalletModule.redeemAssetToRedeemPoint(digitalAssetPublicKey, walletPublicKey, actorAssetRedeemPoints, assetAmount, selectedNetwork);
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
            assetUserWalletModule.appropriateAsset(digitalAssetPublicKey, installedWallet.getWalletPublicKey(), selectedNetwork);
        } catch (CantListWalletsException e) {
            e.printStackTrace();
        }
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
    public void changeNetworkType(BlockchainNetworkType networkType) {
        if (networkType == null) return; //NOPE
        selectedNetwork = networkType;
    }

    @Override
    public BlockchainNetworkType getSelectedNetwork() {
        return selectedNetwork;
    }

    @Override
    public List<ActorAssetUser> getAllActorUserRegistered() throws CantGetAssetUserActorsException {
        return actorAssetUserManager.getAllAssetUserActorInTableRegistered();
    }

    @Override
    public List<ActorAssetUser> getAllAssetUserActorConnected() throws CantGetAssetUserActorsException {
        try {
            return actorAssetUserManager.getAllAssetUserActorConnected(getSelectedNetwork());
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
            return actorAssetUserManager.getListActorAssetUserByGroups(groupName, selectedNetwork);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetAssetUserActorsException(e);
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
    public void transferAssets(String assetPublicKey, String walletPublicKey, int assetsAmount) throws CantTransferDigitalAssetsException, CantGetTransactionsException, CantCreateFileException, FileNotFoundException, CantLoadWalletException {
        assetUserWalletModule.transferAsset(assetPublicKey, walletPublicKey, selectedUsersToDeliver, assetsAmount, selectedNetwork);
    }

    @Override
    public ActorAssetUser getActorByPublicKey(String publicKey) throws CantAssetUserActorNotFoundException, CantGetAssetUserActorsException {
        return actorAssetUserManager.getActorByPublicKey(publicKey);
    }

    @Override
    public void startSell(ActorAssetUser userToDeliver, long amountPerUnity, long totalAmount, int quantityToBuy, String assetToOffer) throws CantStartAssetSellTransactionException {
        try {
            AssetUserWallet userWallet = loadAssetUserWallet(WalletUtilities.WALLET_PUBLIC_KEY);
            DigitalAsset asset = userWallet.getDigitalAsset(assetToOffer);
            AssetNegotiation negotiation = new AssetNegotiation(totalAmount, amountPerUnity, quantityToBuy, asset, selectedNetwork);
            assetSellerManager.requestAssetSell(userToDeliver, negotiation);
        } catch (Exception e) {
            throw new CantStartAssetSellTransactionException(e);
        }
    }

    @Override
    public SettingsManager getSettingsManager() {
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
            List<IdentityAssetUser> identities = assetUserWalletModule.getActiveIdentities();
            return (identities == null || identities.isEmpty()) ? null : identities.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {
        identityAssetUserManager.createNewIdentityAssetUser(name, profile_img);
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
    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }


    @Override
    public List<AssetNegotiation> getPendingAssetNegotiations(BlockchainNetworkType blockchainNetworkType) throws CantGetAssetNegotiationsException {
        //todo implement
        return null;
    }
}
