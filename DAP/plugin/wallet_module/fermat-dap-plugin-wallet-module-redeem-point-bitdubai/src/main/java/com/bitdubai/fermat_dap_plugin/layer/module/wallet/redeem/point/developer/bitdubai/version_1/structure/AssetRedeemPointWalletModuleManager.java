package com.bitdubai.fermat_dap_plugin.layer.module.wallet.redeem.point.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentity;
import com.bitdubai.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentityManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 16/10/15.
 */
public class AssetRedeemPointWalletModuleManager {
    AssetRedeemPointWalletManager assetRedeemPointWalletManager;
    RedeemPointIdentityManager redeemPointIdentityManager;
    UUID pluginId;
    PluginFileSystem pluginFileSystem;

    public AssetRedeemPointWalletModuleManager(AssetRedeemPointWalletManager assetRedeemPointWalletManager, RedeemPointIdentityManager redeemPointIdentityManager, UUID pluginId, PluginFileSystem pluginFileSystem) {
        this.assetRedeemPointWalletManager  = assetRedeemPointWalletManager;
        this.redeemPointIdentityManager     = redeemPointIdentityManager;
        this.pluginId = pluginId;
        this.pluginFileSystem = pluginFileSystem;
    }

    public List<AssetRedeemPointWalletList> getAssetRedeemPointWalletBalances(String publicKey) throws CantLoadWalletException {
        try{
            return assetRedeemPointWalletManager.loadAssetRedeemPointWallet(publicKey).getBalance().getAssetIssuerWalletBalances();
        }catch (Exception exception){
            throw new CantLoadWalletException("Error load Wallet Balances Available", exception, "Method: getAssetIssuerWalletBalancesAvailable", "Class: AssetIssuerWalletModuleManager");
        }
    }

    public List<RedeemPointIdentity> getActiveIdentities() {

        try{
            return redeemPointIdentityManager.getRedeemPointsFromCurrentDeviceUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
