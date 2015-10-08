package com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces;

import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletBalance;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.util.List;

/**
 * Created by franklin on 11/09/15.
 */
public interface AssetIssuerWalletSupAppModuleManager {

    /**
     * (non-Javadoc)
     * @see List<AssetIssuerWalletList>() getAssetIssuerWalletBalancesAvailable(String publicKey)
     */
    List<AssetIssuerWalletList> getAssetIssuerWalletBalancesAvailable(String publicKey) throws CantLoadWalletException;
    /**
     * (non-Javadoc)
     * @see List<AssetIssuerWalletList> getAssetIssuerWalletBalancesBook(String publicKey)
     */
    List<AssetIssuerWalletList> getAssetIssuerWalletBalancesBook(String publicKey) throws CantLoadWalletException;
}
