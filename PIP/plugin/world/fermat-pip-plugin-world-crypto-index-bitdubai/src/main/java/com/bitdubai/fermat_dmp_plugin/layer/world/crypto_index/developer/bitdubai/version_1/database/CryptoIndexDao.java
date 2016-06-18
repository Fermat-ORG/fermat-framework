package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.CantGetAllRateExchangeException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.CantGetCryptoIndexIdentitiesException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.CantInitializeCryptoIndexDatabaseException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.CantSaveLastRateExchangeException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.CryptoIndexInterface;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.CryptoIndexList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by francisco on 10/09/15.
 */
public class CryptoIndexDao implements Serializable {

    private PluginDatabaseSystem pluginDatabaseSystem;

    private UUID pluginId;

    /**
     * Represent de Database where i will be working with
     */
    /**
     * This method open or creates the database i'll be working with     *
     *
     * @throws CantInitializeCryptoIndexDatabaseException
     */
    public CryptoIndexDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    Database database;

    public void initializeDatabase() throws CantInitializeCryptoIndexDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, CryptoIndexDatabaseConstants.CRYPTO_INDEX_DATABASE_NAME);
            database.closeDatabase();
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

        } catch (DatabaseNotFoundException databaseNotFoundException) {
            /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            CryptoIndexDatabaseFactory cryptoIndexDatabaseFactory = new CryptoIndexDatabaseFactory(pluginDatabaseSystem);
            try {
                 /*
                   * We create the new database
                   */
                cryptoIndexDatabaseFactory.createDatabase(this.pluginId, CryptoIndexDatabaseConstants.CRYPTO_INDEX_DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {

            } catch (Exception exception) {

            }
        }

    }

    public void saveLastRateExchange(String crypto, String fiat, double exchangeRate) throws CantSaveLastRateExchangeException {
        if (crypto == null || fiat == null || exchangeRate == 0) {
            throw new CantSaveLastRateExchangeException(CantSaveLastRateExchangeException.DEFAULT_MESSAGE, null, "Crypto Fiat Exchange Rate", "The parameters can not be null");
        }
        try {
            Date date = new Date();
            long time = date.getTime();
            UUID id = UUID.randomUUID();
            DatabaseTable table = this.database.getTable(CryptoIndexDatabaseConstants.CRYPTO_INDEX_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();

            record.setUUIDValue(CryptoIndexDatabaseConstants.CRYPTO_INDEX_PRIMARY_KEY_COLUMN_NAME, id);
            record.setStringValue(CryptoIndexDatabaseConstants.CRYPTO_INDEX_CRYPTO_CURRENCY_COLUMN_NAME, crypto);
            record.setStringValue(CryptoIndexDatabaseConstants.CRYPTO_INDEX_FIAT_CURRENCY_COLUMN_NAME, fiat);
            record.setStringValue(CryptoIndexDatabaseConstants.CRYPTO_INDEX_TIME_COLUMN_NAME, String.valueOf(time));
            record.setDoubleValue(CryptoIndexDatabaseConstants.CRYPTO_INDEX_EXCHANGE_RATE_COLUMN_NAME, exchangeRate);

            table.insertRecord(record);

            database.closeDatabase();
        } catch (CantInsertRecordException e) {
            database.closeDatabase();
        } catch (Exception exception) {

        }
    }

    public List<CryptoIndexInterface> getLastExchangeRateList(String crypto, String fiat) throws CantGetAllRateExchangeException {

        List<CryptoIndexInterface> LastExchangeRateList = new ArrayList<>();
        DatabaseTable table;

        try {
            table = this.database.getTable(CryptoIndexDatabaseConstants.CRYPTO_INDEX_TABLE_NAME);
            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetCryptoIndexIdentitiesException("Cant get Crypto Index list, table not found.", "Crypto Index", "");
            }
            table.addStringFilter(CryptoIndexDatabaseConstants.CRYPTO_INDEX_CRYPTO_CURRENCY_COLUMN_NAME, crypto, DatabaseFilterType.EQUAL);
            table.addStringFilter(CryptoIndexDatabaseConstants.CRYPTO_INDEX_FIAT_CURRENCY_COLUMN_NAME, fiat, DatabaseFilterType.EQUAL);

            table.loadToMemory();
            for (DatabaseTableRecord record : table.getRecords()) {
                LastExchangeRateList.add(new CryptoIndexList(
                        record.getStringValue(CryptoIndexDatabaseConstants.CRYPTO_INDEX_CRYPTO_CURRENCY_COLUMN_NAME),
                        record.getStringValue(CryptoIndexDatabaseConstants.CRYPTO_INDEX_FIAT_CURRENCY_COLUMN_NAME),
                        record.getStringValue(CryptoIndexDatabaseConstants.CRYPTO_INDEX_TIME_COLUMN_NAME),
                        record.getDoubleValue(CryptoIndexDatabaseConstants.CRYPTO_INDEX_EXCHANGE_RATE_COLUMN_NAME)));
            }
            database.closeDatabase();
        } catch (CantGetCryptoIndexIdentitiesException e) {
            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            database.closeDatabase();
        }
        return LastExchangeRateList;
    }

    public List<CryptoIndexInterface> getHistoricalExchangeRateList(String crypto, String fiat, long time) {
        List<CryptoIndexInterface> HistoricalExchangeRateList = new ArrayList<>();
        DatabaseTable table;
        try {
            table = this.database.getTable(CryptoIndexDatabaseConstants.CRYPTO_INDEX_TABLE_NAME);
            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetCryptoIndexIdentitiesException("Cant get Crypto Index list, table not found.", "Crypto Index", "");
            }
            table.addStringFilter(CryptoIndexDatabaseConstants.CRYPTO_INDEX_CRYPTO_CURRENCY_COLUMN_NAME, crypto, DatabaseFilterType.EQUAL);
            table.addStringFilter(CryptoIndexDatabaseConstants.CRYPTO_INDEX_CRYPTO_CURRENCY_COLUMN_NAME, fiat, DatabaseFilterType.EQUAL);
            table.addStringFilter(CryptoIndexDatabaseConstants.CRYPTO_INDEX_TIME_COLUMN_NAME, String.valueOf(time), DatabaseFilterType.EQUAL);

            table.loadToMemory();
            System.out.println(table.getRecords().get(0));
            for (DatabaseTableRecord record : table.getRecords()) {
                HistoricalExchangeRateList.add(new CryptoIndexList(
                        record.getStringValue(CryptoIndexDatabaseConstants.CRYPTO_INDEX_CRYPTO_CURRENCY_COLUMN_NAME),
                        record.getStringValue(CryptoIndexDatabaseConstants.CRYPTO_INDEX_FIAT_CURRENCY_COLUMN_NAME),
                        record.getStringValue(CryptoIndexDatabaseConstants.CRYPTO_INDEX_TIME_COLUMN_NAME),
                        record.getDoubleValue(CryptoIndexDatabaseConstants.CRYPTO_INDEX_EXCHANGE_RATE_COLUMN_NAME)));
            }
            database.closeDatabase();
        } catch (CantGetCryptoIndexIdentitiesException e) {
            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            database.closeDatabase();
        }
        return HistoricalExchangeRateList;
    }
}
