package com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.discount_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.enums.CryptoValueChunkStatus;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;

/**
 * Created by eze on 28/04/15.
 */

    /*
     * This wallet manage crypto currency to store the value of the user money.
     * The user never see this fact, he just see fiat money. So this wallet will
     * buy crypto currency when the user deposit new money.
     * The balance is the sum of the fiat money the user has deposited and hasn't spent.
     *
     * This class encapsulates the algorithm to calculate the balance of the wallet.
     */

class BasicWalletBalance {
    private Database database;

    void setDatabase(Database database) {
        this.database = database;
    }

    /*
     * balance must iterate over the ValueChunks table and sum the
     * fiat amount of money of the records with Status UNSPENT
     */
    long getBalance() throws CantCalculateBalanceException {
        long balance = 0;
        DatabaseTable valueChunksTable = database.getTable(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_NAME);

        // We set the filter to get the UNSPENT chunks
        valueChunksTable.setStringFilter(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_STATUS_COLUMN_NAME, CryptoValueChunkStatus.UNSPENT.getCode(), DatabaseFilterType.EQUAL);

        // now we apply the filter
        try {
            valueChunksTable.loadToMemory();
        }
        catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantLoadTableToMemoryException: " + cantLoadTableToMemory.getMessage());
            cantLoadTableToMemory.printStackTrace();
            throw new CantCalculateBalanceException();
        }

        // and finally we calculate the balance
        for(DatabaseTableRecord record : valueChunksTable.getRecords())
            balance += record.getLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME);

        return balance;

    }
}