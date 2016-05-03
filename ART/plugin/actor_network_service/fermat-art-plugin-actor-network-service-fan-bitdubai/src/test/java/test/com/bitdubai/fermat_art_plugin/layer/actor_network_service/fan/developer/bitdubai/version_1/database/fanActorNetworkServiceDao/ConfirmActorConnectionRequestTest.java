package test.com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.database.fanActorNetworkServiceDao;

import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.database.FanActorNetworkServiceDao;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.exceptions.CantConfirmConnectionRequestException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.exceptions.CantFindRequestException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.UUID;

import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;

/**
 * Created by gianco on 02/05/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(FanActorNetworkServiceDao.class)
public class ConfirmActorConnectionRequestTest {

    final UUID requestId = null;


    @Test
    public void confirmActorConnectionRequestTest() throws CantConfirmConnectionRequestException, ConnectionRequestNotFoundException {

        FanActorNetworkServiceDao fanActorNetworkServiceDao = PowerMockito.mock(FanActorNetworkServiceDao.class);

        doCallRealMethod().when(fanActorNetworkServiceDao).confirmActorConnectionRequest(requestId);
    }
}
