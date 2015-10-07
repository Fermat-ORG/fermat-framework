package com.bitdubai.fermat_dap_plugin.layer.module.asset.issuer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.DealsWithAssetIssuerWalletSubAppModule;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletBalance;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;

import java.util.List;

/**
 * Created by franklin on 06/10/15.
 */
public class AssetIssuerWalletModuleManager {
    //TODO: Excepciones y documentar
    private AssetIssuerWallet assetIssuerWallet;
    AssetIssuerWalletSupAppModuleManager assetIssuerWalletSupAppModuleManager;


    private AssetIssuerWalletBalance getAssetIssuerWalletBalancesAvailable(){
        try{
            return assetIssuerWallet.getBookBalance(BalanceType.AVAILABLE);
        }catch (Exception e){
            return null;
        }
    }

    private AssetIssuerWalletBalance getAssetIssuerWalletBalancesBook(){
        try{
            return assetIssuerWallet.getBookBalance(BalanceType.BOOK);
        }catch (Exception e){
            return null;
        }
    }

    public List<AssetIssuerWalletList>  listAssetIssuerWalletBalancesAvailable(){

        try{
            return getAssetIssuerWalletBalancesAvailable().getAssetIssuerWalletBalancesAvailable();
        }catch (Exception exception){
            return null;
        }
    }

    public List<AssetIssuerWalletList>  listAssetIssuerWalletBalancesBook(){

        try{
            return getAssetIssuerWalletBalancesAvailable().getAssetIssuerWalletBalancesBook();
        }catch (Exception exception){
            return null;
        }
    }

    AssetIssuerWalletModuleManager(AssetIssuerWallet assetIssuerWallet){
        this.assetIssuerWallet = assetIssuerWallet;
    }
}
