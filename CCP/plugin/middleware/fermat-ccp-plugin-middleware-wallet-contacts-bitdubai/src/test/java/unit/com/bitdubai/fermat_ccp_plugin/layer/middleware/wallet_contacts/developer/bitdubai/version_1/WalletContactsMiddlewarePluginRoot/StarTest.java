package unit.com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;


import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import junit.framework.TestCase;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;


import java.util.UUID;


/**
 * Created by natalia on 10/09/15.
 */
public class StarTest extends TestCase
{

    /**
     * DealsWithEvents Interface member variables.
     */
    @Mock
    private EventManager mockEventManager;

    /**
     * DealsWithErrors interface Mocked
     */
    @Mock
    private ErrorManager mockErrorManager;

    /**
     * PluginDatabaseSystem Interface member variables.
     */

    @Mock
    PluginDatabaseSystem mockPluginDatabaseSystem;


    private WalletContactsMiddlewarePluginRoot pluginRoot;


    UUID pluginId;

    @Before
    public void setUp() throws Exception {

        pluginId= UUID.randomUUID();


        pluginRoot = new WalletContactsMiddlewarePluginRoot();
        pluginRoot.setErrorManager(mockErrorManager);
        pluginRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        pluginRoot.setId(pluginId);

    }



    @Test
    public void teststart_StartedOk_ThrowsCantStartPluginException() throws Exception {

        pluginRoot.start();
        ServiceStatus serviceStatus=pluginRoot.getStatus();
        Assertions.assertThat(serviceStatus).isEqualTo(ServiceStatus.STARTED);
    }

    @Test
    public void teststart_StopOk_ThrowsCantStartPluginException() throws Exception {

        pluginRoot.start();
        pluginRoot.stop();
        ServiceStatus serviceStatus=pluginRoot.getStatus();
        Assertions.assertThat(serviceStatus).isEqualTo(ServiceStatus.STOPPED);
    }

    @Test
    public void teststart_PauseOk_ThrowsCantStartPluginException() throws Exception {

        pluginRoot.start();
        pluginRoot.pause();
        ServiceStatus serviceStatus=pluginRoot.getStatus();
        Assertions.assertThat(serviceStatus).isEqualTo(ServiceStatus.PAUSED);
    }


    @Test
    public void teststart_ResumeOk_ThrowsCantStartPluginException() throws Exception {

        pluginRoot.start();
        pluginRoot.resume();
        ServiceStatus serviceStatus=pluginRoot.getStatus();
        Assertions.assertThat(serviceStatus).isEqualTo(ServiceStatus.STARTED);
    }

}
