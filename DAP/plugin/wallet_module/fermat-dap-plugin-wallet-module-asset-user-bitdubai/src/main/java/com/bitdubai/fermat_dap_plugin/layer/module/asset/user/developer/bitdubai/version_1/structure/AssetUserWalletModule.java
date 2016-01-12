package com.bitdubai.fermat_dap_plugin.layer.module.asset.user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.exceptions.CantExecuteAppropriationTransactionException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.exceptions.TransactionAlreadyStartedException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.interfaces.AssetAppropriationManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.user_redemption.exceptions.CantRedeemDigitalAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.user_redemption.interfaces.UserRedemptionManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.util.HashMap;
import java.util.List;

/**
 * Created by franklin on 16/10/15.
 */
public class AssetUserWalletModule {
    AssetUserWalletManager assetUserWalletManager;
    AssetAppropriationManager assetAppropriationManager;
    UserRedemptionManager userRedemptionManager;
    IdentityAssetUserManager identityAssetUserManager;

    public AssetUserWalletModule(AssetUserWalletManager assetUserWalletManager, AssetAppropriationManager assetAppropriationManager, UserRedemptionManager userRedemptionManager, IdentityAssetUserManager identityAssetUserManager) {
        this.assetUserWalletManager     = assetUserWalletManager;
        this.assetAppropriationManager  = assetAppropriationManager;
        this.userRedemptionManager      = userRedemptionManager;
        this.identityAssetUserManager   = identityAssetUserManager;
    }

    public List<AssetUserWalletList> getAssetUserWalletBalances(String publicKey) throws CantLoadWalletException {
        try {
            return assetUserWalletManager.loadAssetUserWallet(publicKey).getBalance().getAssetUserWalletBalances();
        } catch (Exception exception) {
            throw new CantLoadWalletException("Error load Wallet Balances Book", exception, "Method: getAssetUserWalletBalancesBook", "Class: AssetUserWalletModule");
        }
    }


    public void redeemAssetToRedeemPoint(String digitalAssetPublicKey, String walletPublicKey, List<ActorAssetRedeemPoint> actorAssetRedeemPoint) throws CantRedeemDigitalAssetException {
        String context = "Asset Public Key: " + digitalAssetPublicKey + " - ActorAssetRedeemPoint: " + actorAssetRedeemPoint;
        try {
            if (actorAssetRedeemPoint.isEmpty()) {
                throw new CantRedeemDigitalAssetException(null, context, "THE REDEEM POINT LIST IS EMPTY.");
            }
            walletPublicKey = "walletPublicKeyTest"; //TODO: Solo para la prueba del Redemption
            HashMap<DigitalAssetMetadata, ActorAssetRedeemPoint> hashMap = createRedemptionMap(walletPublicKey, digitalAssetPublicKey, actorAssetRedeemPoint);
            userRedemptionManager.redeemAssetToRedeemPoint(hashMap, walletPublicKey);
        } catch (CantGetDigitalAssetFromLocalStorageException | CantLoadWalletException | CantGetTransactionsException | FileNotFoundException | CantCreateFileException e) {
            throw new CantRedeemDigitalAssetException(e, context, null);
        }
    }


    private HashMap<DigitalAssetMetadata, ActorAssetRedeemPoint> createRedemptionMap(String walletPublicKey, String assetPublicKey, List<ActorAssetRedeemPoint> redeemPoints) throws CantGetTransactionsException, FileNotFoundException, CantCreateFileException, CantLoadWalletException, CantGetDigitalAssetFromLocalStorageException {
        HashMap<DigitalAssetMetadata, ActorAssetRedeemPoint> hashMap = new HashMap<>();
        for (ActorAssetRedeemPoint redeemPoint : redeemPoints) {
            DigitalAssetMetadata digitalAssetMetadata = assetUserWalletManager.loadAssetUserWallet(walletPublicKey).getDigitalAssetMetadata(assetPublicKey);
            hashMap.put(digitalAssetMetadata, redeemPoint);
        }
        return hashMap;
    }


    public void appropriateAsset(String digitalAssetPublicKey, String bitcoinWalletPublicKey) throws CantExecuteAppropriationTransactionException, TransactionAlreadyStartedException {
        String context = "Asset Public Key: " + digitalAssetPublicKey + " - BTC Wallet Public Key: " + bitcoinWalletPublicKey;
        try {
            DigitalAssetMetadata assetMetadata = assetUserWalletManager.loadAssetUserWallet("walletPublicKeyTest").getDigitalAssetMetadata(digitalAssetPublicKey);
            assetAppropriationManager.appropriateAsset(assetMetadata, "walletPublicKeyTest", bitcoinWalletPublicKey);
        } catch (CantGetDigitalAssetFromLocalStorageException | CantLoadWalletException e) {
            throw new CantExecuteAppropriationTransactionException(e, context, null);
        }
    }

    public List<IdentityAssetUser> getActiveIdentities() {

        try{
            return identityAssetUserManager.getIdentityAssetUsersFromCurrentDeviceUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
