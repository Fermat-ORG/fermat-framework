package com.bitdubai.fermat_dap_plugin.layer.module.asset.user.developer.bitdubai.version_1.structure;

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

    public AssetUserWalletModule(AssetUserWalletManager assetUserWalletManager){
        this.assetUserWalletManager = assetUserWalletManager;
    }

    public List<AssetUserWalletList>  getAssetUserWalletBalancesAvailable(String publicKey) throws CantLoadWalletException{
        try{
            return assetUserWalletManager.loadAssetUserWallet(publicKey).getBookBalance(BalanceType.AVAILABLE).getAssetUserWalletBalancesAvailable();
        }catch (Exception exception){
            throw new CantLoadWalletException("Error load Wallet Balances Available", exception, "Method: getAssetUserWalletBalancesAvailable", "Class: AssetUserWalletModule");
        }
    }

    public List<AssetUserWalletList>  getAssetUserWalletBalancesBook(String publicKey) throws CantLoadWalletException{
        try{
            return assetUserWalletManager.loadAssetUserWallet(publicKey).getBookBalance(BalanceType.BOOK).getAssetUserWalletBalancesBook();
        }catch (Exception exception){
            throw new CantLoadWalletException("Error load Wallet Balances Book", exception, "Method: getAssetUserWalletBalancesBook", "Class: AssetUserWalletModule");
        }
    }
}
