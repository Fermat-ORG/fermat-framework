package com.bitdubai.fermat_cbp_plugin.layer.request.customer_broker_sale.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.RequestStatus;
import com.bitdubai.fermat_cbp_api.layer.request.customer_broker_sale.exceptions.CantGetRequestListException;
import com.bitdubai.fermat_cbp_api.layer.request.customer_broker_sale.exceptions.CantRequestCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.request.customer_broker_sale.exceptions.FailedToRejectTheRequestSaleException;
import com.bitdubai.fermat_cbp_api.layer.request.customer_broker_sale.exceptions.RequestUnexpectedErrorException;
import com.bitdubai.fermat_cbp_api.layer.request.customer_broker_sale.interfaces.RequestCustomerBrokerSale;
import com.bitdubai.fermat_cbp_plugin.layer.request.customer_broker_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerSaleRequestDaoException;
import com.bitdubai.fermat_cbp_plugin.layer.request.customer_broker_sale.developer.bitdubai.version_1.structure.CustomerBrokerSaleRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 28/9/15.
 */
public class CustomerBrokerSaleRequestDao {

    private Database             database;
    private PluginDatabaseSystem pluginDatabaseSystem;

    /*
        Builders
     */

    public CustomerBrokerSaleRequestDao(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*
        Public methods
     */

    public void initialize(UUID pluginId) throws CantInitializeCustomerBrokerSaleRequestDaoException {
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_TABLE_NAME);
        } catch (DatabaseNotFoundException e) {

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeCustomerBrokerSaleRequestDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_TABLE_NAME, "");
        } catch (Exception exception) {
            throw new CantInitializeCustomerBrokerSaleRequestDaoException(CantInitializeCustomerBrokerSaleRequestDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public void sendRequestSale(
            String requestSenderPublicKey,
            String requestDestinationPublicKey,
            CurrencyType merchandiseCurrency,
            float merchandiseAmount,
            CurrencyType paymentCurrency,
            RequestStatus requestStatus
    ) throws RequestUnexpectedErrorException {
        try {
            DatabaseTable CustomerBrokerSaleRequestTable = this.database.getTable(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_TABLE_NAME);
            DatabaseTableRecord recordToInsert   = CustomerBrokerSaleRequestTable.getEmptyRecord();

            UUID requestID = UUID.randomUUID();

            loadRecordAsNewSend(
                    recordToInsert,
                    requestID,
                    requestSenderPublicKey,
                    requestDestinationPublicKey,
                    merchandiseCurrency,
                    merchandiseAmount,
                    paymentCurrency,
                    requestStatus
            );

            CustomerBrokerSaleRequestTable.insertRecord(recordToInsert);
        } catch (CantInsertRecordException e) {
            throw new RequestUnexpectedErrorException("An exception happened",e,"","");
        }
    }

    public void rejectRequestSale(UUID requestId) throws FailedToRejectTheRequestSaleException {
        try {
            DatabaseTable RequestSaleTable = this.database.getTable(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_TABLE_NAME);
            DatabaseTableRecord recordToDelete   = RequestSaleTable.getEmptyRecord();

            recordToDelete.setUUIDValue(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_REQUEST_ID_COLUMN_NAME, requestId);

            RequestSaleTable.deleteRecord(recordToDelete);
        } catch (CantDeleteRecordException e) {
            throw new FailedToRejectTheRequestSaleException("An exception happened",e,"","");
        }
    }

    public void createRequestCustomerBrokerSale(
            String requestSenderPublicKey,
            String requestDestinationPublicKey,
            CurrencyType merchandiseCurrency,
            float merchandiseAmount,
            CurrencyType paymentCurrency
    ) throws CantRequestCustomerBrokerSaleException {

        try {
            DatabaseTable CustomerBrokerSaleRequestTable = this.database.getTable(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_TABLE_NAME);
            DatabaseTableRecord recordToInsert   = CustomerBrokerSaleRequestTable.getEmptyRecord();

            UUID requestID = UUID.randomUUID();

            loadRecordAsNew(
                    recordToInsert,
                    requestID,
                    requestSenderPublicKey,
                    requestDestinationPublicKey,
                    merchandiseCurrency,
                    merchandiseAmount,
                    paymentCurrency
            );

            CustomerBrokerSaleRequestTable.insertRecord(recordToInsert);
        } catch (CantInsertRecordException e) {
            throw new CantRequestCustomerBrokerSaleException("An exception happened",e,"","");
        }
    }

    List<RequestCustomerBrokerSale> getRequestSaleSent(String requestSenderPublicKey) throws CantGetRequestListException, CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable RequestSaleTable = this.database.getTable(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_TABLE_NAME);
        RequestSaleTable.setStringFilter(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_REQUEST_SENDER_PUBLIC_KEY_COLUMN_NAME, requestSenderPublicKey, DatabaseFilterType.EQUAL);
        RequestSaleTable.loadToMemory();

        List<DatabaseTableRecord> records = RequestSaleTable.getRecords();
        RequestSaleTable.clearAllFilters();

        List<RequestCustomerBrokerSale> SaleRequests = new ArrayList<>();

        for (DatabaseTableRecord record : records) {
            SaleRequests.add(constructCustomerBrokerSaleRequestFromRecord(record));
        }

        return SaleRequests;
    }

    List<RequestCustomerBrokerSale> getReceivedRequestSale(String requestSenderPublicKey) throws CantGetRequestListException, CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable RequestSaleTable = this.database.getTable(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_TABLE_NAME);
        RequestSaleTable.setStringFilter(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_REQUEST_SENDER_PUBLIC_KEY_COLUMN_NAME, requestSenderPublicKey, DatabaseFilterType.EQUAL);
        RequestSaleTable.loadToMemory();

        List<DatabaseTableRecord> records = RequestSaleTable.getRecords();
        RequestSaleTable.clearAllFilters();

        List<RequestCustomerBrokerSale> SaleRequests = new ArrayList<>();

        for (DatabaseTableRecord record : records) {
            SaleRequests.add(constructCustomerBrokerSaleRequestFromRecord(record));
        }

        return SaleRequests;
    }


    /*
        Methods Private
     */

    private void loadRecordAsNewSend(DatabaseTableRecord databaseTableRecord,
                                     UUID requestID,
                                     String requestSenderPublicKey,
                                     String requestDestinationPublicKey,
                                     CurrencyType merchandiseCurrency,
                                     float merchandiseAmount,
                                     CurrencyType paymentCurrency,
                                     RequestStatus requestStatus) {

        databaseTableRecord.setUUIDValue(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_REQUEST_ID_COLUMN_NAME, requestID);
        databaseTableRecord.setStringValue(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_REQUEST_SENDER_PUBLIC_KEY_COLUMN_NAME, requestSenderPublicKey);
        databaseTableRecord.setStringValue(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_REQUEST_DESTINATION_PUBLIC_KEY_COLUMN_NAME, requestDestinationPublicKey);
        databaseTableRecord.setStringValue(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_MERCHANDISE_CURRENCY_COLUMN_NAME, merchandiseCurrency.getCode());
        databaseTableRecord.setFloatValue(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_MERCHANDISE_AMOUNT_COLUMN_NAME, merchandiseAmount);
        databaseTableRecord.setStringValue(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_PAYMENT_CURRENCY_COLUMN_NAME, paymentCurrency.getCode());
        databaseTableRecord.setStringValue(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_REQUEST_STATUS_COLUMN_NAME, requestStatus.getCode());

    }

    private void loadRecordAsNew(DatabaseTableRecord databaseTableRecord,
                                 UUID requestID,
                                 String requestSenderPublicKey,
                                 String requestDestinationPublicKey,
                                 CurrencyType merchandiseCurrency,
                                 float merchandiseAmount,
                                 CurrencyType paymentCurrency) {

        databaseTableRecord.setUUIDValue(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_REQUEST_ID_COLUMN_NAME, requestID);
        databaseTableRecord.setStringValue(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_REQUEST_SENDER_PUBLIC_KEY_COLUMN_NAME, requestSenderPublicKey);
        databaseTableRecord.setStringValue(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_REQUEST_DESTINATION_PUBLIC_KEY_COLUMN_NAME, requestDestinationPublicKey);
        databaseTableRecord.setStringValue(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_MERCHANDISE_CURRENCY_COLUMN_NAME, merchandiseCurrency.getCode());
        databaseTableRecord.setFloatValue(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_MERCHANDISE_AMOUNT_COLUMN_NAME, merchandiseAmount);
        databaseTableRecord.setStringValue(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_PAYMENT_CURRENCY_COLUMN_NAME, paymentCurrency.getCode());

    }

    private RequestCustomerBrokerSale constructCustomerBrokerSaleRequestFromRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID            requestId                       = record.getUUIDValue(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_REQUEST_ID_COLUMN_NAME);
        String          requestSenderPublicKey          = record.getStringValue(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_REQUEST_SENDER_PUBLIC_KEY_COLUMN_NAME);
        String          requestDestinationPublicKey     = record.getStringValue(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_REQUEST_DESTINATION_PUBLIC_KEY_COLUMN_NAME);
        CurrencyType    merchandiseCurrency             = CurrencyType.getByCode(record.getStringValue(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_MERCHANDISE_CURRENCY_COLUMN_NAME));
        float           merchandiseAmount               = record.getFloatValue(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_MERCHANDISE_AMOUNT_COLUMN_NAME);
        CurrencyType    paymentCurrency                 = CurrencyType.getByCode(record.getStringValue(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_PAYMENT_CURRENCY_COLUMN_NAME));
        RequestStatus   requestStatus                   = RequestStatus.getByCode(record.getStringValue(CustomerBrokerSaleRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_SALE_REQUEST_STATUS_COLUMN_NAME));

        return new CustomerBrokerSaleRequest(
                requestId,
                requestSenderPublicKey,
                requestDestinationPublicKey,
                merchandiseCurrency,
                merchandiseAmount,
                paymentCurrency,
                requestStatus);
    }
}
