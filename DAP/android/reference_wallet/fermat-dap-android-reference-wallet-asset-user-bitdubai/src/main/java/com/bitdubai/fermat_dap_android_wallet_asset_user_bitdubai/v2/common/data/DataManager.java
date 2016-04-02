package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v2.common.data;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v2.models.Asset;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v2.models.AssetUserNegotiation;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v2.models.Issuer;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v2.models.RedeemPoint;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantGetAssetRedeemPointActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransaction;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
//        Map<ActorAssetIssuer, AssetUserWalletList> map = moduleManager.getAssetUserWalletBalancesByIssuer(walletPublicKey);
//        Iterator<ActorAssetIssuer> it = map.keySet().iterator();
//        ActorAssetIssuer actorAssetIssuer;
//        AssetUserWalletList assetUserWalletList;
//        List<Issuer> issuers = new ArrayList<>();
//        List<Asset> assets;
//        Issuer issuer;
//        while(it.hasNext()) {
//            actorAssetIssuer = it.next();
//            assetUserWalletList = map.get(actorAssetIssuer);
//            issuer = new Issuer(actorAssetIssuer);
//
//            long quantityAvailableBalance = assetUserWalletList.getQuantityAvailableBalance();
//            assets = new ArrayList<>();
//            for(long i = 0; i < quantityAvailableBalance; i++) {
//                assets.add(new Asset(assetUserWalletList, 0, Asset.Status.CONFIRMED));
//            }
//
//            long quantityBookBalance = assetUserWalletList.getQuantityBookBalance() - quantityAvailableBalance;
//            for(long i = 0; i < quantityBookBalance; i++) {
//                assets.add(new Asset(assetUserWalletList, 0, Asset.Status.PENDING));
//            }
//
//            issuer.setAssets(assets);
//            issuers.add(issuer);
//        }
//        return issuers;
        return null;
    }

    public List<Asset> getAssets() throws CantLoadWalletException, CantGetTransactionsException {
        List<AssetUserWalletList> assetUserWalletBalances = moduleManager.getAssetUserWalletBalances(walletPublicKey);

        List<Asset> assets = new ArrayList<>();
        Set<String> hashes = new HashSet<>();
        for(AssetUserWalletList assetUserWalletList : assetUserWalletBalances) {
            List<AssetUserWalletTransaction> assetUserWalletTransactions = moduleManager.loadAssetUserWallet(walletPublicKey).getAllTransactions(assetUserWalletList.getDigitalAsset().getPublicKey());
            for(AssetUserWalletTransaction assetUserWalletTransaction : assetUserWalletTransactions) {
                hashes.add(assetUserWalletTransaction.getTransactionHash());
                assets.add(new Asset(assetUserWalletList, assetUserWalletTransaction));
            }
        }

        List<Asset> newAssets = new ArrayList<>();

        for (String hash : hashes) {
            List<Asset> sublist = new ArrayList<>();
            for (Asset asset : assets) {
                if (asset.getId().equals(hash)) {
                    sublist.add(asset);
                }
            }
            if (sublist.size() == 1) {
                newAssets.add(sublist.get(0));
            } else if (sublist.size() > 1) {
                for (int i = sublist.size() - 1; i >= 0; i--) {
                    if (sublist.get(i).getAssetUserWalletTransaction().getTransactionType().equals(TransactionType.CREDIT)
                            && sublist.get(i).getStatus().equals(Asset.Status.CONFIRMED)) {
                        newAssets.add(sublist.get(i));
                        break;
                    } else if (sublist.get(i).getAssetUserWalletTransaction().getTransactionType().equals(TransactionType.DEBIT)
                            && sublist.get(i).getStatus().equals(Asset.Status.CONFIRMED)) {
                        break;
                    }
                }
            }
        }

        Collections.sort(newAssets, new Comparator<Asset>() {
            @Override
            public int compare(Asset lhs, Asset rhs) {
                if (lhs.getDate().getTime() > rhs.getDate().getTime()) return -1;
                else if (lhs.getDate().getTime() < rhs.getDate().getTime()) return 1;
                return 0;
            }
        });

        return newAssets;
    }

    public List<RedeemPoint> getConnectedRedeemPoints(String assetPublicKey) throws CantGetAssetRedeemPointActorsException {
        List<RedeemPoint> redeemPoints = new ArrayList<>();
        List<ActorAssetRedeemPoint> actorAssetRedeemPoints = moduleManager.getRedeemPointsConnectedForAsset(assetPublicKey);
        for (ActorAssetRedeemPoint actorAssetRedeemPoint : actorAssetRedeemPoints) {
            RedeemPoint newUser = new RedeemPoint(actorAssetRedeemPoint);
            redeemPoints.add(newUser);
        }
        return redeemPoints;
    }

    public List<Asset> getAllPendingNegotiations() throws Exception {
        List<AssetNegotiation> assetNegotiations = moduleManager.getPendingAssetNegotiations();
        List<Asset> digitalAssets = new ArrayList<>();
        Asset digitalAsset;

        for (AssetNegotiation asset : assetNegotiations){

            digitalAsset = new Asset();
            digitalAsset.setDigitalAsset(asset.getAssetToOffer());
            digitalAsset.setName(asset.getAssetToOffer().getName());
            digitalAsset.setAmount(1);
            digitalAsset.setId(asset.getNegotiationId().toString());
            digitalAsset.setDate(new Timestamp(new Date().getTime()));
            digitalAsset.setStatus(Asset.Status.PENDING);

            ActorAssetUser seller = moduleManager.getSellerFromNegotiation(asset.getNegotiationId());

            digitalAsset.setActorName(seller.getName());
            digitalAsset.setActorImage(seller.getProfileImage());

            AssetUserNegotiation userAssetNegotiation = new AssetUserNegotiation();
            userAssetNegotiation.setId(asset.getNegotiationId());
            userAssetNegotiation.setAmount(asset.getTotalAmount());

            digitalAsset.setAssetUserNegotiation(userAssetNegotiation);
            digitalAsset.setExpDate((Timestamp) asset.getAssetToOffer().getContract().getContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE).getValue());

            digitalAssets.add(digitalAsset);

            List<Resource> resources = asset.getAssetToOffer().getResources();
            if(resources != null && !resources.isEmpty()){
                digitalAsset.setImage(resources.get(0).getResourceBinayData());
            }
        }

        return digitalAssets;
    }
}
