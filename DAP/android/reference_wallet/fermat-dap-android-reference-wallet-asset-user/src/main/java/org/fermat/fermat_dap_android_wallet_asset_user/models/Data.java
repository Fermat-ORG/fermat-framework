package org.fermat.fermat_dap_android_wallet_asset_user.models;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantGetAssetRedeemPointActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransaction;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank on 12/9/15.
 */
public class Data {
    public static List<DigitalAsset> getAllDigitalAssets(AssetUserWalletSubAppModuleManager moduleManager) throws Exception {
        List<AssetUserWalletList> assets = moduleManager.getAssetUserWalletBalances(WalletUtilities.WALLET_PUBLIC_KEY);
        List<DigitalAsset> digitalAssets = new ArrayList<>();
        DigitalAsset digitalAsset;

        for (AssetUserWalletList asset : assets) {
            digitalAsset = new DigitalAsset();
            digitalAsset.setAssetPublicKey(asset.getDigitalAsset().getPublicKey());
            digitalAsset.setName(asset.getDigitalAsset().getName());
            digitalAsset.setAvailableBalanceQuantity(asset.getQuantityAvailableBalance());
            digitalAsset.setBookBalanceQuantity(asset.getQuantityBookBalance());
            digitalAsset.setAvailableBalance(asset.getAvailableBalance());
            digitalAsset.setExpDate((Timestamp) asset.getDigitalAsset().getContract().getContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE).getValue());
            digitalAsset.setLockedAssets(asset.getLockedAssets());
            digitalAsset.setDigitalAsset(asset.getDigitalAsset());
            digitalAssets.add(digitalAsset);

            List<Resource> resources = asset.getDigitalAsset().getResources();
            if (resources != null && !resources.isEmpty()) {
                digitalAsset.setImage(resources.get(0).getResourceBinayData());
            }
        }
        return digitalAssets;
    }

    public static DigitalAsset getDigitalAsset(AssetUserWalletSubAppModuleManager moduleManager, String digitalAssetPublicKey) throws CantLoadWalletException {
        List<AssetUserWalletList> balances = moduleManager.getAssetUserWalletBalances(WalletUtilities.WALLET_PUBLIC_KEY);
        DigitalAsset digitalAsset;
        String publicKey;
        for (AssetUserWalletList balance : balances) {
            publicKey = balance.getDigitalAsset().getPublicKey();
            if (publicKey.equals(digitalAssetPublicKey)) {
                digitalAsset = new DigitalAsset();
                digitalAsset.setAssetPublicKey(balance.getDigitalAsset().getPublicKey());
                digitalAsset.setName(balance.getDigitalAsset().getName());
                digitalAsset.setAvailableBalanceQuantity(balance.getQuantityAvailableBalance());
                digitalAsset.setBookBalanceQuantity(balance.getQuantityBookBalance());
                digitalAsset.setAvailableBalance(balance.getAvailableBalance());
                Timestamp expirationDate = (Timestamp) balance.getDigitalAsset().getContract().getContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE).getValue();
                digitalAsset.setExpDate(expirationDate);
                digitalAsset.setLockedAssets(balance.getLockedAssets());
                digitalAsset.setDigitalAsset(balance.getDigitalAsset());

                List<Resource> resources = balance.getDigitalAsset().getResources();
                if (resources != null && resources.size() > 0) {
                    digitalAsset.setImage(balance.getDigitalAsset().getResources().get(0).getResourceBinayData());
                }
                return digitalAsset;
            }
        }
        return null;
    }

    public static List<RedeemPoint> getConnectedRedeemPoints(AssetUserWalletSubAppModuleManager moduleManager, List<RedeemPoint> usersSelected, DigitalAsset digitalAsset) throws CantGetAssetRedeemPointActorsException {
//        List<RedeemPoint> redeemPoints = new ArrayList<>();
//        redeemPoints.add(new RedeemPoint("Frank Contreras RP", null));
//        redeemPoints.add(new RedeemPoint("Victor Mars RP", null));
//        redeemPoints.add(new RedeemPoint("Nerio Indriago RP", null));
//        redeemPoints.add(new RedeemPoint("Rodrigo Acosta RP", null));
        List<RedeemPoint> redeemPoints = new ArrayList<>();
        List<ActorAssetRedeemPoint> actorAssetRedeemPoints = moduleManager.getRedeemPointsConnectedForAsset(digitalAsset.getAssetPublicKey());
        for (ActorAssetRedeemPoint actorAssetRedeemPoint : actorAssetRedeemPoints) {
            RedeemPoint newUser = new RedeemPoint(actorAssetRedeemPoint.getName(), actorAssetRedeemPoint);
//            int index = usersSelected.indexOf(newUser);
//            if (index > 0) newUser.setSelected(usersSelected.get(index).isSelected());
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

    public static List<User> getConnectedUsers(AssetUserWalletSubAppModuleManager moduleManager) throws CantGetAssetUserActorsException {
        List<User> users = new ArrayList<>();
        List<ActorAssetUser> actorAssetUsers = moduleManager.getAllAssetUserActorConnected();
        for (ActorAssetUser actorAssetUser : actorAssetUsers) {
            User newUser = new User(actorAssetUser.getName(), actorAssetUser);
            users.add(newUser);
        }
        return users;
    }

    public static List<Transaction> getTransactions(AssetUserWalletSubAppModuleManager moduleManager, DigitalAsset digitalAsset) throws CantLoadWalletException, CantGetTransactionsException, CantGetAssetUserActorsException, CantAssetUserActorNotFoundException {
        List<Transaction> transactions = new ArrayList<>();
        List<AssetUserWalletTransaction> assetUserWalletTransactions = moduleManager.loadAssetUserWallet(WalletUtilities.WALLET_PUBLIC_KEY).getTransactionsForDisplay(digitalAsset.getDigitalAsset().getGenesisAddress());
        DAPActor dapActor;
        for (AssetUserWalletTransaction assetUserWalletTransaction :
                assetUserWalletTransactions) {
            if (assetUserWalletTransaction.getTransactionType().equals(TransactionType.CREDIT)) {
                dapActor = assetUserWalletTransaction.getActorFrom();
            } else {
                dapActor = assetUserWalletTransaction.getActorTo();
            }
            Transaction transaction = new Transaction(assetUserWalletTransaction, dapActor);
            transactions.add(transaction);
        }
        return transactions;
    }

    public static List<DigitalAsset> getAllPendingNegotiations(AssetUserWalletSubAppModuleManager moduleManager) throws Exception {
        List<AssetNegotiation> assetNegotiations = moduleManager.getPendingAssetNegotiations();
        List<DigitalAsset> digitalAssets = new ArrayList<>();
        DigitalAsset digitalAsset;

        for (AssetNegotiation asset : assetNegotiations){
            digitalAsset = new DigitalAsset();
            digitalAsset.setAssetPublicKey(asset.getAssetToOffer().getPublicKey());
            digitalAsset.setName(asset.getAssetToOffer().getName());

            UserAssetNegotiation userAssetNegotiation = new UserAssetNegotiation();
            userAssetNegotiation.setNegotiationId(asset.getNegotiationId());
            userAssetNegotiation.setTotalAmmount(asset.getTotalAmount());
            userAssetNegotiation.setAmmountPerUnit(asset.getAmountPerUnity());
            userAssetNegotiation.setQuantityToBuy(asset.getQuantityToBuy());

            digitalAsset.setUserAssetNegotiation(userAssetNegotiation);
            digitalAsset.setExpDate((Timestamp) asset.getAssetToOffer().getContract().getContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE).getValue());
            digitalAsset.setDigitalAsset(asset.getAssetToOffer());
            digitalAssets.add(digitalAsset);

            List<Resource> resources = asset.getAssetToOffer().getResources();
            if(resources != null && !resources.isEmpty()){
                digitalAsset.setImage(resources.get(0).getResourceBinayData());
            }
        }

        return digitalAssets;
    }


}
