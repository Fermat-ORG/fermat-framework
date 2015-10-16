package com.bitdubai.fermat_dap_plugin.layer.module.asset.issuer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.DealsWithAssetIssuerWalletSubAppModule;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletBalance;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.DealsWithAssetIssuerWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.util.List;

/**
 * Created by franklin on 06/10/15.
 */
public class AssetIssuerWalletModuleManager {
    //TODO: Excepciones y documentar
    AssetIssuerWalletManager assetIssuerWalletManager;

    /**
     * constructor
     * @param assetIssuerWalletManager
     */
    public AssetIssuerWalletModuleManager(AssetIssuerWalletManager assetIssuerWalletManager) {
        this.assetIssuerWalletManager = assetIssuerWalletManager;
        //setAssetIssuerManager(assetIssuerWalletManager);
    }

    public List<AssetIssuerWalletList>  getAssetIssuerWalletBalancesAvailable(String publicKey) throws CantLoadWalletException{
        try{
            return assetIssuerWalletManager.loadAssetIssuerWallet(publicKey).getBookBalance(BalanceType.AVAILABLE).getAssetIssuerWalletBalancesAvailable();
        }catch (Exception exception){
            throw new CantLoadWalletException("Error load Wallet Balances Available", exception, "Method: getAssetIssuerWalletBalancesAvailable", "Class: AssetIssuerWalletModuleManager");
        }
    }

    public List<AssetIssuerWalletList>  getAssetIssuerWalletBalancesBook(String publicKey) throws CantLoadWalletException{
        try{
            return assetIssuerWalletManager.loadAssetIssuerWallet(publicKey).getBookBalance(BalanceType.BOOK).getAssetIssuerWalletBalancesBook();
        }catch (Exception exception){
            throw new CantLoadWalletException("Error load Wallet Balances Book", exception, "Method: getAssetIssuerWalletBalancesBook", "Class: AssetIssuerWalletModuleManager");
        }
    }

    public void setAssetIssuerManager(AssetIssuerWalletManager assetIssuerWalletManager) {
        this.assetIssuerWalletManager = assetIssuerWalletManager;
    }
}
