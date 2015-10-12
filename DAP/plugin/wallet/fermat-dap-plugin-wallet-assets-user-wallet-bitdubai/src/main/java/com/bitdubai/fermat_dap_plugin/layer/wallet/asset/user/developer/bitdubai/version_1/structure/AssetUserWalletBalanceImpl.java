package com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransactionRecord;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.*;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.structure.database.AssetUserWalletDao;

import java.util.List;

/**
 * Created by franklin on 08/10/15.
 */
public class AssetUserWalletBalanceImpl implements com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletBalance {
    private Database database;
    private AssetUserWalletDao assetUserWalletDao;
    /**
     * Constructor.
     */
    public AssetUserWalletBalanceImpl(final Database database){
        this.database = database;
    }

    @Override
    public long getBalance() throws CantCalculateBalanceException {
        assetUserWalletDao = new AssetUserWalletDao(database);
        return assetUserWalletDao.getAvailableBalance();
    }

    @Override
    public List<AssetUserWalletList> getAssetUserWalletBalancesAvailable() throws CantCalculateBalanceException {
        assetUserWalletDao = new AssetUserWalletDao(database);
        return assetUserWalletDao.getAvailableBalanceByAsset();
    }

    @Override
    public List<AssetUserWalletList> getAssetUserWalletBalancesBook() throws CantCalculateBalanceException {
        assetUserWalletDao = new AssetUserWalletDao(database);
        return assetUserWalletDao.getBookBalanceByAssets();
    }

    @Override
    public void debit(AssetUserWalletTransactionRecord assetUserWalletTransactionRecord, BalanceType balanceType) throws CantRegisterDebitException {
        assetUserWalletDao = new AssetUserWalletDao(database);
        assetUserWalletDao.addDebit(assetUserWalletTransactionRecord, balanceType);
    }

    @Override
    public void credit(AssetUserWalletTransactionRecord assetUserWalletTransactionRecord, BalanceType balanceType) throws CantRegisterCreditException {
        assetUserWalletDao = new AssetUserWalletDao(database);
        assetUserWalletDao.addCredit(assetUserWalletTransactionRecord, balanceType);
    }
}
