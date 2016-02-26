package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v2.common.data;

import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v2.models.Asset;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v2.models.Issuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 2/24/16.
 */
public class DataManager {
    private AssetUserWalletSubAppModuleManager moduleManager;
    private String walletPublicKey = WalletUtilities.WALLET_PUBLIC_KEY;

    public DataManager(AssetUserWalletSubAppModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    public List<Issuer> getIssuers() throws CantLoadWalletException {
        Map<ActorAssetIssuer, AssetUserWalletList> map = moduleManager.getAssetUserWalletBalancesByIssuer(walletPublicKey);
        Iterator<ActorAssetIssuer> it = map.keySet().iterator();
        ActorAssetIssuer actorAssetIssuer;
        AssetUserWalletList assetUserWalletList;
        List<Issuer> issuers = new ArrayList<>();
        List<Asset> assets;
        Issuer issuer;
        while(it.hasNext()) {
            actorAssetIssuer = it.next();
            assetUserWalletList = map.get(actorAssetIssuer);
            issuer = new Issuer(actorAssetIssuer);

            long quantityAvailableBalance = assetUserWalletList.getQuantityAvailableBalance();
            assets = new ArrayList<>();
            for(long i = 0; i < quantityAvailableBalance; i++) {
                assets.add(new Asset(assetUserWalletList.getDigitalAsset(), Asset.Status.CONFIRMED));
            }

            long quantityBookBalance = assetUserWalletList.getQuantityBookBalance() - quantityAvailableBalance;
            assets = new ArrayList<>();
            for(long i = 0; i < quantityBookBalance; i++) {
                assets.add(new Asset(assetUserWalletList.getDigitalAsset(), Asset.Status.PENDING));
            }

            issuer.setAssets(assets);
            issuers.add(issuer);
        }
        return issuers;
    }
}
