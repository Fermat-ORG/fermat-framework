package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 13/07/16.
 */
public class AbstractBusinessTransactionDao
        /*<T extends AbstractPlugin,
        C extends AbstractBusinessTransactionDatabaseConstants>*/ {

    /*private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private T pluginRoot;
    private Database database;

    public AbstractBusinessTransactionDao(
            final PluginDatabaseSystem pluginDatabaseSystem,
            final UUID pluginId,
            final Database database,
            final T pluginRoot) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
        this.database             = database            ;
        this.pluginRoot = pluginRoot        ;
    }

    /**
     * Returns the Database
     *
     * @return Database
     */
    /*protected Database getDataBase() {
        return database;
    }*/

    /**
     * Returns the Ack Offline Payment Events DatabaseTable
     *
     * @return DatabaseTable
     */
    /*protected DatabaseTable getMessageStatusTable() {
        return getDataBase().getTable(
                C.BUSINESS_TRANSACTION_MESSAGE_STATUS_TABLE);
    }

    public void registerTransaction(String contractHash){
        if(isContractHashRegisteredInMessageStatusTable(contractHash)){
            //In this case, I don't want to create another register
            return;
        }
        try {
            DatabaseTable databaseTable = getMessageStatusTable();
            DatabaseTableRecord statusRecord = databaseTable.getEmptyRecord();
            //Contract Hash
            statusRecord.setStringValue(
                    C.BUSINESS_TRANSACTION_CONTRACT_HASH_COLUMN_NAME,
                    contractHash);
            //First timestamp
            long
            statusRecord.setLongValue(
                    C.BUSINESS_TRANSACTION_SEND_TIMESTAMP_COLUMN_NAME,
                    contractHash);
        }
    }

    /**
     * This method checks if a contract hash is registered in message status table
     * Returns <b>true</b> if the record exists in database
     * @param contractHash
     * @return
     */
    /*private boolean isContractHashRegisteredInMessageStatusTable(String contractHash)
            throws CantLoadTableToMemoryException {
        DatabaseTable databaseTable = getMessageStatusTable();
        databaseTable.addStringFilter(
                C.BUSINESS_TRANSACTION_CONTRACT_HASH_COLUMN_NAME,
                contractHash,
                DatabaseFilterType.EQUAL);
        databaseTable.loadToMemory();
        List<DatabaseTableRecord> records = databaseTable.getRecords();
        if(records==null||records.isEmpty()){
            return false;
        }
        return true;
    }*/

}
