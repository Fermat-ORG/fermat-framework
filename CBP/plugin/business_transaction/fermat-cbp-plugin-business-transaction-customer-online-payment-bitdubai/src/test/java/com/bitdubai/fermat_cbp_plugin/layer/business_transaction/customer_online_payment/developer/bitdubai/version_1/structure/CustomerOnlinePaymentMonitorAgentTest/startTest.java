package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.structure.CustomerOnlinePaymentMonitorAgentTest;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.structure.CustomerOnlinePaymentMonitorAgent;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    private CustomerOnlinePaymentMonitorAgent customerOnlinePaymentMonitorAgent;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        pluginId = UUID.randomUUID();
        customerOnlinePaymentMonitorAgent = new CustomerOnlinePaymentMonitorAgent(pluginDatabaseSystem,
                logManager,
                errorManager,
                eventManager,
                pluginId,
                transactionTransmissionManager,
                customerBrokerContractPurchaseManager,
                customerBrokerContractSaleManager,
                outgoingIntraActorManager);
    }

    @Test
    public void testStart() throws Exception {
        customerOnlinePaymentMonitorAgent.setEventManager(eventManager);
        customerOnlinePaymentMonitorAgent.setErrorManager(errorManager);
        customerOnlinePaymentMonitorAgent.setPluginDatabaseSystem(pluginDatabaseSystem);
        customerOnlinePaymentMonitorAgent.setLogManager(logManager);
        customerOnlinePaymentMonitorAgent.setPluginId(pluginId);
        customerOnlinePaymentMonitorAgent.start();


    }
    @Test
    public void testStop() throws Exception {
        customerOnlinePaymentMonitorAgent.start();
        customerOnlinePaymentMonitorAgent.stop();

    }
    @Test(expected = Exception.class)
    public void testStart_Should_Return_Exception() throws Exception {
        customerOnlinePaymentMonitorAgent = new CustomerOnlinePaymentMonitorAgent(null,null,null,null,null,null,null,null,null);
        customerOnlinePaymentMonitorAgent.start();
    }
}
