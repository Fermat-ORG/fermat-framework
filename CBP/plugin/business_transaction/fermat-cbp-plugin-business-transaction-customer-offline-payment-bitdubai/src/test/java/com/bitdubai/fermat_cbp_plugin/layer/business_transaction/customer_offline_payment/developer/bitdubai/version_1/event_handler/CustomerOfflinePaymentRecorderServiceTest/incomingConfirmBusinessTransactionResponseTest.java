package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.event_handler.CustomerOfflinePaymentRecorderServiceTest;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events.IncomingConfirmBusinessTransactionResponse;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.event_handler.CustomerOfflinePaymentRecorderService;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 16/02/16.
 */
public class incomingConfirmBusinessTransactionResponseTest {
    @Mock
    CustomerOfflinePaymentBusinessTransactionDao customerOfflinePaymentBusinessTransactionDao;
    @Mock
    EventManager eventManager;
    @Mock
    ErrorManager errorManager;
    @Mock
    FermatEventListener mockFermatEventListener;
    @Mock
    IncomingConfirmBusinessTransactionResponse mockIncomingConfirmBusinessTransactionResponse;
    @Mock
    FermatEventEnum fermatEventEnum;
    EventSource eventSource = EventSource.ACTOR_ASSET_ISSUER;
    CustomerOfflinePaymentRecorderService customerOfflinePaymentRecorderService;

    public void setUpGeneralMockitoRules() throws Exception{
        when(mockIncomingConfirmBusinessTransactionResponse.getRemoteBusinessTransaction()).
                thenReturn(Plugins.CUSTOMER_OFFLINE_PAYMENT);
        when(mockIncomingConfirmBusinessTransactionResponse.getSource()).thenReturn(eventSource);
        doNothing().when(customerOfflinePaymentBusinessTransactionDao).saveNewEvent(
                "Test",eventSource.getCode());
    }
    @Before
    public void setup()throws Exception{
        MockitoAnnotations.initMocks(this);
        setUpGeneralMockitoRules();
    }
    @Test
    public void incomingConfirmBusinessTransactionResponseTest() throws Exception {
        when(mockIncomingConfirmBusinessTransactionResponse.getEventType()).thenReturn(fermatEventEnum);
        when(fermatEventEnum.getCode()).thenReturn("Test");
        customerOfflinePaymentRecorderService = new CustomerOfflinePaymentRecorderService(
                customerOfflinePaymentBusinessTransactionDao,eventManager,errorManager);
        customerOfflinePaymentRecorderService.incomingConfirmBusinessTransactionResponse(
                mockIncomingConfirmBusinessTransactionResponse);
        verify(customerOfflinePaymentBusinessTransactionDao,times(1)).saveNewEvent("Test", eventSource.getCode());

    }

    @Test(expected = CantSaveEventException.class)
    public void incomingConfirmBusinessTransactionResponseTest_Should_Throw_Exception() throws Exception {
        customerOfflinePaymentRecorderService = new CustomerOfflinePaymentRecorderService(
                customerOfflinePaymentBusinessTransactionDao,eventManager,errorManager);
        customerOfflinePaymentRecorderService.incomingConfirmBusinessTransactionResponse(
                mockIncomingConfirmBusinessTransactionResponse);
    }
}
