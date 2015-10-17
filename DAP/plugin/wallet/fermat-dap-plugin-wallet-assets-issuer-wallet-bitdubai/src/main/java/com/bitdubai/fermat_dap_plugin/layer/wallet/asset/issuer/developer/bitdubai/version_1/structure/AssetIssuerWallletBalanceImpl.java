package com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.*;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1.structure.database.AssetIssuerWalletDao;

import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 29/09/15.
 */
public class AssetIssuerWallletBalanceImpl implements com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletBalance {
    private Database database;
    private AssetIssuerWalletDao assetIssuerWalletDao;
    UUID plugin;
    PluginFileSystem pluginFileSystem;
    /**
     * Constructor.
     */
    public AssetIssuerWallletBalanceImpl(final Database database, final UUID plugin, final PluginFileSystem pluginFileSystem){
        this.database = database;
        this.plugin = plugin;
        this.pluginFileSystem = pluginFileSystem;
    }

    @Override
    public long getBalance() throws CantCalculateBalanceException {
        assetIssuerWalletDao = new AssetIssuerWalletDao(database);
        assetIssuerWalletDao.setPlugin(plugin);

        return assetIssuerWalletDao.getAvailableBalance();
    }

    @Override
    public List<AssetIssuerWalletList> getAssetIssuerWalletBalancesAvailable() throws CantCalculateBalanceException {
        assetIssuerWalletDao = new AssetIssuerWalletDao(database);
        assetIssuerWalletDao.setPlugin(plugin);
        assetIssuerWalletDao.setPluginFileSystem(pluginFileSystem);
        return assetIssuerWalletDao.getAvailableBalanceByAsset();
    }

    @Override
    public List<AssetIssuerWalletList> getAssetIssuerWalletBalancesBook() throws CantCalculateBalanceException {
        assetIssuerWalletDao = new AssetIssuerWalletDao(database);
        assetIssuerWalletDao.setPlugin(plugin);
        assetIssuerWalletDao.setPluginFileSystem(pluginFileSystem);
        return assetIssuerWalletDao.getBookBalanceByAssets();
    }

    @Override
    public void debit(AssetIssuerWalletTransactionRecord assetIssuerWalletTransactionRecord, BalanceType balanceType) throws CantRegisterDebitException {
        assetIssuerWalletDao = new AssetIssuerWalletDao(database);
        assetIssuerWalletDao.setPlugin(plugin);
        assetIssuerWalletDao.addDebit(assetIssuerWalletTransactionRecord, balanceType);
    }

    @Override
    public void credit(AssetIssuerWalletTransactionRecord assetIssuerWalletTransactionRecord, BalanceType balanceType) throws CantRegisterCreditException {
        assetIssuerWalletDao = new AssetIssuerWalletDao(database);
        assetIssuerWalletDao.setPlugin(plugin);
        assetIssuerWalletDao.setPluginFileSystem(pluginFileSystem);
        assetIssuerWalletDao.addCredit(assetIssuerWalletTransactionRecord, balanceType);
    }
}
