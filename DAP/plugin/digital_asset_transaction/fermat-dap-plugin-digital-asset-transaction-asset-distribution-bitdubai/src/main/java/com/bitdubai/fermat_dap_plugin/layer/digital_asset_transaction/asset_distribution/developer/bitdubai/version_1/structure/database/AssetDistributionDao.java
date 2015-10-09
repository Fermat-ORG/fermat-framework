package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistDigitalAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 24/09/15.
 */
public class AssetDistributionDao {
    UUID pluginId;
    Database database;
    PluginDatabaseSystem pluginDatabaseSystem;

    public AssetDistributionDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) throws CantExecuteDatabaseOperationException {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        database = openDatabase();
        database.closeDatabase();

    }

    private DatabaseTable getDatabaseTable(String tableName){
        DatabaseTable assetIssuingDatabaseTable = database.getTable(tableName);
        return assetIssuingDatabaseTable;
    }

    private Database openDatabase() throws CantExecuteDatabaseOperationException {
        try {
            return pluginDatabaseSystem.openDatabase(pluginId, AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DATABASE);
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantExecuteDatabaseOperationException(exception, "Opening the Asset Distribution Transaction Database", "Error in database plugin.");
        }
    }
    public void persistDigitalAsset(String genesisTransaction, String localStoragePath, String digitalAssetHash, String actorReceiverPublicKey)throws CantPersistDigitalAssetException{
        try{
            this.database=openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_TABLE_NAME);
            DatabaseTableRecord record = databaseTable.getEmptyRecord();
            record.setStringValue(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_GENESIS_TRANSACTION_COLUMN_NAME, genesisTransaction);
            record.setStringValue(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DIGITAL_ASSET_HASH_COLUMN_NAME, digitalAssetHash);
            record.setStringValue(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DIGITAL_ASSET_STORAGE_LOCAL_PATH_COLUMN_NAME, localStoragePath);
            record.setStringValue(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_ACTOR_ASSET_USER_PUBLIC_KEY_COLUMN_NAME, actorReceiverPublicKey);
            record.setStringValue(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DISTRIBUTION_STATUS_COLUMN_NAME, DistributionStatus.CHECKING_HASH.getCode());
            record.setStringValue(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_PROTOCOL_STATUS_COLUMN_NAME, ProtocolStatus.TO_BE_NOTIFIED.getCode());
            record.setStringValue(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_CRYPTO_STATUS_COLUMN_NAME, CryptoStatus.PENDING_SUBMIT.getCode());
            databaseTable.insertRecord(record);
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantPersistDigitalAssetException(exception, "Persisting a forming genesis digital asset","Cannot open the Asset Distribution database");
        } catch (CantInsertRecordException exception) {
            throw new CantPersistDigitalAssetException(exception, "Persisting a forming genesis digital asset","Cannot insert a record in the Asset Distribution database");
        } catch (Exception exception){
            throw new CantPersistDigitalAssetException(exception, "Persisting a forming genesis digital asset","Unexpected exception");
        }
    }

    public void updateDistributionStatusByGenesisTransaction(DistributionStatus distributionStatus, String genesisTransaction) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        updateStringValueByStringField(distributionStatus.getCode(),
                AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DISTRIBUTION_STATUS_COLUMN_NAME,
                genesisTransaction,
                AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_GENESIS_TRANSACTION_COLUMN_NAME);
    }

    private void updateStringValueByStringField(String value, String columnName, String filterValue, String filterColumn) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        try{
            this.database=openDatabase();
            DatabaseTable databaseTable=this.database.getTable(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_TABLE_NAME);
            databaseTable.setStringFilter(filterColumn, filterValue, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            DatabaseTableRecord databaseTableRecord;
            List<DatabaseTableRecord> databaseTableRecords=databaseTable.getRecords();
            if (databaseTableRecords.size() > 1){
                this.database.closeDatabase();
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.",  filterColumn+": " + filterColumn);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            databaseTableRecord.setStringValue(columnName, value);
            databaseTable.updateRecord(databaseTableRecord);
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE,exception, "Trying to update "+columnName,"Check the cause");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, exception,"Trying to update "+columnName,"Check the cause");
        } catch (Exception exception){
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantExecuteQueryException.DEFAULT_MESSAGE, FermatException.wrapException(exception),"Trying to update "+columnName,"Check the cause");
        }
    }

}
