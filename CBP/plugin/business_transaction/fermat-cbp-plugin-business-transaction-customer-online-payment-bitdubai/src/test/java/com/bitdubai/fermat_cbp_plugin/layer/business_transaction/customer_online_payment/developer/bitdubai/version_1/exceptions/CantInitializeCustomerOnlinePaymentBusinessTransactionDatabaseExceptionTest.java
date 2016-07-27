package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDao;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 04/02/16.
 */
public class CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseExceptionTest {
    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    private Database mockDatabase;
    @Mock
    private DatabaseTable databaseTable;
    @Mock
    ErrorManager errorManager;
    private UUID testId;
    private CustomerOnlinePaymentBusinessTransactionDao customerOnlinePaymentBusinessTransactionDao;

    @Before
    public void setup() throws Exception {
        testId = UUID.randomUUID();
        MockitoAnnotations.initMocks(this);
        customerOnlinePaymentBusinessTransactionDao =
                new CustomerOnlinePaymentBusinessTransactionDao(mockPluginDatabaseSystem, testId, mockDatabase, errorManager);
    }

    @Test(expected = CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException.class)
    public void CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseExceptionTest() throws Exception {
        customerOnlinePaymentBusinessTransactionDao =
                new CustomerOnlinePaymentBusinessTransactionDao(null, null, mockDatabase, errorManager);
        customerOnlinePaymentBusinessTransactionDao.initialize();

    }
}
