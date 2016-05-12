package test.com.bitduabi.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.ArtistActorNetworkServiceDao;

import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.ArtistActorNetworkServiceDao;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantConfirmConnectionRequestException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.UUID;

import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;

/**
 * Created by gianco on 04/05/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ArtistActorNetworkServiceDao.class)
public class ConfirmActorConnectionRequestTest {

    final UUID requestId = null;

    @Test
    public void confirmActorConnectionRequestTest() throws CantConfirmConnectionRequestException, ConnectionRequestNotFoundException {

        ArtistActorNetworkServiceDao artistActorNetworkServiceDao = PowerMockito.mock(ArtistActorNetworkServiceDao.class);

        doCallRealMethod().when(artistActorNetworkServiceDao).confirmActorConnectionRequest(requestId);
    }

}
