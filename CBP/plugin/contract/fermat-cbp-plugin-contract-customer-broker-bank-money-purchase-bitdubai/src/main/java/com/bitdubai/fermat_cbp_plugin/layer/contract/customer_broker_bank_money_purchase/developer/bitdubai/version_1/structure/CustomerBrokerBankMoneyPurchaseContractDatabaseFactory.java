package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_bank_money_purchase.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 * Created by angel on 24/9/15.
 */

public class CustomerBrokerBankMoneyPurchaseContractDatabaseFactory implements DealsWithPluginDatabaseSystem {

    private PluginDatabaseSystem pluginDatabaseSystem;

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    public Database createDatabase(UUID ownerId) throws CantCreateDatabaseException {
        Database database = null;
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.BANK_MONEY_PURCHASE_DATABASE_NAME);
            createCustomerBrokerBankMoneyPurchaseTables(ownerId, database.getDatabaseFactory());
            database.closeDatabase();
            return database;
        } catch(CantCreateDatabaseException exception){
            throw exception;
        } catch(Exception exception){
            if(database != null)
                database.closeDatabase();
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    private void createCustomerBrokerBankMoneyPurchaseTables(final UUID ownerId, final DatabaseFactory databaseFactory) throws CantCreateTableException {
        try{
            DatabaseTableFactory BankMoneyPurchasetableFactory = createCustomerBrokerBankMoneyPurchaseTableFactory(ownerId, databaseFactory);
            databaseFactory.createTable(BankMoneyPurchasetableFactory);

            DatabaseTableFactory EventsRecordedtableFactory = createCustomerBrokerBankMoneyPurchaseEventsRecordedTableFactory(ownerId, databaseFactory);
            databaseFactory.createTable(EventsRecordedtableFactory);

        } catch(InvalidOwnerIdException exception){
            throw new CantCreateTableException(CantCreateTableException.DEFAULT_MESSAGE, exception, null, "The ownerId of the database factory didn't match with the given owner id");
        }
    }

    private DatabaseTableFactory createCustomerBrokerBankMoneyPurchaseTableFactory(final UUID ownerId, final DatabaseFactory databaseFactory) throws InvalidOwnerIdException{

        DatabaseTableFactory table = databaseFactory.newTableFactory(ownerId, CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.BANK_MONEY_PURCHASE_TABLE_NAME);

        // ID del Contrato
            table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.BANK_MONEY_PURCHASE_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, true);

        // PublicKeys del Broker y el Customer
            table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.BANK_MONEY_PURCHASE_PUBLIC_KEY_BROKER_COLUMN_NAME, DatabaseDataType.STRING, 100, false);
            table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.BANK_MONEY_PURCHASE_PUBLIC_KEY_CUSTOMER_COLUMN_NAME, DatabaseDataType.STRING, 100, false);

        // Tipos de Currency (MERCHANDISE y PAYMENT)
            table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.BANK_MONEY_PURCHASE_PAYMENT_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 20, false);
            table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.BANK_MONEY_PURCHASE_MERCHANDISE_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 20, false);

        // Referencias (Price y Currency)
            table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.BANK_MONEY_PURCHASE_REFERENCE_PRICE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, false);
            table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.BANK_MONEY_PURCHASE_REFERENCE_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 20, false);

        // Montos (PAYMENT y MERCHANDISE)
            // Estas columnas las coloque LONG_INTEGER porque en la BasicWallet tienen los campos AMOUNT de esta forma (Hay que revisar)
            table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.BANK_MONEY_PURCHASE_PAYMENT_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, false);
            table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.BANK_MONEY_PURCHASE_MERCHANDISE_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, false);

        // Fechas de expiraciones
            table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.BANK_MONEY_PURCHASE_PAYMENT_EXPIRATION_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, false);
            table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.BANK_MONEY_PURCHASE_MERCHANDISE_DELIVERY_EXPIRATION_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, false);

        // Status del contrato
            table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.BANK_MONEY_PURCHASE_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 20, false);

        // PrimaryKey
            table.addIndex(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.BANK_MONEY_PURCHASE_TABLE_FIRST_KEY_COLUMN);

        return table;
    }

    private DatabaseTableFactory createCustomerBrokerBankMoneyPurchaseEventsRecordedTableFactory(final UUID ownerId, final DatabaseFactory databaseFactory) throws InvalidOwnerIdException{

        DatabaseTableFactory table = databaseFactory.newTableFactory(ownerId, CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.BANK_MONEY_PURCHASE_EVENTS_RECORDED_TABLE_NAME);

        table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.BANK_MONEY_PURCHASE_EVENTS_RECORDED_TABLE_ID_COLUMN, DatabaseDataType.STRING, 36, true);
        table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.BANK_MONEY_PURCHASE_EVENTS_RECORDED_TABLE_EVENT_COLUMN, DatabaseDataType.STRING, 10, false);
        table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.BANK_MONEY_PURCHASE_EVENTS_RECORDED_TABLE_SOURCE_COLUMN, DatabaseDataType.STRING, 10, false);
        table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.BANK_MONEY_PURCHASE_EVENTS_RECORDED_TABLE_STATUS_COLUMN, DatabaseDataType.STRING, 10, false);
        table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.BANK_MONEY_PURCHASE_EVENTS_RECORDED_TABLE_TIMESTAMP_COLUMN, DatabaseDataType.LONG_INTEGER, 100, false);

        table.addIndex(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.BANK_MONEY_PURCHASE_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN);

        return table;
    }

}