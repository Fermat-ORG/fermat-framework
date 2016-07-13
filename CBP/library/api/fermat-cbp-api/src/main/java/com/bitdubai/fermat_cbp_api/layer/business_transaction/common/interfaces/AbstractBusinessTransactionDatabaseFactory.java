package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 13/07/16.
 */
public class AbstractBusinessTransactionDatabaseFactory {

    /*protected DatabaseTableFactory getMessageStatusDatabaseTablefactory(
            UUID ownerId,
            DatabaseFactory databaseFactory) throws InvalidOwnerIdException {
        DatabaseTableFactory messageStatusTable = databaseFactory.newTableFactory(
                ownerId,
                AbstractBusinessTransactionDatabaseConstants
                        .BUSINESS_TRANSACTION_MESSAGE_STATUS_TABLE);
        //Add columns
        messageStatusTable.addColumn(AbstractBusinessTransactionDatabaseConstants.BUSINESS_TRANSACTION_CONTRACT_HASH_COLUMN_NAME, DatabaseDataType.TEXT, 100, Boolean.TRUE);
        messageStatusTable.addColumn(AbstractBusinessTransactionDatabaseConstants.BUSINESS_TRANSACTION_SEND_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
        messageStatusTable.addColumn(AbstractBusinessTransactionDatabaseConstants.BUSINESS_TRANSACTION_LAST_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
        messageStatusTable.addColumn(AbstractBusinessTransactionDatabaseConstants.BUSINESS_TRANSACTION_COMPLETION_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
        messageStatusTable.addColumn(AbstractBusinessTransactionDatabaseConstants.BUSINESS_TRANSACTION_FAILED_EVENT_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
        messageStatusTable.addColumn(AbstractBusinessTransactionDatabaseConstants.BUSINESS_TRANSACTION_COUNTER_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.FALSE);
        messageStatusTable.addColumn(AbstractBusinessTransactionDatabaseConstants.BUSINESS_TRANSACTION_RESEND_COUNTER_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.FALSE);
        messageStatusTable.addColumn(AbstractBusinessTransactionDatabaseConstants.BUSINESS_TRANSACTION_SUCCESSFUL_FLAG_TIMESTAMP_COLUMN_NAME, DatabaseDataType.TEXT, 100, Boolean.FALSE);

        messageStatusTable.addIndex(AbstractBusinessTransactionDatabaseConstants.BUSINESS_TRANSACTION_FIRST_KEY_COLUMN);

        return messageStatusTable;
    }*/

}
