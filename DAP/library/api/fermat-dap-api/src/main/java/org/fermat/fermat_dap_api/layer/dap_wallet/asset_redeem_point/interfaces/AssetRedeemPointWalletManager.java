package org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;

import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantCreateWalletException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

/**
 * Created by franklin on 14/10/15.
 */
public interface AssetRedeemPointWalletManager extends FermatManager {

    AssetRedeemPointWallet loadAssetRedeemPointWallet(String walletPublicKey, BlockchainNetworkType networkType) throws CantLoadWalletException;

    void createWalletAssetRedeemPoint(String walletPublicKey, BlockchainNetworkType networkType) throws CantCreateWalletException;
}
