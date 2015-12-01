package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockBalance;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantAddCreditCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantAddDebitCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetAvailableBalanceCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetBookedBalanceCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletBalanceRecord;

import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 30/11/15.
 */
public class StockBalanceImpl implements StockBalance {
    private Database database;
    UUID plugin;
    PluginFileSystem pluginFileSystem;
    /**
     * Constructor.
     */
    public StockBalanceImpl(final Database database, final UUID plugin, final PluginFileSystem pluginFileSystem){
        this.database = database;
        this.plugin = plugin;
        this.pluginFileSystem = pluginFileSystem;
    }
    @Override
    public float getBookedBalance() throws CantGetBookedBalanceCryptoBrokerWalletException {
        return 0;
    }

    @Override
    public float getBookedAvailable() throws CantGetAvailableBalanceCryptoBrokerWalletException {
        return 0;
    }

    @Override
    public float getBookedAvailableFrozed() throws CantGetAvailableBalanceCryptoBrokerWalletException {
        return 0;
    }

    @Override
    public List<CryptoBrokerWalletBalanceRecord> getCryptoBrokerWalletBalanceBook() throws CantGetBookedBalanceCryptoBrokerWalletException {
        return null;
    }

    @Override
    public List<CryptoBrokerWalletBalanceRecord> getCryptoBrokerWalletBalanceAvailable() throws CantGetBookedBalanceCryptoBrokerWalletException {
        return null;
    }

    @Override
    public void debit() throws CantAddDebitCryptoBrokerWalletException {

    }

    @Override
    public void credit() throws CantAddCreditCryptoBrokerWalletException {

    }
}
