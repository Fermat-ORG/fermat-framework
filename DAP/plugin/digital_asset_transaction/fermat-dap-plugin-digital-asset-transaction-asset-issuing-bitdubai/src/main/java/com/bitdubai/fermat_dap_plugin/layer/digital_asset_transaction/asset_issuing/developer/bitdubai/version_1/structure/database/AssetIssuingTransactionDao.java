package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.TransactionStatus;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCheckAssetIssuingProgressException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantPersistDigitalAssetException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.UnexpectedResultReturnedFromDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 31/08/15.
 */
public class AssetIssuingTransactionDao {

    UUID pluginId;
    Database database;

    PluginDatabaseSystem pluginDatabaseSystem;
    private final int INITIAL_DIGITAL_ASSET_GENERATED_AMOUNT=0;

    public AssetIssuingTransactionDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) throws CantExecuteDatabaseOperationException {

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
            return pluginDatabaseSystem.openDatabase(pluginId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DATABASE);
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantExecuteDatabaseOperationException(exception, "Opening the Asset Issuing Transaction Database", "Error in database plugin.");
        }
    }

    public void updateTransactionProtocolStatus(UUID transactionID, ProtocolStatus protocolStatus) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        try{
            this.database=openDatabase();
            DatabaseTable databaseTable=this.database.getTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_FIRST_KEY_COLUMN, transactionID.toString(), DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            DatabaseTransaction databaseTransaction=this.database.newTransaction();
            DatabaseTableRecord databaseTableRecord;
            List<DatabaseTableRecord> databaseTableRecords=databaseTable.getRecords();
            if (databaseTableRecords.size() > 1)
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.",  "Transaction ID:" + transactionID+ " Protocol Status:" + protocolStatus.toString());
            else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            databaseTableRecord.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_PROTOCOL_STATUS, protocolStatus.toString());
            databaseTransaction.addRecordToUpdate(databaseTable, databaseTableRecord);
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE,exception, "Trying to update "+AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME,"Check the cause");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, exception,"Trying to update "+AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME,"Check the cause");
        } catch (Exception exception){
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantExecuteQueryException.DEFAULT_MESSAGE, FermatException.wrapException(exception),"Trying to update "+AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME,"Check the cause");
        }
    }

    public void persistDigitalAsset(String digitalAssetPublicKey, String digitalAssetLocalStoragePath, int assetsAmount)throws CantPersistDigitalAssetException{

        try{
            this.database=openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_TABLE_NAME);
            DatabaseTableRecord record = databaseTable.getEmptyRecord();
            record.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME,digitalAssetPublicKey);
            record.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_LOCAL_STORAGE_PATH_COLUMN_NAME, digitalAssetLocalStoragePath);
            record.setIntegerValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ASSETS_TO_GENERATE, assetsAmount);
            record.setIntegerValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ASSETS_GENERATED, INITIAL_DIGITAL_ASSET_GENERATED_AMOUNT);
            databaseTable.insertRecord(record);
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantPersistDigitalAssetException(exception,"Opening the Asset Issuing plugin database","Cannot open the Asset Issuing database");
        } catch (CantInsertRecordException exception) {
            this.database.closeDatabase();
            throw new CantPersistDigitalAssetException(exception,"Persisting a forming genesis digital asset","Cannot insert a record in the Asset Issuing database");
        } catch (Exception exception){
            this.database.closeDatabase();
            throw new CantPersistDigitalAssetException(exception, "Persisting a forming genesis digital asset", "Unexpected exception");
        }

    }

    private List<String> getDigitalAssetPublicKeys(){
        //No se si esto me pueda servir a futuro.
        return null;
    }

    public List<String> getPendingDigitalAssetPublicKeys()throws CantCheckAssetIssuingProgressException{
        String publicKey;
        int assetsToGenerate;
        int assetsGenerated;
        try{
            this.database=openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_TABLE_NAME);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecords=databaseTable.getRecords();
            List<String> pendingDigitalAssetPublicKeyList=new ArrayList<>();
            for(DatabaseTableRecord databaseTableRecord: databaseTableRecords){
                publicKey=databaseTableRecord.getStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME);
                assetsToGenerate=databaseTableRecord.getIntegerValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ASSETS_TO_GENERATE);
                assetsGenerated=databaseTableRecord.getIntegerValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ASSETS_GENERATED);
                if(assetsToGenerate-assetsGenerated>0){
                    pendingDigitalAssetPublicKeyList.add(publicKey);
                }
            }
            this.database.closeDatabase();
            return pendingDigitalAssetPublicKeyList;
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Checking pending Digital Assets PublicKeys", "Cannot find or open the database");
        }  catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Checking pending Digital Assets PublicKeys", "Cannot load the database into memory");
        } catch (Exception exception){
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception,"Checking pending Digital Assets PublicKeys","Unexpected exception");
        }
    }

    public List<String> getPendingDigitalAssetsTransactionIdByPublicKey(String publicKey)throws CantCheckAssetIssuingProgressException{

        String digitalAssetTransactionStatus;
        String digitalAssetTransactionId;
        try{
            this.database=openDatabase();
            List<String> pendingDigitalAssetsTransactionId=new ArrayList<>();
            DatabaseTable databaseTable = getDatabaseTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_PUBLIC_KEY, publicKey, DatabaseFilterType.EQUAL);
            //Para ejecutar este segundo filtro necesitaría modificar el objeto DatabaseFilterType para que acepte NOT_EQUAL, por ahora, lo hago "MANUALMENTE"
            //databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_STATE_COLUMN_NAME, publicKey, DatabaseFilterType.);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecords=databaseTable.getRecords();
            for(DatabaseTableRecord databaseTableRecord: databaseTableRecords){
                //Este procedimiento puede cambiar si logro añadir el filtro que necesito.
                digitalAssetTransactionStatus=databaseTableRecord.getStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_ID);
                if(!digitalAssetTransactionStatus.equals(TransactionStatus.ISSUED)){
                    digitalAssetTransactionId=databaseTableRecord.getStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_ID);
                    pendingDigitalAssetsTransactionId.add(digitalAssetTransactionId);
                }
            }
            this.database.closeDatabase();
            return pendingDigitalAssetsTransactionId;
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception,"Getting pending digital assets transactions to issue","Cannot open or find the Asset Issuing database");
        }  catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception,"Getting pending digital assets transactions to issue","Cannot load to memory the Asset Issuing database");
        } catch (Exception exception){
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception,"Getting pending digital assets transactions to issue","Unexpected exception");
        }

    }

    public int getNumberOfPendingAssets(String publicKey)throws CantCheckAssetIssuingProgressException, UnexpectedResultReturnedFromDatabaseException {

        try{
            this.database.openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            //DatabaseTransaction databaseTransaction=this.database.newTransaction();
            DatabaseTableRecord databaseTableRecord;
            List<DatabaseTableRecord> databaseTableRecords=databaseTable.getRecords();
            if (databaseTableRecords.size() > 1)
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.",  "Public key:" + publicKey);
            else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            int assetsToGenerate=databaseTableRecord.getIntegerValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ASSETS_TO_GENERATE);
            int assetsGenerated=databaseTableRecord.getIntegerValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ASSETS_GENERATED);
            this.database.closeDatabase();
            return assetsToGenerate-assetsGenerated;

        } catch (DatabaseNotFoundException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception,"Opening the Asset Issuing plugin database","Cannot found the Asset Issuing database");
        } catch (CantOpenDatabaseException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception,"Opening the Asset Issuing plugin database","Cannot open the Asset Issuing database");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception,"Loading Asset Issuing plugin database to memory","Cannot load the Asset Issuing database");
        } catch (Exception exception){
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception,"Checking pending assets to issue","Unexpected exception");
        }
    }

    public boolean isTransactionIdUsed(UUID transactionId)throws CantCheckAssetIssuingProgressException, UnexpectedResultReturnedFromDatabaseException{
        try{
            this.database=openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME);
            databaseTable.setUUIDFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_FIRST_KEY_COLUMN, transactionId, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecords=databaseTable.getRecords();
            int recordsInTable=databaseTableRecords.size();
            this.database.closeDatabase();
            switch (recordsInTable){
                case 0:
                    return false;
                case 1:
                    return true;
                default:
                    throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.",  "Transaction ID:" + transactionId);
            }

        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception,"Opening the Asset Issuing plugin database","Cannot open or find the Asset Issuing database");
        }  catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception,"Loading Asset Issuing plugin database to memory","Cannot load the Asset Issuing database");
        } catch (Exception exception){
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception,"Checking pending assets to issue","Unexpected exception");
        }
    }

    public boolean isPendingTransactions(/*CryptoStatus cryptoStatus*/) throws CantExecuteQueryException {
        //TODO: implement this method
        return false;
    }

    public int updateTransactionProtocolStatus(boolean occurrence) throws CantExecuteQueryException {
        //TODO: implement this method
        return 0;
    }

}
