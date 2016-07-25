package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDaoTest;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDatabaseConstants;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 01/02/16.
 */
public class getCustomerOfflinePaymentRecordTest {
    private CustomerOfflinePaymentBusinessTransactionDao customerOfflinePaymentBusinessTransactionDao;
    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    private Database mockDatabase;
    @Mock
    DatabaseTable databaseTable;
    @Mock
    ErrorManager errorManager;
    @Mock
    List<DatabaseTableRecord> databaseTableRecordList;
    @Mock
    DatabaseTableRecord databaseTableRecord;
    private UUID testId;

    @Before
    public void setup() throws Exception {
        testId = UUID.randomUUID();
        MockitoAnnotations.initMocks(this);
        customerOfflinePaymentBusinessTransactionDao = new CustomerOfflinePaymentBusinessTransactionDao(
                mockPluginDatabaseSystem, testId, mockDatabase, errorManager);
        when(mockDatabase.getTable(CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_TABLE_NAME)).
                thenReturn(databaseTable);
        doNothing().when(databaseTable).addStringFilter(
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                "Test",
                DatabaseFilterType.EQUAL);
        doNothing().when(databaseTable).loadToMemory();
        when(databaseTable.getRecords()).thenReturn(databaseTableRecordList);
        when(databaseTableRecordList.get(0)).thenReturn(databaseTableRecord);
        when(databaseTableRecord.getStringValue(CustomerOfflinePaymentBusinessTransactionDatabaseConstants.
                OFFLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME)).thenReturn("contract_transaction_status");
        when(databaseTableRecord.getStringValue(
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.
                        OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME)).thenReturn("contract_hash");
    }

    @Test
    public void getCustomerOfflinePaymentRecord() throws Exception {
        when(databaseTableRecord.getStringValue(
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.
                        OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME)).thenReturn("POMC");
        assertEquals("Test",
                customerOfflinePaymentBusinessTransactionDao.getCustomerOfflinePaymentRecord("Test").getTransactionHash());
    }

    //InvalidParameterException
    @Test(expected = UnexpectedResultReturnedFromDatabaseException.class)
    public void getCustomerOfflinePaymentRecord_Should_Throw_Exception() throws Exception {
        customerOfflinePaymentBusinessTransactionDao.getCustomerOfflinePaymentRecord("Test");
    }

    @Test(expected = UnexpectedResultReturnedFromDatabaseException.class)
    public void getCustomerOfflinePaymentRecord_Should_Return_Exception() throws Exception {
        customerOfflinePaymentBusinessTransactionDao = new CustomerOfflinePaymentBusinessTransactionDao(
                null, null, null, errorManager);
        customerOfflinePaymentBusinessTransactionDao.getCustomerOfflinePaymentRecord(null);
    }
}
