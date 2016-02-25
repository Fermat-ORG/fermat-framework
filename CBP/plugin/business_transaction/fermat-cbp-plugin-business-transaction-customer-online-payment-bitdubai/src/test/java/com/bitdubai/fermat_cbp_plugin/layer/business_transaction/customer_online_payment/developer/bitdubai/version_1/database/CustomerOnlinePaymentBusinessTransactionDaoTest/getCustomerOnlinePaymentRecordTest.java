package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDaoTest;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import java.util.List;
import java.util.UUID;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 01/02/16.
 */
public class getCustomerOnlinePaymentRecordTest {
    private CustomerOnlinePaymentBusinessTransactionDao customerOnlinePaymentBusinessTransactionDao;

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
    public void setup()throws Exception{
        testId = UUID.randomUUID();
        MockitoAnnotations.initMocks(this);
        customerOnlinePaymentBusinessTransactionDao = new CustomerOnlinePaymentBusinessTransactionDao(mockPluginDatabaseSystem,testId, mockDatabase,errorManager);
        when(mockDatabase.getTable(CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_TABLE_NAME)).thenReturn(databaseTable);
        doNothing().when(databaseTable).addStringFilter(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME,
                "Test",
                DatabaseFilterType.EQUAL);
        doNothing().when(databaseTable).loadToMemory();
        when(databaseTable.getRecords()).thenReturn(databaseTableRecordList);
        when(databaseTableRecordList.get(0)).thenReturn(databaseTableRecord);
        when(databaseTableRecord.getStringValue(CustomerOnlinePaymentBusinessTransactionDatabaseConstants.
                ONLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME)).thenReturn("contract_transaction_status");
        when(databaseTableRecord.getStringValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.
                        ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME)).thenReturn("contract_hash");

    }
    //InvalidParameterException
    @Test(expected = UnexpectedResultReturnedFromDatabaseException.class)
    public void getCustomerOnlinePaymentRecord_Should_Throw_Exception() throws Exception{
        customerOnlinePaymentBusinessTransactionDao.getCustomerOnlinePaymentRecord("Test");
    }

    @Test
    public void getCustomerOnlinePaymentRecord_Should_() throws Exception{

        when(databaseTableRecord.getStringValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.
                        ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME)).thenReturn("POMC");
        customerOnlinePaymentBusinessTransactionDao.getCustomerOnlinePaymentRecord("Test");
    }
    //Generic Exception
    @Test(expected = UnexpectedResultReturnedFromDatabaseException.class)
    public void getCustomerOnlinePaymentRecord_Should_Throw_Generic_Exception() throws Exception{
        customerOnlinePaymentBusinessTransactionDao = new CustomerOnlinePaymentBusinessTransactionDao(null,null,null,errorManager);
        customerOnlinePaymentBusinessTransactionDao.getCustomerOnlinePaymentRecord(null);
    }
}
