package com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseSystemException;
import com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.exceptions.CantExecuteBitconTransactionException;

/**
 * Created by ciencias on 7/6/15.
 */
public class BitcoinWalletBasicWalletDaoTransaction {

    /**
     * CryptoAddressBook Interface member variables.
     */
    private Database database;

    private DatabaseTable transactionTable;
    private DatabaseTableRecord transactionRecord;

    private DatabaseTable updateTable;
    private DatabaseTableRecord updateRecord;


    /**
     * Constructor
     */

    public  BitcoinWalletBasicWalletDaoTransaction(Database database){
        this.database = database;
    }


    public void executeTransaction(DatabaseTable transactionTable ,DatabaseTableRecord transactionRecord,DatabaseTable updateTable,DatabaseTableRecord updateRecord) throws CantExecuteBitconTransactionException{
        try {
            DatabaseTransaction dbTransaction = database.newTransaction();

            dbTransaction.addRecordToInsert(transactionTable, transactionRecord);

            dbTransaction.addRecordToUpdate(updateTable, updateRecord);

            database.executeTransaction(dbTransaction);
        }catch(DatabaseSystemException e){
            throw new CantExecuteBitconTransactionException("Error to insert and update transaction records",e, null, "Error execute database transaction" );
        }
    }

}
