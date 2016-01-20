package unit.com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.CommunicationNetworkServiceConnectionManager;

import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 16/12/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM,       layer = Layers.PLATFORM_SERVICE,    addon = Addons.ERROR_MANAGER)
    @Mock
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM,       layer = Layers.PLATFORM_SERVICE,    addon = Addons.EVENT_MANAGER)
    @Mock
    private EventManager eventManager;

    @Mock
    private PlatformComponentProfile platformComponentProfile;

    @Mock
    private CommunicationsClientConnection communicationsClientConnection;

    @Mock
    private Database database;

    private EventSource eventSource = EventSource.NETWORK_SERVICE_TRANSACTION_TRANSMISSION;

    @Mock
    private PluginVersionReference pluginVersionReference;

    @Mock
    private AbstractNetworkService abstractNetworkService;

    @Test
    public void Construction() throws Exception{

        ECCKeyPair identity = new ECCKeyPair();
        CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager = new CommunicationNetworkServiceConnectionManager(
                this.platformComponentProfile,
                identity,
                this.communicationsClientConnection,
                this.database,
                this.errorManager,
                this.eventManager,
                this.eventSource,
                pluginVersionReference,
                abstractNetworkService
        );
        assertThat(communicationNetworkServiceConnectionManager).isNotNull();
    }
}
