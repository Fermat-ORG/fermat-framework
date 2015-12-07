package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockBalance;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantAddCreditCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantAddDebitCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetAvailableBalanceCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetBookedBalanceCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletBalanceRecord;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerWalletDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantAddCreditException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantAddDebitException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantGetBalanceRecordException;

import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 30/11/15.
 */
public class StockBalanceImpl implements StockBalance {
    //TODO: Documentar y manejo de excepciones
    private Database database;
    private CryptoBrokerWalletDatabaseDao cryptoBrokerWalletDatabaseDao;
    UUID plugin;
    PluginFileSystem pluginFileSystem;

    /**
     * Constructor.
     */
    public StockBalanceImpl(final Database database, final UUID plugin, final PluginFileSystem pluginFileSystem){
        this.database = database;
        this.plugin = plugin;
        this.pluginFileSystem = pluginFileSystem;

        cryptoBrokerWalletDatabaseDao = new CryptoBrokerWalletDatabaseDao(this.database);
        cryptoBrokerWalletDatabaseDao.setPlugin(this.plugin);
        cryptoBrokerWalletDatabaseDao.setPluginFileSystem(this.pluginFileSystem);
    }
    @Override
    public float getBookedBalance(FermatEnum merchandise) throws CantGetBookedBalanceCryptoBrokerWalletException {
        return cryptoBrokerWalletDatabaseDao.getBookedBalance(null);
    }

    @Override
    public float getAvailableBalance(FermatEnum merchandise) throws CantGetAvailableBalanceCryptoBrokerWalletException {
        return cryptoBrokerWalletDatabaseDao.geAvailableBalance(null);
    }

    @Override
    public float getAvailableBalanceFrozen(FermatEnum merchandise) throws CantGetAvailableBalanceCryptoBrokerWalletException {
        return cryptoBrokerWalletDatabaseDao.getAvailableBalanceFrozen(null);
    }

    @Override
    public List<CryptoBrokerWalletBalanceRecord> getCryptoBrokerWalletBalanceBook() throws CantGetBookedBalanceCryptoBrokerWalletException {
        List<CryptoBrokerWalletBalanceRecord> cryptoBrokerWalletBalanceRecords = null;
        try {
            cryptoBrokerWalletBalanceRecords =  cryptoBrokerWalletDatabaseDao.getAvailableBalanceByMerchandise();
        } catch (CantCalculateBalanceException e) {
            e.printStackTrace();
        } catch (CantGetBalanceRecordException e) {
            e.printStackTrace();
        }
        return cryptoBrokerWalletBalanceRecords;
    }

    @Override
    public List<CryptoBrokerWalletBalanceRecord> getCryptoBrokerWalletBalanceAvailable() throws CantGetBookedBalanceCryptoBrokerWalletException {
        List<CryptoBrokerWalletBalanceRecord> cryptoBrokerWalletBalanceRecords = null;
        try {
            cryptoBrokerWalletBalanceRecords = cryptoBrokerWalletDatabaseDao.getBookBalanceByMerchandise();
        } catch (CantCalculateBalanceException e) {
            e.printStackTrace();
        } catch (CantGetBalanceRecordException e) {
            e.printStackTrace();
        }
        return cryptoBrokerWalletBalanceRecords;
    }

    @Override
    public List<CryptoBrokerWalletBalanceRecord> getCryptoBrokerWalletBalanceBookFrozen() throws CantGetBookedBalanceCryptoBrokerWalletException {
        List<CryptoBrokerWalletBalanceRecord> cryptoBrokerWalletBalanceRecords = null;
        try {
            cryptoBrokerWalletBalanceRecords = cryptoBrokerWalletDatabaseDao.getBookBalanceByMerchandiseFrozen();
        } catch (CantCalculateBalanceException e) {
            e.printStackTrace();
        }
        return cryptoBrokerWalletBalanceRecords;
    }

    @Override
    public List<CryptoBrokerWalletBalanceRecord> getCryptoBrokerWalletBalanceAvailableFrozen() throws CantGetBookedBalanceCryptoBrokerWalletException {
        List<CryptoBrokerWalletBalanceRecord> cryptoBrokerWalletBalanceRecords = null;
        try {
            cryptoBrokerWalletBalanceRecords = cryptoBrokerWalletDatabaseDao.getAvailableBalanceByMerchandiseFrozen();
        } catch (CantCalculateBalanceException e) {
            e.printStackTrace();
        }
        return cryptoBrokerWalletBalanceRecords;
    }

    @Override
    public void debit(CryptoBrokerStockTransactionRecord cryptoBrokerStockTransactionRecord, BalanceType balanceType) throws CantAddDebitCryptoBrokerWalletException {
        try {
            cryptoBrokerWalletDatabaseDao.addDebit(cryptoBrokerStockTransactionRecord, balanceType);
        } catch (CantAddDebitException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void credit(CryptoBrokerStockTransactionRecord cryptoBrokerStockTransactionRecord, BalanceType balanceType) throws CantAddCreditCryptoBrokerWalletException {
        try {
            cryptoBrokerWalletDatabaseDao.addCredit(cryptoBrokerStockTransactionRecord, balanceType);
        } catch (CantAddCreditException e) {
            e.printStackTrace();
        }
    }
}
