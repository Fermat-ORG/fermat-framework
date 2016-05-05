package test.com.bitduabi.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.ArtistActorNetworkServiceDao;

import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantDenyConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistConnectionRequest;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.ArtistActorNetworkServiceDao;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantFindRequestException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;

/**
 * Created by gianco on 04/05/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ArtistActorNetworkServiceDao.class)
public class GetConnectionRequestTest {

    final UUID requestId = null;
    ArtistConnectionRequest artistConnectionRequest = null;

    @Test
    public void getConnectionRequestTest() throws ConnectionRequestNotFoundException, CantFindRequestException {

        ArtistActorNetworkServiceDao artistActorNetworkServiceDao = PowerMockito.mock(ArtistActorNetworkServiceDao.class);

        when(artistActorNetworkServiceDao.getConnectionRequest(requestId)).thenReturn(artistConnectionRequest);

    }

}
