package org.fermat.fermat_dap_android_wallet_asset_user.v2.common.data;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;

import org.fermat.fermat_dap_android_wallet_asset_user.models.RedeemPoint;
import org.fermat.fermat_dap_android_wallet_asset_user.models.User;
import org.fermat.fermat_dap_android_wallet_asset_user.v2.models.Asset;
import org.fermat.fermat_dap_android_wallet_asset_user.v2.models.AssetUserNegotiation;
import org.fermat.fermat_dap_android_wallet_asset_user.v2.models.Issuer;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantGetAssetRedeemPointActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWallet;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 2/24/16.
 */
public class DataManager implements Serializable {
    private static AssetUserWalletSubAppModuleManager moduleManager;
    private static String walletPublicKey = WalletUtilities.WALLET_PUBLIC_KEY;

    public DataManager(AssetUserWalletSubAppModuleManager moduleManager) {
        DataManager.moduleManager = moduleManager;
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

    public static List<Asset> getAssets() throws CantLoadWalletException, CantGetTransactionsException {
        List<AssetUserWalletList> assetUserWalletBalances = moduleManager.getAssetUserWalletBalances(WalletUtilities.WALLET_PUBLIC_KEY);
        List<Asset> assets = new ArrayList<>();
        try {
            AssetUserWallet userWallet = moduleManager.loadAssetUserWallet(WalletUtilities.WALLET_PUBLIC_KEY);
            if (assetUserWalletBalances != null && userWallet != null) {
                for (AssetUserWalletList assetUserWalletList : assetUserWalletBalances) {
                    List<CryptoAddress> addresses = assetUserWalletList.getAddresses();
                    for (int i = 0; i < assetUserWalletList.getQuantityBookBalance(); i++) {
                        try {
                            CryptoAddress address = addresses.get(i);
                            assets.add(new Asset(assetUserWalletList, userWallet.getAllTransactions(address), address));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(assets, new Comparator<Asset>() {
            @Override
            public int compare(Asset lhs, Asset rhs) {
                if (lhs.getDate().getTime() > rhs.getDate().getTime()) return -1;
                else if (lhs.getDate().getTime() < rhs.getDate().getTime()) return 1;
                return 0;
            }
        });

        return assets;
    }

    public static List<RedeemPoint> getConnectedRedeemPoints(String assetPublicKey) throws CantGetAssetRedeemPointActorsException {
        List<RedeemPoint> redeemPoints = new ArrayList<>();
        List<ActorAssetRedeemPoint> actorAssetRedeemPoints = moduleManager.getRedeemPointsConnectedForAsset(assetPublicKey);
        for (ActorAssetRedeemPoint actorAssetRedeemPoint : actorAssetRedeemPoints) {
            RedeemPoint newUser = new RedeemPoint(actorAssetRedeemPoint);
            redeemPoints.add(newUser);
        }
        return redeemPoints;
    }

    public static List<ActorAssetRedeemPoint> getRedeemPoints(List<RedeemPoint> redeemPoints) {
        List<ActorAssetRedeemPoint> actorAssetRedeemPoints = new ArrayList<>();
        for (RedeemPoint redeemPoint : redeemPoints) {
            if (redeemPoint.isSelected()) {
                actorAssetRedeemPoints.add(redeemPoint.getActorAssetRedeemPoint());
            }
        }
        return actorAssetRedeemPoints;
    }

    public static List<User> getConnectedUsers() throws CantGetAssetUserActorsException {
        List<User> users = new ArrayList<>();
        List<ActorAssetUser> actorAssetUsers = moduleManager.getAllAssetUserActorConnected();
        for (ActorAssetUser actorAssetUser : actorAssetUsers) {
            User newUser = new User(actorAssetUser.getName(), actorAssetUser);
            users.add(newUser);
        }
        return users;
    }

    public static List<Asset> getAllPendingNegotiations() throws Exception {
        List<Asset> digitalAssets = new ArrayList<>();
        try {
            List<AssetNegotiation> assetNegotiations = moduleManager.getPendingAssetNegotiations();
            Asset digitalAsset;

            if (assetNegotiations != null) {
                for (AssetNegotiation asset : assetNegotiations) {

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
                    if (resources != null && !resources.isEmpty()) {
                        digitalAsset.setImage(resources.get(0).getResourceBinayData());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return digitalAssets;
    }
}
