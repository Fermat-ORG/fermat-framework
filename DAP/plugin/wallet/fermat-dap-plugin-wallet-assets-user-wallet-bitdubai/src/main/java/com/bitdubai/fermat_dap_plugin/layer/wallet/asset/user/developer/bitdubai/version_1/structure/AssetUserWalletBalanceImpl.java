package com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransactionRecord;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.structure.database.AssetUserWalletDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by franklin on 08/10/15.
 */
public class AssetUserWalletBalanceImpl implements com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletBalance {
    private final AssetUserWalletDao assetUserWalletDao;

    /**
     * Constructor.
     */
    public AssetUserWalletBalanceImpl(AssetUserWalletDao assetUserWalletDao) {
        this.assetUserWalletDao = assetUserWalletDao;
    }

    @Override
    public long getBalance() throws CantCalculateBalanceException {
        return assetUserWalletDao.getAvailableBalance();
    }

    @Override
    public List<AssetUserWalletList> getAssetUserWalletBalances() throws CantCalculateBalanceException {
        return assetUserWalletDao.getBalanceByAssets();
    }

    @Override
    public Map<ActorAssetIssuer, AssetUserWalletList> getWalletBalanceByIssuer() throws CantCalculateBalanceException {
        List<AssetUserWalletList> balances = getAssetUserWalletBalances();
        HashMap<ActorAssetIssuer, AssetUserWalletList> toReturn = new HashMap<>();
        for (AssetUserWalletList balance : balances) {
            toReturn.put(assetUserWalletDao.getActorByAsset(balance.getDigitalAsset()), balance);
        }
        return toReturn;
    }

    @Override
    public void debit(AssetUserWalletTransactionRecord assetUserWalletTransactionRecord, BalanceType balanceType) throws CantRegisterDebitException {
        assetUserWalletDao.addDebit(assetUserWalletTransactionRecord, balanceType);
    }

    @Override
    public void credit(AssetUserWalletTransactionRecord assetUserWalletTransactionRecord, BalanceType balanceType) throws CantRegisterCreditException {
        assetUserWalletDao.addCredit(assetUserWalletTransactionRecord, balanceType);
    }
}
