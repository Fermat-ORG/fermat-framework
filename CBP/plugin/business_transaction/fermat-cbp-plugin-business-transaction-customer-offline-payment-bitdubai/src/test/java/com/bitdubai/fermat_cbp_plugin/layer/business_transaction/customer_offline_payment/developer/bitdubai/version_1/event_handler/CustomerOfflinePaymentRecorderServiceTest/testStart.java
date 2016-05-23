package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.event_handler.CustomerOfflinePaymentRecorderServiceTest;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartServiceException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.event_handler.CustomerOfflinePaymentRecorderService;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 02/02/16.
 */
public class testStart {
    @Mock
    CustomerOfflinePaymentBusinessTransactionDao customerOfflinePaymentBusinessTransactionDao;
    @Mock
    EventManager eventManager;
    @Mock
    ErrorManager errorManager;
    @Mock
    FermatEventListener mockFermatEventListener;

    CustomerOfflinePaymentRecorderService customerOfflinePaymentRecorderService;

    public void setUpGeneralMockitoRules() throws Exception{
        when(eventManager.getNewListener(EventType.INCOMING_NEW_CONTRACT_STATUS_UPDATE)).thenReturn(mockFermatEventListener);
        when(eventManager.getNewListener(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE)).thenReturn(mockFermatEventListener);

    }
    @Before
    public void setup()throws Exception{
        MockitoAnnotations.initMocks(this);
        setUpGeneralMockitoRules();
    }

    @Test
    public void testStart() throws Exception {
        customerOfflinePaymentRecorderService = new CustomerOfflinePaymentRecorderService(
                customerOfflinePaymentBusinessTransactionDao,eventManager,errorManager);
        customerOfflinePaymentRecorderService.setEventManager(eventManager);
        customerOfflinePaymentRecorderService.start();
        assertEquals(ServiceStatus.STARTED, customerOfflinePaymentRecorderService.getStatus());
    }

    @Test(expected = CantStartServiceException.class)
    public void testStart_Should_Return_Exception() throws Exception {
        customerOfflinePaymentRecorderService = new CustomerOfflinePaymentRecorderService(null,null,errorManager);
        customerOfflinePaymentRecorderService.start();
    }

    @Test
    public void testStop_Should_Return_Stop() throws Exception {
        customerOfflinePaymentRecorderService = new CustomerOfflinePaymentRecorderService(
                customerOfflinePaymentBusinessTransactionDao,eventManager,errorManager);
        customerOfflinePaymentRecorderService.setEventManager(eventManager);
        customerOfflinePaymentRecorderService.start();
        customerOfflinePaymentRecorderService.stop();
        assertEquals(customerOfflinePaymentRecorderService.getStatus(), ServiceStatus.STOPPED);
    }

}
