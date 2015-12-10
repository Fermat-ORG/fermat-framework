package unit.com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.communication.event_handlers.ClientConnectionCloseNotificationEventHandler;

import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.NetworkServiceNegotiationTransmissionPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.communication.event_handlers.ClientConnectionCloseNotificationEventHandler;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Yordin Alayn on 07.12.15.
 */

@RunWith(MockitoJUnitRunner.class)
public class HandleEventTest {

    @Mock
    private EventManager mockEventManager;

    private NetworkService networkServiceRecieved;

    private ClientConnectionCloseNotificationEventHandler testEventHandler;

    @Before
    public void setUpRecorderService(){
        networkServiceRecieved = new NetworkServiceNegotiationTransmissionPluginRoot();
    }

    @Test
    public void HandleEvent_TestRecorderNotStarted_ThrowsException() throws Exception{
        testEventHandler = new ClientConnectionCloseNotificationEventHandler(networkServiceRecieved);
        catchException(testEventHandler).handleEvent(null);
        assertThat(caughtException()).isInstanceOf(TransactionServiceNotStartedException.class);
    }

}
