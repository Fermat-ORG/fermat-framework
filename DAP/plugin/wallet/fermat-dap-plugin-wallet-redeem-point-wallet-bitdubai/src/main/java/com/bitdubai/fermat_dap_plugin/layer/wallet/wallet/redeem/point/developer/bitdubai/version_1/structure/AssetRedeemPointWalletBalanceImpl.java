package com.bitdubai.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.*;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.bitdubai.version_1.structure.database.AssetRedeemPointWalletDao;

import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 16/10/15.
 */
public class AssetRedeemPointWalletBalanceImpl implements com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletBalance {
    private Database database;
    private AssetRedeemPointWalletDao assetRedeemPointWalletDao;
    UUID plugin;
    PluginFileSystem pluginFileSystem;
    /**
     * Constructor.
     */
    public AssetRedeemPointWalletBalanceImpl(final Database database, final UUID plugin, final PluginFileSystem pluginFileSystem){
        this.database = database;
        this.plugin = plugin;
        this.pluginFileSystem = pluginFileSystem;
    }
    @Override
    public long getBalance() throws CantCalculateBalanceException {
        assetRedeemPointWalletDao = new AssetRedeemPointWalletDao(database);
        assetRedeemPointWalletDao.setPlugin(plugin);
        return assetRedeemPointWalletDao.getAvailableBalance();
    }

    @Override
    public List<AssetRedeemPointWalletList> getAssetIssuerWalletBalancesAvailable() throws CantCalculateBalanceException {
        assetRedeemPointWalletDao = new AssetRedeemPointWalletDao(database);
        assetRedeemPointWalletDao.setPlugin(plugin);
        assetRedeemPointWalletDao.setPluginFileSystem(pluginFileSystem);
        return assetRedeemPointWalletDao.getAvailableBalanceByAsset();
    }

    @Override
    public List<AssetRedeemPointWalletList> getAssetIssuerWalletBalancesBook() throws CantCalculateBalanceException {
        assetRedeemPointWalletDao = new AssetRedeemPointWalletDao(database);
        assetRedeemPointWalletDao.setPlugin(plugin);
        assetRedeemPointWalletDao.setPluginFileSystem(pluginFileSystem);
        return assetRedeemPointWalletDao.getBookBalanceByAssets();
    }

    @Override
    public void debit(AssetRedeemPointWalletTransactionRecord assetRedeemPointWalletTransactionRecord, BalanceType balanceType) throws CantRegisterDebitException {
        assetRedeemPointWalletDao = new AssetRedeemPointWalletDao(database);
        assetRedeemPointWalletDao.setPlugin(plugin);
        assetRedeemPointWalletDao.addDebit(assetRedeemPointWalletTransactionRecord, balanceType);
    }

    @Override
    public void credit(AssetRedeemPointWalletTransactionRecord assetRedeemPointWalletTransactionRecord, BalanceType balanceType) throws CantRegisterCreditException {
        assetRedeemPointWalletDao = new AssetRedeemPointWalletDao(database);
        assetRedeemPointWalletDao.setPlugin(plugin);
        assetRedeemPointWalletDao.setPluginFileSystem(pluginFileSystem);
        assetRedeemPointWalletDao.addCredit(assetRedeemPointWalletTransactionRecord, balanceType);
    }
}
