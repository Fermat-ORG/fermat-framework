package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.event_handler.CustomerOnlinePaymentRecorderServiceTest;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events.IncomingConfirmBusinessTransactionResponse;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.event_handler.CustomerOnlinePaymentRecorderService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 18/02/16.
 */
public class incomingConfirmBussinessTransactionResponseTest {
    @Mock
    CustomerOnlinePaymentBusinessTransactionDao customerOnlinePaymentBusinessTransactionDao;
    @Mock
    EventManager eventManager;
    @Mock
    ErrorManager errorManager;
    @Mock
    FermatEventListener mockFermatEventListener;
    @Mock
    IncomingConfirmBusinessTransactionResponse mockIncomingConfirmBusinessTransactionResponse;

    CustomerOnlinePaymentRecorderService customerOnlinePaymentRecorderService;
    @Mock
    FermatEventEnum fermatEventEnum;
    EventSource eventSource = EventSource.ACTOR_ASSET_ISSUER;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        setUpGeneralMockitoRules();
    }

    public void setUpGeneralMockitoRules() throws Exception {
        when(mockIncomingConfirmBusinessTransactionResponse.getRemoteBusinessTransaction()).
                thenReturn(Plugins.CUSTOMER_ONLINE_PAYMENT);
        when(mockIncomingConfirmBusinessTransactionResponse.getSource()).thenReturn(eventSource);
        doNothing().when(customerOnlinePaymentBusinessTransactionDao).saveNewEvent(
                "Test",
                eventSource.getCode());

    }

    @Test
    public void incomingConfirmBusinessTransactionResponseTest_Should_Return_() throws Exception {
        when(mockIncomingConfirmBusinessTransactionResponse.getEventType()).thenReturn(fermatEventEnum);
        when(fermatEventEnum.getCode()).thenReturn("Test");
        customerOnlinePaymentRecorderService = new CustomerOnlinePaymentRecorderService(
                customerOnlinePaymentBusinessTransactionDao, eventManager, errorManager);
        customerOnlinePaymentRecorderService.incomingConfirmBusinessTransactionResponse(
                mockIncomingConfirmBusinessTransactionResponse);
        verify(customerOnlinePaymentBusinessTransactionDao, times(1)).saveNewEvent("Test", eventSource.getCode());
    }

    //generic Exception
    @Test(expected = CantSaveEventException.class)
    public void incomingConfirmBusinessTransactionResponseTest_Should_Throw_() throws Exception {
        customerOnlinePaymentRecorderService = new CustomerOnlinePaymentRecorderService(
                customerOnlinePaymentBusinessTransactionDao, eventManager, errorManager);
        customerOnlinePaymentRecorderService.incomingConfirmBusinessTransactionResponse(
                mockIncomingConfirmBusinessTransactionResponse);
    }


}
