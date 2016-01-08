package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank on 12/9/15.
 */
public class Data {
    public static List<DigitalAsset> getAllDigitalAssets(AssetUserWalletSubAppModuleManager moduleManager) throws Exception {
        List<AssetUserWalletList> balances = moduleManager.getAssetUserWalletBalances("walletPublicKeyTest");
        List<DigitalAsset> digitalAssets = new ArrayList<>();
        DigitalAsset digitalAsset;
        for (AssetUserWalletList balance : balances) {
            digitalAsset = new DigitalAsset();
            digitalAsset.setAssetPublicKey(balance.getDigitalAsset().getPublicKey());
            digitalAsset.setName(balance.getDigitalAsset().getName());
            digitalAsset.setAvailableBalance(balance.getQuantityAvailableBalance());
            digitalAsset.setBookBalance(balance.getQuantityBookBalance());
            digitalAsset.setBitcoinAmount((double) balance.getDigitalAsset().getGenesisAmount() / 100000000);
            digitalAsset.setExpDate((Timestamp) balance.getDigitalAsset().getContract().getContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE).getValue()); //TODO get from asset
            digitalAssets.add(digitalAsset);

            List<Resource> resources = balance.getDigitalAsset().getResources();
            if (resources != null && !resources.isEmpty()) {
                digitalAsset.setImage(resources.get(0).getResourceBinayData());
            }
        }
        return digitalAssets;
    }
}
