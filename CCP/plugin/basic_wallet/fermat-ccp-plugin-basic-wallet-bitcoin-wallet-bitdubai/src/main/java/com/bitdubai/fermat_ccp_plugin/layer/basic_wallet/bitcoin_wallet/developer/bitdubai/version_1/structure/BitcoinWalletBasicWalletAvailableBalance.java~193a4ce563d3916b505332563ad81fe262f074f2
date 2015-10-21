package com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletBalance;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransactionRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;

/**
 * Created by ciencias on 7/6/15.
 *
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
            bitcoinWalletBasicWalletDao = new BitcoinWalletBasicWalletDao(this.database);
            return bitcoinWalletBasicWalletDao.getAvailableBalance();
        } catch(CantCalculateBalanceException exception){
            throw exception;
        } catch(Exception exception){
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
            bitcoinWalletBasicWalletDao = new BitcoinWalletBasicWalletDao(this.database);
            bitcoinWalletBasicWalletDao.addDebit(cryptoTransaction,BalanceType.AVAILABLE);
        } catch(CantRegisterDebitException exception){
            throw exception;
        } catch(Exception exception){
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public void credit(BitcoinWalletTransactionRecord cryptoTransaction) throws CantRegisterCreditException {
        try {
            bitcoinWalletBasicWalletDao = new BitcoinWalletBasicWalletDao(this.database);
            bitcoinWalletBasicWalletDao.addCredit(cryptoTransaction,BalanceType.AVAILABLE);
        } catch(CantRegisterCreditException exception){
            throw exception;
        } catch(Exception exception){
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }
}
