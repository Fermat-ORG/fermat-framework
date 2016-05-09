package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_identity.designer.interfaces.DesignerIdentity;
import com.bitdubai.fermat_api.layer.dmp_identity.translator.interfaces.TranslatorIdentity;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.CatalogItems;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetWalletDetailsException;
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
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_identity.developer.interfaces.DeveloperIdentity;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions.InvalidDatabaseOperationException;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions.InvalidResultReturnedByDatabaseException;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.CatalogItemImpl;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Designer;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.DetailedCatalogItemImpl;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Developer;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Language;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Skin;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Translator;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.common.DatabaseOperations;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigo on 7/22/15.
 */
public class WalletStoreCatalogDatabaseDao implements DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem {
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
    public WalletStoreCatalogDatabaseDao(ErrorManager errorManager, LogManager logManager, PluginDatabaseSystem pluginDatabaseSystem, UUID databaseOwnerId, String databaseName) throws CantExecuteDatabaseOperationException {
        this.errorManager = errorManager;
        this.logManager = logManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.databaseOwnerId = databaseOwnerId;
        this.databaseName = databaseName;
    }

    private DatabaseTransaction getDatabaseTransaction()  {
        DatabaseTransaction databaseTransaction = database.newTransaction();
        return databaseTransaction;
    }

    private DatabaseTable getDatabaseTable(String tableName)  {
        DatabaseTable databaseTable = database.getTable(tableName);
        return databaseTable;
    }

    private Database openDatabase() throws CantExecuteDatabaseOperationException {
        try {
            if(database == null)
                database = pluginDatabaseSystem.openDatabase(this.databaseOwnerId, this.databaseName);
            else
                database.openDatabase();
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantExecuteDatabaseOperationException(cantOpenDatabaseException, "Trying to open database " + databaseName, "Error in Database plugin");
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            throw new CantExecuteDatabaseOperationException(databaseNotFoundException, "Trying to open database " + databaseName, "Error in Database plugin. Database should already exists.");
        }
        return database;
    }

    private void closeDatabase(){
        if(database != null)
            database.closeDatabase();
    }

    private DatabaseTableRecord getDesignerDatabaseTableRecord(com.bitdubai.fermat_api.layer.dmp_identity.designer.interfaces.DesignerIdentity designer){
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreCatalogDatabaseConstants.DESIGNER_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();
        if (designer.getPublicKey() != null)
            record.setStringValue(WalletStoreCatalogDatabaseConstants.DESIGNER_ID_COLUMN_NAME, designer.getPublicKey()); //Todo: Revisar si guarda publickey o UUID id
        if (designer.getAlias() != null)
            record.setStringValue(WalletStoreCatalogDatabaseConstants.DESIGNER_NAME_COLUMN_NAME, designer.getAlias());
        if (designer.getPublicKey() != null)
            record.setStringValue(WalletStoreCatalogDatabaseConstants.DESIGNER_PUBLICKEY_COLUMN_NAME, designer.getPublicKey());

        return record;
    }

    private DatabaseTransaction addDesignerInTransaction(DatabaseOperations databaseOperation, DatabaseTransaction transaction, com.bitdubai.fermat_api.layer.dmp_identity.designer.interfaces.DesignerIdentity designer) throws InvalidDatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreCatalogDatabaseConstants.DESIGNER_TABLE_NAME);
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

    private DatabaseTableRecord getTranslatorDatabaseTableRecord (TranslatorIdentity translator){
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreCatalogDatabaseConstants.TRANSLATOR_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();
        if (translator.getPublicKey() != null)
            record.setStringValue(WalletStoreCatalogDatabaseConstants.TRANSLATOR_ID_COLUMN_NAME, translator.getPublicKey()); //Todo: Revisar si guarda publickey o UIID id
        if (translator.getAlias() != null)
            record.setStringValue(WalletStoreCatalogDatabaseConstants.TRANSLATOR_NAME_COLUMN_NAME, translator.getAlias());
        if (translator.getPublicKey() != null)
            record.setStringValue(WalletStoreCatalogDatabaseConstants.TRANSLATOR_PUBLICKEY_COLUMN_NAME, translator.getPublicKey());

        return record;
    }

    private DatabaseTransaction addTranslatorInTransaction(DatabaseOperations databaseOperation, DatabaseTransaction transaction, TranslatorIdentity translator) throws InvalidDatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreCatalogDatabaseConstants.TRANSLATOR_TABLE_NAME);
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

    private DatabaseTableRecord getItemInDatabaseTableRecord(CatalogItemImpl catalogItemImpl) throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreCatalogDatabaseConstants.ITEM_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();
        record.setStringValue(WalletStoreCatalogDatabaseConstants.ITEM_ID_COLUMN_NAME, catalogItemImpl.getId().toString());
        if (catalogItemImpl.getName() != null)
            record.setStringValue(WalletStoreCatalogDatabaseConstants.ITEM_NAME_COLUMN_NAME, catalogItemImpl.getName());
        if (catalogItemImpl.getCategory() != null)
            record.setStringValue(WalletStoreCatalogDatabaseConstants.ITEM_CATEGORY_COLUMN_NAME, catalogItemImpl.getCategory().toString());
        if (catalogItemImpl.getDescription() != null)
            record.setStringValue(WalletStoreCatalogDatabaseConstants.ITEM_DESCRIPTION_COLUMN_NAME, catalogItemImpl.getDescription());
        record.setIntegerValue(WalletStoreCatalogDatabaseConstants.ITEM_SIZE_COLUMN_NAME, catalogItemImpl.getDefaultSizeInBytes());
        if (catalogItemImpl.getpublisherWebsiteUrl() != null)
            record.setStringValue(WalletStoreCatalogDatabaseConstants.ITEM_PUBLISHER_WEB_SITE_URL_COLUMN_NAME, catalogItemImpl.getpublisherWebsiteUrl().toString());
        try {
            if (catalogItemImpl.getDetailedCatalogItemImpl().getVersion() != null)
                record.setStringValue(WalletStoreCatalogDatabaseConstants.ITEM_VERSION_COLUMN_NAME, catalogItemImpl.getDetailedCatalogItemImpl().getVersion().toString());
            if (catalogItemImpl.getDetailedCatalogItemImpl().getPlatformInitialVersion() != null)
                record.setStringValue(WalletStoreCatalogDatabaseConstants.ITEM_PLATFORMINITIALVERSION_COLUMN_NAME, catalogItemImpl.getDetailedCatalogItemImpl().getPlatformInitialVersion().toString());
            if (catalogItemImpl.getDetailedCatalogItemImpl().getPlatformFinalVersion() != null)
                record.setStringValue(WalletStoreCatalogDatabaseConstants.ITEM_PLATFORMFINALVERSION_COLUMN_NAME, catalogItemImpl.getDetailedCatalogItemImpl().getPlatformFinalVersion().toString());
            if (catalogItemImpl.getDetailedCatalogItemImpl().getDeveloper() != null){
                if (catalogItemImpl.getDetailedCatalogItemImpl().getDeveloper().getPublicKey() != null)
                    record.setStringValue(WalletStoreCatalogDatabaseConstants.ITEM_DEVELOPER_ID_COLUMN_NAME, catalogItemImpl.getDetailedCatalogItemImpl().getDeveloper().getPublicKey()); //Todo Revisar Este Caso si guardara la publickey o id UUID
            }

        } catch (CantGetWalletDetailsException cantGetWalletDetailsException) {
            throw new CantExecuteDatabaseOperationException(cantGetWalletDetailsException, null, null);
        }
        return record;
    }

    private DatabaseTransaction addCatalogItemInTransaction(DatabaseOperations databaseOperation, DatabaseTransaction transaction, CatalogItemImpl catalogItemImpl) throws InvalidDatabaseOperationException, CantExecuteDatabaseOperationException {
        DatabaseTable itemDatabaseTable = getDatabaseTable(WalletStoreCatalogDatabaseConstants.ITEM_TABLE_NAME);
        DatabaseTableRecord itemRecord = getItemInDatabaseTableRecord(catalogItemImpl);

        switch (databaseOperation){
            case INSERT:
                transaction.addRecordToInsert(itemDatabaseTable, itemRecord);
                break;
            case UPDATE:
                transaction.addRecordToUpdate(itemDatabaseTable, itemRecord);
                break;
            case SELECT:
                transaction.addRecordToSelect(itemDatabaseTable, itemRecord );
                break;
            default:
                throw throwInvalidDatabaseOperationException();
        }

        return transaction;
    }

    private DatabaseTableRecord getSkinRecord(com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Skin skin){
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreCatalogDatabaseConstants.WALLETSKIN_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();
        record.setUUIDValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_ID_COLUMN_NAME, skin.getSkinId());
        if (skin.getSkinName() != null)
            record.setStringValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_NAME_COLUMN_NAME, skin.getSkinName());
        if (skin.getVersion() != null)
            record.setStringValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_VERSION_COLUMN_NAME, skin.getVersion().toString());
        if (skin.getWalletId() != null)
            record.setUUIDValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_WALLETID_COLUMN_NAME, skin.getWalletId());
        if (skin.getInitialWalletVersion() != null)
            record.setStringValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_WALLETINITIALVERSION_COLUMN_NAME, skin.getInitialWalletVersion().toString());
        if (skin.getFinalWalletVersion() != null)
            record.setStringValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_WALLETFINALVERSION_COLUMN_NAME, skin.getFinalWalletVersion().toString());
        if (skin.getDesigner() != null){
            if (skin.getDesigner().getPublicKey() != null)
                record.setStringValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_DESIGNERID_COLUMN_NAME, skin.getDesigner().getPublicKey()); //Todo Revisar Este Caso si guardara la publickey o id UUID
        }

        record.setLongValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_SIZE_COLUMN_NAME, skin.getSkinSizeInBytes());
        record.setStringValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_ISDEFAULT_COLUMN_NAME, String.valueOf(skin.isDefault()));
        record.setStringValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_SCREEN_SIZE, String.valueOf(skin.getScreenSize()));

        return record;
    }

    private DatabaseTransaction addSkinInTransaction(DatabaseOperations databaseOperation, DatabaseTransaction transaction, com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Skin skin) throws InvalidDatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreCatalogDatabaseConstants.WALLETSKIN_TABLE_NAME);
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

    private DatabaseTableRecord getDeveloperInDatabaseTableRecord(DeveloperIdentity developer) {
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreCatalogDatabaseConstants.DEVELOPER_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();
        record.setStringValue(WalletStoreCatalogDatabaseConstants.DEVELOPER_ID_COLUMN_NAME, developer.getPublicKey()); //Todo: Revisar Esta caso si guardara el publickey o id UUID
        if (developer.getAlias() != null)
            record.setStringValue(WalletStoreCatalogDatabaseConstants.DEVELOPER_NAME_COLUMN_NAME, developer.getAlias());
        record.setStringValue(WalletStoreCatalogDatabaseConstants.DEVELOPER_PUBLICKEY_COLUMN_NAME, developer.getPublicKey());

        return record;
    }

    private DatabaseTransaction addDeveloperInTransaction(DatabaseOperations databaseOperation, DatabaseTransaction transaction, DeveloperIdentity developer) throws InvalidDatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreCatalogDatabaseConstants.DEVELOPER_TABLE_NAME);
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
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();
        record.setUUIDValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_ID_COLUMN_NAME, language.getLanguageId());
        if (language.getLanguageName() != null)
            record.setStringValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_NAME_COLUMN_NAME, language.getLanguageName().getCode());
        if (language.getLanguageLabel() != null)
            record.setStringValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_LABEL_COLUMN_NAME, language.getLanguageLabel());
        if (language.getVersion() != null)
            record.setStringValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_VERSION_COLUMN_NAME, language.getVersion().toString());
        if (language.getWalletId() != null)
            record.setUUIDValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_WALLETID_COLUMN_NAME, language.getWalletId());
        if (language.getInitialWalletVersion() != null)
            record.setStringValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_WALLETINITIALVERSION_COLUMN_NAME, language.getInitialWalletVersion().toString());
        if (language.getFinalWalletVersion() != null)
            record.setStringValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_WALLETFINALVERSION_COLUMN_NAME, language.getFinalWalletVersion().toString());

        record.setIntegerValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_FILESIZE_COLUMN_NAME, language.getLanguagePackageSizeInBytes());
        if (language.getTranslator() != null){
            if (language.getTranslator().getPublicKey() != null)
                record.setStringValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_TRANSLATORID_COLUMN_NAME, language.getTranslator().getPublicKey()); //Todo: Revisar si guardar publickey o  id UUID
        }

        record.setStringValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_ISDEFAULT_COLUMN_NAME, String.valueOf(language.isDefault()));

        return record;
    }

    private DatabaseTransaction addLanguageInTransaction(DatabaseOperations databaseOperation, DatabaseTransaction transaction, Language language) throws InvalidDatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_TABLE_NAME);
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
        table.addUUIDFilter(filter.getColumn(), UUID.fromString(filter.getValue()), filter.getType());
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

    private List<DatabaseTableRecord> getAllRecordsFromDatabase (String tableName) throws CantExecuteDatabaseOperationException, InvalidResultReturnedByDatabaseException {
        DatabaseTable table = getDatabaseTable(tableName);
        try {
            table.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemoryException) {
            throw new CantExecuteDatabaseOperationException(cantLoadTableToMemoryException, null, "Error in database plugin.");
        }

        List<DatabaseTableRecord> recordList = table.getRecords();
        if (recordList.size() ==  0)
            throw new InvalidResultReturnedByDatabaseException(null, " number of records: " + recordList.size(), "database inconsistency");

        return recordList;
    }

    private CatalogItemImpl getCatalogItemFromDatabase (final UUID id) throws InvalidResultReturnedByDatabaseException, CantExecuteDatabaseOperationException {
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
                return WalletStoreCatalogDatabaseConstants.ITEM_ID_COLUMN_NAME;
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
        List<DatabaseTableRecord> records = getRecordsFromDatabase(WalletStoreCatalogDatabaseConstants.ITEM_TABLE_NAME, tableFilter);

        if (records.size() != 1)
            throw new InvalidResultReturnedByDatabaseException(null, "Id: " + tableFilter.getValue() + " number of records: " + records.size(), "database inconsistency");

        DatabaseTableRecord record = records.get(0);

        CatalogItemImpl catalogItemImpl = new CatalogItemImpl();
        catalogItemImpl.setWalletName(record.getStringValue(WalletStoreCatalogDatabaseConstants.ITEM_NAME_COLUMN_NAME));
        catalogItemImpl.setCategory(WalletCategory.valueOf(record.getStringValue(WalletStoreCatalogDatabaseConstants.ITEM_CATEGORY_COLUMN_NAME)));
        catalogItemImpl.setDescription(record.getStringValue(WalletStoreCatalogDatabaseConstants.ITEM_DESCRIPTION_COLUMN_NAME));
        catalogItemImpl.setDefaultSizeInBytes(record.getIntegerValue(WalletStoreCatalogDatabaseConstants.ITEM_SIZE_COLUMN_NAME));
        catalogItemImpl.setId(record.getUUIDValue(WalletStoreCatalogDatabaseConstants.ITEM_ID_COLUMN_NAME));
        /**
         * the detailed catalog item will be null at this point.
         */

        String databaseURL = null;
        URL url = null;
        try {
            databaseURL = record.getStringValue(WalletStoreCatalogDatabaseConstants.ITEM_PUBLISHER_WEB_SITE_URL_COLUMN_NAME);
            url = new URL(databaseURL);
        } catch (MalformedURLException e) {
            throw new InvalidResultReturnedByDatabaseException(e, databaseURL, "incorrect URL format.");
        }
        catalogItemImpl.setpublisherWebsiteUrl(url);
        return catalogItemImpl;
    }

    private List<CatalogItemImpl> getAllCatalogItemsFromDatabase() throws InvalidResultReturnedByDatabaseException, CantExecuteDatabaseOperationException{
        List<CatalogItemImpl> catalogItemImpls = new ArrayList<CatalogItemImpl>();

        List<DatabaseTableRecord> records = getAllRecordsFromDatabase(WalletStoreCatalogDatabaseConstants.ITEM_TABLE_NAME);

        for (DatabaseTableRecord record : records){
            CatalogItemImpl catalogItemImpl = new CatalogItemImpl();
            catalogItemImpl.setWalletName(record.getStringValue(WalletStoreCatalogDatabaseConstants.ITEM_NAME_COLUMN_NAME));
            catalogItemImpl.setCategory(WalletCategory.valueOf(record.getStringValue(WalletStoreCatalogDatabaseConstants.ITEM_CATEGORY_COLUMN_NAME)));
            catalogItemImpl.setDescription(record.getStringValue(WalletStoreCatalogDatabaseConstants.ITEM_DESCRIPTION_COLUMN_NAME));
            catalogItemImpl.setDefaultSizeInBytes(record.getIntegerValue(WalletStoreCatalogDatabaseConstants.ITEM_SIZE_COLUMN_NAME));
            catalogItemImpl.setId(record.getUUIDValue(WalletStoreCatalogDatabaseConstants.ITEM_ID_COLUMN_NAME));
            

            catalogItemImpls.add(catalogItemImpl);
        }
        return catalogItemImpls;
    }

    private DetailedCatalogItemImpl getDetailedCatalogItemFromDatabase (final UUID walletId) throws InvalidResultReturnedByDatabaseException, CantExecuteDatabaseOperationException {
        DetailedCatalogItemImpl detailedCatalogItemImpl = new DetailedCatalogItemImpl();

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
                return WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_WALLETID_COLUMN_NAME;
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
        List<DatabaseTableRecord> records = getRecordsFromDatabase(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_TABLE_NAME, tableFilter);

        List<com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Language> languages = new ArrayList<>();
        for (DatabaseTableRecord record : records){
            languages.add(getLanguageFromDatabase(record.getUUIDValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_ID_COLUMN_NAME)));
        }

        if (languages.size() == 0)
            throw new InvalidResultReturnedByDatabaseException(null, "Id: " + tableFilter.getValue() + " number of records: " + records.size(), "database inconsistency");

        for (com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Language language : languages){
            if (language.isDefault())
                detailedCatalogItemImpl.setLanguage(language);
        }

        /**
         * Modify by Manuel Perez on 09/12/2015:
         * I need to fix the installation process, the default language can be null at this point, so, if
         * detailedCatalogItemImpl.getDefaultLanguage() is null, I'm going to force that the first language in
         * languages will be the defaultLanguage
         * */
        try {
            if(detailedCatalogItemImpl.getDefaultLanguage()==null){
                detailedCatalogItemImpl.setLanguage(languages.get(0));
            }
        } catch (CantGetLanguageException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_STORE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

        //End of modify
        detailedCatalogItemImpl.setLanguages(languages);

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
                return WalletStoreCatalogDatabaseConstants.WALLETSKIN_WALLETID_COLUMN_NAME;
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

        List<com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Skin> skins = new ArrayList<>();
        records = getRecordsFromDatabase(WalletStoreCatalogDatabaseConstants.WALLETSKIN_TABLE_NAME, tableFilter);

        for (DatabaseTableRecord record : records){
            skins.add(getSkinFromDatabase(record.getUUIDValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_ID_COLUMN_NAME)));
        }
        if (skins.size() == 0)
            throw new InvalidResultReturnedByDatabaseException(null, "Id: " + tableFilter.getValue() + " number of records: " + records.size(), "database inconsistency");

        for (com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Skin skin : skins){
            if (skin.isDefault())
                detailedCatalogItemImpl.setDefaultSkin(skin);
        }


        detailedCatalogItemImpl.setSkins(skins);

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
                return WalletStoreCatalogDatabaseConstants.ITEM_ID_COLUMN_NAME;
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
        records = getRecordsFromDatabase(WalletStoreCatalogDatabaseConstants.ITEM_TABLE_NAME, tableFilter);

        if (records.size() != 1)
            throw new InvalidResultReturnedByDatabaseException(null, "Id: " + tableFilter.getValue() + " number of records: " + records.size(), "database inconsistency");

        DatabaseTableRecord record = records.get(0);
        if (record.getStringValue(WalletStoreCatalogDatabaseConstants.ITEM_VERSION_COLUMN_NAME) != null)
            detailedCatalogItemImpl.setVersion(new Version(record.getStringValue(WalletStoreCatalogDatabaseConstants.ITEM_VERSION_COLUMN_NAME)));
        if (record.getStringValue(WalletStoreCatalogDatabaseConstants.ITEM_PLATFORMINITIALVERSION_COLUMN_NAME) != null)
            detailedCatalogItemImpl.setPlatformInitialVersion(new Version(record.getStringValue(WalletStoreCatalogDatabaseConstants.ITEM_PLATFORMINITIALVERSION_COLUMN_NAME)));
        if (record.getStringValue(WalletStoreCatalogDatabaseConstants.ITEM_PLATFORMFINALVERSION_COLUMN_NAME) != null)
            detailedCatalogItemImpl.setPlatformFinalVersion(new Version(record.getStringValue(WalletStoreCatalogDatabaseConstants.ITEM_PLATFORMFINALVERSION_COLUMN_NAME)));

        // Gets the developer from database and assign it to the catalog item
        if (record.getStringValue(WalletStoreCatalogDatabaseConstants.ITEM_DEVELOPER_ID_COLUMN_NAME) != null){
            final UUID developerId = record.getUUIDValue(WalletStoreCatalogDatabaseConstants.ITEM_DEVELOPER_ID_COLUMN_NAME);
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
                    return WalletStoreCatalogDatabaseConstants.DEVELOPER_ID_COLUMN_NAME;
                }

                @Override
                public String getValue() {
                    return developerId.toString();
                }

                @Override
                public DatabaseFilterType getType() {
                    return DatabaseFilterType.EQUAL;
                }
            };
            records = getRecordsFromDatabase(WalletStoreCatalogDatabaseConstants.DEVELOPER_TABLE_NAME, tableFilter);

            if (records.size() != 1)
                throw new InvalidResultReturnedByDatabaseException(null, "Id: " + tableFilter.getValue() + " number of records: " + records.size(), "database inconsistency");

            DatabaseTableRecord developerRecord = records.get(0);

            Developer developer = new Developer();
            developer.setId(developerRecord.getUUIDValue(WalletStoreCatalogDatabaseConstants.DEVELOPER_ID_COLUMN_NAME));
            developer.setPublicKey(developerRecord.getStringValue(WalletStoreCatalogDatabaseConstants.DEVELOPER_PUBLICKEY_COLUMN_NAME));
            developer.setAlias(developerRecord.getStringValue(WalletStoreCatalogDatabaseConstants.DEVELOPER_NAME_COLUMN_NAME));
            detailedCatalogItemImpl.setDeveloper(developer);
        }


        return detailedCatalogItemImpl;
    }


    /**
     * Gets the Catalog item from the database
     * @param walletId
     * @return
     * @throws CantExecuteDatabaseOperationException
     */
    public CatalogItemImpl getCatalogItem(UUID walletId) throws CantExecuteDatabaseOperationException{
        openDatabase();
        CatalogItemImpl catalogItemImpl =null;
        try {
            catalogItemImpl = getCatalogItemFromDatabase(walletId);
            closeDatabase();
        } catch (Exception exception){
            closeDatabase();
            throw new CantExecuteDatabaseOperationException(exception, null,null);
        }

        return catalogItemImpl;
    }

    /**
     * Gets the entire catalog from the database
     * @return
     * @throws CantExecuteDatabaseOperationException
     */
    public List<CatalogItemImpl> getCatalogItems() throws CantExecuteDatabaseOperationException{
        openDatabase();
        try {
            List<CatalogItemImpl> catalogItemImpls =getAllCatalogItemsFromDatabase();
            closeDatabase();
            return catalogItemImpls;
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
    public DetailedCatalogItemImpl getDetailedCatalogItem(UUID walletId) throws  CantExecuteDatabaseOperationException{
        openDatabase();
        DetailedCatalogItemImpl detailedCatalogItemImpl = null;
        try {
            detailedCatalogItemImpl = getDetailedCatalogItemFromDatabase(walletId);
            closeDatabase();
        } catch (Exception exception){
            closeDatabase();
            throw new CantExecuteDatabaseOperationException(exception, null,null);
        }

        return detailedCatalogItemImpl;
    }

    /**
     * gets the skin from the specified wallet
     * @param walletId
     * @return
     * @throws InvalidResultReturnedByDatabaseException
     * @throws CantExecuteDatabaseOperationException
     */
    public Skin getSkinFromDatabase(final UUID walletId) throws InvalidResultReturnedByDatabaseException, CantExecuteDatabaseOperationException {
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
                return WalletStoreCatalogDatabaseConstants.WALLETSKIN_ID_COLUMN_NAME;
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
        List<DatabaseTableRecord> records = getRecordsFromDatabase(WalletStoreCatalogDatabaseConstants.WALLETSKIN_TABLE_NAME, tableFilter);

        if (records.size() != 1)
            throw new InvalidResultReturnedByDatabaseException(null, "Id: " + tableFilter.getValue() + " number of records: " + records.size(), "database inconsistency");

        DatabaseTableRecord record = records.get(0);
        Skin skin = new Skin();
        skin.setId(record.getUUIDValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_ID_COLUMN_NAME));
        skin.setWalletId(walletId);
        if (record.getStringValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_NAME_COLUMN_NAME) != null)
            skin.setName(record.getStringValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_NAME_COLUMN_NAME));
        if (record.getStringValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_WALLETINITIALVERSION_COLUMN_NAME) != null)
            skin.setInitialWalletVersion(new Version(record.getStringValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_WALLETINITIALVERSION_COLUMN_NAME)));
        if (record.getStringValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_WALLETFINALVERSION_COLUMN_NAME) != null)
            skin.setFinalWalletVersion(new Version(record.getStringValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_WALLETFINALVERSION_COLUMN_NAME)));
        if (record.getStringValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_VERSION_COLUMN_NAME) != null)
            skin.setVersion(new Version(record.getStringValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_VERSION_COLUMN_NAME)));
        if (record.getStringValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_ISDEFAULT_COLUMN_NAME) != null)
            skin.setIsDefault(Boolean.valueOf(record.getStringValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_ISDEFAULT_COLUMN_NAME)));
        skin.setScreenSize(ScreenSize.valueOf(record.getStringValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_SCREEN_SIZE)));
        if (record.getStringValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_DESIGNERID_COLUMN_NAME) != null){
            Designer designer = getDesigner(record.getUUIDValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_DESIGNERID_COLUMN_NAME));
            skin.setDesigner(designer);
        }

        skin.setSkinSizeInBytes(record.getIntegerValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_SIZE_COLUMN_NAME));

        return skin;
    }

    /**
     * Gets the language of the wallet passed.
     * @param walletId
     * @return
     * @throws InvalidResultReturnedByDatabaseException
     * @throws CantExecuteDatabaseOperationException
     */
    public Language getLanguageFromDatabase(final UUID walletId) throws InvalidResultReturnedByDatabaseException, CantExecuteDatabaseOperationException {
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
                return WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_ID_COLUMN_NAME;
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
        List<DatabaseTableRecord> records = getRecordsFromDatabase(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_TABLE_NAME, tableFilter);

        if (records.size() != 1)
            throw new InvalidResultReturnedByDatabaseException(null, "Id: " + tableFilter.getValue() + " number of records: " + records.size(), "database inconsistency");

        DatabaseTableRecord record = records.get(0);
        Language language = new Language();
        language.setWalletId(walletId);
        language.setId(record.getUUIDValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_ID_COLUMN_NAME));
        if (record.getStringValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_NAME_COLUMN_NAME) != null)
            language.setLanguageName(Languages.fromValue(record.getStringValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_NAME_COLUMN_NAME)));
        if (record.getStringValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_WALLETFINALVERSION_COLUMN_NAME) != null)
            language.setFinalWalletVersion(new Version(record.getStringValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_WALLETFINALVERSION_COLUMN_NAME)));
        if (record.getStringValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_WALLETINITIALVERSION_COLUMN_NAME) != null)
            language.setInitialWalletVersion(new Version(record.getStringValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_WALLETINITIALVERSION_COLUMN_NAME)));
        if (record.getStringValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_VERSION_COLUMN_NAME) != null)
            language.setVersion(new Version(record.getStringValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_VERSION_COLUMN_NAME)));
        if (record.getStringValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_ISDEFAULT_COLUMN_NAME) != null)
            language.setIsDefault(Boolean.valueOf(record.getStringValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_ISDEFAULT_COLUMN_NAME)));
        if (record.getStringValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_LABEL_COLUMN_NAME) != null)
            language.setLanguageLabel(record.getStringValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_LABEL_COLUMN_NAME));

        language.setLanguagePackageSizeInBytes(record.getIntegerValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_FILESIZE_COLUMN_NAME));

        if (record.getStringValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_TRANSLATORID_COLUMN_NAME) != null){
            Translator translator = getTranslator(record.getUUIDValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_TRANSLATORID_COLUMN_NAME));
            language.setTranslator(translator);
        }



        return language;

    }

    /**
     * Inserts or Update an Item in the catalog Database base.
     * Every other object added will be updated or inserted in the same transaction.
     * @param databaseOperation enum. The database operation to execute in the transaction
     * @param catalogItemImpl the item to save/update or select in the transaction
     * @param developer
     * @param language
     * @param translator
     * @param skin
     * @param designer
     * @throws CantExecuteDatabaseOperationException
     */
    public void catalogDatabaseOperation(DatabaseOperations databaseOperation, CatalogItemImpl catalogItemImpl, DeveloperIdentity developer, Language language, TranslatorIdentity translator,  com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Skin skin, DesignerIdentity designer) throws CantExecuteDatabaseOperationException {

        database = openDatabase();
        DatabaseTransaction transaction = database.newTransaction();
        try{
            if (catalogItemImpl != null)
                transaction = addCatalogItemInTransaction(databaseOperation, transaction, catalogItemImpl);
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
            throw new CantExecuteDatabaseOperationException(exception, catalogItemImpl.toString(), null);
        }
    }

    /**
     * Returns the developer from Database with the passed ID
     * @param developerId
     * @return
     * @throws CantExecuteDatabaseOperationException
     */
    public Developer getDeveloper(UUID developerId) throws CantExecuteDatabaseOperationException {
        try {
            DatabaseTableRecord record = getDeveloperRecord(developerId);
            Developer developer = new Developer();
            developer.setId(record.getUUIDValue(WalletStoreCatalogDatabaseConstants.DEVELOPER_ID_COLUMN_NAME));
            developer.setAlias(record.getStringValue(WalletStoreCatalogDatabaseConstants.DEVELOPER_NAME_COLUMN_NAME));
            developer.setPublicKey(record.getStringValue(WalletStoreCatalogDatabaseConstants.DEVELOPER_PUBLICKEY_COLUMN_NAME));
            return developer;
        } catch (Exception e) {
            throw new CantExecuteDatabaseOperationException(e, null, null);
        }
    }

    /**
     * Gets the designer with the specified id.
     * @param designerId
     * @return
     * @throws CantExecuteDatabaseOperationException
     */
    public Designer getDesigner(UUID designerId) throws CantExecuteDatabaseOperationException {
        try{
            DatabaseTableRecord record = getDesignerRecord(designerId);
            Designer designer = new Designer();
            designer.setiD(designerId);
            designer.setAlias(record.getStringValue(WalletStoreCatalogDatabaseConstants.DESIGNER_NAME_COLUMN_NAME));
            designer.setPublicKey(record.getStringValue(WalletStoreCatalogDatabaseConstants.DESIGNER_PUBLICKEY_COLUMN_NAME));

            return designer;
        } catch (Exception exception){
            throw new CantExecuteDatabaseOperationException(exception,null,null);
        }
    }

    /**
     * Gets the specified translator Id
     * @param translatorId
     * @return
     * @throws CantExecuteDatabaseOperationException
     */
    public Translator getTranslator(UUID translatorId) throws CantExecuteDatabaseOperationException {
        try{
            DatabaseTableRecord record = getTranslatorRecord(translatorId);
            Translator translator= new Translator();
            translator.setId(translatorId);
            translator.setAlias(record.getStringValue(WalletStoreCatalogDatabaseConstants.TRANSLATOR_ID_COLUMN_NAME));
            translator.setPublicKey(record.getStringValue(WalletStoreCatalogDatabaseConstants.TRANSLATOR_PUBLICKEY_COLUMN_NAME));

            return translator;
        } catch (Exception exception){
            throw new CantExecuteDatabaseOperationException(exception,null,null);
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


    private List<UUID> getWalletIdsForNetworkService() throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreCatalogDatabaseConstants.ITEM_TABLE_NAME);
        List<UUID> uuids = new ArrayList<>();
        try {
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            for (DatabaseTableRecord record : records){
                uuids.add(record.getUUIDValue(WalletStoreCatalogDatabaseConstants.ITEM_ID_COLUMN_NAME));
            }
            return uuids;
        } catch (CantLoadTableToMemoryException e) {
            throw  new CantExecuteDatabaseOperationException(e, null, null);
        }
    }

    private List<UUID> getWalletLanguagesForNetworkService() throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_TABLE_NAME);
        List<UUID> uuids = new ArrayList<>();
        try {
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            for (DatabaseTableRecord record : records){
                uuids.add(record.getUUIDValue(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_ID_COLUMN_NAME));
            }
            return uuids;
        } catch (CantLoadTableToMemoryException e) {
            throw  new CantExecuteDatabaseOperationException(e, null, null);
        }
    }

    private List<UUID> getWalletSkinsForNetworkService() throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreCatalogDatabaseConstants.WALLETSKIN_TABLE_NAME);
        List<UUID> uuids = new ArrayList<>();
        try {
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            for (DatabaseTableRecord record : records){
                uuids.add(record.getUUIDValue(WalletStoreCatalogDatabaseConstants.WALLETSKIN_ID_COLUMN_NAME));
            }
            return uuids;
        } catch (CantLoadTableToMemoryException e) {
            throw  new CantExecuteDatabaseOperationException(e, null, null);
        }
    }

    private DatabaseTableRecord getDeveloperRecord(UUID developerId) throws InvalidResultReturnedByDatabaseException, CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreCatalogDatabaseConstants.DEVELOPER_TABLE_NAME);
        databaseTable.addStringFilter(WalletStoreCatalogDatabaseConstants.DEVELOPER_ID_COLUMN_NAME, developerId.toString(), DatabaseFilterType.EQUAL);
        try {
            databaseTable.loadToMemory();
            if (databaseTable.getRecords().size() != 1)
                throw new InvalidResultReturnedByDatabaseException(null, "developer Id: " + developerId.toString(), null );

            return databaseTable.getRecords().get(0);
        } catch (CantLoadTableToMemoryException e) {
            throw new CantExecuteDatabaseOperationException(e, null, null);
        }
    }

    private DatabaseTableRecord getDesignerRecord(UUID designerId) throws InvalidResultReturnedByDatabaseException, CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreCatalogDatabaseConstants.DESIGNER_TABLE_NAME);
        databaseTable.addStringFilter(WalletStoreCatalogDatabaseConstants.DESIGNER_ID_COLUMN_NAME, designerId.toString(), DatabaseFilterType.EQUAL);
        try {
            databaseTable.loadToMemory();
            if (databaseTable.getRecords().size() != 1)
                throw new InvalidResultReturnedByDatabaseException(null, "Designer Id: " + designerId.toString(), null );

            return databaseTable.getRecords().get(0);
        } catch (CantLoadTableToMemoryException e) {
            throw new CantExecuteDatabaseOperationException(e, null, null);
        }
    }

    private DatabaseTableRecord getTranslatorRecord(UUID translatorId) throws InvalidResultReturnedByDatabaseException, CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(WalletStoreCatalogDatabaseConstants.TRANSLATOR_TABLE_NAME);
        databaseTable.addStringFilter(WalletStoreCatalogDatabaseConstants.TRANSLATOR_ID_COLUMN_NAME, translatorId.toString(), DatabaseFilterType.EQUAL);
        try {
            databaseTable.loadToMemory();
            if (databaseTable.getRecords().size() != 1)
                throw new InvalidResultReturnedByDatabaseException(null, "Designer Id: " + translatorId.toString(), null );

            return databaseTable.getRecords().get(0);
        } catch (CantLoadTableToMemoryException e) {
            throw new CantExecuteDatabaseOperationException(e, null, null);
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
