package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models;

import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by frank on 12/9/15.
 */
public class Data {
    public static List<DigitalAsset> getAllDigitalAssets(AssetIssuerWalletSupAppModuleManager moduleManager) throws Exception {
        List<AssetIssuerWalletList> assets = moduleManager.getAssetIssuerWalletBalances("walletPublicKeyTest");
        List<DigitalAsset> digitalAssets = new ArrayList<>();
        DigitalAsset digitalAsset;
        for (AssetIssuerWalletList asset : assets) {
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
