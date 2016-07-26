package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDao;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 04/02/16.
 */
public class CantInitializeCustomerOfflinePaymentBusinessTransactionDatabaseExceptionTest {
    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    private Database mockDatabase;
    @Mock
    private DatabaseTable databaseTable;
    @Mock
    ErrorManager errorManager;
    private UUID testId;
    private CustomerOfflinePaymentBusinessTransactionDao customerOfflinePaymentBusinessTransactionDao;

    @Before
    public void setup() throws Exception {
        testId = UUID.randomUUID();
        MockitoAnnotations.initMocks(this);
        customerOfflinePaymentBusinessTransactionDao =
                new CustomerOfflinePaymentBusinessTransactionDao(mockPluginDatabaseSystem, testId, mockDatabase, errorManager);
    }

    @Test(expected = CantInitializeCustomerOfflinePaymentBusinessTransactionDatabaseException.class)
    public void CantInitializeCustomerOfflinePaymentBusinessTransactionDatabaseExceptionTest() throws Exception {
        customerOfflinePaymentBusinessTransactionDao =
                new CustomerOfflinePaymentBusinessTransactionDao(null, null, mockDatabase, errorManager);
        customerOfflinePaymentBusinessTransactionDao.initialize();

    }
}
