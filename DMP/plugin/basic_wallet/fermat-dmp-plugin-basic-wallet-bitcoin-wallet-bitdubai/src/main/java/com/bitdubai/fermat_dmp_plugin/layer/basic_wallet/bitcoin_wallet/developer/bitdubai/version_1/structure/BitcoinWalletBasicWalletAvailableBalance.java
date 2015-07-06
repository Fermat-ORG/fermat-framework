package com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterDebitDebitException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletBalance;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransactionRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.UUID;

/**
 * Created by ciencias on 7/6/15.
 */
public class BitcoinWalletBasicWalletAvailableBalance implements BitcoinWalletBalance {

    /**
     * BitcoinWalletBasicWallet member variables.
     */
    private Database database;

    private BitcoinWalletBasicWalletDao bitcoinWalletBasicWalletDao;

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;


    /**
     * Constructor.
     */
    public BitcoinWalletBasicWalletAvailableBalance(ErrorManager errorManager,PluginDatabaseSystem pluginDatabaseSystem,Database database){

        this.errorManager = errorManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.database = database;
    }
    @Override
    public long getBalance() throws CantCalculateBalanceException
    {
        long balance;
        bitcoinWalletBasicWalletDao = new BitcoinWalletBasicWalletDao(this.database);

        balance = bitcoinWalletBasicWalletDao.getAvilableBalance();

        return balance;
    }








    /*
    * NOTA:
    *  El debit y el credit debería mirar primero si la tramsacción que
    *  se quiere aplicar existe. Si no existe aplica los cambios normalmente, pero si existe
    *  debería ignorar la transacción.
    */
    @Override
    public void debit(BitcoinWalletTransactionRecord cryptoTransaction) throws CantRegisterDebitDebitException {

        bitcoinWalletBasicWalletDao = new BitcoinWalletBasicWalletDao(this.database);

        bitcoinWalletBasicWalletDao.addDebit(cryptoTransaction);
    }

    @Override
    public void credit(BitcoinWalletTransactionRecord cryptoTransaction) throws CantRegisterCreditException {

        bitcoinWalletBasicWalletDao = new BitcoinWalletBasicWalletDao(this.database);

        bitcoinWalletBasicWalletDao.addCredit(cryptoTransaction);
    }
}
