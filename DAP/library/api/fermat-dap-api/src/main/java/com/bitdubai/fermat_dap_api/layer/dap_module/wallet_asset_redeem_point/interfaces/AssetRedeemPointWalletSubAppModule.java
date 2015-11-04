package com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces;

import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.util.List;

/**
 * Created by franklin on 16/10/15.
 */
public interface AssetRedeemPointWalletSubAppModule extends ModuleManager {
    /**
     * (non-Javadoc)
     * @see List <AssetRedeemPointWalletList>() getAssetRedeemPointWalletBalancesAvailable(String publicKey)
     */
    List<AssetRedeemPointWalletList> getAssetRedeemPointWalletBalancesAvailable(String publicKey) throws CantLoadWalletException;
    /**
     * (non-Javadoc)
     * @see List<AssetRedeemPointWalletList> getAssetRedeemPointWalletBalancesBook(String publicKey)
     */
    List<AssetRedeemPointWalletList> getAssetRedeemPointWalletBalancesBook(String publicKey) throws CantLoadWalletException;

}
