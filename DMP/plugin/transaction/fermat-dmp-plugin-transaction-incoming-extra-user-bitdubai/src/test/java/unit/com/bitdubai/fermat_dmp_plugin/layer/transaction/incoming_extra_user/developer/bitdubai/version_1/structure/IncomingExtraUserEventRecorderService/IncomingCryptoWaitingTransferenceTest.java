package unit.com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserEventRecorderService;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventType;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserEventRecorderService;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.googlecode.catchexception.CatchException.catchException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by jorgegonzalez on 2015.07.02..
 */
@RunWith(MockitoJUnitRunner.class)
public class IncomingCryptoWaitingTransferenceTest {

    @Mock
    private EventManager mockEventManager;
    @Mock
    private EventListener mockEventListener;

    private IncomingExtraUserRegistry testRegistry;

    private IncomingExtraUserEventRecorderService testEventRecorderService;

    @Test
    public void IncomingCryptoWaitingTransference_EventManagerSetWithValidEventListener_ServiceStarted() throws Exception{
        when(mockEventManager.getNewListener(EventType.INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE_EXTRA_USER)).thenReturn(mockEventListener);
        testRegistry = new IncomingExtraUserRegistry();

        testEventRecorderService = new IncomingExtraUserEventRecorderService(mockEventManager, testRegistry);

        catchException(testEventRecorderService).start();
        assertThat(testEventRecorderService.getStatus()).isEqualTo(ServiceStatus.STARTED);
    }


}
