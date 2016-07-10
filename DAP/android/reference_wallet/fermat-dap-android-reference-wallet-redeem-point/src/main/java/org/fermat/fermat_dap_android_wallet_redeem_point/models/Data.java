package org.fermat.fermat_dap_android_wallet_redeem_point.models;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;

import org.fermat.fermat_dap_android_wallet_redeem_point.v3.models.DigitalAssetHistory;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletList;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletTransaction;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.RedeemPointStatistic;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by frank on 12/9/15.
 */
public class Data implements Serializable {
    public static List<DigitalAsset> getAllDigitalAssets(AssetRedeemPointWalletSubAppModule moduleManager) throws Exception {
        List<AssetRedeemPointWalletList> assets = moduleManager.getAssetRedeemPointWalletBalances(WalletUtilities.WALLET_PUBLIC_KEY);
        List<DigitalAsset> digitalAssets = new ArrayList<>();
        DigitalAsset digitalAsset;

        if (assets != null) {
            for (AssetRedeemPointWalletList asset : assets) {
                digitalAsset = new DigitalAsset();
                digitalAsset.setAssetPublicKey(asset.getDigitalAsset().getPublicKey());
                digitalAsset.setName(asset.getDigitalAsset().getName());
                digitalAsset.setAvailableBalanceQuantity(asset.getQuantityAvailableBalance());
                digitalAsset.setBookBalanceQuantity(asset.getQuantityBookBalance());
                digitalAsset.setAvailableBalance(asset.getAvailableBalance());
                digitalAsset.setExpDate((Timestamp) asset.getDigitalAsset().getContract().getContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE).getValue());
                digitalAsset.setAssetDescription(asset.getDigitalAsset().getDescription());
                digitalAssets.add(digitalAsset);

                List<Resource> resources = asset.getDigitalAsset().getResources();
                if (resources != null && !resources.isEmpty()) {
                    digitalAsset.setImage(resources.get(0).getResourceBinayData());
                }
            }
        }
        return digitalAssets;
    }


    public static List<DigitalAsset> getAllRedeemPointAssetsNew(AssetRedeemPointWalletSubAppModule moduleManager) throws Exception {
        List<AssetRedeemPointWalletList> assetsRedeemPointBalances = moduleManager.getAssetRedeemPointWalletBalances(WalletUtilities.WALLET_PUBLIC_KEY);
        List<DigitalAsset> digitalAssets = new ArrayList<>();
        DigitalAsset digitalAsset;
        String currentHash = "";

        for (AssetRedeemPointWalletList assetRedeemPointWalletList : assetsRedeemPointBalances) {
            String assetPublicKey = assetRedeemPointWalletList.getDigitalAsset().getPublicKey();
            List<AssetRedeemPointWalletTransaction> transactions = moduleManager.getTransactionsForDisplay(WalletUtilities.WALLET_PUBLIC_KEY, assetPublicKey);

            Map<String, List<AssetRedeemPointWalletTransaction>> mappedTransactionsByHash = new HashMap<>();

            for (AssetRedeemPointWalletTransaction transaction : transactions) {
                if (currentHash.equals(transaction.getTransactionHash())) {
                    mappedTransactionsByHash.get(currentHash).add(transaction);
                } else {
                    currentHash = transaction.getTransactionHash();
                    List<AssetRedeemPointWalletTransaction> sublist = new ArrayList<>();
                    sublist.add(transaction);
                    mappedTransactionsByHash.put(currentHash, sublist);

                }
            }

            for (List<AssetRedeemPointWalletTransaction> list : mappedTransactionsByHash.values()) {
                digitalAsset = new DigitalAsset();
                digitalAsset.setAssetPublicKey(assetRedeemPointWalletList.getDigitalAsset().getPublicKey());
                digitalAsset.setName(assetRedeemPointWalletList.getDigitalAsset().getName());
                digitalAsset.setExpDate((Timestamp) assetRedeemPointWalletList.getDigitalAsset().getContract().getContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE).getValue());
                digitalAsset.setAssetDescription(assetRedeemPointWalletList.getDigitalAsset().getDescription());
                digitalAsset.setAvailableBalance(list.get(list.size() - 1).getAmount());

                digitalAsset.setActorUserNameFrom(list.get(list.size() - 1).getActorFrom().getName());
                digitalAsset.setImageActorUserFrom(list.get(list.size() - 1).getActorFrom().getProfileImage());


                digitalAsset.setActorIssuerNameFrom(assetRedeemPointWalletList.getDigitalAsset().getIdentityAssetIssuer().getAlias());
                digitalAsset.setActorIssuerAddress("Asset Issuer " + digitalAsset.getActorIssuerNameFrom() + " address");
                digitalAsset.setImageActorIssuerFrom(assetRedeemPointWalletList.getDigitalAsset().getIdentityAssetIssuer().getImage());

                digitalAsset.setDate(new Timestamp(list.get(list.size() - 1).getTimestamp()));
                digitalAsset.setStatus(list.get(list.size() - 1).getBalanceType().equals(BalanceType.AVAILABLE) ? DigitalAsset.Status.CONFIRMED : DigitalAsset.Status.PENDING);


                List<Resource> resources = assetRedeemPointWalletList.getDigitalAsset().getResources();
                if (resources != null && !resources.isEmpty()) {
                    digitalAsset.setImage(resources.get(0).getResourceBinayData());
                }

                digitalAssets.add(digitalAsset);
            }

        }

        Collections.sort(digitalAssets, new Comparator<DigitalAsset>() {
            @Override
            public int compare(DigitalAsset lhs, DigitalAsset rhs) {
                if (lhs.getDate().getTime() > rhs.getDate().getTime())
                    return -1;
                else if (lhs.getDate().getTime() < rhs.getDate().getTime())
                    return 1;
                return 0;
            }
        });

        return digitalAssets;
    }


    public static List<UserRedeemed> getUserRedeemedPointList(String walletPublicKey, DigitalAsset digitalAsset, AssetRedeemPointWalletSubAppModule moduleManager) throws Exception {

        List<RedeemPointStatistic> all = moduleManager.getStatisticsByAssetPublicKey(walletPublicKey, digitalAsset.getAssetPublicKey());
        List<UserRedeemed> userRedeemeds = new ArrayList<>();

        for (RedeemPointStatistic stadistic : all) {
            UserRedeemed user = new UserRedeemed(stadistic.userThatRedeemed().getName(), new Timestamp(stadistic.redemptionTime().getTime()));
            userRedeemeds.add(user);
        }

        return userRedeemeds;
    }

    public static List<Transaction> getTransactions(AssetRedeemPointWalletSubAppModule moduleManager, DigitalAsset digitalAsset) throws CantLoadWalletException, CantGetTransactionsException {
        List<Transaction> transactions = new ArrayList<>();
        List<AssetRedeemPointWalletTransaction> assetRedeemPointWalletTransactions = moduleManager.getTransactionsForDisplay(WalletUtilities.WALLET_PUBLIC_KEY, digitalAsset.getAssetPublicKey());
        DAPActor dapActor;
        for (AssetRedeemPointWalletTransaction assetRedeemPointWalletTransaction :
                assetRedeemPointWalletTransactions) {
            if (assetRedeemPointWalletTransaction.getTransactionType().equals(TransactionType.CREDIT)) {
                dapActor = assetRedeemPointWalletTransaction.getActorFrom();
            } else {
                dapActor = assetRedeemPointWalletTransaction.getActorTo();
            }
            Transaction transaction = new Transaction(assetRedeemPointWalletTransaction, dapActor);
            transactions.add(transaction);
        }
        return transactions;
    }

    public static DigitalAsset getDigitalAsset(AssetRedeemPointWalletSubAppModule moduleManager, String digitalAssetPublicKey) throws CantLoadWalletException {
        List<AssetRedeemPointWalletList> balances = moduleManager.getAssetRedeemPointWalletBalances(WalletUtilities.WALLET_PUBLIC_KEY);
        DigitalAsset digitalAsset;
        String publicKey;
        for (AssetRedeemPointWalletList balance : balances) {
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


    public static List<DigitalAssetHistory> getAllAcceptedDigitalAssets(AssetRedeemPointWalletSubAppModule moduleManager) throws Exception {

        List<DigitalAssetHistory> digitalAssets = new ArrayList<>();
        DigitalAssetHistory digitalAsset;
        List<RedeemPointStatistic> allStatistic = moduleManager.getAllStatisticsByWallet(WalletUtilities.WALLET_PUBLIC_KEY);

        for (RedeemPointStatistic stadistic : allStatistic) {
            digitalAsset = new DigitalAssetHistory();
            digitalAsset.setStadicticID(stadistic.statisticId().toString());
            digitalAsset.setAssetPublicKey(stadistic.assetRedeemed().getDigitalAsset().getPublicKey());
            digitalAsset.setHistoryNameAsset(stadistic.assetRedeemed().getDigitalAsset().getName());
            digitalAsset.setExpDate((Timestamp) stadistic.assetRedeemed().getDigitalAsset().getContract().getContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE).getValue());
            digitalAsset.setAcceptedDate(new Timestamp(stadistic.redemptionTime().getTime()));

            digitalAsset.setHistoryNameUser(stadistic.userThatRedeemed().getName());
            digitalAsset.setImageActorUserFrom(stadistic.userThatRedeemed().getProfileImage());
            digitalAsset.setImageAsset(getDigitalAsset(moduleManager, stadistic.assetRedeemed().getDigitalAsset().getPublicKey()).getImage());
            digitalAsset.setActorUserPublicKey(stadistic.userThatRedeemed().getActorPublicKey());

            digitalAssets.add(digitalAsset);

        }
        Collections.sort(digitalAssets);

        return digitalAssets;
    }

}