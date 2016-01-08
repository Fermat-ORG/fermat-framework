package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by frank on 12/9/15.
 */
public class Data {
    public static List<DigitalAsset> getAllDigitalAssets(AssetUserWalletSubAppModuleManager moduleManager) throws Exception {
        List<AssetUserWalletList> assets = moduleManager.getAssetUserWalletBalances("walletPublicKeyTest");
        List<DigitalAsset> digitalAssets = new ArrayList<>();
        DigitalAsset digitalAsset;
        for (AssetUserWalletList asset : assets) {
//            AssetFactory assetFactory = moduleManager.getAssetFactory(asset.getAssetPublicKey());

            digitalAsset = new DigitalAsset();
            digitalAsset.setAssetPublicKey(asset.getAssetPublicKey());
            digitalAsset.setName(asset.getName());
            digitalAsset.setAvailableBalanceQuantity(asset.getQuantityAvailableBalance());
            digitalAsset.setBookBalanceQuantity(asset.getQuantityBookBalance());
            digitalAsset.setAvailableBalance(asset.getAvailableBalance());
//            digitalAsset.setExpDate(assetFactory.getExpirationDate());
//
//            List<Resource> resources = assetFactory.getResources();
//            if (resources != null && resources.size() > 0) {
//                digitalAsset.setImage(moduleManager.getAssetFactoryResource(resources.get(0)).getContent());
//            }

            digitalAssets.add(digitalAsset);
        }
        return digitalAssets;
    }
}
