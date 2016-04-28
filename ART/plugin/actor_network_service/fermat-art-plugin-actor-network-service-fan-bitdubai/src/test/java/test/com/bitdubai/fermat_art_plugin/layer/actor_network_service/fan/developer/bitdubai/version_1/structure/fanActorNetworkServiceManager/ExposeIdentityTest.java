package test.com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.structure.fanActorNetworkServiceManager;


import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanExposingData;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.DeveloperBitDubai;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.FanActorNetworkServicePluginRoot;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.database.FanActorNetworkServiceDao;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.structure.FanActorNetworkServiceManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;


/**
 * Created by gianco on 25/04/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(FanActorNetworkServiceManager.class)
public class ExposeIdentityTest {



    @Test
    public void constructorTest () throws CantExposeIdentityException {

        FanActorNetworkServiceManager fanActorNetworkServiceManager = PowerMockito.mock(FanActorNetworkServiceManager.class);
        final FanExposingData fan = new FanExposingData("public key","gianco",new byte[0]);

        doCallRealMethod().when(fanActorNetworkServiceManager).exposeIdentity(fan);

    }


}
