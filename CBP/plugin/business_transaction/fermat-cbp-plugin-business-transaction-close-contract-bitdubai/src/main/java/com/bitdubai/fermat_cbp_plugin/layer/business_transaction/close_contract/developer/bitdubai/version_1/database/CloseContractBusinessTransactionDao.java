package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_api.all_definition.contract.Contract;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.enums.ContractType;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.enums.TransactionTransmissionStates;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.exceptions.CantInitializeCloseContractBusinessTransactionDatabaseException;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/12/15.
 */
public class CloseContractBusinessTransactionDao {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId            ;

    private Database database;

    public CloseContractBusinessTransactionDao(final PluginDatabaseSystem pluginDatabaseSystem,
                                              final UUID                 pluginId           ,
                                              final Database database) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
        this.database = database;
    }

    public void initialize() throws CantInitializeCloseContractBusinessTransactionDatabaseException {
        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    CloseContractBusinessTransactionDatabaseConstants.DATABASE_NAME
            );

        } catch (DatabaseNotFoundException e) {

            try {

                CloseContractBusinessTransactionDatabaseFactory databaseFactory = new CloseContractBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(
                        pluginId,
                        CloseContractBusinessTransactionDatabaseConstants.DATABASE_NAME
                );

            } catch (CantCreateDatabaseException f) {

                throw new CantInitializeCloseContractBusinessTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {

                throw new CantInitializeCloseContractBusinessTransactionDatabaseException(CantInitializeCloseContractBusinessTransactionDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }

        } catch (CantOpenDatabaseException e) {

            throw new CantInitializeCloseContractBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {

            throw new CantInitializeCloseContractBusinessTransactionDatabaseException(CantInitializeCloseContractBusinessTransactionDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    /**
     * Returns the Database
     *
     * @return Database
     */
    private Database getDataBase() {
        return database;
    }

    /**
     * Returns the Open Contract DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseContractTable() {
        return getDataBase().getTable(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_TABLE_NAME);
    }

    /**
     * Returns the Open Contract Events DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseEventsTable() {
        return getDataBase().getTable(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_EVENTS_RECORDED_TABLE_NAME);
    }

    public void persistContractRecord(Contract contractRecord, ContractType contractType) throws CantInsertRecordException {

        ContractTransactionStatus contractTransactionStatus=ContractTransactionStatus.CLOSING_CONTRACT;
        TransactionTransmissionStates transactionTransmissionStates=TransactionTransmissionStates.NOT_READY_TO_SEND;
        DatabaseTable databaseTable=getDatabaseContractTable();
        DatabaseTableRecord databaseTableRecord=databaseTable.getEmptyRecord();
        databaseTableRecord=buildDatabaseRecord(databaseTableRecord,
                contractRecord,
                contractTransactionStatus,
                transactionTransmissionStates,
                contractType);
        databaseTable.insertRecord(databaseTableRecord);

    }

    /**
     * Returns a DatabaseTableRecord from a contract
     * @param record
     * @param contractRecord
     * @param contractTransactionStatus
     * @param transactionTransmissionStates
     * @param contractType
     * @return
     */
    private DatabaseTableRecord buildDatabaseRecord(DatabaseTableRecord record,
                                                    Contract contractRecord,
                                                    ContractTransactionStatus contractTransactionStatus,
                                                    TransactionTransmissionStates transactionTransmissionStates,
                                                    ContractType contractType) {

        UUID transactionId=UUID.randomUUID();
        String contractXML= XMLParser.parseObject(contractRecord);
        record.setUUIDValue(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_TRANSACTION_ID_COLUMN_NAME, transactionId);
        record.setStringValue(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_NEGOTIATION_ID_COLUMN_NAME, contractRecord.getNegotiatiotId());
        record.setStringValue(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_CONTRACT_HASH_COLUMN_NAME, contractRecord.getContractId());
        record.setStringValue(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_CONTRACT_STATUS_COLUMN_NAME, contractRecord.getStatus().getCode());
        record.setStringValue(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME, contractTransactionStatus.getCode());
        record.setStringValue(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_TRANSMISSION_STATUS_COLUMN_NAME, transactionTransmissionStates.getCode());
        record.setStringValue(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_CONTRACT_TYPE_COLUMN_NAME, contractType.getCode());
        record.setStringValue(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_CONTRACT_XML_COLUMN_NAME, contractXML);

        return record;
    }

}
