package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.structure.CustomerOnlinePaymentTransactionManagerTest;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.structure.CustomerOnlinePaymentTransactionManager;

import org.junit.Test;
import org.mockito.Mock;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 04/02/16.
 */
public class parseToLongTest {
    @Mock
    CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;
    @Mock
    CustomerOnlinePaymentBusinessTransactionDao customerOnlinePaymentBusinessTransactionDao;
    @Mock
    TransactionTransmissionManager transactionTransmissionManager;
    @Mock
    CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;
    CustomerOnlinePaymentTransactionManager customerOnlinePaymentTransactionManager;

    @Test
    public void parseToLongTest_Should_Return_Long()throws Exception{
        customerOnlinePaymentTransactionManager = new CustomerOnlinePaymentTransactionManager(customerBrokerContractPurchaseManager,customerOnlinePaymentBusinessTransactionDao,transactionTransmissionManager,customerBrokerPurchaseNegotiationManager);
        customerOnlinePaymentTransactionManager.parseToLong("1");
    }
    @Test(expected = InvalidParameterException.class)
    public void parseToLongTest_Should_Return_null_InvalidParameterException()throws Exception{
        customerOnlinePaymentTransactionManager = new CustomerOnlinePaymentTransactionManager(customerBrokerContractPurchaseManager,customerOnlinePaymentBusinessTransactionDao,transactionTransmissionManager,customerBrokerPurchaseNegotiationManager);
        customerOnlinePaymentTransactionManager.parseToLong(null);
    }/*
    @Test(expected = InvalidParameterException.class)
    public void parseToLongTest_Should_Return_long_InvalidParameterException()throws Exception{
        customerOnlinePaymentTransactionManager = new CustomerOnlinePaymentTransactionManager(customerBrokerContractPurchaseManager,customerOnlinePaymentBusinessTransactionDao,transactionTransmissionManager,customerBrokerPurchaseNegotiationManager);
        customerOnlinePaymentTransactionManager.parseToLong("A");
    }*/
}
