package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
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
        List<AssetUserWalletList> assets = moduleManager.getAssetUserWalletBalances("walletPublicKeyTest");
        List<DigitalAsset> digitalAssets = new ArrayList<>();
        DigitalAsset digitalAsset;

        for (AssetUserWalletList asset : assets) {
            digitalAsset = new DigitalAsset();
            digitalAsset.setAssetPublicKey(asset.getDigitalAsset().getPublicKey());
            digitalAsset.setName(asset.getDigitalAsset().getName());
            digitalAsset.setAvailableBalanceQuantity(asset.getQuantityAvailableBalance());
            digitalAsset.setBookBalanceQuantity(asset.getQuantityBookBalance());
            digitalAsset.setAvailableBalance(asset.getDigitalAsset().getGenesisAmount());
            digitalAsset.setExpDate((Timestamp) asset.getDigitalAsset().getContract().getContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE).getValue());

            digitalAssets.add(digitalAsset);

            List<Resource> resources = asset.getDigitalAsset().getResources();
            if (resources != null && !resources.isEmpty()) {
                digitalAsset.setImage(resources.get(0).getResourceBinayData());
            }
        }
        return digitalAssets;
    }

    public static List<RedeemPoint> getConnectedRedeemPoints(AssetUserWalletSubAppModuleManager moduleManager, List<RedeemPoint> usersSelected) throws CantGetAssetUserActorsException {
        List<RedeemPoint> users = new ArrayList<>();
        users.add(new RedeemPoint("Frank Contreras RP", null));
        users.add(new RedeemPoint("Victor Mars RP", null));
        users.add(new RedeemPoint("Nerio Indriago RP", null));
        users.add(new RedeemPoint("Rodrigo Acosta RP", null));
//        List<RedeemPoint> users = new ArrayList<>();
//        List<ActorAssetUser> actorAssetRedeemPoints = moduleManager.getAllActorAssetRedeemPointConnected();
//        for (ActorAssetUser actorAssetRedeemPoint:actorAssetRedeemPoints) {
//            RedeemPoint newUser = new RedeemPoint(actorAssetRedeemPoint.getName(), actorAssetRedeemPoint);
////            int index = usersSelected.indexOf(newUser);
////            if (index > 0) newUser.setSelected(usersSelected.get(index).isSelected());
//            users.add(newUser);
//        }
        return users;
    }
}
