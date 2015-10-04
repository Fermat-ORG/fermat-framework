package com.bitdubai.fermat_cbp_plugin.layer.request.customer_broker_purchase.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
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
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.layer.cbp_request.customer_broker_purchase.exceptions.CantGetRequestListException;
import com.bitdubai.fermat_cbp_api.layer.cbp_request.customer_broker_purchase.exceptions.CantRequestCustomerBrokerPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_request.customer_broker_purchase.exceptions.RequestPurchaseRejectFailedException;
import com.bitdubai.fermat_cbp_api.layer.cbp_request.customer_broker_purchase.exceptions.RequestUnexpectedErrorException;
import com.bitdubai.fermat_cbp_api.layer.cbp_request.customer_broker_purchase.interfaces.RequestCustomerBrokerPurchase;
import com.bitdubai.fermat_cbp_plugin.layer.request.customer_broker_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerPurchaseRequestDaoException;
import com.bitdubai.fermat_cbp_plugin.layer.request.customer_broker_purchase.developer.bitdubai.version_1.structure.CustomerBrokerPurchaseRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 28/9/15.
 */
public class CustomerBrokerPurchaseRequestDao {

    private Database             database;
    private PluginDatabaseSystem pluginDatabaseSystem;

    /*
        Builders
     */

    public CustomerBrokerPurchaseRequestDao(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*
        Public methods
     */

    public void initialize(UUID pluginId) throws CantInitializeCustomerBrokerPurchaseRequestDaoException {
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_TABLE_NAME);
        } catch (DatabaseNotFoundException e) {

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeCustomerBrokerPurchaseRequestDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_TABLE_NAME, "");
        } catch (Exception exception) {
            throw new CantInitializeCustomerBrokerPurchaseRequestDaoException(CantInitializeCustomerBrokerPurchaseRequestDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public void sendRequestPurchase(
            String requestSenderPublicKey,
            String requestDestinationPublicKey,
            CurrencyType merchandiseCurrency,
            float merchandiseAmount,
            CurrencyType paymentCurrency,
            RequestStatus requestStatus
    ) throws RequestUnexpectedErrorException{
        try {
            DatabaseTable CustomerBrokerPurchaseRequestTable = this.database.getTable(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_TABLE_NAME);
            DatabaseTableRecord recordToInsert   = CustomerBrokerPurchaseRequestTable.getEmptyRecord();

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

            CustomerBrokerPurchaseRequestTable.insertRecord(recordToInsert);
        } catch (CantInsertRecordException e) {
            throw new RequestUnexpectedErrorException("An exception happened",e,"","");
        }
    }

    public void rejectRequestPurchase(UUID requestId) throws RequestPurchaseRejectFailedException{
        try {
            DatabaseTable RequestPurchaseTable = this.database.getTable(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_TABLE_NAME);
            DatabaseTableRecord recordToDelete   = RequestPurchaseTable.getEmptyRecord();

            recordToDelete.setUUIDValue(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_REQUEST_ID_COLUMN_NAME, requestId);

            RequestPurchaseTable.deleteRecord(recordToDelete);
        } catch (CantDeleteRecordException e) {
            throw new RequestPurchaseRejectFailedException("An exception happened",e,"","");
        }
    }

    public void createRequestCustomerBrokerPurchase(
            String requestSenderPublicKey,
            String requestDestinationPublicKey,
            CurrencyType merchandiseCurrency,
            float merchandiseAmount,
            CurrencyType paymentCurrency
    ) throws CantRequestCustomerBrokerPurchaseException{

        try {
            DatabaseTable CustomerBrokerPurchaseRequestTable = this.database.getTable(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_TABLE_NAME);
            DatabaseTableRecord recordToInsert   = CustomerBrokerPurchaseRequestTable.getEmptyRecord();

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

            CustomerBrokerPurchaseRequestTable.insertRecord(recordToInsert);
        } catch (CantInsertRecordException e) {
            throw new CantRequestCustomerBrokerPurchaseException("An exception happened",e,"","");
        }
    }

    List<RequestCustomerBrokerPurchase> getRequestPurchaseSent(String requestSenderPublicKey) throws CantGetRequestListException, CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable RequestPurchaseTable = this.database.getTable(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_TABLE_NAME);
        RequestPurchaseTable.setStringFilter(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_REQUEST_SENDER_PUBLIC_KEY_COLUMN_NAME, requestSenderPublicKey, DatabaseFilterType.EQUAL);
        RequestPurchaseTable.loadToMemory();

        List<DatabaseTableRecord> records = RequestPurchaseTable.getRecords();
        RequestPurchaseTable.clearAllFilters();

        List<RequestCustomerBrokerPurchase> PurchaseRequests = new ArrayList<>();

        for (DatabaseTableRecord record : records) {
            PurchaseRequests.add(constructCustomerBrokerPurchaseRequestFromRecord(record));
        }

        return PurchaseRequests;
    }

    List<RequestCustomerBrokerPurchase> getReceivedRequestPurchase(String requestSenderPublicKey) throws CantGetRequestListException, CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable RequestPurchaseTable = this.database.getTable(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_TABLE_NAME);
        RequestPurchaseTable.setStringFilter(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_REQUEST_SENDER_PUBLIC_KEY_COLUMN_NAME, requestSenderPublicKey, DatabaseFilterType.EQUAL);
        RequestPurchaseTable.loadToMemory();

        List<DatabaseTableRecord> records = RequestPurchaseTable.getRecords();
        RequestPurchaseTable.clearAllFilters();

        List<RequestCustomerBrokerPurchase> PurchaseRequests = new ArrayList<>();

        for (DatabaseTableRecord record : records) {
            PurchaseRequests.add(constructCustomerBrokerPurchaseRequestFromRecord(record));
        }

        return PurchaseRequests;
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

        databaseTableRecord.setUUIDValue(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_REQUEST_ID_COLUMN_NAME, requestID);
        databaseTableRecord.setStringValue(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_REQUEST_SENDER_PUBLIC_KEY_COLUMN_NAME, requestSenderPublicKey);
        databaseTableRecord.setStringValue(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_REQUEST_DESTINATION_PUBLIC_KEY_COLUMN_NAME, requestDestinationPublicKey);
        databaseTableRecord.setStringValue(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_MERCHANDISE_CURRENCY_COLUMN_NAME, merchandiseCurrency.getCode());
        databaseTableRecord.setFloatValue(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_MERCHANDISE_AMOUNT_COLUMN_NAME, merchandiseAmount);
        databaseTableRecord.setStringValue(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_PAYMENT_CURRENCY_COLUMN_NAME, paymentCurrency.getCode());
        databaseTableRecord.setStringValue(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_REQUEST_STATUS_COLUMN_NAME, requestStatus.getCode());

    }

    private void loadRecordAsNew(DatabaseTableRecord databaseTableRecord,
                                 UUID requestID,
                                 String requestSenderPublicKey,
                                 String requestDestinationPublicKey,
                                 CurrencyType merchandiseCurrency,
                                 float merchandiseAmount,
                                 CurrencyType paymentCurrency) {

        databaseTableRecord.setUUIDValue(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_REQUEST_ID_COLUMN_NAME, requestID);
        databaseTableRecord.setStringValue(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_REQUEST_SENDER_PUBLIC_KEY_COLUMN_NAME, requestSenderPublicKey);
        databaseTableRecord.setStringValue(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_REQUEST_DESTINATION_PUBLIC_KEY_COLUMN_NAME, requestDestinationPublicKey);
        databaseTableRecord.setStringValue(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_MERCHANDISE_CURRENCY_COLUMN_NAME, merchandiseCurrency.getCode());
        databaseTableRecord.setFloatValue(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_MERCHANDISE_AMOUNT_COLUMN_NAME, merchandiseAmount);
        databaseTableRecord.setStringValue(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_PAYMENT_CURRENCY_COLUMN_NAME, paymentCurrency.getCode());

    }

    private RequestCustomerBrokerPurchase constructCustomerBrokerPurchaseRequestFromRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID            requestId                       = record.getUUIDValue(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_REQUEST_ID_COLUMN_NAME);
        String          requestSenderPublicKey          = record.getStringValue(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_REQUEST_SENDER_PUBLIC_KEY_COLUMN_NAME);
        String          requestDestinationPublicKey     = record.getStringValue(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_REQUEST_DESTINATION_PUBLIC_KEY_COLUMN_NAME);
        CurrencyType    merchandiseCurrency             = CurrencyType.getByCode(record.getStringValue(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_MERCHANDISE_CURRENCY_COLUMN_NAME));
        float           merchandiseAmount               = record.getFloatValue(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_MERCHANDISE_AMOUNT_COLUMN_NAME);
        CurrencyType    paymentCurrency                 = CurrencyType.getByCode(record.getStringValue(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_PAYMENT_CURRENCY_COLUMN_NAME));
        RequestStatus   requestStatus                   = RequestStatus.getByCode(record.getStringValue(CustomerBrokerPurchaseRequestDatabaseConstants.REQUEST_CUSTOMER_BROKER_PURCHASE_REQUEST_STATUS_COLUMN_NAME));

        return new CustomerBrokerPurchaseRequest(
                requestId,
                requestSenderPublicKey,
                requestDestinationPublicKey,
                merchandiseCurrency,
                merchandiseAmount,
                paymentCurrency,
                requestStatus
        );
    }
}
