package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantGetAssetRedeemPointActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransaction;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
            digitalAsset.setAvailableBalance(asset.getAvailableBalance());
            digitalAsset.setExpDate((Timestamp) asset.getDigitalAsset().getContract().getContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE).getValue());

            digitalAssets.add(digitalAsset);

            List<Resource> resources = asset.getDigitalAsset().getResources();
            if (resources != null && !resources.isEmpty()) {
                digitalAsset.setImage(resources.get(0).getResourceBinayData());
            }
        }
        return digitalAssets;
    }

    public static DigitalAsset getDigitalAsset(AssetUserWalletSubAppModuleManager moduleManager, String digitalAssetPublicKey) throws CantLoadWalletException {
        List<AssetUserWalletList> balances = moduleManager.getAssetUserWalletBalances("walletPublicKeyTest");
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
            actorAssetRedeemPoints.add(redeemPoint.getActorAssetRedeemPoint());
        }
        return actorAssetRedeemPoints;
    }

    public static List<Transaction> getTransactionsSent(AssetUserWalletSubAppModuleManager moduleManager, DigitalAsset digitalAsset) throws CantLoadWalletException, CantGetTransactionsException, CantGetAssetUserActorsException, CantAssetUserActorNotFoundException {
//        List<Transaction> transactions = new ArrayList<>();
//        List<AssetUserWalletTransaction> assetUserWalletTransactions = moduleManager.loadAssetUserWallet("walletPublicKeyTest").getTransactions(BalanceType.AVAILABLE, TransactionType.DEBIT, digitalAsset.getAssetPublicKey());
//        ActorAssetUser actorAssetUser;
//        for (AssetUserWalletTransaction assetUserWalletTransaction :
//             assetUserWalletTransactions) {
//            actorAssetUser = moduleManager.getActorByPublicKey(assetUserWalletTransaction.getActorFromPublicKey());
//            Transaction transaction = new Transaction();
//            transaction.setAssetUserWalletTransaction(assetUserWalletTransaction);
//            transaction.setUserName(actorAssetUser.getName());
//            transaction.setAmount(assetUserWalletTransaction.getAmount());
//            transaction.setTransactionUserName(moduleManager.getActorRegisteredByPublicKey(assetUserWalletTransaction.getActorToPublicKey()).getName());
//            transaction.setDate(new Timestamp(assetUserWalletTransaction.getTimestamp()));
//            transaction.setImagePerson(actorAssetUser.getProfileImage());
//        }
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction();
        transaction.setUserName("Prueba");
        transaction.setAmount(1.12);
        transaction.setTransactionUserName("Prueba");
        transaction.setDate(new Timestamp(new Date().getTime()));
        transactions.add(transaction);
        return transactions;
    }

    public static List<Transaction> getTransactionsRecieved(AssetUserWalletSubAppModuleManager moduleManager, DigitalAsset digitalAsset) throws CantLoadWalletException, CantGetTransactionsException, CantGetAssetUserActorsException, CantAssetUserActorNotFoundException {
//        List<Transaction> transactions = new ArrayList<>();
//        List<AssetUserWalletTransaction> assetUserWalletTransactions = moduleManager.loadAssetUserWallet("walletPublicKeyTest").getTransactions(BalanceType.AVAILABLE, TransactionType.CREDIT, digitalAsset.getAssetPublicKey());
//        ActorAssetUser actorAssetUser;
//        for (AssetUserWalletTransaction assetUserWalletTransaction :
//                assetUserWalletTransactions) {
//            actorAssetUser = moduleManager.getActorByPublicKey(assetUserWalletTransaction.getActorFromPublicKey());
//            Transaction transaction = new Transaction();
//            transaction.setAssetUserWalletTransaction(assetUserWalletTransaction);
//            transaction.setUserName(moduleManager.getActorRegisteredByPublicKey(assetUserWalletTransaction.getActorFromPublicKey()).getName());
//            transaction.setAmount(assetUserWalletTransaction.getAmount());
//            transaction.setTransactionUserName(moduleManager.getActorByPublicKey(assetUserWalletTransaction.getActorToPublicKey()).getName());
//            transaction.setDate(new Timestamp(assetUserWalletTransaction.getTimestamp()));
//            transaction.setImagePerson(actorAssetUser.getProfileImage());
//        }
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction();
        transaction.setUserName("Prueba");
        transaction.setAmount(1.12);
        transaction.setTransactionUserName("Prueba");
        transaction.setDate(new Timestamp(new Date().getTime()));
        transactions.add(transaction);
        return transactions;
    }
}
