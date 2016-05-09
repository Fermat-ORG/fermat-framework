package org.fermat.fermat_dap_plugin.layer.module.asset.issuer.developer.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuerManager;
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
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 06/10/15.
 */
public class AssetIssuerWalletModuleManager {
    AssetIssuerWalletManager assetIssuerWalletManager;
    ActorAssetUserManager actorAssetUserManager;
    IdentityAssetIssuerManager identityAssetIssuerManager;
    AssetDistributionManager assetDistributionManager;
    UUID pluginId;
    PluginFileSystem pluginFileSystem;
    ErrorManager errorManager;
    IssuerAppropriationManager issuerAppropriationManager;

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
                                          UUID pluginId,
                                          PluginFileSystem pluginFileSystem,
                                          ErrorManager errorManager) {
        this.assetIssuerWalletManager = assetIssuerWalletManager;
        this.actorAssetUserManager = actorAssetUserManager;
        this.assetDistributionManager = assetDistributionManager;
        this.identityAssetIssuerManager = identityAssetIssuerManager;
        this.pluginId = pluginId;
        this.pluginFileSystem = pluginFileSystem;
        this.errorManager = errorManager;
        this.issuerAppropriationManager = issuerAppropriationManager;
    }

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
}
