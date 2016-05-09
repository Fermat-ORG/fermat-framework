package org.fermat.fermat_dap_plugin.layer.module.asset.user.developer.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUser;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUserManager;
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
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by franklin on 16/10/15.
 */
public class AssetUserWalletModule {
    private final AssetUserWalletManager assetUserWalletManager;
    private final AssetAppropriationManager assetAppropriationManager;
    private final UserRedemptionManager userRedemptionManager;
    private final IdentityAssetUserManager identityAssetUserManager;
    private final AssetTransferManager assetTransferManager;
    private ErrorManager errorManager;

    public AssetUserWalletModule(AssetUserWalletManager assetUserWalletManager,
                                 AssetAppropriationManager assetAppropriationManager,
                                 UserRedemptionManager userRedemptionManager,
                                 IdentityAssetUserManager identityAssetUserManager,
                                 AssetTransferManager assetTransferManager,
                                 ErrorManager errorManager) {

        this.assetUserWalletManager = assetUserWalletManager;
        this.assetAppropriationManager = assetAppropriationManager;
        this.userRedemptionManager = userRedemptionManager;
        this.identityAssetUserManager = identityAssetUserManager;
        this.assetTransferManager = assetTransferManager;
        this.errorManager = errorManager;
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
}
