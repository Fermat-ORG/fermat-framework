package org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.modules.ModuleSettingsImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentity;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.RedeemPointSettings;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWallet;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletList;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantCreateWalletException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by franklin on 16/10/15.
 */
public interface AssetRedeemPointWalletSubAppModule extends ModuleManager<RedeemPointSettings, ActiveActorIdentityInformation>, ModuleSettingsImpl<RedeemPointSettings>, Serializable {
    /**
     * (non-Javadoc)
     * @see List<AssetRedeemPointWalletList> getAssetRedeemPointWalletBalancesBook(String publicKey)
     */
    List<AssetRedeemPointWalletList> getAssetRedeemPointWalletBalances(String publicKey) throws CantLoadWalletException;

    AssetRedeemPointWallet loadAssetRedeemPointWallet(String walletPublicKey) throws CantLoadWalletException;

    void createWalletAssetRedeemPoint(String walletPublicKey) throws CantCreateWalletException;

    RedeemPointIdentity getActiveAssetRedeemPointIdentity() throws CantGetIdentityRedeemPointException;

    void changeNetworkType(BlockchainNetworkType networkType);

    BlockchainNetworkType getSelectedNetwork();
}
