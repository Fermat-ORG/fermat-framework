package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDaoTest;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

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
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 25/02/16.
 */
public class getEventTypeTest {
    CustomerOnlinePaymentBusinessTransactionDao customerOnlinePaymentBusinessTransactionDao;
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
    public void setup()throws Exception{
        testId = UUID.randomUUID();
        MockitoAnnotations.initMocks(this);
        customerOnlinePaymentBusinessTransactionDao = new CustomerOnlinePaymentBusinessTransactionDao(
                pluginDatabaseSystem,testId,mockDatabase,errorManager);
        setupGeneralMockitoRules();
    }
    public void setupGeneralMockitoRules()throws Exception{
        doNothing().when(databaseTable).loadToMemory();
        when(databaseTable.getRecords()).thenReturn(databaseTableRecordList);
        when(databaseTableRecordList.get(0)).thenReturn(databaseTableRecord);
        when(databaseTableRecord.getStringValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_EVENTS_RECORDED_EVENT_COLUMN_NAME
        )).thenReturn("Test");
    }
    @Test
    public void getEventTypeTest_Should_Equal_Test()throws Exception{
        when(mockDatabase.getTable(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_EVENTS_RECORDED_TABLE_NAME
        )).thenReturn(databaseTable);
        assertEquals("Test",customerOnlinePaymentBusinessTransactionDao.getEventType("eventId"));
    }

    @Test(expected = UnexpectedResultReturnedFromDatabaseException.class)
    public void getEventTypeTest_Should_Throw_Exception()throws Exception{
        customerOnlinePaymentBusinessTransactionDao.getEventType("eventId");
    }
}
