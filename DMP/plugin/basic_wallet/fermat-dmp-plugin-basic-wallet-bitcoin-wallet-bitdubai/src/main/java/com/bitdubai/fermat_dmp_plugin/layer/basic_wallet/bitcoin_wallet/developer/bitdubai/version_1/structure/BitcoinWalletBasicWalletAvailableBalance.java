package com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterDebitDebitException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletBalance;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransactionRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;

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
    public long getBalance() throws CantCalculateBalanceException{
        try {
            database.openDatabase();
            bitcoinWalletBasicWalletDao = new BitcoinWalletBasicWalletDao(this.database);
            long balance = bitcoinWalletBasicWalletDao.getAvailableBalance();
            database.closeDatabase();
            return balance;
        } catch (CantOpenDatabaseException | DatabaseNotFoundException e) {
           throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, e, "", "Check the cause");
        } catch(CantCalculateBalanceException exception){
            database.closeDatabase();
            throw exception;
        }
    }

    /*
    * NOTA:
    *  El debit y el credit debería mirar primero si la tramsacción que
    *  se quiere aplicar existe. Si no existe aplica los cambios normalmente, pero si existe
    *  debería ignorar la transacción.
    */
    @Override
    public void debit(BitcoinWalletTransactionRecord cryptoTransaction) throws CantRegisterDebitDebitException {
        try {
            database.openDatabase();
            bitcoinWalletBasicWalletDao = new BitcoinWalletBasicWalletDao(this.database);
            bitcoinWalletBasicWalletDao.addDebit(cryptoTransaction,BalanceType.AVILABLE);
            database.closeDatabase();
        } catch (CantOpenDatabaseException | DatabaseNotFoundException e) {
            throw new CantRegisterDebitDebitException(CantRegisterDebitDebitException.DEFAULT_MESSAGE, e, "", "Check the cause");
        } catch(CantRegisterDebitDebitException exception){
            database.closeDatabase();
            throw exception;
        }
    }

    @Override
    public void credit(BitcoinWalletTransactionRecord cryptoTransaction) throws CantRegisterCreditException {
        try {
            database.openDatabase();
            bitcoinWalletBasicWalletDao = new BitcoinWalletBasicWalletDao(this.database);
            bitcoinWalletBasicWalletDao.addCredit(cryptoTransaction,BalanceType.AVILABLE);
            database.closeDatabase();
        } catch (CantOpenDatabaseException | DatabaseNotFoundException e) {
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, e, "", "Check the cause");
        } catch(CantRegisterCreditException exception){
            database.closeDatabase();
            throw exception;
        }
    }
}
