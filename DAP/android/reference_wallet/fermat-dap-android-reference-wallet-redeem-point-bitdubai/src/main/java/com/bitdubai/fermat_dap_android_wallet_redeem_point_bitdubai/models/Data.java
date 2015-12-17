package com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.models;

import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by frank on 12/9/15.
 */
public class Data {
    public static List<DigitalAsset> getAllDigitalAssets(AssetRedeemPointWalletSubAppModule moduleManager) throws Exception {
        List<AssetRedeemPointWalletList> assets = moduleManager.getAssetRedeemPointWalletBalances("walletPublicKeyTest");
        List<DigitalAsset> digitalAssets = new ArrayList<>();
        DigitalAsset digitalAsset;
        for (AssetRedeemPointWalletList asset : assets) {
            digitalAsset = new DigitalAsset();
            digitalAsset.setAssetPublicKey(asset.getAssetPublicKey());
            digitalAsset.setName(asset.getName());
            digitalAsset.setAvailableBalance(asset.getQuantityAvailableBalance());
            digitalAsset.setBookBalance(asset.getQuantityBookBalance());
            digitalAsset.setBitcoinAmount(0.2); //TODO get from asset
            digitalAsset.setExpDate(Calendar.getInstance().getTime()); //TODO get from asset
            digitalAssets.add(digitalAsset);
        }
        return digitalAssets;
    }
}
