package com.bitdubai.fermat_dap_plugin.layer.module.asset.issuer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions.CantDistributeDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.interfaces.AssetDistributionManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteAppropriationTransactionException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.NotEnoughAcceptsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.TransactionAlreadyStartedException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.issuer_appropriation.interfaces.IssuerAppropriationManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransaction;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

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
                                          PluginFileSystem pluginFileSystem) {
        this.assetIssuerWalletManager = assetIssuerWalletManager;
        this.actorAssetUserManager = actorAssetUserManager;
        this.assetDistributionManager = assetDistributionManager;
        this.identityAssetIssuerManager = identityAssetIssuerManager;
        this.pluginId = pluginId;
        this.pluginFileSystem = pluginFileSystem;
        this.issuerAppropriationManager = issuerAppropriationManager;
    }

    public List<AssetIssuerWalletList> getAssetIssuerWalletBalances(String publicKey) throws CantLoadWalletException {
        try {
            return assetIssuerWalletManager.loadAssetIssuerWallet(publicKey).getBalance().getAssetIssuerWalletBalances();
        } catch (Exception exception) {
            throw new CantLoadWalletException("Error load Wallet Balances Book", exception, "Method: getAssetIssuerWalletBalancesBook", "Class: AssetIssuerWalletModuleManager");
        }
    }

    public void distributionAssets(String assetPublicKey, String walletPublicKey, List<ActorAssetUser> actorAssetUsers) throws CantDistributeDigitalAssetsException, CantGetTransactionsException, CantCreateFileException, FileNotFoundException, CantLoadWalletException {
        try {
            String context = "Asset PublicKey: " + assetPublicKey + " - Wallet PublicKey: " + walletPublicKey + " - Users: " + actorAssetUsers.toString();
            if (actorAssetUsers.isEmpty()) {
                throw new CantDistributeDigitalAssetsException(null, context, "THE USER LIST IS EMPTY.");
            }
            System.out.println("******* ASSET DISTRIBUTION TEST (Init Distribution)******");
            walletPublicKey = "walletPublicKeyTest"; //TODO: Solo para la prueba del Distribution
            HashMap<DigitalAssetMetadata, ActorAssetUser> hashMap = createMapDistribution(walletPublicKey, assetPublicKey, actorAssetUsers);
            assetDistributionManager.distributeAssets(hashMap, walletPublicKey);

        } catch (Exception exception) {
            throw new CantLoadWalletException("Error distribution Assets", exception, "Method: distributionAssets", "Class: AssetIssuerWalletModuleManager");
        }
    }

    public void appropriateAsset(String digitalAssetPublicKey, String bitcoinWalletPublicKey) throws CantExecuteAppropriationTransactionException, TransactionAlreadyStartedException, NotEnoughAcceptsException {
        String context = "Asset Public Key: " + digitalAssetPublicKey + " - BTC Wallet Public Key: " + bitcoinWalletPublicKey;
        try {
            AssetIssuerWallet wallet = assetIssuerWalletManager.loadAssetIssuerWallet("walletPublicKeyTest");
            List<AssetIssuerWalletTransaction> transactions = wallet.getAvailableTransactions(digitalAssetPublicKey);
            if (transactions.isEmpty())
                throw new NotEnoughAcceptsException(null, context, "We don't have any asset to appropriate.");
            for (AssetIssuerWalletTransaction transaction : transactions) {
                DigitalAssetMetadata assetMetadata = wallet.getDigitalAssetMetadata(transaction.getTransactionHash());
                issuerAppropriationManager.appropriateAsset(assetMetadata, "walletPublicKeyTest", bitcoinWalletPublicKey);
            }
        } catch (CantGetDigitalAssetFromLocalStorageException | CantLoadWalletException | CantGetTransactionsException e) {
            throw new CantExecuteAppropriationTransactionException(e, context, null);
        }
    }

    public List<ActorAssetUser> getAllAssetUserActorConnected() throws CantGetAssetUserActorsException {
        try {
            return actorAssetUserManager.getAllAssetUserActorConnected();
        } catch (Exception exception) {
            throw new CantGetAssetUserActorsException("Error Get Actor Connected", exception, "Method: getAllAssetUserActorConnected", "Class: AssetIssuerWalletModuleManager");
        }
    }

    public List<AssetIssuerWalletTransaction> getTransactionsAssetAll(String walletPublicKey, String assetPublicKey) throws CantGetTransactionsException {
        try {
            return assetIssuerWalletManager.loadAssetIssuerWallet(walletPublicKey).getTransactionsAssetAll(assetPublicKey);
        } catch (Exception exception) {
            throw new CantGetTransactionsException("Error Error load Wallet Asset Transaction", exception, "Method: getTransactionsAssetAll", "Class: AssetIssuerWalletModuleManager");
        }
    }

    private HashMap<DigitalAssetMetadata, ActorAssetUser> createMapDistribution(String walletPublicKey, String assetPublicKey, List<ActorAssetUser> actorAssetUsers) throws CantGetTransactionsException, FileNotFoundException, CantCreateFileException, CantLoadWalletException, CantGetDigitalAssetFromLocalStorageException {
        HashMap<DigitalAssetMetadata, ActorAssetUser> hashMap = new HashMap<>();
        AssetIssuerWallet wallet = assetIssuerWalletManager.loadAssetIssuerWallet(walletPublicKey);
        List<AssetIssuerWalletTransaction> transactions = wallet.getAvailableTransactions(assetPublicKey);
        if (actorAssetUsers.size() > transactions.size())
            throw new IllegalStateException("WE DON'T HAVE ENOUGH ASSETS!!");
        for (int i = 0; i < actorAssetUsers.size(); i++) {
            hashMap.put(wallet.getDigitalAssetMetadata(transactions.get(i).getTransactionHash()), actorAssetUsers.get(i));
        }
        return hashMap;
    }

    public List<IdentityAssetIssuer> getActiveIdentities() {

        try {
            return identityAssetIssuerManager.getIdentityAssetIssuersFromCurrentDeviceUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
