package test.com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.database.fanActorNetworkServiceDao;

import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantListPendingConnectionRequestsException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanConnectionRequest;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.database.FanActorNetworkServiceDao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by gianco on 02/05/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(FanActorNetworkServiceDao.class)
public class ListPendingConnectionUpdatesTest {

    @Mock
    List<FanConnectionRequest> fanConnectionRequest;

    @Test
    public void listPendingConnectionUpdatesTest() throws CantListPendingConnectionRequestsException {


        FanActorNetworkServiceDao fanActorNetworkServiceDao = PowerMockito.mock(FanActorNetworkServiceDao.class);

        when(fanActorNetworkServiceDao.listPendingConnectionUpdates()).thenReturn(fanConnectionRequest);

    }

}
