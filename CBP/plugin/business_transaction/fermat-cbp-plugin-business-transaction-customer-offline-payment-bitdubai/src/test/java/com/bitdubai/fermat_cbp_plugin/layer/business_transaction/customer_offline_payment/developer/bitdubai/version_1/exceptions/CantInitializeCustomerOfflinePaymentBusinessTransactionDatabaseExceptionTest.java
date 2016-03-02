package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;

import org.junit.Test;
import org.mockito.Mock;


/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 04/02/16.
 */
public class CantInitializeCustomerOfflinePaymentBusinessTransactionDatabaseExceptionTest {
    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    @Mock
    private DatabaseTable databaseTable;
    private CantInitializeCustomerOfflinePaymentBusinessTransactionDatabaseException cantInitializeCustomerOfflinePaymentBusinessTransactionDatabaseException;

    @Test
    public void CustomerOfflinePaymentBusinessTransactionDaoInitTest()throws Exception{
        cantInitializeCustomerOfflinePaymentBusinessTransactionDatabaseException = new CantInitializeCustomerOfflinePaymentBusinessTransactionDatabaseException("");
        cantInitializeCustomerOfflinePaymentBusinessTransactionDatabaseException = new CantInitializeCustomerOfflinePaymentBusinessTransactionDatabaseException();

    }

    }
