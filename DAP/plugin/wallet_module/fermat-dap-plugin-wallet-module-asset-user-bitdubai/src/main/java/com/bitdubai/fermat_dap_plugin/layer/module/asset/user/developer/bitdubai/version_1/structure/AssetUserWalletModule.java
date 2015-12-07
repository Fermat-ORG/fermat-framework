package com.bitdubai.fermat_dap_plugin.layer.module.asset.user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.exceptions.CantExecuteAppropriationTransactionException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.exceptions.TransactionAlreadyStartedException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.interfaces.AssetAppropriationManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.user_redemption.exceptions.CantRedeemDigitalAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.user_redemption.interfaces.UserRedemptionManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.util.List;

/**
 * Created by franklin on 16/10/15.
 */
public class AssetUserWalletModule {
    AssetUserWalletManager assetUserWalletManager;
    AssetAppropriationManager assetAppropriationManager;
    UserRedemptionManager userRedemptionManager;

    public AssetUserWalletModule(AssetUserWalletManager assetUserWalletManager, AssetAppropriationManager assetAppropriationManager, UserRedemptionManager userRedemptionManager) {
        this.assetUserWalletManager = assetUserWalletManager;
        this.assetAppropriationManager = assetAppropriationManager;
        this.userRedemptionManager = userRedemptionManager;
    }

    public List<AssetUserWalletList> getAssetUserWalletBalancesAvailable(String publicKey) throws CantLoadWalletException {
        try {
            return assetUserWalletManager.loadAssetUserWallet(publicKey).getBookBalance(BalanceType.AVAILABLE).getAssetUserWalletBalancesAvailable();
        } catch (Exception exception) {
            throw new CantLoadWalletException("Error load Wallet Balances Available", exception, "Method: getAssetUserWalletBalancesAvailable", "Class: AssetUserWalletModule");
        }
    }

    public List<AssetUserWalletList> getAssetUserWalletBalancesBook(String publicKey) throws CantLoadWalletException {
        try {
            return assetUserWalletManager.loadAssetUserWallet(publicKey).getBookBalance(BalanceType.BOOK).getAssetUserWalletBalancesBook();
        } catch (Exception exception) {
            throw new CantLoadWalletException("Error load Wallet Balances Book", exception, "Method: getAssetUserWalletBalancesBook", "Class: AssetUserWalletModule");
        }
    }


    public void redeemAssetToRedeemPoint(String digitalAssetPublicKey, ActorAssetRedeemPoint actorAssetRedeemPoint) throws CantRedeemDigitalAssetException {
        String context = "Asset Public Key: " + digitalAssetPublicKey + " - ActorAssetRedeemPoint: " + actorAssetRedeemPoint;
        try {
            DigitalAssetMetadata digitalAssetMetadata = assetUserWalletManager.loadAssetUserWallet("walletPublicKeyTest").getDigitalAssetMetadata(digitalAssetPublicKey);
            userRedemptionManager.redeemAssetToRedeemPoint(digitalAssetMetadata, actorAssetRedeemPoint, "walletPublicKeyTest");
        } catch (CantGetDigitalAssetFromLocalStorageException | CantLoadWalletException e) {
            throw new CantRedeemDigitalAssetException(e, context, null);
        }
    }


    public void appropriateAsset(String digitalAssetPublicKey, String bitcoinWalletPublicKey) throws CantExecuteAppropriationTransactionException, TransactionAlreadyStartedException {
        String context = "Asset Public Key: " + digitalAssetPublicKey + " - BTC Wallet Public Key: " + bitcoinWalletPublicKey;
        try {
            DigitalAsset digitalAsset = assetUserWalletManager.loadAssetUserWallet("walletPublicKeyTest").getDigitalAssetMetadata(digitalAssetPublicKey).getDigitalAsset();
            assetAppropriationManager.appropriateAsset(digitalAsset, "walletPublicKeyTest", bitcoinWalletPublicKey);
        } catch (CantGetDigitalAssetFromLocalStorageException | CantLoadWalletException e) {
            throw new CantExecuteAppropriationTransactionException(e, context, null);
        }
    }
}
