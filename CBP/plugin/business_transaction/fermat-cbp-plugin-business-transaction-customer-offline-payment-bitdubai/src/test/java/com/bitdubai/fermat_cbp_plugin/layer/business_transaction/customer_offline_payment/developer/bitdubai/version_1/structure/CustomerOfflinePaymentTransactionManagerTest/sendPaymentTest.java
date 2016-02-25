package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.structure.CustomerOfflinePaymentTransactionManagerTest;

import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantSendPaymentException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.structure.CustomerOfflinePaymentTransactionManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 16/02/16.
 */
public class sendPaymentTest {
    CustomerOfflinePaymentTransactionManager customerOfflinePaymentTransactionManager;
    @Mock
    CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;
    @Mock
    CustomerOfflinePaymentBusinessTransactionDao customerOfflinePaymentBusinessTransactionDao;
    @Mock
    ErrorManager errorManager;

    @Before
    public void setup() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sendPaymentTest_Should() throws Exception{
        customerOfflinePaymentTransactionManager = new CustomerOfflinePaymentTransactionManager(customerBrokerContractPurchaseManager,
                customerOfflinePaymentBusinessTransactionDao,
                errorManager);
        customerOfflinePaymentTransactionManager.sendPayment("");
    }

    @Test(expected = CantSendPaymentException.class)
    public void sendPaymentTest_Should_Throw_CantSendPaymentException() throws Exception{
        customerOfflinePaymentTransactionManager = new CustomerOfflinePaymentTransactionManager(null,
                customerOfflinePaymentBusinessTransactionDao,
                errorManager);
        customerOfflinePaymentTransactionManager.sendPayment("");
    }

    @Test(expected = Exception.class)
    public void sendPaymentTest_Should_Throw_Exception() throws Exception{
        customerOfflinePaymentTransactionManager = new CustomerOfflinePaymentTransactionManager(null,null,null);
        customerOfflinePaymentTransactionManager.sendPayment(null);
    }
}
