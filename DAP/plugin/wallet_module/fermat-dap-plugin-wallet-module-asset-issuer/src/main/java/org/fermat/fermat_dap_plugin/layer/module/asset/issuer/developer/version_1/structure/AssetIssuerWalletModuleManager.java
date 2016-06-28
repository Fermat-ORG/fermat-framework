package org.fermat.fermat_dap_plugin.layer.module.asset.issuer.developer.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.dmp_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer.modules.ModuleManagerImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
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
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.NotEnoughAcceptsException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.TransactionAlreadyStartedException;
import org.fermat.fermat_dap_api.layer.dap_transaction.issuer_appropriation.interfaces.IssuerAppropriationManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletList;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransaction;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetStatistic;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantCreateWalletException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import org.fermat.fermat_dap_plugin.layer.module.asset.issuer.developer.version_1.AssetIssuerWalletModulePluginRoot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 06/10/15.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.MEDIUM,
        maintainerMail = "nerioindriago@gmail.com",
        createdBy = "franklin",
        layer = Layers.WALLET_MODULE,
        platform = Platforms.DIGITAL_ASSET_PLATFORM,
        plugin = Plugins.ASSET_ISSUER)
public class AssetIssuerWalletModuleManager extends ModuleManagerImpl<AssetIssuerSettings> implements AssetIssuerWalletSupAppModuleManager, Serializable {

    private final AssetIssuerWalletManager assetIssuerWalletManager;
    private final ActorAssetUserManager actorAssetUserManager;
    private final IdentityAssetIssuerManager identityAssetIssuerManager;
    private final AssetDistributionManager assetDistributionManager;
    private final IssuerAppropriationManager issuerAppropriationManager;
    private final AssetFactoryManager assetFactoryManager;
    private final WalletManagerManager walletMiddlewareManager;
    private final ErrorManager errorManager;
    //    private final PluginFileSystem                  pluginFileSystem;
//    private final UUID                              pluginId;
    private final EventManager eventManager;
    private final Broadcaster broadcaster;
    private final AssetIssuerWalletModulePluginRoot assetIssuerWalletModulePluginRoot;

    private BlockchainNetworkType selectedNetwork;
    //    private SettingsManager<AssetIssuerSettings> settingsManager;
    AssetIssuerSettings settings = null;
    private boolean showUsersOutsideGroup;
    String publicKeyApp;
    private AssetIssuerWallet wallet;

    private List<ActorAssetUser> selectedUsersToDeliver;

    {
        selectedUsersToDeliver = new ArrayList<>();
    }

    /**
     * constructor
     *
     * @param assetIssuerWalletManager
     */
    public AssetIssuerWalletModuleManager(AssetIssuerWalletManager assetIssuerWalletManager,
                                          ActorAssetUserManager actorAssetUserManager,
                                          AssetDistributionManager assetDistributionManager,
                                          IssuerAppropriationManager issuerAppropriationManager,
                                          IdentityAssetIssuerManager identityAssetIssuerManager,
                                          AssetFactoryManager assetFactoryManager,
                                          WalletManagerManager walletMiddlewareManager,
                                          UUID pluginId,
                                          PluginFileSystem pluginFileSystem,
                                          Broadcaster broadcaster,
                                          ErrorManager errorManager,
                                          EventManager eventManager,
                                          AssetIssuerWalletModulePluginRoot assetIssuerWalletModulePluginRoot) {

        super(pluginFileSystem, pluginId);

        this.assetIssuerWalletManager = assetIssuerWalletManager;
        this.actorAssetUserManager = actorAssetUserManager;
        this.assetDistributionManager = assetDistributionManager;
        this.issuerAppropriationManager = issuerAppropriationManager;
        this.identityAssetIssuerManager = identityAssetIssuerManager;
        this.assetFactoryManager = assetFactoryManager;
        this.walletMiddlewareManager = walletMiddlewareManager;
//        this.pluginId                           = pluginId;
//        this.pluginFileSystem                   = pluginFileSystem;
        this.broadcaster = broadcaster;
        this.errorManager = errorManager;
        this.eventManager = eventManager;
        this.assetIssuerWalletModulePluginRoot = assetIssuerWalletModulePluginRoot;
    }

//    public final void initialize() throws CantInitializeAssetIssuerWalletManagerException {
//
//    }

    public List<AssetIssuerWalletList> getAssetIssuerWalletBalances(String publicKey, BlockchainNetworkType networkType) throws CantLoadWalletException {
        try {
            return assetIssuerWalletManager.loadAssetIssuerWallet(publicKey, networkType).getBalance().getAssetIssuerWalletBalances();
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantLoadWalletException("Error load Wallet Balances Book", exception, "Method: getAssetIssuerWalletBalancesBook", "Class: AssetIssuerWalletModuleManager");
        }
    }

    public void distributionAssets(String assetPublicKey, String walletPublicKey, List<ActorAssetUser> actorAssetUsers, int assetsAmount, BlockchainNetworkType networkType) throws CantDistributeDigitalAssetsException, CantGetTransactionsException, CantCreateFileException, FileNotFoundException, CantLoadWalletException {
        try {
            String context = "Asset PublicKey: " + assetPublicKey + " - Wallet PublicKey: " + walletPublicKey + " - Users: " + actorAssetUsers.toString();
            if (actorAssetUsers.isEmpty()) {
                throw new CantDistributeDigitalAssetsException(null, context, "THE USER LIST IS EMPTY.");
            }
            System.out.println("******* ASSET DISTRIBUTION TEST (Init Distribution)******");
            HashMap<DigitalAssetMetadata, ActorAssetUser> hashMap = createMapDistribution(walletPublicKey, assetPublicKey, actorAssetUsers, assetsAmount, networkType);
            assetDistributionManager.distributeAssets(hashMap, walletPublicKey);

        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantLoadWalletException("Error distribution Assets", exception, "Method: distributionAssets", "Class: AssetIssuerWalletModuleManager");
        }
    }

    public void appropriateAsset(String digitalAssetPublicKey, String bitcoinWalletPublicKey, BlockchainNetworkType networkType) throws CantExecuteAppropriationTransactionException, TransactionAlreadyStartedException, NotEnoughAcceptsException {
        String context = "Asset Public Key: " + digitalAssetPublicKey + " - BTC Wallet Public Key: " + bitcoinWalletPublicKey;
        try {
            AssetIssuerWallet wallet = assetIssuerWalletManager.loadAssetIssuerWallet(WalletUtilities.WALLET_PUBLIC_KEY, networkType);
            List<AssetIssuerWalletTransaction> transactions = wallet.getAvailableTransactions(digitalAssetPublicKey);
            if (transactions.isEmpty())
                throw new NotEnoughAcceptsException(null, context, "We don't have any asset to appropriate.");
            for (AssetIssuerWalletTransaction transaction : transactions) {
                DigitalAssetMetadata assetMetadata = wallet.getDigitalAssetMetadata(transaction.getTransactionHash());
                issuerAppropriationManager.appropriateAsset(assetMetadata, WalletUtilities.WALLET_PUBLIC_KEY, bitcoinWalletPublicKey, networkType);
            }
        } catch (CantGetDigitalAssetFromLocalStorageException | CantLoadWalletException | CantGetTransactionsException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantExecuteAppropriationTransactionException(exception, context, null);
        }
    }

    public List<ActorAssetUser> getAllAssetUserActorConnected(BlockchainNetworkType blockchainNetworkType) throws CantGetAssetUserActorsException {
        try {
            return actorAssetUserManager.getAllAssetUserActorConnected(blockchainNetworkType);
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantGetAssetUserActorsException("Error Get Actor Connected", exception, "Method: getAllAssetUserActorConnected", "Class: AssetIssuerWalletModuleManager");
        }
    }

    public List<AssetIssuerWalletTransaction> getTransactionsAssetAll(String walletPublicKey, String assetPublicKey, BlockchainNetworkType networkType) throws CantGetTransactionsException {
        try {
            return assetIssuerWalletManager.loadAssetIssuerWallet(walletPublicKey, networkType).getTransactionsAssetAll(assetPublicKey);
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantGetTransactionsException("Error Error load Wallet Asset Transaction", exception, "Method: getTransactionsAssetAll", "Class: AssetIssuerWalletModuleManager");
        }
    }

    private HashMap<DigitalAssetMetadata, ActorAssetUser> createMapDistribution(String walletPublicKey, String assetPublicKey, List<ActorAssetUser> actorAssetUsers, int assetsAmount, BlockchainNetworkType networkType) throws CantGetTransactionsException, FileNotFoundException, CantCreateFileException, CantLoadWalletException, CantGetDigitalAssetFromLocalStorageException {
        HashMap<DigitalAssetMetadata, ActorAssetUser> hashMap = new HashMap<>();
        AssetIssuerWallet wallet = assetIssuerWalletManager.loadAssetIssuerWallet(walletPublicKey, networkType);
        List<AssetIssuerWalletTransaction> transactions = wallet.getAvailableTransactions(assetPublicKey);
        if (assetsAmount > transactions.size())
            throw new IllegalStateException("WE DON'T HAVE ENOUGH ASSETS!!");

        int assetsPerUser = assetsAmount / actorAssetUsers.size();
        for (int j = 0, i = 0; j < actorAssetUsers.size(); j++) {
            for (int k = 0; k < assetsPerUser; i++, k++) {
                hashMap.put(wallet.getDigitalAssetMetadata(transactions.get(i).getTransactionHash()), actorAssetUsers.get(j));
            }
        }
        return hashMap;
    }

    public List<IdentityAssetIssuer> getActiveIdentities() {

        try {
            return identityAssetIssuerManager.getIdentityAssetIssuersFromCurrentDeviceUser();
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            exception.printStackTrace();
        }
        return null;
    }


    @Override
    public List<AssetIssuerWalletList> getAssetIssuerWalletBalances(String publicKey) throws CantLoadWalletException {
        try {
            return this.getAssetIssuerWalletBalances(publicKey, selectedNetwork);
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
            return this.getAllAssetUserActorConnected(selectedNetwork);
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
        this.distributionAssets(assetPublicKey, walletPublicKey, selectedUsersToDeliver, assetsAmount, selectedNetwork);
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
            this.appropriateAsset(digitalAssetPublicKey, installedWallet.getWalletPublicKey(), selectedNetwork);
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
    public byte[] getAssetFactoryResource(Resource resource) throws FileNotFoundException, CantCreateFileException, CantGetResourcesException {
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
    public BlockchainNetworkType getSelectedNetwork() {
//        if (selectedNetwork == null) {
//            try {
//                if (settings == null) {
//                    settingsManager = getSettingsManager();
//                }
//                settings = settingsManager.loadAndGetSettings(WalletsPublicKeys.DAP_ISSUER_WALLET.getCode());
//                selectedNetwork = settings.getBlockchainNetwork().get(settings.getBlockchainNetworkPosition());
//            } catch (CantGetSettingsException exception) {
//                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
//                exception.printStackTrace();
//            } catch (SettingsNotFoundException e) {
//                //TODO: Only enter while the Active Actor Wallet is not open.
//                selectedNetwork = BlockchainNetworkType.getDefaultBlockchainNetworkType();
////                e.printStackTrace();
//            }
//        }
        return selectedNetwork;
    }

    @Override
    public List<AssetIssuerWalletTransaction> getTransactionsForDisplay(String walletPublicKey, String assetPublicKey) throws CantGetTransactionsException, CantLoadWalletException {
        try {
            return assetIssuerWalletManager.loadAssetIssuerWallet(walletPublicKey, selectedNetwork).getTransactionsForDisplay(assetPublicKey);
        } catch (CantLoadWalletException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantLoadWalletException("Error load Wallet", exception, "Method: getTransactionsForDisplay", "Class: AssetIssuerWalletModuleManager");
        } catch (CantGetTransactionsException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantGetTransactionsException("Error loading transactions for display in wallet", exception, "Method: getTransactionsForDisplay", "Class: AssetIssuerWalletModuleManager");
        }
    }

    @Override
    public Date assetLastTransaction(String walletPublicKey, String assetPublicKey) throws CantGetTransactionsException, CantLoadWalletException {
        try {
            return assetIssuerWalletManager.loadAssetIssuerWallet(walletPublicKey, selectedNetwork).assetLastTransaction(assetPublicKey);
        } catch (CantLoadWalletException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantLoadWalletException("Error load Wallet", exception, "Method: getTransactionsForDisplay", "Class: AssetIssuerWalletModuleManager");
        } catch (CantGetTransactionsException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantGetTransactionsException("Error loading transactions for display in wallet", exception, "Method: getTransactionsForDisplay", "Class: AssetIssuerWalletModuleManager");
        }
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        try {
            List<IdentityAssetIssuer> identities = this.getActiveIdentities();
            return (identities == null || identities.isEmpty()) ? null : this.getActiveIdentities().get(0);
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {
        identityAssetIssuerManager.createNewIdentityAssetIssuer(
                name,
                profile_img,
                identityAssetIssuerManager.getAccuracyDataDefault(),
                identityAssetIssuerManager.getFrequencyDataDefault());
    }

    @Override
    public void setAppPublicKey(String publicKey) {
        this.publicKeyApp = publicKey;
    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }
}
