package org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;

import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantCreateWalletException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

/**
 * Created by franklin on 04/09/15.
 */
public interface AssetIssuerWalletManager extends FermatManager {

    AssetIssuerWallet loadAssetIssuerWallet(String walletPublicKey, BlockchainNetworkType networkType) throws CantLoadWalletException;

    void createWalletAssetIssuer(String walletPublicKey, BlockchainNetworkType networkType) throws CantCreateWalletException;
}