package unit.com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure.NegotiationTransmissioAgent;

import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.NetworkServiceNegotiationTransmissionPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.communication.structure.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.database.NegotiationTransmissionNetworkServiceConnectionsDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.database.NegotiationTransmissionNetworkServiceDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure.NegotiationTransmissionAgent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

/**
 * Created by Yordin Alayn on 07.12.15.
 */

@RunWith(MockitoJUnitRunner.class)
public class StartTest {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    @Mock
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    @Mock
    private EventManager eventManager;

    @Mock
    private NegotiationTransmissionNetworkServiceDatabaseDao databaseDao;

    @Mock
    private NegotiationTransmissionNetworkServiceConnectionsDatabaseDao databaseConnectionsDao;

    @Mock
    private WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;

    @Mock
    private NetworkServiceNegotiationTransmissionPluginRoot pluginRoot;

    @Mock
    private PlatformComponentProfile platformComponentProfileRegistered;

    private CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;

    private NegotiationTransmissionAgent testMonitorAgent;

    @Test
    public void Start_ParametersProperlySet_ThreadStarted() throws Exception {
        ECCKeyPair identity = new ECCKeyPair();
        testMonitorAgent = new NegotiationTransmissionAgent(
                pluginRoot,
                databaseConnectionsDao,
                databaseDao,
                communicationNetworkServiceConnectionManager,
                wsCommunicationsCloudClientManager,
                platformComponentProfileRegistered,
                errorManager,
                new ArrayList<PlatformComponentProfile>(),
                identity,
                eventManager
        );

        testMonitorAgent.start();
        Thread.sleep(100);
        assertThat(testMonitorAgent.isRunning()).isTrue();
    }

    @Test
    public void Start_ParametersProperlySet_ThreadStopped() throws Exception {
        ECCKeyPair identity = new ECCKeyPair();
        testMonitorAgent = new NegotiationTransmissionAgent(
                pluginRoot,
                databaseConnectionsDao,
                databaseDao,
                communicationNetworkServiceConnectionManager,
                wsCommunicationsCloudClientManager,
                platformComponentProfileRegistered,
                errorManager,
                new ArrayList<PlatformComponentProfile>(),
                identity,
                eventManager
        );

        testMonitorAgent.start();
        Thread.sleep(100);
        testMonitorAgent.stop();
        assertThat(testMonitorAgent.isRunning()).isFalse();
    }

}
