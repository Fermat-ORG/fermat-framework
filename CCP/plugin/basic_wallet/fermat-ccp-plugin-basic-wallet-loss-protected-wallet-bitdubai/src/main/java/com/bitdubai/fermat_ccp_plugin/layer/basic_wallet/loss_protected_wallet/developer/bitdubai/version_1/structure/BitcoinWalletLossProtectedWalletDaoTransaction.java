package com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.loss_protected_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseSystemException;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.loss_protected_wallet.developer.bitdubai.version_1.exceptions.CantExecuteLossProtectedBitcoinTransactionException;

/**
 * Created by ciencias on 7/6/15.
 */
public class BitcoinWalletLossProtectedWalletDaoTransaction {

    /**
     * CryptoAddressBook Interface member variables.
     */
    private Database database;

    /**
     * Constructor
     */

    public  BitcoinWalletLossProtectedWalletDaoTransaction(Database database){
        this.database = database;
    }


    public void executeTransaction(DatabaseTable transactionTable ,DatabaseTableRecord transactionRecord,DatabaseTable updateTable,DatabaseTableRecord updateRecord) throws CantExecuteLossProtectedBitcoinTransactionException{
        try {
            DatabaseTransaction dbTransaction = database.newTransaction();

            dbTransaction.addRecordToInsert(transactionTable, transactionRecord);

            dbTransaction.addRecordToUpdate(updateTable, updateRecord);

            database.executeTransaction(dbTransaction);
        }catch(DatabaseSystemException exception){
            throw new CantExecuteLossProtectedBitcoinTransactionException("Error to insert and update transaction records",exception, null, "Error execute database transaction" );
        }catch(Exception exception){
            throw new CantExecuteLossProtectedBitcoinTransactionException(CantExecuteLossProtectedBitcoinTransactionException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

}
