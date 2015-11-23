package com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

/**
 * Created by franklin on 03/10/15.
 */
public interface AssetUserWalletManager extends FermatManager {

    AssetUserWallet loadAssetUserWallet(String walletPublicKey) throws CantLoadWalletException;

    void createAssetUserWallet (String walletPublicKey) throws CantCreateWalletException;
}
