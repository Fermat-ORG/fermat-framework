package com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces;

import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.util.List;

/**
 * Created by franklin on 16/10/15.
 */
public interface AssetUserWalletSubAppModuleManager extends ModuleManager {
    /**
     * (non-Javadoc)
     * @see List <AssetUserWalletList>() getAssetIssuerWalletBalancesAvailable(String publicKey)
     */
    List<AssetUserWalletList> getAssetUserWalletBalancesAvailable(String publicKey) throws CantLoadWalletException;
    /**
     * (non-Javadoc)
     * @see List<AssetUserWalletList> getAssetIssuerWalletBalancesBook(String publicKey)
     */
    List<AssetUserWalletList> getAssetUserWalletBalancesBook(String publicKey) throws CantLoadWalletException;

    //TODO: Implementar este metodo mas adelante
    //void redeemptionAsset();
}
