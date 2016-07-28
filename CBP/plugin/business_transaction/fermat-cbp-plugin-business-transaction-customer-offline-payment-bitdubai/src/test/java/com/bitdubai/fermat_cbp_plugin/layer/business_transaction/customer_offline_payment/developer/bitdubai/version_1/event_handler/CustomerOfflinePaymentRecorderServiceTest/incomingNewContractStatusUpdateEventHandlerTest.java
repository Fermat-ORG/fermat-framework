package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.event_handler.CustomerOfflinePaymentRecorderServiceTest;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events.IncomingNewContractStatusUpdate;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.event_handler.CustomerOfflinePaymentRecorderService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 16/02/16.
 */
public class incomingNewContractStatusUpdateEventHandlerTest {
    @Mock
    CustomerOfflinePaymentBusinessTransactionDao customerOfflinePaymentBusinessTransactionDao;
    @Mock
    EventManager eventManager;
    @Mock
    ErrorManager errorManager;
    @Mock
    FermatEventListener mockFermatEventListener;

    IncomingNewContractStatusUpdate incomingNewContractStatusUpdate =
            new IncomingNewContractStatusUpdate(EventType.CUSTOMER_OFFLINE_PAYMENT_CONFIRMED);
    CustomerOfflinePaymentRecorderService customerOfflinePaymentRecorderService;
    @Mock
    FermatEventEnum fermatEventEnum;
    EventSource eventSource = EventSource.ACTOR_ASSET_ISSUER;

    public void setUpGeneralMockitoRules() throws Exception {
        doNothing().when(customerOfflinePaymentBusinessTransactionDao).saveNewEvent(
                EventType.CUSTOMER_OFFLINE_PAYMENT_CONFIRMED.getCode(),
                eventSource.getCode());

    }

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        setUpGeneralMockitoRules();
    }

    @Test
    public void incomingNewContractStatusUpdateEventHandlerTest() throws Exception {
        incomingNewContractStatusUpdate.setRemoteBusinessTransaction(Plugins.CUSTOMER_OFFLINE_PAYMENT);
        incomingNewContractStatusUpdate.setSource(eventSource);
        customerOfflinePaymentRecorderService = new CustomerOfflinePaymentRecorderService(
                customerOfflinePaymentBusinessTransactionDao, eventManager, errorManager);
        customerOfflinePaymentRecorderService.incomingNewContractStatusUpdateEventHandler(incomingNewContractStatusUpdate);
        verify(customerOfflinePaymentBusinessTransactionDao, times(1)).saveNewEvent(
                EventType.CUSTOMER_OFFLINE_PAYMENT_CONFIRMED.getCode(), eventSource.getCode());
    }
}
