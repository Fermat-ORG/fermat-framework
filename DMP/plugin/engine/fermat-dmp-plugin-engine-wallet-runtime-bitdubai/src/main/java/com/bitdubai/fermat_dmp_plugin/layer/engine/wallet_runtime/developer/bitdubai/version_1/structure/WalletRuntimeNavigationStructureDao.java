package com.bitdubai.fermat_dmp_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;


/**
 * Created by Matias Furszyfer
 */

public class WalletRuntimeNavigationStructureDao {

    /**
     * CryptoAddressBook Interface member variables.
     */
    private Database database;

    /**
     * Constructor.
     */
    public WalletRuntimeNavigationStructureDao(Database database){
        /**
         * The only one who can set the pluginId is the Plugin Root.
         */
        this.database = database;
    }

    /*
     *
     */
    public String getNavigationStructure(String publicKey) throws CantCalculateBalanceException {
        try{
            return getNavigationStructureFilename(publicKey);
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }


    /*
     *
     */
    public void addNavigationStructure(String publicKey,String filename) throws CantRegisterDebitException {
        try {

            //todo update if the record exists. The record might exists if many send request are executed so add an else to this If

            if (!isPublicKeyExist(publicKey))
                saveNavigationStructure(publicKey, filename);

        } catch (Exception exception){
            exception.printStackTrace();
        }
    }



    private void saveNavigationStructure(String publicKey, String filename){
        DatabaseTable  databaseTable=getNavigationStructureTable();
        DatabaseTableRecord databaseTableRecord= databaseTable.getEmptyRecord();
        databaseTableRecord.setStringValue(WalletRuntimeEngineDatabaseConstants.NAVIGATION_STRUCTURE_PUBLIC_KEY_COLUMN_NAME,publicKey);
        databaseTableRecord.setStringValue(WalletRuntimeEngineDatabaseConstants.NAVIGATION_STRUCTURE_NAVIGATION_STRUCTURE_FILE_COLUMN_NAME,filename);
        try {
            databaseTable.insertRecord(databaseTableRecord);
        } catch (CantInsertRecordException e) {
            e.printStackTrace();
        }
    }

    private boolean isPublicKeyExist(String publicKey) {
        return false;
    }

    private String getNavigationStructureFilename(final String publicKey){
        return getNavigationStructureName().getStringValue(WalletRuntimeEngineDatabaseConstants.NAVIGATION_STRUCTURE_NAVIGATION_STRUCTURE_FILE_COLUMN_NAME);
    }

    private DatabaseTableRecord getNavigationStructureName(){
        try {
            database.openDatabase();
            DatabaseTable navigationStructureTable = getNavigationStructureTable();
            navigationStructureTable.loadToMemory();
            return navigationStructureTable.getRecords().get(0);
        } catch (CantOpenDatabaseException | DatabaseNotFoundException | CantLoadTableToMemoryException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private DatabaseTable getNavigationStructureTable(){
        return database.getTable(WalletRuntimeEngineDatabaseConstants.NAVIGATION_STRUCTURE_TABLE_NAME);
    }


}