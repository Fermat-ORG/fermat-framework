package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletList;

import java.sql.Timestamp;
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
            AssetFactory assetFactory = moduleManager.getAssetFactory(asset.getAssetPublicKey());

            digitalAsset = new DigitalAsset();
            digitalAsset.setAssetPublicKey(asset.getAssetPublicKey());
            digitalAsset.setName(asset.getName());
            digitalAsset.setAvailableBalanceQuantity(asset.getQuantityAvailableBalance());
            digitalAsset.setBookBalanceQuantity(asset.getQuantityBookBalance());
            digitalAsset.setAvailableBalance(asset.getAvailableBalance());
            digitalAsset.setExpDate(assetFactory.getExpirationDate());

            List<Resource> resources = assetFactory.getResources();
            if (resources != null && resources.size() > 0) {
                digitalAsset.setImage(moduleManager.getAssetFactoryResource(resources.get(0)).getContent());
            }

            digitalAssets.add(digitalAsset);
        }
        return digitalAssets;
    }

    public static List<UserDelivery> getUserDeliveryList(AssetIssuerWalletSupAppModuleManager moduleManager) throws Exception {
        //TODO get from database
        List<UserDelivery> users = new ArrayList<>();
        UserDelivery userDelivery = new UserDelivery();
        userDelivery.setUserName("Janet Williams");
        userDelivery.setDeliveryDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        userDelivery.setDeliveryStatus("Redeemed");
        users.add(userDelivery);
        userDelivery = new UserDelivery();
        userDelivery.setUserName("Amanda Hood");
        userDelivery.setDeliveryDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        userDelivery.setDeliveryStatus("Appropriated");
        users.add(userDelivery);
        userDelivery = new UserDelivery();
        userDelivery.setUserName("Tom Snow");
        userDelivery.setDeliveryDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        userDelivery.setDeliveryStatus("Unused");
        users.add(userDelivery);
        return users;
    }
}
