package com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.basic_wallet_common_exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.basic_wallet_common_exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.basic_wallet_common_exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletBalance;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransactionRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;

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
     * DealsWithPluginDatabaseSystem Interface member variables.
     */

    /**
     * Constructor.
     */
    public BitcoinWalletBasicWalletAvailableBalance(final Database database){
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
        } catch(CantCalculateBalanceException exception){
            database.closeDatabase();
            throw exception;
        } catch (CantOpenDatabaseException | DatabaseNotFoundException e) {
           throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, e, "", "Check the cause");
        } catch(Exception exception){
            database.closeDatabase();
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception  ), null, null);
        }
    }

    /*
    * NOTA:
    *  El debit y el credit debería mirar primero si la transacción que
    *  se quiere aplicar existe. Si no existe aplica los cambios normalmente, pero si existe
    *  debería ignorar la transacción.
    */
    @Override
    public void debit(BitcoinWalletTransactionRecord cryptoTransaction) throws CantRegisterDebitException {
        try {
            database.openDatabase();
            bitcoinWalletBasicWalletDao = new BitcoinWalletBasicWalletDao(this.database);
            bitcoinWalletBasicWalletDao.addDebit(cryptoTransaction,BalanceType.AVAILABLE);
            database.closeDatabase();
        } catch(CantRegisterDebitException exception){
            database.closeDatabase();
            throw exception;
        } catch (CantOpenDatabaseException | DatabaseNotFoundException e) {
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, e, "", "Check the cause");
        } catch(Exception exception){
            database.closeDatabase();
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public void credit(BitcoinWalletTransactionRecord cryptoTransaction) throws CantRegisterCreditException {
        try {
            database.openDatabase();
            bitcoinWalletBasicWalletDao = new BitcoinWalletBasicWalletDao(this.database);
            bitcoinWalletBasicWalletDao.addCredit(cryptoTransaction,BalanceType.AVAILABLE);
            database.closeDatabase();
        } catch(CantRegisterCreditException exception){
            database.closeDatabase();
            throw exception;
        } catch (CantOpenDatabaseException | DatabaseNotFoundException e) {
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, e, "", "Check the cause");
        } catch(Exception exception){
            database.closeDatabase();
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }
}
