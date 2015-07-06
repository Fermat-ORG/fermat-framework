package unit.com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.event_handlers.IncomingCryptoTransactionsWaitingTransferenceExtraUserEventHandler;

import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.event_handlers.IncomingCryptoTransactionsWaitingTransferenceExtraUserEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserEventRecorderService;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserRegistry;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.googlecode.catchexception.CatchException.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
/**
 * Created by jorgegonzalez on 2015.07.03..
 */
public class HandleEventTest {

    @Mock
    private EventManager mockEventManager;
    @Mock
    private IncomingExtraUserRegistry mockRegistry;

    private IncomingExtraUserEventRecorderService testRecorderService;
    private IncomingCryptoTransactionsWaitingTransferenceExtraUserEventHandler testEventHandler;

    @Before
    public void setUpRecorderService(){
        testRecorderService = new IncomingExtraUserEventRecorderService(mockEventManager, mockRegistry);
    }

    @Test
    public void HandleEvent_TestRecorderNotStarted_ThrowsException() throws Exception{
        testEventHandler = new IncomingCryptoTransactionsWaitingTransferenceExtraUserEventHandler(testRecorderService);
        catchException(testEventHandler).handleEvent(null);
        assertThat(caughtException()).isInstanceOf(TransactionServiceNotStartedException.class);
    }
}
