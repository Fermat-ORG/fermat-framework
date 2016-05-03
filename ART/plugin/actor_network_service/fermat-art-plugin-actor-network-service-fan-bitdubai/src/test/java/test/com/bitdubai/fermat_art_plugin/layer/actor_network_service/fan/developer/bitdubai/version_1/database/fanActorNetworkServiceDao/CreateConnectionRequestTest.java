package test.com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.database.fanActorNetworkServiceDao;

import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.ConnectionRequestAction;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.ProtocolState;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.RequestType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanConnectionInformation;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.database.FanActorNetworkServiceDao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;

/**
 * Created by gianco on 02/05/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(FanActorNetworkServiceDao.class)
public class CreateConnectionRequestTest {

    final FanConnectionInformation fanConnectionInformation = null;
    final ProtocolState state = null           ;
    final RequestType type = null             ;
    final ConnectionRequestAction action = null;

    @Test
    public void createConnectionRequestTest() throws CantRequestConnectionException {

        FanActorNetworkServiceDao fanActorNetworkServiceDao = PowerMockito.mock(FanActorNetworkServiceDao.class);

        doCallRealMethod().when(fanActorNetworkServiceDao).createConnectionRequest(fanConnectionInformation,
                                                                                    state,
                                                                                    type,
                                                                                    action);
    }

}
