package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDaoTest;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
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
import static org.mockito.Mockito.when;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 25/02/16.
 */
public class getEventTypeTest {
    CustomerOfflinePaymentBusinessTransactionDao customerOfflinePaymentBusinessTransactionDao;
    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;
    @Mock
    Database mockDatabase;
    @Mock
    ErrorManager errorManager;
    @Mock
    DatabaseTable databaseTable;
    @Mock
    List<DatabaseTableRecord> databaseTableRecordList;
    @Mock
    DatabaseTableRecord databaseTableRecord;
    private UUID testId;

    @Before
    public void setup() {
        testId = UUID.randomUUID();
        MockitoAnnotations.initMocks(this);
        customerOfflinePaymentBusinessTransactionDao = new CustomerOfflinePaymentBusinessTransactionDao(
                pluginDatabaseSystem, testId, mockDatabase, errorManager);
        setupGeneralMockitoRules();
    }

    public void setupGeneralMockitoRules() {
        when(databaseTable.getRecords()).thenReturn(databaseTableRecordList);
        when(databaseTableRecordList.get(0)).thenReturn(databaseTableRecord);
        when(databaseTableRecord.getStringValue(
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_EVENTS_RECORDED_EVENT_COLUMN_NAME
        )).thenReturn("Test");
    }

    @Test
    public void getEventTypeTest() throws Exception {
        when(mockDatabase.getTable(
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_EVENTS_RECORDED_TABLE_NAME
        )).thenReturn(databaseTable);
        assertEquals("Test", customerOfflinePaymentBusinessTransactionDao.getEventType("eventId"));
    }

    @Test(expected = UnexpectedResultReturnedFromDatabaseException.class)
    public void getEventTypeTest_Should_Throw_Exception() throws Exception {
        customerOfflinePaymentBusinessTransactionDao.getEventType("eventId");
    }
}
