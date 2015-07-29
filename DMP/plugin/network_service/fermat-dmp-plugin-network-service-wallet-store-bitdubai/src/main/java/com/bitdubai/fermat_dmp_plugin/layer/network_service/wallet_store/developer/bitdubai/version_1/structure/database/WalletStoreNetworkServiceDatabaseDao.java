package com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.CatalogItems;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletDetailsException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions.InvalidDatabaseOperationException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions.InvalidResultReturnedByDatabaseException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.CatalogItem;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Designer;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.DetailedCatalogItem;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Developer;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Language;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Skin;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Translator;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.common.DatabaseOperations;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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


    private List<DatabaseTableRecord> getRecordsFromDatabase (String tableName, DatabaseTableFilter filter) throws CantExecuteDatabaseOperationException, InvalidResultReturnedByDatabaseException {
        DatabaseTable table = getDatabaseTable(tableName);
        table.setUUIDFilter(filter.getColumn(), UUID.fromString(filter.getValue()), filter.getType());
        try {
            table.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemoryException) {
            throw new CantExecuteDatabaseOperationException(cantLoadTableToMemoryException, filter.getValue(), "Error in database plugin.");
        }

        List<DatabaseTableRecord> recordList = table.getRecords();
        if (recordList.size() ==  0)
            throw new InvalidResultReturnedByDatabaseException(null, "Id: " + filter.getValue() + " number of records: " + recordList.size(), "database inconsistency");

        return recordList;
    }

    private CatalogItem getCatalogItemFromDatabase (final UUID id) throws InvalidResultReturnedByDatabaseException, CantExecuteDatabaseOperationException {
        DatabaseTableFilter tableFilter = new DatabaseTableFilter() {
            @Override
            public void setColumn(String column) {

            }

            @Override
            public void setType(DatabaseFilterType type) {

            }

            @Override
            public void setValue(String value) {

            }

            @Override
            public String getColumn() {
                return WalletStoreNetworkServiceDatabaseConstants.ITEM_ID_COLUMN_NAME;
            }

            @Override
            public String getValue() {
                return id.toString();
            }

            @Override
            public DatabaseFilterType getType() {
                return DatabaseFilterType.EQUAL;
            }
        };
        List<DatabaseTableRecord> records = getRecordsFromDatabase(WalletStoreNetworkServiceDatabaseConstants.ITEM_TABLE_NAME, tableFilter);

        if (records.size() != 1)
            throw new InvalidResultReturnedByDatabaseException(null, "Id: " + tableFilter.getValue() + " number of records: " + records.size(), "database inconsistency");

        DatabaseTableRecord record = records.get(0);

        CatalogItem catalogItem = new CatalogItem();
        catalogItem.setWalletName(record.getStringValue(WalletStoreNetworkServiceDatabaseConstants.ITEM_NAME_COLUMN_NAME));
        catalogItem.setCategory(WalletCategory.valueOf(record.getStringValue(WalletStoreNetworkServiceDatabaseConstants.ITEM_CATEGORY_COLUMN_NAME)));
        catalogItem.setDescription(record.getStringValue(WalletStoreNetworkServiceDatabaseConstants.ITEM_DESCRIPTION_COLUMN_NAME));
        catalogItem.setDefaultSizeInBytes(record.getIntegerValue(WalletStoreNetworkServiceDatabaseConstants.ITEM_SIZE_COLUMN_NAME));
        /**
         * the detailed catalog item will be null at this point.
         */
        return catalogItem;
    }

    private List<CatalogItem> getAllCatalogItemsFromDatabase() throws InvalidResultReturnedByDatabaseException, CantExecuteDatabaseOperationException{
        List<CatalogItem> catalogItems = new ArrayList<CatalogItem>();

        DatabaseTableFilter tableFilter = new DatabaseTableFilter() {
            @Override
            public void setColumn(String column) {

            }

            @Override
            public void setType(DatabaseFilterType type) {

            }

            @Override
            public void setValue(String value) {

            }

            @Override
            public String getColumn() {
                return WalletStoreNetworkServiceDatabaseConstants.ITEM_ID_COLUMN_NAME;
            }

            @Override
            public String getValue() {
                return "";
            }

            @Override
            public DatabaseFilterType getType() {
                return DatabaseFilterType.LIKE;
            }
        };
        List<DatabaseTableRecord> records = getRecordsFromDatabase(WalletStoreNetworkServiceDatabaseConstants.ITEM_TABLE_NAME, tableFilter);

        for (DatabaseTableRecord record : records){
            CatalogItem catalogItem = new CatalogItem();
            catalogItem.setWalletName(record.getStringValue(WalletStoreNetworkServiceDatabaseConstants.ITEM_NAME_COLUMN_NAME));
            catalogItem.setCategory(WalletCategory.valueOf(record.getStringValue(WalletStoreNetworkServiceDatabaseConstants.ITEM_CATEGORY_COLUMN_NAME)));
            catalogItem.setDescription(record.getStringValue(WalletStoreNetworkServiceDatabaseConstants.ITEM_DESCRIPTION_COLUMN_NAME));
            catalogItem.setDefaultSizeInBytes(record.getIntegerValue(WalletStoreNetworkServiceDatabaseConstants.ITEM_SIZE_COLUMN_NAME));

            catalogItems.add(catalogItem);
        }
        return catalogItems;
    }

    private DetailedCatalogItem getDetailedCatalogItemFromDatabase (final UUID walletId) throws InvalidResultReturnedByDatabaseException, CantExecuteDatabaseOperationException {
        DetailedCatalogItem detailedCatalogItem = new DetailedCatalogItem();

        /**
         * Get Language records from database
         */
        DatabaseTableFilter tableFilter = new DatabaseTableFilter() {
            @Override
            public void setColumn(String column) {

            }

            @Override
            public void setType(DatabaseFilterType type) {

            }

            @Override
            public void setValue(String value) {

            }

            @Override
            public String getColumn() {
                return WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_WALLETID_COLUMN_NAME;
            }

            @Override
            public String getValue() {
                return walletId.toString();
            }

            @Override
            public DatabaseFilterType getType() {
                return DatabaseFilterType.EQUAL;
            }
        };
        List<DatabaseTableRecord> records = getRecordsFromDatabase(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_TABLE_NAME, tableFilter);

        List<Language> languages = new ArrayList<Language>();
        for (DatabaseTableRecord record : records){
            languages.add(getLanguageFromDatabase(record.getUUIDValue(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_ID_COLUMN_NAME)));
        }

        if (languages.size() == 0)
            throw new InvalidResultReturnedByDatabaseException(null, "Id: " + tableFilter.getValue() + " number of records: " + records.size(), "database inconsistency");

        for (Language language : languages){
            if (language.isDefault())
                detailedCatalogItem.setLanguage(language);
        }

        //todo why this does not work?
        //detailedCatalogItem.setLanguages(languages);

        /**
         * Get skin records from database
         */
        tableFilter = new DatabaseTableFilter() {
            @Override
            public void setColumn(String column) {

            }

            @Override
            public void setType(DatabaseFilterType type) {

            }

            @Override
            public void setValue(String value) {

            }

            @Override
            public String getColumn() {
                return WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_WALLETID_COLUMN_NAME;
            }

            @Override
            public String getValue() {
                return walletId.toString();
            }

            @Override
            public DatabaseFilterType getType() {
                return DatabaseFilterType.EQUAL;
            }
        };

        List<Skin> skins = new ArrayList<Skin>();
        records = getRecordsFromDatabase(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_TABLE_NAME, tableFilter);

        for (DatabaseTableRecord record : records){
            skins.add(getSkinFromDatabase(record.getUUIDValue(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_ID_COLUMN_NAME)));
        }
        if (skins.size() == 0)
            throw new InvalidResultReturnedByDatabaseException(null, "Id: " + tableFilter.getValue() + " number of records: " + records.size(), "database inconsistency");

        for (Skin skin : skins){
            if (skin.isDefault())
                detailedCatalogItem.setDefaultSkin(skin);
        }

        //todo why this does not work?
        //detailedCatalogItem.setSkins(skins);

        /**
         * Get rest of Item information from item table
         */
        tableFilter = new DatabaseTableFilter() {
            @Override
            public void setColumn(String column) {

            }

            @Override
            public void setType(DatabaseFilterType type) {

            }

            @Override
            public void setValue(String value) {

            }

            @Override
            public String getColumn() {
                return WalletStoreNetworkServiceDatabaseConstants.ITEM_ID_COLUMN_NAME;
            }

            @Override
            public String getValue() {
                return walletId.toString();
            }

            @Override
            public DatabaseFilterType getType() {
                return DatabaseFilterType.EQUAL;
            }
        };
        records = getRecordsFromDatabase(WalletStoreNetworkServiceDatabaseConstants.ITEM_TABLE_NAME, tableFilter);

        if (records.size() != 1)
            throw new InvalidResultReturnedByDatabaseException(null, "Id: " + tableFilter.getValue() + " number of records: " + records.size(), "database inconsistency");

        DatabaseTableRecord record = records.get(0);
        detailedCatalogItem.setVersion(new Version(record.getStringValue(WalletStoreNetworkServiceDatabaseConstants.ITEM_VERSION_COLUMN_NAME)));
        detailedCatalogItem.setPlatformInitialVersion(new Version(record.getStringValue(WalletStoreNetworkServiceDatabaseConstants.ITEM_PLATFORMINITIALVERSION_COLUMN_NAME)));
        detailedCatalogItem.setPlatformFinalVersion(new Version(record.getStringValue(WalletStoreNetworkServiceDatabaseConstants.ITEM_PLATFORMFINALVERSION_COLUMN_NAME)));
        detailedCatalogItem.setDeveloperId(record.getUUIDValue(WalletStoreNetworkServiceDatabaseConstants.ITEM_DEVELOPER_ID_COLUMN_NAME));

        return detailedCatalogItem;
    }

    /**
     * Gets the Catalog item from the database
     * @param walletId
     * @return
     * @throws CantExecuteDatabaseOperationException
     */
    public CatalogItem getCatalogItem(UUID walletId) throws CantExecuteDatabaseOperationException{
        openDatabase();
        CatalogItem catalogItem=null;
        try {
            catalogItem = getCatalogItemFromDatabase(walletId);
            closeDatabase();
        } catch (Exception exception){
            closeDatabase();
            throw new CantExecuteDatabaseOperationException(exception, null,null);
        }

        return catalogItem;
    }

    /**
     * Gets the entire catalog from the database
     * @return
     * @throws CantExecuteDatabaseOperationException
     */
    public List<CatalogItem> getCatalogItems() throws CantExecuteDatabaseOperationException{
        openDatabase();
        try {
            List<CatalogItem> catalogItems =getAllCatalogItemsFromDatabase();
            closeDatabase();
            return  catalogItems;
        } catch (Exception exception){
            closeDatabase();
            throw new CantExecuteDatabaseOperationException(exception, null,null);
        }
    }

    /**
     * Gets the DetailedCatalog ITem from the Database
     * @param walletId
     * @return
     * @throws CantExecuteDatabaseOperationException
     */
    public DetailedCatalogItem getDetailedCatalogItem(UUID walletId) throws  CantExecuteDatabaseOperationException{
        openDatabase();
        DetailedCatalogItem detailedCatalogItem = null;
        try {
            detailedCatalogItem= getDetailedCatalogItemFromDatabase(walletId);
            closeDatabase();
        } catch (Exception exception){
            closeDatabase();
            throw new CantExecuteDatabaseOperationException(exception, null,null);
        }

        return detailedCatalogItem;
    }

    private Skin getSkinFromDatabase(final UUID id) throws InvalidResultReturnedByDatabaseException, CantExecuteDatabaseOperationException {
        DatabaseTableFilter tableFilter = new DatabaseTableFilter() {
            @Override
            public void setColumn(String column) {

            }

            @Override
            public void setType(DatabaseFilterType type) {

            }

            @Override
            public void setValue(String value) {

            }

            @Override
            public String getColumn() {
                return WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_ID_COLUMN_NAME;
            }

            @Override
            public String getValue() {
                return id.toString();
            }

            @Override
            public DatabaseFilterType getType() {
                return DatabaseFilterType.EQUAL;
            }
        };
        List<DatabaseTableRecord> records = getRecordsFromDatabase(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_TABLE_NAME, tableFilter);

        if (records.size() != 1)
            throw new InvalidResultReturnedByDatabaseException(null, "Id: " + tableFilter.getValue() + " number of records: " + records.size(), "database inconsistency");

        DatabaseTableRecord record = records.get(0);
        Skin skin = new Skin();
        skin.setId(id);
        skin.setName(record.getStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_NAME_COLUMN_NAME));
        skin.setWalletId(record.getUUIDValue(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_WALLETID_COLUMN_NAME));
        skin.setInitialWalletVersion(new Version(record.getStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_WALLETINITIALVERSION_COLUMN_NAME)));
        skin.setFinalWalletVersion(new Version(record.getStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_WALLETFINALVERSION_COLUMN_NAME)));
        skin.setVersion(new Version(record.getStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_VERSION_COLUMN_NAME)));
        skin.setSkinDesignerId(record.getUUIDValue(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_DESIGNERID_COLUMN_NAME));
        skin.setSkinSizeInBytes(record.getIntegerValue(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_SIZE_COLUMN_NAME));
        URL url = null;
        String databaseURL = record.getStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_URL_COLUMN_NAME);
        try {
            url = new URL(databaseURL);
        } catch (MalformedURLException e) {
            throw new InvalidResultReturnedByDatabaseException(e, databaseURL, "incorrect URL format.");
        }
        skin.setUrl(url);


        return skin;
    }

    private Language getLanguageFromDatabase(final UUID id) throws InvalidResultReturnedByDatabaseException, CantExecuteDatabaseOperationException {
        DatabaseTableFilter tableFilter = new DatabaseTableFilter() {
            @Override
            public void setColumn(String column) {

            }

            @Override
            public void setType(DatabaseFilterType type) {

            }

            @Override
            public void setValue(String value) {

            }

            @Override
            public String getColumn() {
                return WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_ID_COLUMN_NAME;
            }

            @Override
            public String getValue() {
                return id.toString();
            }

            @Override
            public DatabaseFilterType getType() {
                return DatabaseFilterType.EQUAL;
            }
        };
        List<DatabaseTableRecord> records = getRecordsFromDatabase(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_TABLE_NAME, tableFilter);

        if (records.size() != 1)
            throw new InvalidResultReturnedByDatabaseException(null, "Id: " + tableFilter.getValue() + " number of records: " + records.size(), "database inconsistency");

        DatabaseTableRecord record = records.get(0);
        Language language = new Language();
        language.setId(id);
        language.setLanguageName(Languages.valueOf(record.getStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_NAME_COLUMN_NAME)));
        language.setFinalWalletVersion(new Version(record.getStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_WALLETFINALVERSION_COLUMN_NAME)));
        language.setInitialWalletVersion(new Version(record.getStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_WALLETINITIALVERSION_COLUMN_NAME)));
        language.setVersion(new Version(record.getStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_VERSION_COLUMN_NAME)));
        language.setIsDefault(Boolean.getBoolean(record.getStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_ISDEFAULT_COLUMN_NAME)));
        language.setLanguageLabel(record.getStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_LABEL_COLUMN_NAME));
        language.setLanguagePackageSizeInBytes(record.getIntegerValue(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_FILESIZE_COLUMN_NAME));
        language.setTranslatorId(UUID.fromString(record.getStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_TRANSLATORID_COLUMN_NAME)));

        String databaseURL = null;
        URL url = null;
        try {
            databaseURL = record.getStringValue(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_URL_COLUMN_NAME);
            url = new URL(databaseURL);
        } catch (MalformedURLException e) {
            throw new InvalidResultReturnedByDatabaseException(e, databaseURL, "incorrect URL format.");
        }
        language.setUrl(url);

        language.setWalletId(record.getUUIDValue(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_WALLETID_COLUMN_NAME));

        return language;

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
    public void catalogDatabaseOperation(DatabaseOperations databaseOperation, CatalogItem  catalogItem, Developer developer, Language language, Translator translator, Skin skin, Designer designer) throws CantExecuteDatabaseOperationException {
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
            closeDatabase();
        }  catch (Exception exception){
            closeDatabase();
            throw new CantExecuteDatabaseOperationException(exception, catalogItem.toString(), null);
        }
    }

    private List<UUID> getWalletIdsForNetworkService() throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreNetworkServiceDatabaseConstants.ITEM_TABLE_NAME);
        List<UUID> uuids = new ArrayList<>();
        try {
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            for (DatabaseTableRecord record : records){
                uuids.add(record.getUUIDValue(WalletStoreNetworkServiceDatabaseConstants.ITEM_ID_COLUMN_NAME));
            }
            return uuids;
        } catch (CantLoadTableToMemoryException e) {
            throw  new CantExecuteDatabaseOperationException(e, null, null);
        }
    }

    private List<UUID> getWalletLanguagesForNetworkService() throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_TABLE_NAME);
        List<UUID> uuids = new ArrayList<>();
        try {
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            for (DatabaseTableRecord record : records){
                uuids.add(record.getUUIDValue(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_ID_COLUMN_NAME));
            }
            return uuids;
        } catch (CantLoadTableToMemoryException e) {
            throw  new CantExecuteDatabaseOperationException(e, null, null);
        }
    }

    private List<UUID> getWalletSkinsForNetworkService() throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_TABLE_NAME);
        List<UUID> uuids = new ArrayList<>();
        try {
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            for (DatabaseTableRecord record : records){
                uuids.add(record.getUUIDValue(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_ID_COLUMN_NAME));
            }
            return uuids;
        } catch (CantLoadTableToMemoryException e) {
            throw  new CantExecuteDatabaseOperationException(e, null, null);
        }
    }

    /**
     * Used by the network service. Gets the list of IDs installed in the catalog to compare.
     * @param catalogItems
     * @return
     * @throws InvalidParameterException
     * @throws CantExecuteDatabaseOperationException
     */
    public List<UUID> getCatalogIdsForNetworkService(CatalogItems catalogItems) throws InvalidParameterException, CantExecuteDatabaseOperationException {
        List<UUID> uuids;
        openDatabase();
        try{
            switch (catalogItems){
                case LANGUAGE:
                    uuids = getWalletLanguagesForNetworkService();
                    break;
                case SKIN:
                    uuids=  getWalletSkinsForNetworkService();
                    break;
                case WALLET:
                    uuids=  getWalletIdsForNetworkService();
                    break;
                default:
                    throw new InvalidParameterException("Invalid parameter.", null, catalogItems.toString(), null);
            }
            closeDatabase();
            return uuids;
        } catch (Exception exception) {
            closeDatabase();
            throw new CantExecuteDatabaseOperationException(exception, null, null);
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
