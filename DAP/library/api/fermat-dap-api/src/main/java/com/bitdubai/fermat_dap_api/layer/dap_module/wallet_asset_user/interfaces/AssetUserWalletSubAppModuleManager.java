package com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.exceptions.CantGetIdentityAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.exceptions.CantExecuteAppropriationTransactionException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.exceptions.TransactionAlreadyStartedException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.user_redemption.exceptions.CantRedeemDigitalAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.util.List;

/**
 * Created by franklin on 16/10/15.
 */
public interface AssetUserWalletSubAppModuleManager extends ModuleManager<FermatSettings, ActorIdentityInformation> {
    /**
     * (non-Javadoc)
     * @see List<AssetUserWalletList> getAssetIssuerWalletBalancesBook(String publicKey)
     */
    List<AssetUserWalletList> getAssetUserWalletBalances(String publicKey) throws CantLoadWalletException;

    AssetUserWallet loadAssetUserWallet(String walletPublicKey) throws CantLoadWalletException;

    void createAssetUserWallet(String walletPublicKey) throws CantCreateWalletException;

    IdentityAssetUser getActiveAssetUserIdentity() throws CantGetIdentityAssetUserException;

    void redeemAssetToRedeemPoint(String digitalAssetPublicKey, ActorAssetRedeemPoint actorAssetRedeemPoint) throws CantRedeemDigitalAssetException;

    void appropriateAsset(String digitalAssetPublicKey, String bitcoinWalletPublicKey) throws CantExecuteAppropriationTransactionException, TransactionAlreadyStartedException;
}
