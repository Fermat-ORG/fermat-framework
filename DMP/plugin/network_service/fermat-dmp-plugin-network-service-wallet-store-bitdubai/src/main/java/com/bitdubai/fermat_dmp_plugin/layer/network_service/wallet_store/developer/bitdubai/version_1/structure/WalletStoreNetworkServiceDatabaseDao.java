package com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletDetailsException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions.InvalidDatabaseOperationException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.CatalogItem;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Designer;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Developer;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Language;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Skin;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Translator;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.common.DatabaseOperations;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.sun.istack.internal.Nullable;

import java.util.UUID;

/**
 * Created by rodrigo on 7/22/15.
 */
public class WalletStoreNetworkServiceDatabaseDao implements DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem {
    UUID databaseOwnerId;
    String databaseName;
    Database database;


    /**
     * DealsWithErrors interface member variables
     */
    ErrorManager errorManager;

    /**
     * DealsWithLogger interface member variables
     */
    LogManager logManager;


    /**
     * DealsWithPluginDatabaseSystem interface member variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;


    /**
     * Constructor
     * @param errorManager
     * @param logManager
     * @param pluginDatabaseSystem
     */
    public WalletStoreNetworkServiceDatabaseDao(ErrorManager errorManager, LogManager logManager, PluginDatabaseSystem pluginDatabaseSystem, UUID databaseOwnerId, String databaseName) throws CantExecuteDatabaseOperationException {
        this.errorManager = errorManager;
        this.logManager = logManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.databaseOwnerId = databaseOwnerId;
        this.databaseName = databaseName;

        openDatabase();
    }


    private DatabaseTransaction getDatabaseTransaction()  {
        DatabaseTransaction databaseTransaction = database.newTransaction();
        return databaseTransaction;
    }
    /**
     * Opens the database and gets the table
     * @param tableName
     * @return
     * @throws CantOpenDatabaseException
     * @throws DatabaseNotFoundException
     */
    private DatabaseTable getDatabaseTable(String tableName)  {
        DatabaseTable databaseTable = database.getTable(tableName);
        return databaseTable;
    }


    /**
     * Opens the databases
     * @return Database
     * @throws CantOpenDatabaseException
     * @throws DatabaseNotFoundException
     */
    private Database openDatabase() throws CantExecuteDatabaseOperationException {
        try {
            database = pluginDatabaseSystem.openDatabase(this.databaseOwnerId, this.databaseName);
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantExecuteDatabaseOperationException(cantOpenDatabaseException, "Trying to open database " + databaseName, "Error in Database plugin");
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            throw new CantExecuteDatabaseOperationException(databaseNotFoundException, "Trying to open database " + databaseName, "Error in Database plugin. Database should already exists.");
        }
        return database;
    }

    /**
     * closes the database
     */
    private void closeDatabase(){
        database.closeDatabase();
    }


    private DatabaseTableRecord getDesignerDatabaseTableRecord(Designer designer){
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreNetworkServiceDatabaseConstants.DESIGNER_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.DESIGNER_ID_COLUMN_NAME, designer.getId().toString());
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.DESIGNER_NAME_COLUMN_NAME, designer.getName());
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.DESIGNER_PUBLICKEY_COLUMN_NAME, designer.getPublicKey());

        return record;
    }


    private DatabaseTransaction addDesignerInTransaction(DatabaseOperations databaseOperation, DatabaseTransaction transaction, Designer designer) throws InvalidDatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreNetworkServiceDatabaseConstants.DESIGNER_TABLE_NAME);
        DatabaseTableRecord record = getDesignerDatabaseTableRecord(designer);

        switch (databaseOperation){
            case INSERT:
                transaction.addRecordToInsert(databaseTable, record);
                break;
            case UPDATE:
                transaction.addRecordToUpdate(databaseTable, record);
                break;
            case SELECT:
                transaction.addRecordToSelect(databaseTable, record);
                break;
            default:
                throw throwInvalidDatabaseOperationException();
        }

        return transaction;
    }


    private DatabaseTableRecord getTranslatorDatabaseTableRecord (Translator translator){
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreNetworkServiceDatabaseConstants.TRANSLATOR_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.TRANSLATOR_ID_COLUMN_NAME, translator.getId().toString());
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.TRANSLATOR_NAME_COLUMN_NAME, translator.getName());
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.TRANSLATOR_PUBLICKEY_COLUMN_NAME, translator.getPublicKey());

        return record;
    }

    private DatabaseTransaction addTranslatorInTransaction(DatabaseOperations databaseOperation, DatabaseTransaction transaction, Translator translator) throws InvalidDatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreNetworkServiceDatabaseConstants.TRANSLATOR_TABLE_NAME);
        DatabaseTableRecord record = getTranslatorDatabaseTableRecord(translator);

        switch (databaseOperation){
            case UPDATE:
                transaction.addRecordToUpdate(databaseTable, record);
                break;
            case INSERT:
                transaction.addRecordToInsert(databaseTable, record);
                break;
            case SELECT:
                transaction.addRecordToSelect(databaseTable, record);
                break;
            default:
                throw throwInvalidDatabaseOperationException();
        }

        return transaction;
    }

    private DatabaseTableRecord getItemInTransaction(CatalogItem catalogItem) throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreNetworkServiceDatabaseConstants.ITEM_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.ITEM_ID_COLUMN_NAME, catalogItem.getId().toString());
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.ITEM_NAME_COLUMN_NAME, catalogItem.getName());
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.ITEM_CATEGORY_COLUMN_NAME, catalogItem.getCategory().toString());
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.ITEM_DESCRIPTION_COLUMN_NAME, catalogItem.getDescription());
        record.setIntegerValue(WalletStoreNetworkServiceDatabaseConstants.ITEM_SIZE_COLUMN_NAME, catalogItem.getDefaultSizeInBytes());
        //detailedCatalogItem
        try {
            record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.ITEM_VERSION_COLUMN_NAME, catalogItem.getDetailedCatalogItem().getVersion().toString());
            record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.ITEM_PLATFORMINITIALVERSION_COLUMN_NAME, catalogItem.getDetailedCatalogItem().getPlatformInitialVersion().toString());
            record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.ITEM_PLATFORMFINALVERSION_COLUMN_NAME, catalogItem.getDetailedCatalogItem().getPlatformFinalVersion().toString());
            record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.ITEM_DEVELOPER_ID_COLUMN_NAME, catalogItem.getDetailedCatalogItem().getDeveloperId().toString());
        } catch (CantGetWalletDetailsException cantGetWalletDetailsException) {
            throw new CantExecuteDatabaseOperationException(cantGetWalletDetailsException, null, null);
        }
        return record;
    }

    private DatabaseTransaction addCatalogItemInTransaction(DatabaseOperations databaseOperation, DatabaseTransaction transaction, CatalogItem catalogItem) throws InvalidDatabaseOperationException, CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreNetworkServiceDatabaseConstants.ITEM_TABLE_NAME);
        DatabaseTableRecord record = getItemInTransaction(catalogItem);

        switch (databaseOperation){
            case INSERT:
                transaction.addRecordToInsert(databaseTable, record);
                break;
            case UPDATE:
                transaction.addRecordToUpdate(databaseTable, record);
                break;
            case SELECT:
                transaction.addRecordToSelect(databaseTable, record);
                break;
            default:
                throw throwInvalidDatabaseOperationException();
        }

        return transaction;
    }

    private DatabaseTableRecord getSkinRecord(Skin skin){
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_ID_COLUMN_NAME, skin.getSkinId().toString());
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_NAME_COLUMN_NAME, skin.getSkinName());
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_VERSION_COLUMN_NAME, skin.getVersion().toString());
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_WALLETID_COLUMN_NAME, skin.getWalletId().toString());
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_WALLETINITIALVERSION_COLUMN_NAME, skin.getInitialWalletVersion().toString());
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_WALLETFINALVERSION_COLUMN_NAME, skin.getFinalWalletVersion().toString());
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_URL_COLUMN_NAME, skin.getSkinURL().toString());
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_DESIGNERID_COLUMN_NAME, skin.getSkinDesignerId().toString());

        return record;
    }

    private DatabaseTransaction addSkinInTransaction(DatabaseOperations databaseOperation, DatabaseTransaction transaction, Skin skin) throws InvalidDatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_TABLE_NAME);
        DatabaseTableRecord record = getSkinRecord(skin);

        switch (databaseOperation){
            case UPDATE:
                transaction.addRecordToUpdate(databaseTable, record);
                break;
            case INSERT:
                transaction.addRecordToInsert(databaseTable, record);
                break;
            case SELECT:
                transaction.addRecordToSelect(databaseTable, record);
                break;
            default:
                throw throwInvalidDatabaseOperationException();
        }

        return transaction;
    }



    private DatabaseTableRecord getDeveloperInDatabaseTableRecord(Developer developer){
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreNetworkServiceDatabaseConstants.DEVELOPER_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.DEVELOPER_ID_COLUMN_NAME, developer.getId().toString());
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.DEVELOPER_NAME_COLUMN_NAME, developer.getName());
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.DEVELOPER_PUBLICKEY_COLUMN_NAME, developer.getPublicKey());

        return record;
    }

    private DatabaseTransaction addDeveloperInTransaction(DatabaseOperations databaseOperation, DatabaseTransaction transaction, Developer developer) throws InvalidDatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreNetworkServiceDatabaseConstants.DEVELOPER_TABLE_NAME);
        DatabaseTableRecord record = getDeveloperInDatabaseTableRecord(developer);

        switch (databaseOperation){
            case INSERT:
                transaction.addRecordToInsert(databaseTable, record);
                break;
            case UPDATE:
                transaction.addRecordToUpdate(databaseTable, record);
                break;
            case SELECT:
                transaction.addRecordToSelect(databaseTable, record);
                break;
            default:
                throw throwInvalidDatabaseOperationException();
        }

        return transaction;
    }

    private DatabaseTableRecord getLanguageInDatabaseTableRecord(Language language){
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_ID_COLUMN_NAME, language.getLanguageId().toString());
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_NAME_COLUMN_NAME, language.getLanguageName().value());
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_LABEL_COLUMN_NAME, language.getLanguageLabel());
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_VERSION_COLUMN_NAME, language.getVersion().toString());
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_WALLETINITIALVERSION_COLUMN_NAME, language.getInitialWalletVersion().toString());
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_WALLETFINALVERSION_COLUMN_NAME, language.getFinalWalletVersion().toString());
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_URL_COLUMN_NAME, language.getFileURL().toString());
        record.setIntegerValue(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_FILESIZE_COLUMN_NAME, language.getLanguagePackageSizeInBytes());
        record.setStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_TRANSLATORID_COLUMN_NAME, language.getTranslatorId().toString());

        return record;
    }

    private DatabaseTransaction addLanguageInTransaction(DatabaseOperations databaseOperation, DatabaseTransaction transaction, Language language) throws InvalidDatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_TABLE_NAME);
        DatabaseTableRecord record = getLanguageInDatabaseTableRecord(language);

        switch (databaseOperation){
            case INSERT:
                transaction.addRecordToInsert(databaseTable, record);
                break;
            case UPDATE:
                transaction.addRecordToUpdate(databaseTable, record);
                break;
            case SELECT:
                transaction.addRecordToSelect(databaseTable, record);
                break;
            default:
                throw throwInvalidDatabaseOperationException();
        }

        return transaction;
    }

    private InvalidDatabaseOperationException throwInvalidDatabaseOperationException(){
        InvalidDatabaseOperationException invalidDatabaseOperationException = new InvalidDatabaseOperationException(InvalidDatabaseOperationException.DEFAULT_MESSAGE, null, null, "invalid call to method");
        return invalidDatabaseOperationException;
    }


    /**
     * Inserts or Update an Item in the catalog Database base.
     * Every other object added will be updated or inserted in the same transaction.
     * @param databaseOperation enum. The database operation to execute in the transaction
     * @param catalogItem the item to save/update or select in the transaction
     * @param developer
     * @param language
     * @param translator
     * @param skin
     * @param designer
     * @throws CantExecuteDatabaseOperationException
     */
    public void catalogItemDatabaseOperation(DatabaseOperations databaseOperation, @Nullable CatalogItem catalogItem, @Nullable Developer developer, @Nullable Language language, @Nullable Translator translator, @Nullable Skin skin, @Nullable Designer designer) throws CantExecuteDatabaseOperationException {
        database = openDatabase();
        DatabaseTransaction transaction = database.newTransaction();
        try{
            if (catalogItem != null)
                transaction = addCatalogItemInTransaction(databaseOperation, transaction, catalogItem);
            if (developer != null)
                transaction = addDeveloperInTransaction(databaseOperation, transaction, developer);
            if (language != null)
                transaction = addLanguageInTransaction(databaseOperation, transaction, language);
            if (translator != null)
                transaction = addTranslatorInTransaction(databaseOperation, transaction, translator);
            if (skin != null)
                transaction = addSkinInTransaction(databaseOperation, transaction, skin);
            if (designer != null)
                transaction = addDesignerInTransaction(databaseOperation, transaction, designer);

            database.executeTransaction(transaction);
        } catch (InvalidDatabaseOperationException invalidDatabaseOperationException) {
            throw new CantExecuteDatabaseOperationException(invalidDatabaseOperationException, databaseOperation.toString(), null);
        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {
            throw new CantExecuteDatabaseOperationException(databaseTransactionFailedException, catalogItem.toString(), null);
        } finally {
            closeDatabase();
        }
    }


    /**
     * DealsWithErrors interface implementation
     * @param errorManager
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithLogger interface implementation
     * @param logManager
     */
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}
