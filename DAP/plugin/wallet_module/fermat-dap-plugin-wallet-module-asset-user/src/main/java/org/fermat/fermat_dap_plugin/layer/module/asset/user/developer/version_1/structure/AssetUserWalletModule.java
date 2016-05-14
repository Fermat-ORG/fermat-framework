package org.fermat.fermat_dap_plugin.layer.module.asset.user.developer.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.fermat_api.layer.modules.ModuleManagerImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetAssetNegotiationsException;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetUserException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserGroupException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantGetAssetRedeemPointActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import org.fermat.fermat_dap_api.layer.dap_funds_transaction.asset_buyer.exceptions.CantProcessBuyingTransactionException;
import org.fermat.fermat_dap_api.layer.dap_funds_transaction.asset_buyer.interfaces.AssetBuyerManager;
import org.fermat.fermat_dap_api.layer.dap_funds_transaction.asset_seller.exceptions.CantStartAssetSellTransactionException;
import org.fermat.fermat_dap_api.layer.dap_funds_transaction.asset_seller.interfaces.AssetSellerManager;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_user.exceptions.CantGetAssetUserIdentitiesException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUser;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactoryManager;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.AssetUserSettings;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_appropriation.interfaces.AssetAppropriationManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions.CantDistributeDigitalAssetsException;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_transfer.exceptions.CantTransferDigitalAssetsException;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_transfer.interfaces.AssetTransferManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteAppropriationTransactionException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.NotEnoughAcceptsException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.TransactionAlreadyStartedException;
import org.fermat.fermat_dap_api.layer.dap_transaction.user_redemption.exceptions.CantRedeemDigitalAssetException;
import org.fermat.fermat_dap_api.layer.dap_transaction.user_redemption.interfaces.UserRedemptionManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWallet;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransaction;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantCreateWalletException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import org.fermat.fermat_dap_plugin.layer.module.asset.user.developer.version_1.AssetUserWalletModulePluginRoot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by franklin on 16/10/15.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.MEDIUM,
        maintainerMail = "nerioindriago@gmail.com",
        createdBy = "franklin",
        layer = Layers.WALLET_MODULE,
        platform = Platforms.DIGITAL_ASSET_PLATFORM,
        plugin = Plugins.ASSET_USER)
public class AssetUserWalletModule extends ModuleManagerImpl<AssetUserSettings> implements AssetUserWalletSubAppModuleManager, Serializable {

    private final AssetUserWalletManager            assetUserWalletManager;
    private final AssetAppropriationManager         assetAppropriationManager;
    private final UserRedemptionManager             userRedemptionManager;
    private final IdentityAssetUserManager          identityAssetUserManager;
    private final AssetTransferManager              assetTransferManager;
    private final ErrorManager                      errorManager;
    private final EventManager                      eventManager;
    private final Broadcaster                       broadcaster;
    private final UUID                              pluginId;
    private final PluginFileSystem                  pluginFileSystem;
    private final ActorAssetRedeemPointManager      actorAssetRedeemPointManager;
    private final WalletManagerManager              walletMiddlewareManager;
    private final AssetFactoryManager               assetFactoryManager;
    private final ActorAssetUserManager             actorAssetUserManager;
    private final AssetBuyerManager                 assetBuyerManager;
    private final AssetSellerManager                assetSellerManager;
    private final BitcoinWalletManager              bitcoinWalletManager;
    private final AssetUserWalletModulePluginRoot   assetUserWalletModulePluginRoot;

    private BlockchainNetworkType selectedNetwork;
    AssetUserSettings settings = null;
    private AssetUserWallet wallet;
    String publicKeyApp;

    private List<ActorAssetUser> selectedUsersToDeliver;

    {
        selectedUsersToDeliver = new ArrayList<>();
    }

    private SettingsManager<AssetUserSettings> settingsManager;

    public AssetUserWalletModule(AssetUserWalletManager assetUserWalletManager,
                                 AssetAppropriationManager assetAppropriationManager,
                                 UserRedemptionManager userRedemptionManager,
                                 IdentityAssetUserManager identityAssetUserManager,
                                 AssetTransferManager assetTransferManager,
                                 ErrorManager errorManager,
                                 UUID pluginId,
                                 PluginFileSystem pluginFileSystem,
                                 Broadcaster broadcaster,
                                 EventManager eventManager,
                                 ActorAssetRedeemPointManager actorAssetRedeemPointManager,
                                 WalletManagerManager walletMiddlewareManager,
                                 AssetFactoryManager assetFactoryManager,
                                 ActorAssetUserManager actorAssetUserManager,
                                 AssetBuyerManager assetBuyerManager,
                                 AssetSellerManager assetSellerManager,
                                 BitcoinWalletManager bitcoinWalletManager,
                                 AssetUserWalletModulePluginRoot assetUserWalletModulePluginRoot) {

        super(pluginFileSystem, pluginId);

        this.assetUserWalletManager             = assetUserWalletManager;
        this.assetAppropriationManager          = assetAppropriationManager;
        this.userRedemptionManager              = userRedemptionManager;
        this.identityAssetUserManager           = identityAssetUserManager;
        this.assetTransferManager               = assetTransferManager;
        this.errorManager                       = errorManager;
        this.broadcaster                        = broadcaster;
        this.eventManager                       = eventManager;
        this.pluginId                           = pluginId;
        this.pluginFileSystem                   = pluginFileSystem;
        this.actorAssetRedeemPointManager       = actorAssetRedeemPointManager;
        this.walletMiddlewareManager            = walletMiddlewareManager;
        this.assetFactoryManager                = assetFactoryManager;
        this.actorAssetUserManager              = actorAssetUserManager;
        this.assetBuyerManager                  = assetBuyerManager;
        this.assetSellerManager                 = assetSellerManager;
        this.bitcoinWalletManager               = bitcoinWalletManager;
        this.assetUserWalletModulePluginRoot    = assetUserWalletModulePluginRoot;
    }

    public List<AssetUserWalletList> getAssetUserWalletBalances(String publicKey, BlockchainNetworkType networkType) throws CantLoadWalletException {
        try {
            return assetUserWalletManager.loadAssetUserWallet(publicKey, networkType).getBalance().getAssetUserWalletBalances();
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantLoadWalletException("Error load Wallet Balances Book", exception, "Method: getAssetUserWalletBalancesBook", "Class: AssetUserWalletModule");
        }
    }

    public Map<ActorAssetIssuer, AssetUserWalletList> getWalletBalanceByIssuer(String publicKey, BlockchainNetworkType networkType) throws CantLoadWalletException {
        try {
            return assetUserWalletManager.loadAssetUserWallet(publicKey, networkType).getBalance().getWalletBalanceByIssuer();
        } catch (Exception exception) {
            throw new CantLoadWalletException("Error load Wallet Balances Book", exception, "Method: getAssetUserWalletBalancesBook", "Class: AssetUserWalletModule");
        }
    }

    public void redeemAssetToRedeemPoint(DigitalAsset asset, String walletPublicKey, List<ActorAssetRedeemPoint> actorAssetRedeemPoint, int assetsAmount, BlockchainNetworkType networkType) throws CantRedeemDigitalAssetException, NotEnoughAcceptsException {
        String context = "Asset Public Key: " + asset.getGenesisAddress() + " - ActorAssetRedeemPoint: " + actorAssetRedeemPoint;
        try {
            if (actorAssetRedeemPoint.isEmpty()) {
                throw new CantRedeemDigitalAssetException(null, context, "THE REDEEM POINT LIST IS EMPTY.");
            }
            walletPublicKey = WalletUtilities.WALLET_PUBLIC_KEY; //TODO: Solo para la prueba del Redemption
            HashMap<DigitalAssetMetadata, ActorAssetRedeemPoint> hashMap = createRedemptionMap(walletPublicKey, asset, actorAssetRedeemPoint, assetsAmount, networkType);
            userRedemptionManager.redeemAssetToRedeemPoint(hashMap, walletPublicKey);
        } catch (CantGetDigitalAssetFromLocalStorageException | CantLoadWalletException | CantGetTransactionsException | FileNotFoundException | CantCreateFileException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantRedeemDigitalAssetException(exception, context, null);
        }
    }


    private HashMap<DigitalAssetMetadata, ActorAssetRedeemPoint> createRedemptionMap(String walletPublicKey, DigitalAsset asset, List<ActorAssetRedeemPoint> redeemPoints, int assetsAmount, BlockchainNetworkType networkType) throws CantGetTransactionsException, FileNotFoundException, CantCreateFileException, CantLoadWalletException, CantGetDigitalAssetFromLocalStorageException, NotEnoughAcceptsException {
        String context = "Asset Public Key: " + asset.getGenesisAddress() + " - BTC Wallet Public Key: " + walletPublicKey + " - RedeemPoints: " + redeemPoints;
        HashMap<DigitalAssetMetadata, ActorAssetRedeemPoint> hashMap = new HashMap<>();
        AssetUserWallet wallet = assetUserWalletManager.loadAssetUserWallet(walletPublicKey, networkType);
        List<AssetUserWalletTransaction> availableTransactions = wallet.getAllAvailableTransactions(asset.getPublicKey());
        if (redeemPoints.size() > availableTransactions.size())
            throw new NotEnoughAcceptsException(null, context, "WE DON'T HAVE ENOUGH ASSETS!!");
        int assetsPerUser = assetsAmount / redeemPoints.size();
        for (int j = 0, i = 0; j < redeemPoints.size(); j++) {
            for (int k = 0; k < assetsPerUser; i++, k++) {
                hashMap.put(wallet.getDigitalAssetMetadata(availableTransactions.get(i).getGenesisTransaction()), redeemPoints.get(j));
            }
        }
        return hashMap;
    }


    public void transferAsset(DigitalAsset asset, String walletPublicKey, List<ActorAssetUser> actorAssetUsers, int assetsAmount, BlockchainNetworkType networkType) throws CantTransferDigitalAssetsException, CantGetTransactionsException, CantCreateFileException, FileNotFoundException, CantLoadWalletException {
        try {
            String context = "Asset PublicKey: " + asset.getGenesisAddress().getAddress() + " - Wallet PublicKey: " + walletPublicKey + " - Users: " + actorAssetUsers.toString();
            if (actorAssetUsers.isEmpty()) {
                throw new CantDistributeDigitalAssetsException(null, context, "THE USER LIST IS EMPTY.");
            }
            System.out.println("******* ASSET DISTRIBUTION TEST (Init Transfer)******");
            walletPublicKey = WalletUtilities.WALLET_PUBLIC_KEY; //TODO: DELETE HARDCODE
            HashMap<DigitalAssetMetadata, ActorAssetUser> hashMap = createTransferMap(walletPublicKey, asset, actorAssetUsers, assetsAmount, networkType);
            assetTransferManager.transferAssets(hashMap, walletPublicKey);

        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantLoadWalletException("Error transfer Assets", exception, "Method: Transfer Assets", "Class: AssetIssuerWalletModuleManager");
        }
    }


    private HashMap<DigitalAssetMetadata, ActorAssetUser> createTransferMap(String walletPublicKey, DigitalAsset asset, List<ActorAssetUser> actorAssetUsers, int assetsAmount, BlockchainNetworkType networkType) throws CantGetTransactionsException, FileNotFoundException, CantCreateFileException, CantLoadWalletException, CantGetDigitalAssetFromLocalStorageException {
        HashMap<DigitalAssetMetadata, ActorAssetUser> hashMap = new HashMap<>();
        AssetUserWallet wallet = assetUserWalletManager.loadAssetUserWallet(walletPublicKey, networkType);
        List<AssetUserWalletTransaction> transactions = wallet.getAllAvailableTransactions(asset.getPublicKey());
        if (assetsAmount > transactions.size())
            throw new IllegalStateException("WE DON'T HAVE ENOUGH ASSETS!!");

        int assetsPerUser = assetsAmount / actorAssetUsers.size();
        for (int j = 0, i = 0; j < actorAssetUsers.size(); j++) {
            for (int k = 0; k < assetsPerUser; i++, k++) {
                hashMap.put(wallet.getDigitalAssetMetadata(transactions.get(i).getGenesisTransaction()), actorAssetUsers.get(j));
            }
        }
        return hashMap;
    }

    public void appropriateAsset(DigitalAsset asset, String bitcoinWalletPublicKey, BlockchainNetworkType networkType) throws CantExecuteAppropriationTransactionException, TransactionAlreadyStartedException, NotEnoughAcceptsException {
        String context = "Asset Public Key: " + asset.getGenesisAddress() + " - BTC Wallet Public Key: " + bitcoinWalletPublicKey;
        try {
            AssetUserWallet wallet = assetUserWalletManager.loadAssetUserWallet(WalletUtilities.WALLET_PUBLIC_KEY, networkType);
            List<AssetUserWalletTransaction> transactions = wallet.getAllAvailableTransactions(asset.getPublicKey());
            if (transactions.isEmpty())
                throw new NotEnoughAcceptsException(null, context, "There are no assets available to appropriate!!");

            DigitalAssetMetadata assetMetadata = wallet.getDigitalAssetMetadata(transactions.get(0).getGenesisTransaction());
            assetAppropriationManager.appropriateAsset(assetMetadata, WalletUtilities.WALLET_PUBLIC_KEY, bitcoinWalletPublicKey, networkType);
        } catch (CantGetDigitalAssetFromLocalStorageException | CantGetTransactionsException | CantLoadWalletException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantExecuteAppropriationTransactionException(exception, context, null);
        }
    }

    public List<IdentityAssetUser> getActiveIdentities() {

        try {
            return identityAssetUserManager.getIdentityAssetUsersFromCurrentDeviceUser();
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public List<AssetUserWalletList> getAssetUserWalletBalances(String publicKey) throws CantLoadWalletException {
        try {
            return this.getAssetUserWalletBalances(publicKey, selectedNetwork);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantLoadWalletException(e);
        }
    }

    @Override
    public Map<ActorAssetIssuer, AssetUserWalletList> getAssetUserWalletBalancesByIssuer(String publicKey) throws CantLoadWalletException {
        try {
            return this.getWalletBalanceByIssuer(publicKey, selectedNetwork);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantLoadWalletException(e);
        }
    }

    @Override
    public List<ActorAssetRedeemPoint> getAllActorAssetRedeemPointConnected() throws CantGetAssetRedeemPointActorsException {
        try {
            return actorAssetRedeemPointManager.getAllRedeemPointActorConnected(getSelectedNetwork());
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.REDEEM_POINT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetAssetRedeemPointActorsException(e);
        }
    }

    @Override
    public List<ActorAssetRedeemPoint> getRedeemPointsConnectedForAsset(String assetPublicKey) throws CantGetAssetRedeemPointActorsException {
        String context = "Asset PK: " + assetPublicKey;
        try {
            loadAssetUserWallet(WalletUtilities.WALLET_PUBLIC_KEY);
            DigitalAsset digitalAsset = wallet.getDigitalAsset(assetPublicKey);
            return actorAssetRedeemPointManager.getAllRedeemPointActorConnectedForIssuer(digitalAsset.getIdentityAssetIssuer().getPublicKey(), getSelectedNetwork());
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.REDEEM_POINT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetAssetRedeemPointActorsException(CantGetAssetRedeemPointActorsException.DEFAULT_MESSAGE, e, context, null);
        }
    }

    @Override
    public AssetUserWallet loadAssetUserWallet(String walletPublicKey) throws CantLoadWalletException {
        try {
            wallet = assetUserWalletManager.loadAssetUserWallet(walletPublicKey, selectedNetwork);
            return wallet;
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantLoadWalletException(e);
        }
    }

    @Override
    public void createAssetUserWallet(String walletPublicKey) throws CantCreateWalletException {
        try {
            assetUserWalletManager.createAssetUserWallet(walletPublicKey, selectedNetwork);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateWalletException(e);
        }
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
    public void redeemAssetToRedeemPoint(DigitalAsset asset, String walletPublicKey, List<ActorAssetRedeemPoint> actorAssetRedeemPoints, int assetAmount) throws CantRedeemDigitalAssetException {
        try {
            this.redeemAssetToRedeemPoint(asset, walletPublicKey, actorAssetRedeemPoints, assetAmount, selectedNetwork);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.USER_REDEMPTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRedeemDigitalAssetException(e);
        }
    }

    @Override
    public void appropriateAsset(DigitalAsset asset, String bitcoinWalletPublicKey) throws CantExecuteAppropriationTransactionException {
        try {
            List<InstalledWallet> installedWallets = walletMiddlewareManager.getInstalledWallets();
            if (installedWallets.isEmpty()) {
                System.out.println("NO INSTALLED WALLETS, CANNOT PROCEED.");
                return;
            }
            //TODO REMOVE HARDCODE
            InstalledWallet installedWallet = installedWallets.get(0);
            this.appropriateAsset(asset, installedWallet.getWalletPublicKey(), selectedNetwork);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.ASSET_APPROPRIATION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExecuteAppropriationTransactionException(e);
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
        if (networkType == null) {
            selectedNetwork = BlockchainNetworkType.getDefaultBlockchainNetworkType();
        } else {
            selectedNetwork = networkType;
        }
    }

    @Override
    public BlockchainNetworkType getSelectedNetwork() {
        if (selectedNetwork == null) {
            try {
                if (settings == null) {
                    settingsManager = getSettingsManager();
                }
                settings = settingsManager.loadAndGetSettings(WalletsPublicKeys.DAP_USER_WALLET.getCode());
                selectedNetwork = settings.getBlockchainNetwork().get(settings.getBlockchainNetworkPosition());
            } catch (CantGetSettingsException e) {
                e.printStackTrace();
            } catch (SettingsNotFoundException e) {
                //TODO: Only enter while the Active Actor Wallet is not open.
                selectedNetwork = BlockchainNetworkType.getDefaultBlockchainNetworkType();
//                e.printStackTrace();
            }
        }
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
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetAssetUserActorsException(e);
        }
    }

    @Override
    public List<ActorAssetUserGroup> getAssetUserGroupsList() throws CantGetAssetUserGroupException {
        try {
            return actorAssetUserManager.getAssetUserGroupsList();
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetAssetUserGroupException(e);
        }
    }

    @Override
    public List<ActorAssetUser> getListActorAssetUserByGroups(String groupName) throws CantGetAssetUserActorsException {
        try {
            return actorAssetUserManager.getListActorAssetUserByGroups(groupName, selectedNetwork);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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
        try {
            for (ActorAssetUser user : getListActorAssetUserByGroups(group.getGroupName())) {
                selectedUsersToDeliver.add(user);
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetAssetUserActorsException(e);
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
    public void transferAssets(DigitalAsset asset, String walletPublicKey, int assetsAmount) throws CantTransferDigitalAssetsException {
        try {
            this.transferAsset(asset, walletPublicKey, selectedUsersToDeliver, assetsAmount, selectedNetwork);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.ASSET_TRANSFER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantTransferDigitalAssetsException(e);
        }
    }

    @Override
    public ActorAssetUser getActorByPublicKey(String publicKey) throws CantGetAssetUserActorsException {
        try {
            return this.getActorByPublicKey(publicKey);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetAssetUserActorsException(e);
        }
    }

    @Override
    public void startSell(ActorAssetUser userToDeliver, long amountPerUnity, long totalAmount, int quantityToBuy, String assetToOffer) throws CantStartAssetSellTransactionException {
        try {
            AssetUserWallet userWallet = loadAssetUserWallet(WalletUtilities.WALLET_PUBLIC_KEY);
            DigitalAsset asset = userWallet.getDigitalAsset(assetToOffer);
            AssetNegotiation negotiation = new AssetNegotiation(totalAmount, amountPerUnity, quantityToBuy, asset, selectedNetwork);
            assetSellerManager.requestAssetSell(userToDeliver, negotiation);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.ASSET_SELLER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantStartAssetSellTransactionException(e);
        }
    }

    @Override
    public List<AssetNegotiation> getPendingAssetNegotiations() throws CantGetAssetNegotiationsException {
        try {
            return assetBuyerManager.getNewNegotiations(selectedNetwork);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.ASSET_BUYER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetAssetNegotiationsException(e);
        }
    }

    /**
     * This method notifies the seller that we've accepted one of its asset and the transaction for this asset can proceed.
     *
     * @param negotiationId {@link UUID} instance that is the {@link AssetNegotiation} ID.
     * @throws CantProcessBuyingTransactionException
     */
    @Override
    public void acceptAsset(UUID negotiationId) throws CantProcessBuyingTransactionException {
        try {
            List<InstalledWallet> installedWallets = walletMiddlewareManager.getInstalledWallets();
            //TODO REMOVE HARDCODE
            InstalledWallet installedWallet = installedWallets.get(0);
            assetBuyerManager.acceptAsset(negotiationId, installedWallet.getWalletPublicKey());
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.ASSET_BUYER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantProcessBuyingTransactionException(e);
        }
    }

    /**
     * This method notifies the seller that we've rejected one of its asset and the transaction for this asset won't proceed.
     *
     * @param negotiationId {@link UUID} instance that is the {@link AssetNegotiation} ID.
     * @throws CantProcessBuyingTransactionException
     */
    @Override
    public void declineAsset(UUID negotiationId) throws CantProcessBuyingTransactionException {
        try {
            assetBuyerManager.declineAsset(negotiationId);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.ASSET_BUYER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantProcessBuyingTransactionException(e);
        }
    }

    @Override
    public long getBitcoinWalletBalance(String walletPublicKey) throws CantLoadWalletsException, CantCalculateBalanceException {
        return bitcoinWalletManager.loadWallet(walletPublicKey).getBalance(BalanceType.AVAILABLE).getBalance(selectedNetwork);
    }

    @Override
    public List<InstalledWallet> getInstallWallets() throws CantListWalletsException {
        return walletMiddlewareManager.getInstalledWallets();
    }

    @Override
    public ActorAssetUser getSellerFromNegotiation(UUID negotiationID) throws CantGetAssetNegotiationsException {
        try {
            return assetBuyerManager.getSellerFromNegotiation(negotiationID);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.ASSET_BUYER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetAssetNegotiationsException(e);
        }
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        try {
            List<IdentityAssetUser> identities = this.getActiveIdentities();
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

        try {
            settingsManager.persistSettings(publicKeyApp, settings);
        } catch (CantPersistSettingsException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            exception.printStackTrace();
        }
    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }
}
