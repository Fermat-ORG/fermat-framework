package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_language.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.VersionCompatibility;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.enums.LanguageState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantCreateEmptyWalletLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantDeleteWalletLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantGetWalletLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantGetWalletLanguagesException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantUpdateLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.LanguageNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.interfaces.WalletLanguage;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_language.developer.bitdubai.version_1.exceptions.CantInitializeWalletLanguageMiddlewareDatabaseException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_language.developer.bitdubai.version_1.structure.WalletLanguageMiddlewareWalletLanguage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_language.developer.bitdubai.version_1.database.WalletLanguageMiddlewareDao</code>
 * has all methods related with database access.<p/>
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletLanguageMiddlewareDao implements DealsWithPluginDatabaseSystem {

    /**
     * Represent the Plugin Database.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor with parameters
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */

    public WalletLanguageMiddlewareDao(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Represent de Database where i will be working with
     */
    Database database;

    /**
     * This method open or creates the database i'll be working with
     *
     * @param ownerId      plugin id
     * @param databaseName database name
     * @throws CantInitializeWalletLanguageMiddlewareDatabaseException
     */
    public void initializeDatabase(UUID ownerId, String databaseName) throws CantInitializeWalletLanguageMiddlewareDatabaseException {
        try {
             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(ownerId, databaseName);
            database.closeDatabase();
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeWalletLanguageMiddlewareDatabaseException(CantInitializeWalletLanguageMiddlewareDatabaseException.DEFAULT_MESSAGE, cantOpenDatabaseException, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (DatabaseNotFoundException e) {
             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            WalletLanguageMiddlewareDatabaseFactory walletFactoryMiddlewareDatabaseFactory = new WalletLanguageMiddlewareDatabaseFactory(pluginDatabaseSystem);
            try {
                  /*
                   * We create the new database
                   */
                database = walletFactoryMiddlewareDatabaseFactory.createDatabase(ownerId, databaseName);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeWalletLanguageMiddlewareDatabaseException(CantInitializeWalletLanguageMiddlewareDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "There is a problem and i cannot create the database.");
            }
        }
    }

    /**
     * Method that list the all entities on the database.
     *
     * @return All Wallet Factory Projects Proposals who belongs to a developer.
     */
    public List<WalletLanguage> findAllLanguagesByTranslator(String translatorPublicKey) throws CantGetWalletLanguagesException, InvalidParameterException {

        List<WalletLanguage> walletFactoryProjectLanguages = new ArrayList<>();

        try {
            database.openDatabase();
            DatabaseTable projectLanguageTable = database.getTable(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_TABLE_NAME);
            projectLanguageTable.setStringFilter(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_TRANSLATOR_PUBLIC_KEY_COLUMN_NAME, translatorPublicKey, DatabaseFilterType.EQUAL);
            projectLanguageTable.setFilterOrder(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_NAME_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
            projectLanguageTable.loadToMemory();

            List<DatabaseTableRecord> records = projectLanguageTable.getRecords();

            for (DatabaseTableRecord record : records) {
                UUID id = record.getUUIDValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_ID_COLUMN_NAME);
                UUID languageId = record.getUUIDValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_LANGUAGE_ID_COLUMN_NAME);
                String name = record.getStringValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_NAME_COLUMN_NAME);
                Languages type = Languages.fromValue(record.getStringValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_LANGUAGE_TYPE_COLUMN_NAME));
                LanguageState state = LanguageState.getByCode(record.getStringValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_LANGUAGE_STATE_COLUMN_NAME));
                Version version = new Version(record.getStringValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_VERSION_COLUMN_NAME));

                WalletLanguageMiddlewareWalletLanguage walletFactoryProjectLanguage = new WalletLanguageMiddlewareWalletLanguage(id, languageId, name, type, state, translatorPublicKey, version);

                walletFactoryProjectLanguages.add(walletFactoryProjectLanguage);
            }
            database.closeDatabase();
            return walletFactoryProjectLanguages;

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantGetWalletLanguagesException(CantGetWalletLanguagesException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantGetWalletLanguagesException(CantGetWalletLanguagesException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    public WalletLanguage findLanguageById(UUID id) throws CantGetWalletLanguageException, LanguageNotFoundException, InvalidParameterException {
        try {
            database.openDatabase();
            DatabaseTable projectLanguageTable = database.getTable(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_TABLE_NAME);
            projectLanguageTable.setUUIDFilter(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            projectLanguageTable.loadToMemory();

            List<DatabaseTableRecord> records = projectLanguageTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                UUID languageId = record.getUUIDValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_LANGUAGE_ID_COLUMN_NAME);
                String name = record.getStringValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_NAME_COLUMN_NAME);
                Languages type = Languages.fromValue(record.getStringValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_LANGUAGE_TYPE_COLUMN_NAME));
                LanguageState state = LanguageState.getByCode(record.getStringValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_LANGUAGE_STATE_COLUMN_NAME));
                String translatorPublicKey = record.getStringValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_TRANSLATOR_PUBLIC_KEY_COLUMN_NAME);
                Version version = new Version(record.getStringValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_VERSION_COLUMN_NAME));

                database.closeDatabase();
                return new WalletLanguageMiddlewareWalletLanguage(id, languageId, name, type, state, translatorPublicKey, version);
            } else {
                database.closeDatabase();
                throw new LanguageNotFoundException(LanguageNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a wallet language with this name.");
            }

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantGetWalletLanguageException(CantGetWalletLanguageException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantGetWalletLanguageException(CantGetWalletLanguageException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    public void createLanguage(WalletLanguage walletLanguage) throws CantCreateEmptyWalletLanguageException {
        try {
            database.openDatabase();
            DatabaseTable projectLanguageTable = database.getTable(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_TABLE_NAME);

            DatabaseTableRecord record = projectLanguageTable.getEmptyRecord();

            record.setUUIDValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_ID_COLUMN_NAME, walletLanguage.getId());
            record.setUUIDValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_LANGUAGE_ID_COLUMN_NAME, walletLanguage.getLanguageId());
            record.setStringValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_NAME_COLUMN_NAME, walletLanguage.getName());
            record.setStringValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_LANGUAGE_TYPE_COLUMN_NAME, walletLanguage.getType().value());
            record.setStringValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_LANGUAGE_STATE_COLUMN_NAME, walletLanguage.getState().getCode());
            record.setStringValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_TRANSLATOR_PUBLIC_KEY_COLUMN_NAME, walletLanguage.getTranslatorPublicKey().toString());
            record.setStringValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_VERSION_COLUMN_NAME, walletLanguage.getVersion().toString());

            try {
                projectLanguageTable.insertRecord(record);
                database.closeDatabase();
            } catch (CantInsertRecordException e) {
                database.closeDatabase();
                throw new CantCreateEmptyWalletLanguageException(CantCreateEmptyWalletLanguageException.DEFAULT_MESSAGE, e, "Cannot insert the wallet language", "");
            }
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantCreateEmptyWalletLanguageException(CantCreateEmptyWalletLanguageException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    public void updateLanguage(WalletLanguage walletLanguage) throws CantUpdateLanguageException, LanguageNotFoundException, InvalidParameterException {
        try {
            database.openDatabase();
            DatabaseTable projectLanguageTable = database.getTable(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_TABLE_NAME);
            projectLanguageTable.setUUIDFilter(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_ID_COLUMN_NAME, walletLanguage.getId(), DatabaseFilterType.EQUAL);
            projectLanguageTable.loadToMemory();

            List<DatabaseTableRecord> records = projectLanguageTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                UUID languageId = record.getUUIDValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_LANGUAGE_ID_COLUMN_NAME);
                if (!languageId.equals(walletLanguage.getLanguageId()))
                    record.setUUIDValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_LANGUAGE_ID_COLUMN_NAME, walletLanguage.getLanguageId());

                String name = record.getStringValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_NAME_COLUMN_NAME);
                if (!name.equals(walletLanguage.getName()))
                    record.setStringValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_NAME_COLUMN_NAME, walletLanguage.getName());

                Languages type = Languages.fromValue(record.getStringValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_LANGUAGE_TYPE_COLUMN_NAME));
                if (!type.value().equals(walletLanguage.getType().value()))
                    record.setStringValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_LANGUAGE_TYPE_COLUMN_NAME, walletLanguage.getType().value());

                LanguageState state = LanguageState.getByCode(record.getStringValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_LANGUAGE_STATE_COLUMN_NAME));
                if (!state.getCode().equals(walletLanguage.getState().getCode()))
                    record.setStringValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_LANGUAGE_STATE_COLUMN_NAME, walletLanguage.getState().getCode());

                Version version = new Version(record.getStringValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_VERSION_COLUMN_NAME));
                if (!version.equals(walletLanguage.getVersion()))
                    record.setStringValue(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_VERSION_COLUMN_NAME, walletLanguage.getVersion().toString());

                try {
                    projectLanguageTable.updateRecord(record);
                    database.closeDatabase();
                } catch (CantUpdateRecordException e) {
                    database.closeDatabase();
                    throw new CantUpdateLanguageException(CantUpdateLanguageException.DEFAULT_MESSAGE, e, "Cannot update the wallet language", "");
                }
            } else {
                database.closeDatabase();
                throw new LanguageNotFoundException(LanguageNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a wallet language with this name.");
            }

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantUpdateLanguageException(CantUpdateLanguageException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantUpdateLanguageException(CantUpdateLanguageException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    public void deleteLanguage(UUID id) throws CantDeleteWalletLanguageException, LanguageNotFoundException {
        try {
            database.openDatabase();
            DatabaseTable projectLanguageTable = database.getTable(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_TABLE_NAME);
            projectLanguageTable.setUUIDFilter(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            projectLanguageTable.loadToMemory();

            List<DatabaseTableRecord> records = projectLanguageTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                try {
                    projectLanguageTable.deleteRecord(record);
                    database.closeDatabase();
                } catch (CantDeleteRecordException e) {
                    database.closeDatabase();
                    throw new CantDeleteWalletLanguageException(CantDeleteWalletLanguageException.DEFAULT_MESSAGE, e, "Cannot delete the wallet language", "");
                }
            } else {
                database.closeDatabase();
                throw new LanguageNotFoundException(LanguageNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a wallet language with this name.");
            }

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantDeleteWalletLanguageException(CantDeleteWalletLanguageException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantDeleteWalletLanguageException(CantDeleteWalletLanguageException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}
