package unit.com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserEventRecorderService;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserEventRecorderService;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserRegistry;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.googlecode.catchexception.CatchException.catchException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

/**
 * Created by jorgegonzalez on 2015.07.02..
 */
@RunWith(MockitoJUnitRunner.class)
public class StartTest {

    @Mock
    private EventManager mockEventManager;
    @Mock
    private FermatEventListener mockFermatEventListener;

    private IncomingExtraUserRegistry testRegistry;

    private IncomingExtraUserEventRecorderService testEventRecorderService;

    @Test
    public void Start_EventManagerSetWithValidEventListener_ServiceStarted() throws Exception{
        when(mockEventManager.getNewListener(any(EventType.class))).thenReturn(mockFermatEventListener);
        testRegistry = new IncomingExtraUserRegistry();

        testEventRecorderService = new IncomingExtraUserEventRecorderService(mockEventManager, testRegistry);

        catchException(testEventRecorderService).start();
        assertThat(testEventRecorderService.getStatus()).isEqualTo(ServiceStatus.STARTED);
    }


}
