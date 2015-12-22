package unit.com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.CommunicationNetworkServiceRemoteAgent;

import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.CommunicationNetworkServiceRemoteAgent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.communications.IncomingMessageDao;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.communications.OutgoingMessageDao;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsVPNConnection;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 21/12/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class StopTest {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM,       layer = Layers.PLATFORM_SERVICE,    addon = Addons.ERROR_MANAGER)
    @Mock
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM,       layer = Layers.PLATFORM_SERVICE,    addon = Addons.EVENT_MANAGER)
    @Mock
    private EventManager eventManager;

    @Mock
    private ECCKeyPair eccKeyPair;

    @Mock
    private CommunicationsVPNConnection communicationsVPNConnection;


    @Mock
    private IncomingMessageDao incomingMessageDao;

    @Mock
    private OutgoingMessageDao outgoingMessageDao;

    private EventSource eventSource = EventSource.NETWORK_SERVICE_TRANSACTION_TRANSMISSION;

    @Mock
    private PluginVersionReference pluginVersionReference;


    @Test
    public void Stop_AgentStops_TheThreadIsStoppedInmediately() throws Exception{
        CommunicationNetworkServiceRemoteAgent communicationNetworkServiceRemoteAgent = new CommunicationNetworkServiceRemoteAgent(
                this.eccKeyPair,
                this.communicationsVPNConnection,
                this.errorManager,
                this.eventManager,
                this.incomingMessageDao,
                this.outgoingMessageDao,
                this.eventSource,
                this.pluginVersionReference
        );
        communicationNetworkServiceRemoteAgent.start();
        Thread.sleep(100);
        communicationNetworkServiceRemoteAgent.stop();
        Thread.sleep(100);
        assertThat(communicationNetworkServiceRemoteAgent.isRunning()).isFalse();
    }
}
