package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util.EventsLauncher;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.SpecialistNotRegisteredException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util.EventsLauncher;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.EnumSet;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;


@RunWith(MockitoJUnitRunner.class)
public class SendEventsTest extends TestCase {

    @Mock
    EventManager eventManager;

    @Mock
    PlatformEvent platformEvent;

    EnumSet<Specialist> specialistEnumSet;


    EventsLauncher eventsLauncher;

    @Before
    public void setUp() throws Exception {
        eventsLauncher = new EventsLauncher();
        eventsLauncher.setEventManager(eventManager);
        doReturn(platformEvent).when(eventManager).getNewEvent(any(EventType.class));

        // add scpecialists
        specialistEnumSet = EnumSet.noneOf(Specialist.class);
        specialistEnumSet.add(Specialist.EXTRA_USER_SPECIALIST);
    }


    // test that the events are raised ok
    @Test
    public void testSendEvents_Success() throws Exception {
        eventsLauncher.sendEvents(specialistEnumSet);
    }

    // test if an specialist is not registered
    @Test(expected = SpecialistNotRegisteredException.class)
    public void testSendEvents_SpecialistNotRegisteredException() throws Exception {
        specialistEnumSet.add(Specialist.UNKNOWN_SPECIALIST);
        eventsLauncher.sendEvents(specialistEnumSet);
    }

    // TODO what happens when there is more of one specialist and one is unknown?
}
