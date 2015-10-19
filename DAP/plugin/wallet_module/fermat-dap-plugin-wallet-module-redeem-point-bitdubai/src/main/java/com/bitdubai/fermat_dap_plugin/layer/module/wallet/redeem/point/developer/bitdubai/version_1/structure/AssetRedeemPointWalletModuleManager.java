package com.bitdubai.fermat_dap_plugin.layer.module.wallet.redeem.point.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.util.List;

/**
 * Created by franklin on 16/10/15.
 */
public class AssetRedeemPointWalletModuleManager {
    AssetRedeemPointWalletManager assetRedeemPointWalletManager;
    public AssetRedeemPointWalletModuleManager(AssetRedeemPointWalletManager assetRedeemPointWalletManager){
        this.assetRedeemPointWalletManager = assetRedeemPointWalletManager;
    }

    public List<AssetRedeemPointWalletList> getAssetRedeemPointWalletBalancesAvailable(String publicKey) throws CantLoadWalletException {
        try{
            return assetRedeemPointWalletManager.loadAssetRedeemPointWallet(publicKey).getBookBalance(BalanceType.AVAILABLE).getAssetIssuerWalletBalancesAvailable();
        }catch (Exception exception){
            throw new CantLoadWalletException("Error load Wallet Balances Available", exception, "Method: getAssetIssuerWalletBalancesAvailable", "Class: AssetIssuerWalletModuleManager");
        }
    }

    public List<AssetRedeemPointWalletList>  getAssetRedeemPointWalletBalancesBook(String publicKey) throws CantLoadWalletException{
        try{
            return assetRedeemPointWalletManager.loadAssetRedeemPointWallet(publicKey).getBookBalance(BalanceType.BOOK).getAssetIssuerWalletBalancesBook();
        }catch (Exception exception){
            throw new CantLoadWalletException("Error load Wallet Balances Book", exception, "Method: getAssetIssuerWalletBalancesBook", "Class: AssetIssuerWalletModuleManager");
        }
    }
}
