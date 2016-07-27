package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.structure.CustomerOfflinePaymentMonitorAgentTest;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.structure.CustomerOfflinePaymentMonitorAgent;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 01/02/16.
 */


public class startTest {
    @Mock
    private PluginDatabaseSystem pluginDatabaseSystem;
    @Mock
    private ErrorManager errorManager;
    @Mock
    private LogManager logManager;
    @Mock
    private EventManager eventManager;
    @Mock
    TransactionTransmissionManager transactionTransmissionManager;
    @Mock
    CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;
    @Mock
    CustomerBrokerContractSaleManager customerBrokerContractSaleManager;
    @Mock
    OutgoingIntraActorManager outgoingIntraActorManager;

    private UUID pluginId;

    private CustomerOfflinePaymentMonitorAgent customerOfflinePaymentMonitorAgent;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        pluginId = UUID.randomUUID();
        customerOfflinePaymentMonitorAgent = new CustomerOfflinePaymentMonitorAgent(pluginDatabaseSystem,
                logManager,
                errorManager,
                eventManager,
                pluginId,
                transactionTransmissionManager,
                customerBrokerContractPurchaseManager,
                customerBrokerContractSaleManager
        );
    }

    @Test
    public void testStart() throws Exception {
        customerOfflinePaymentMonitorAgent.setEventManager(eventManager);
        customerOfflinePaymentMonitorAgent.setErrorManager(errorManager);
        customerOfflinePaymentMonitorAgent.setPluginDatabaseSystem(pluginDatabaseSystem);
        customerOfflinePaymentMonitorAgent.setLogManager(logManager);
        customerOfflinePaymentMonitorAgent.setPluginId(pluginId);
        customerOfflinePaymentMonitorAgent.start();


    }

    @Test
    public void testStop() throws Exception {
        customerOfflinePaymentMonitorAgent.start();
        customerOfflinePaymentMonitorAgent.stop();

    }
}
