package com.bitdubai.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.bitdubai.version_1.structure.functional;

import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletTransactionRecord;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.bitdubai.version_1.structure.database.AssetRedeemPointWalletDao;

import java.util.List;

/**
 * Created by franklin on 16/10/15.
 */
public class AssetRedeemPointWalletBalanceImpl implements com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletBalance {

    private AssetRedeemPointWalletDao assetRedeemPointWalletDao;

    /**
     * Constructor.
     */
    public AssetRedeemPointWalletBalanceImpl(AssetRedeemPointWalletDao assetRedeemPointWalletDao) {
        this.assetRedeemPointWalletDao = assetRedeemPointWalletDao;
    }

    @Override
    public long getBalance() throws CantCalculateBalanceException {
        return assetRedeemPointWalletDao.getAvailableBalance();
    }

    @Override
    public List<AssetRedeemPointWalletList> getAssetIssuerWalletBalances() throws CantCalculateBalanceException {
        return assetRedeemPointWalletDao.getBalanceByAssets();
    }

    @Override
    public void debit(AssetRedeemPointWalletTransactionRecord assetRedeemPointWalletTransactionRecord, BalanceType balanceType) throws CantRegisterDebitException {
        assetRedeemPointWalletDao.addDebit(assetRedeemPointWalletTransactionRecord, balanceType);
    }

    @Override
    public void credit(AssetRedeemPointWalletTransactionRecord assetRedeemPointWalletTransactionRecord, BalanceType balanceType) throws CantRegisterCreditException {
        assetRedeemPointWalletDao.addCredit(assetRedeemPointWalletTransactionRecord, balanceType);
    }
}
