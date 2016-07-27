package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.event_handler.CustomerOnlinePaymentRecorderServiceTest;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events.IncomingNewContractStatusUpdate;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.event_handler.CustomerOnlinePaymentRecorderService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 18/02/16.
 */
public class incomingNewContractStatusUpdateEventHandlerTest {
    @Mock
    CustomerOnlinePaymentBusinessTransactionDao customerOnlinePaymentBusinessTransactionDao;
    @Mock
    EventManager eventManager;
    @Mock
    ErrorManager errorManager;
    @Mock
    FermatEventListener mockFermatEventListener;

    IncomingNewContractStatusUpdate incomingNewContractStatusUpdate =
            new IncomingNewContractStatusUpdate(EventType.CUSTOMER_ONLINE_PAYMENT_CONFIRMED);
    CustomerOnlinePaymentRecorderService customerOnlinePaymentRecorderService;
    @Mock
    FermatEventEnum fermatEventEnum;
    EventSource eventSource = EventSource.ACTOR_ASSET_ISSUER;

    public void setUpGeneralMockitoRules() throws Exception {
        doNothing().when(customerOnlinePaymentBusinessTransactionDao).saveNewEvent(
                EventType.CUSTOMER_ONLINE_PAYMENT_CONFIRMED.getCode(),
                eventSource.getCode());
    }

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        setUpGeneralMockitoRules();
    }

    @Test
    public void incomingNewContractStatusUpdateEventHandlerTest_Should_Return_() throws Exception {
        incomingNewContractStatusUpdate.setRemoteBusinessTransaction(Plugins.CUSTOMER_ONLINE_PAYMENT);
        incomingNewContractStatusUpdate.setSource(eventSource);
        customerOnlinePaymentRecorderService = new CustomerOnlinePaymentRecorderService(
                customerOnlinePaymentBusinessTransactionDao, eventManager, errorManager);
        customerOnlinePaymentRecorderService.incomingNewContractStatusUpdateEventHandler(incomingNewContractStatusUpdate);
        verify(customerOnlinePaymentBusinessTransactionDao, times(1)).saveNewEvent(
                EventType.CUSTOMER_ONLINE_PAYMENT_CONFIRMED.getCode(), eventSource.getCode());
    }
}
