package unit.com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.TransactionTransmissionAgent;

import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.TransactionTransmissionNetworkServicePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.database.TransactionTransmissionConnectionsDAO;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.database.TransactionTransmissionContractHashDao;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.TransactionTransmissionAgent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 21/12/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    @Mock
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    @Mock
    private EventManager eventManager;


    @NeededPluginReference(platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION, plugin = Plugins.WS_CLOUD_CLIENT)
    @Mock
    private WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;

    @Mock
    private TransactionTransmissionNetworkServicePluginRoot transactionTransmissionNetworkServicePluginRoot;

    @Mock
    private TransactionTransmissionConnectionsDAO transactionTransmissionConnectionsDAO;

    @Mock
    private TransactionTransmissionContractHashDao transactionTransmissionContractHashDao;

    @Mock
    private CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;

    @Mock
    private PlatformComponentProfile platformComponentProfile;

    @Mock
    private List<PlatformComponentProfile> remoteNetworkServicesRegisteredList;

    @Mock
    private ECCKeyPair eccKeyPair;

    @Test
    public void ConstructionTest()throws Exception{

        TransactionTransmissionAgent transactionTransmissionAgent = new TransactionTransmissionAgent(
                transactionTransmissionNetworkServicePluginRoot,
                transactionTransmissionConnectionsDAO,
                transactionTransmissionContractHashDao,
                communicationNetworkServiceConnectionManager,
                wsCommunicationsCloudClientManager,
                platformComponentProfile,
                errorManager,
                remoteNetworkServicesRegisteredList,
                eccKeyPair,
                eventManager
        );
        assertThat(transactionTransmissionAgent).isNotNull();
    }
}
