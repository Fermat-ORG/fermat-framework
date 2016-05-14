package org.fermat.fermat_dap_android_wallet_redeem_point.models;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWallet;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletList;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletTransaction;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.RedeemPointStatistic;
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
    public static List<DigitalAsset> getAllDigitalAssets(AssetRedeemPointWalletSubAppModule moduleManager) throws Exception {
        List<AssetRedeemPointWalletList> assets = moduleManager.getAssetRedeemPointWalletBalances(WalletUtilities.WALLET_PUBLIC_KEY);
        List<DigitalAsset> digitalAssets = new ArrayList<>();
        DigitalAsset digitalAsset;

        if(assets != null) {
            for (AssetRedeemPointWalletList asset : assets) {
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
        }
        return digitalAssets;
    }

    public static List<UserRedeemed> getUserRedeemedPointList(String walletPublicKey, DigitalAsset digitalAsset, AssetRedeemPointWalletSubAppModule moduleManager) throws Exception {

        AssetRedeemPointWallet wallet = moduleManager.loadAssetRedeemPointWallet(walletPublicKey);
        List<RedeemPointStatistic> all = wallet.getStatisticsByAssetPublicKey(digitalAsset.getAssetPublicKey());
        List<UserRedeemed> userRedeemeds = new ArrayList<>();

        for (RedeemPointStatistic stadistic : all) {
            UserRedeemed user = new UserRedeemed(stadistic.userThatRedeemed().getName(), new Timestamp(stadistic.redemptionTime().getTime()));
            userRedeemeds.add(user);
        }

        /*List<UserRedeemed> userRedeemeds = new ArrayList<>();
        UserRedeemed user= new UserRedeemed("Penny Quintero",new Timestamp(new Date().getTime()));
        userRedeemeds.add(user);
        user= new UserRedeemed("Nerio Indriago",new Timestamp(new Date().getTime()));
        userRedeemeds.add(user);
        user= new UserRedeemed("Jinmy Bohorquez",new Timestamp(new Date().getTime()));
        userRedeemeds.add(user);*/

        return userRedeemeds;
    }

    public static List<Transaction> getTransactions(AssetRedeemPointWalletSubAppModule moduleManager, DigitalAsset digitalAsset) throws CantLoadWalletException, CantGetTransactionsException {
        List<Transaction> transactions = new ArrayList<>();
        List<AssetRedeemPointWalletTransaction> assetRedeemPointWalletTransactions = moduleManager.loadAssetRedeemPointWallet(WalletUtilities.WALLET_PUBLIC_KEY).getTransactionsForDisplay(digitalAsset.getAssetPublicKey());
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
}